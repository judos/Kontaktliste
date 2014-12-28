package controller.kontakte.chooseKontakte;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import view.kontakte.ChooseKontakte;

public class ChooseKontakteWindowListener implements WindowListener {

	private ChooseKontakte chooseKontakte;

	public ChooseKontakteWindowListener(ChooseKontakte chooseKontakte) {
		this.chooseKontakte=chooseKontakte;
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		this.chooseKontakte.dispose();
	}

	public void windowDeactivated(WindowEvent e) {
		//
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

}
