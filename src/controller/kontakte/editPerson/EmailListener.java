package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.Person;
import controller.Kontakte;

public class EmailListener implements KeyListener {

	private JTextField personEmail;
	private Kontakte kontakte;

	public EmailListener(Kontakte kontakte, JTextField personEmail) {
		this.personEmail=personEmail;
		this.kontakte=kontakte;
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		Person p=this.kontakte.getSelectedPerson();
		if (p!=null) {
			p.email=this.personEmail.getText();
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
