package launchmacro.midi;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

public class Message {

	private int command;
	private int channel;
	private int data1;
	private int data2;
	private int[] data;
	public Message(int command, int channel, int data1, int data2) {
		this.command = command;
		this.channel = channel;
		this.data1 = data1;
		this.data2 = data2;
		this.data = new int[] {data1, data2};
	}
	
	public Message(int command, int channel, int[] data) {
		this.command = command;
		this.channel = channel;
		this.data = data;
		data1 = data[0];
		data2 = data[1];
	}
	
	public Message(ShortMessage msg) {
		this(msg.getCommand(), msg.getChannel(), msg.getData1(), msg.getData2());
	}
	
	public Message(SysexMessage msg) {
		int[] datas = new int[msg.getData().length];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = msg.getData()[i];
		}
		command = SysexMessage.SYSTEM_EXCLUSIVE;
		channel = 0;
		data = datas;
		data1 = data[0];
		data2 = data[1];
	}
	
	public Message(String str) {
		String[] chopped = str.replace("(", "").replace(")", "").split(";");
		command = new Integer(chopped[0]);
		channel = new Integer(chopped[1]);
		ArrayList<Integer> datas = new ArrayList<>();
		for (int i = 2; i < chopped.length; i++) {
			datas.add(new Integer(chopped[i]));
		}
		Integer[] datas2 = datas.toArray(new Integer[0]);
		this.data = new int[datas2.length];
		for (int i = 0; i < datas2.length; i++) {
			data[i] = datas2[i];
		}
		data1 = data[0];
		data2 = data[1];
	}
	
	public void send(Receiver r) throws InvalidMidiDataException {
		byte[] bytes = new byte[1 + data.length];
		bytes[0] = (byte) command;
		for (int i = 1; i < bytes.length; i++) {
			bytes[i] = (byte) data[i-1];
		}
		MidiMessage mm = null;
		if(command == SysexMessage.SYSTEM_EXCLUSIVE) {
			for (byte b : bytes) {
				System.out.print(String.format("%02X ", b));
			}
			mm = new SysexMessage(bytes, bytes.length);
		} else {
			mm = new ShortMessage(command, channel, data1, data2);
		}
		r.send(mm, -1);
	}
	
	@Override
	public String toString() {
		String datas = "";
		for (int i : data) {
			datas += ";" + i;
		}
		return "(" + command + ";" + channel + datas + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		Message mobj = (Message) obj;
		System.out.println("Comparing " + toString() + " and " + mobj.toString());
		return (mobj.command == command) && (mobj.channel == channel) && (mobj.data1 == data1) && (mobj.data2 == data2);
	}

}
