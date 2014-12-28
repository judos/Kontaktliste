package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.Person;

import controller.Kontakte;

public class LandListener implements KeyListener {

	private Kontakte kontakte;
	private JTextField personLand;

	public LandListener(Kontakte kontakte, JTextField personLand) {
		this.kontakte=kontakte;
		this.personLand=personLand;
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		Person p = this.kontakte.getSelectedPerson();
		if (p != null) {
			p.land = this.personLand.getText();
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
