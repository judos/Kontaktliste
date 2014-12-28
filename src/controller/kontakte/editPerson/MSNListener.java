package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.Person;

import controller.Kontakte;

public class MSNListener implements KeyListener {

	private Kontakte kontakte;
	private JTextField personMSN;

	public MSNListener(Kontakte kontakte, JTextField personMSN) {
		this.kontakte=kontakte;
		this.personMSN=personMSN;
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		Person p = this.kontakte.getSelectedPerson();
		if (p != null) {
			p.msn = this.personMSN.getText();
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
