package launchmacro.config;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;

import launchmacro.file.DataStore;
import launchmacro.midi.DeviceIdentifier;
import launchmacro.midi.Message;
import launchmacro.midi.MultiMessage;

public class Mode {

	private String name;
	private Properties props;
	private HashMap<Trigger, Action> connections;
	public Mode(String name, DeviceIdentifier deviceInput, DeviceIdentifier deviceOutput, Trigger activation, MultiMessage back, MultiMessage done, HashMap<Trigger, Action> _connections) {
		this.name = name;
		props = new Properties();
		props.setProperty("trigger", activation.toString());
		props.setProperty("back", back.toString());
		props.setProperty("done", done.toString());
		props.setProperty("devicei", deviceInput.toString());
		props.setProperty("deviceo", deviceOutput.toString());
		connections = _connections;
	}
	public Mode(DataStore store) {
		name = store.identifier;
		props = store.info;
		Set<String> keys = store.stores.keySet();
		connections = new HashMap<>();
		for (String string : keys) {
			connections.put(new Trigger(string), new Action(new String(store.stores.get(string))));
		}
	}
	
	public DeviceIdentifier iDID() {
		return new DeviceIdentifier(props.getProperty("devicei"));
	}
	
	public DeviceIdentifier oDID() {
		return new DeviceIdentifier(props.getProperty("deviceo"));
	}
	
	public void activate(Receiver r) throws InvalidMidiDataException {
		System.out.println("Activating " + name);
		MultiMessage dogo = new MultiMessage(props.getProperty("back"));
		dogo.sendAll(r);
	}
	
	public void deactivate(Receiver r) throws InvalidMidiDataException {
		MultiMessage dogo = new MultiMessage(props.getProperty("done"));
		dogo.sendAll(r);
	}
	
	public boolean checkActivation(Message m) {
		return new Trigger(props.getProperty("trigger")).checkTrigger(m);
	}
	
	public DataStore toStore() {
		HashMap<String, byte[]> stores = new HashMap<>();
		Set<Trigger> trigs = connections.keySet();
		for (Trigger trigger : trigs) {
			stores.put(trigger.toString(), connections.get(trigger).toString().getBytes());
		}
		return new DataStore(name, props, stores);
	}

}
