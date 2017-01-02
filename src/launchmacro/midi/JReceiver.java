package launchmacro.midi;

import java.util.ArrayList;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class JReceiver implements Receiver {

	public ArrayList<Message> messages;
	public JReceiver() {
		messages = new ArrayList<>();
	}
	
	public Message getMessage() {
		if(messages.size() == 0) {
			return null;
//			return new Message(0, 0, 0, 0);
		}
		return messages.remove(0);
	}

	@Override
	public void close() {
		
	}

	@Override
	public void send(MidiMessage msg, long timestamp) {
		if(msg instanceof ShortMessage) {
			messages.add(new Message((ShortMessage) msg));
		} else {
			System.out.println("Got wierd message");
		}
//		messages.add()
	}

}
