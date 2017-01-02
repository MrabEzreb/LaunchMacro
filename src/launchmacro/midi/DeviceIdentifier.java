package launchmacro.midi;

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
	
	@Override
	public String toString() {
		return "[" + name + ";:;" + description + ";:;" + receivers + ";:;" + transmitters + "]";
	}
}