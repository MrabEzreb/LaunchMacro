package launchmacro.config;

import launchmacro.midi.Message;

public class Trigger {

	private Message m;
	public Trigger(Message m) {
		this.m = m;
	}
	
	public Trigger(String message) {
		m = new Message(message);
	}
	
	public boolean checkTrigger(Message m) {
		return m.equals(this.m);
	}
	
	@Override
	public String toString() {
		return m.toString();
	}

}
