package launchmacro.ui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Configurator extends JFrame {

	public JPanel content;
	public JTabbedPane tabs;
	public JPanel selector;
	public Configurator() throws HeadlessException {
		super("LaunchMacro Configurator");
		setSize(300, 300);
		content = new JPanel(new BorderLayout());
		tabs = new JTabbedPane();
//		selector = new DeviceSelector();
		setContentPane(content);
//		content.add(selector, BorderLayout.SOUTH);
		content.add(tabs, BorderLayout.CENTER);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
//		content.add(new JLabel("Configurator"), BorderLayout.NORTH);
	}

}
