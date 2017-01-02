package launchmacro;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;

import launchmacro.config.Mode;
import launchmacro.config.Trigger;
import launchmacro.file.DataStore;
import launchmacro.macro.Keyboard;
import launchmacro.midi.DeviceIdentifier;
import launchmacro.midi.JReceiver;
import launchmacro.midi.Message;
import launchmacro.midi.MultiMessage;
import launchmacro.ui.Configurator;

public class Main {

	public static Configurator ui;
	
	public static ArrayList<Mode> modes;
	public static HashMap<Integer, Receiver> modesRh;
	public static HashMap<Integer, Transmitter> modesTh;
	public static ArrayList<Receiver> modesR;
	public static ArrayList<Transmitter> modesT;
	public static int activated = -1;
	
	public Main() {
	}
	
	public static void main(String[] args) throws InterruptedException, IOException, AWTException, MidiUnavailableException, InvalidMidiDataException {
		createTrayIcon();
		Runtime.getRuntime().addShutdownHook(deactivateMode);
		ui = new Configurator();
		MidiDevice[] mda = getCorrectDevices();
		createModesTest();
		midiLoop();
	}
	
	public static MidiDevice[] getCorrectDevices() throws MidiUnavailableException {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		int chosen = 0;
		int chosen2 = 0;
		for (int i = 0; i < infos.length; i++) {
			if(infos[i].getName().equals("Launchpad MK2")) {
				MidiDevice md = MidiSystem.getMidiDevice(infos[i]);
				if(md.getMaxReceivers() == -1) {
					chosen2 = i;
				} else {
					chosen = i;
				}
			}
		}
		return new MidiDevice[] {MidiSystem.getMidiDevice(infos[chosen]), MidiSystem.getMidiDevice(infos[chosen2])};
	}
	
	public static void createModesTest() {
		modes = new ArrayList<>();
		modesRh = new HashMap<>();
		modesTh = new HashMap<>();
		DeviceIdentifier launchMk2i = new DeviceIdentifier("Launchpad MK2", ".*", 0, -1);
		DeviceIdentifier launchMk2o = new DeviceIdentifier("Launchpad MK2", ".*", -1, 0);
		Trigger user1T = new Trigger(new Message(ShortMessage.CONTROL_CHANGE, 0, 109, 0));
		Trigger user2T = new Trigger(new Message(ShortMessage.CONTROL_CHANGE, 0, 110, 0));
		Mode mode1 = new Mode("User 1", launchMk2i, launchMk2o, user1T, new MultiMessage(new Message(SysexMessage.SYSTEM_EXCLUSIVE, 0, new int[] {0, 32, 41, 2, 24, 11, 109, 63, 63, 63, 247})), new MultiMessage(new Message(SysexMessage.SYSTEM_EXCLUSIVE, 0, new int[] {0, 32, 41, 2, 24, 11, 109, 0, 0, 0, 247})), new HashMap<>());
		Mode mode2 = new Mode("User 2", launchMk2i, launchMk2o, user2T, new MultiMessage(new Message(SysexMessage.SYSTEM_EXCLUSIVE, 0, new int[] {0, 32, 41, 2, 24, 11, 110, 63, 63, 63, 247})), new MultiMessage(new Message(SysexMessage.SYSTEM_EXCLUSIVE, 0, new int[] {0, 32, 41, 2, 24, 11, 110, 0, 0, 0, 247})), new HashMap<>());
		modes.add(mode1);
		modes.add(mode2);
	}
	
	public static MidiDevice[] getChosenDevice() throws MidiUnavailableException {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		Scanner s = new Scanner(System.in);
		for (int i = 0; i < infos.length; i++) {
			System.out.println(i + ": " + infos[i].getName());
			System.out.println("\t" + infos[i].getDescription());
			MidiDevice m2 = MidiSystem.getMidiDevice(infos[i]);
			m2.open();
			System.out.println("\tReceivers: " + m2.getMaxReceivers());
			System.out.println("\tTransmitters: " + m2.getMaxTransmitters());
			m2.close();
			System.out.println();
		}
		int chosen = new Integer(s.nextLine());
		int chosen2 = new Integer(s.nextLine());
		s.close();
		return new MidiDevice[] {MidiSystem.getMidiDevice(infos[chosen]), MidiSystem.getMidiDevice(infos[chosen2])};
	}
	
