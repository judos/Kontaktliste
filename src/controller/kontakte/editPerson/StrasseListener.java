package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.Person;
import controller.Kontakte;

public class StrasseListener implements KeyListener {

	private JTextField strasse;
	private Kontakte kontakte;

	public StrasseListener(Kontakte kontakte, JTextField personStrasse) {
		this.strasse=personStrasse;
		this.kontakte=kontakte;
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		Person p=this.kontakte.getSelectedPerson();
		if (p!=null) {
			p.strasse=this.strasse.getText();
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
