package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.Person;
import controller.Kontakte;

public class TelefonHomeListener implements KeyListener {

	private JTextField personTelHome;
	private Kontakte kontakte;

	public TelefonHomeListener(Kontakte kontakte, JTextField personTelHome) {
		this.personTelHome = personTelHome;
		this.kontakte=kontakte;
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0) {
		Person p = this.kontakte.getSelectedPerson();
		if (p != null) {
			p.homeTel = this.personTelHome.getText();
		}
	}

	public void keyTyped(KeyEvent arg0) {

	}

}
