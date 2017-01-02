package launchmacro.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class DeviceIdentifier {

	public String name;
	public String description;
	public int receivers;
	public int transmitters;
	
	public DeviceIdentifier(String name, String description, int receivers, int transmitters) {
		this.name = name;
		this.description = description;
		this.receivers = receivers;
		this.transmitters = transmitters;
	}
	
	public DeviceIdentifier(String serial) {
		String[] chopped = serial.replace("[", "").replace("]", "").split(";:;");
		name = chopped[0];
		description = chopped[1];
		receivers = new Integer(chopped[2]);
		transmitters = new Integer(chopped[3]);
	}
	
	public int findDevice(MidiDevice.Info[] infos) throws MidiUnavailableException {
		for (int i = 0; i < infos.length; i++) {
			if(isDevice(infos[i])) {
				return i;
			}
		}
		return -1;
	}

	public int findDevice(MidiDevice[] infos) {
		for (int i = 0; i < infos.length; i++) {
			if(isDevice(infos[i])) {
				System.out.println("worked");
				return i;
			}
		}
		return -1;
	}
	
	public boolean isDevice(MidiDevice.Info info) throws MidiUnavailableException {
		return isDevice(MidiSystem.getMidiDevice(info));
	}
	
	public boolean isDevice(MidiDevice dev) {
//		System.out.println(dev.getDeviceInfo().getName()+this.name);
//		System.out.println(dev.getDeviceInfo().getName().matches(this.name));
		return (dev.getDeviceInfo().getName().matches(this.name)) && (dev.getDeviceInfo().getDescription().matches(this.description)) && (dev.getMaxReceivers() == this.receivers) && (dev.getMaxTransmitters() == this.transmitters);
	}
	
	@Override
	public String toString() {
		return "[" + name + ";:;" + description + ";:;" + receivers + ";:;" + transmitters + "]";
	}
}