	public static Thread deactivateMode = new Thread(new Runnable() {
		
		@Override
		public void run() {
			if(activated > -1) {
				try {
					Mode mode = modes.get(activated);
					Receiver r = modesRh.get(activated);
					mode.deactivate(r);
				} catch (InvalidMidiDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});
	
	public static void midiLoop() throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
		JReceiver jr = new JReceiver();
		for (int i = 0; i < modes.size(); i++) {
			Mode mode = modes.get(i);
			MidiDevice.Info[] dia = MidiSystem.getMidiDeviceInfo();
			MidiDevice devi = MidiSystem.getMidiDevice(dia[mode.iDID().findDevice(dia)]);
			MidiDevice devo = MidiSystem.getMidiDevice(dia[mode.oDID().findDevice(dia)]);
			devi.open();
			Transmitter t = devi.getTransmitter();
			modesTh.put(i, t);
//			modesT.set(i, t);
			devo.open();
			Receiver r = devo.getReceiver();
			modesRh.put(i, r);
//			modesR.set(i, r);
			t.setReceiver(jr);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					devi.close();
					devo.close();
				}
			}));
		}
		while(true) {
			Message m1 = jr.getMessage();
			if(m1 != null) {
				System.out.println(m1.toString());
				for (int i = 0; i < modes.size(); i++) {
					Mode mode = modes.get(i);
					if(mode.checkActivation(m1)) {
						if(activated > -1) {
							modes.get(activated).deactivate(modesRh.get(activated));
						}
						activated = i;
						mode.activate(modesRh.get(activated));
					}
				}
			}
			Thread.sleep(100);
		}
	}
	
	public static void midiLoop(MidiDevice devi, MidiDevice devo) throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
		devi.open();
		Transmitter t = devi.getTransmitter();
		JReceiver jr = new JReceiver();
		devo.open();
		Receiver r = devo.getReceiver();
		t.setReceiver(jr);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				devi.close();
				devo.close();
			}
		}));
		while(true) {
			Message m1 = jr.getMessage();
			if(m1 != null) {
				System.out.println(m1.toString());
				for (int i = 0; i < modes.size(); i++) {
					Mode mode = modes.get(i);
					if(mode.checkActivation(m1)) {
						if(activated > -1) {
							modes.get(activated).deactivate(r);
						}
						mode.activate(r);
						activated = i;
					}
				}
			}
			Thread.sleep(100);
		}
	}
	
	public static void createTrayIcon() throws IOException {
		PopupMenu popup = new PopupMenu();
		InputStream is = Main.class.getResourceAsStream("/trayIcon.png");
		TrayIcon trayIcon = new TrayIcon(ImageIO.read(is));
		is.close();
		SystemTray tray = SystemTray.getSystemTray();
		MenuItem config = new MenuItem("Configurator");
		config.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ui.setVisible(true);
			}
		});
		CheckboxMenuItem enabled = new CheckboxMenuItem("Enabled");
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});
		popup.add(enabled);
		popup.add(config);
		popup.addSeparator();
		popup.add(exitItem);
		trayIcon.setPopupMenu(popup);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private static void testDataStore() throws AWTException, InterruptedException {
		Thread.sleep(5000);
		Keyboard keyboard = new Keyboard();
		keyboard.type(" h\f e\f l\f l\f o\f  \f w\f o\f r\f l\f d\f !");
		Thread.sleep(15000);
		
		HashMap<String, byte[]> map = new HashMap<>();
		map.put("helloworld.txt", "Hello, World!".getBytes());
		map.put("hellohumans.esperanto", "Saluton Homoj".getBytes());
		Properties info = new Properties();
		info.setProperty("Language1", "English");
		info.setProperty("Language2", "Esperanto");
		DataStore ds = new DataStore("hellos", info, map);
		try {
			DataStore.saveStore(new File("test3.zip"), ds);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DataStore ds2 = DataStore.loadStore(new File("test3.zip"));
		try {
			DataStore.saveStore(new File("test3ds2.zip"), ds2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
