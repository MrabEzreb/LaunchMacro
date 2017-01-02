package launchmacro.ui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Configurator extends JFrame {

	public JPanel content;
	public Configurator() throws HeadlessException {
		super("LaunchMacro Configurator");
		content = new JPanel(new BorderLayout());
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

}
