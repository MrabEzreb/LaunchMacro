package launchmacro.midi;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;

public class MultiMessage {

	private ArrayList<Message> messages;
	public MultiMessage(ArrayList<Message> _messages) {
		messages = _messages;
	}
	
	public MultiMessage(Message... msgs) {
		messages = new ArrayList<>();
		for (Message message : msgs) {
			messages.add(message);
		}
	}
	
	public MultiMessage(String str) {
		String[] strs = str.replace("{", "").replace("}", "").split(",");
		messages = new ArrayList<>();
		for (String string : strs) {
			messages.add(new Message(string));
		}
	}
	
	public void sendAll(Receiver r) throws InvalidMidiDataException {
		for (Message message : messages) {
			message.send(r);
		}
	}
	
	@Override
	public String toString() {
		String ret = "{";
		for (Message message : messages) {
			ret += message.toString() + ",";
		}
		ret = ret.substring(0, ret.lastIndexOf(","));
		ret += "}";
		return ret;
	}

}
