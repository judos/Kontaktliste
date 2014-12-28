package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import controller.Kontakte;

public class TelefonListener implements KeyListener {

	private JTextField personTel;
	private Kontakte kontakte;

	public TelefonListener(Kontakte kontakte, JTextField personTel) {
		this.personTel = personTel;
		this.kontakte=kontakte;
	}

	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0) {
		this.kontakte.getSelectedPerson().tel=this.personTel.getText();
	}

	public void keyTyped(KeyEvent arg0) {

	}

}
