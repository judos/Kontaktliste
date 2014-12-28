package view.missingInfo;

import java.awt.event.WindowEvent;

public class WindowListener implements java.awt.event.WindowListener {

	public void windowActivated(WindowEvent e) {
		
	}

	public void windowClosed(WindowEvent e) {
		Window.deactivate();
	}

	public void windowClosing(WindowEvent e) {
		Window.deactivate();
	}

	public void windowDeactivated(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}

	public void windowIconified(WindowEvent e) {
		
	}

	public void windowOpened(WindowEvent e) {
		
	}

}
