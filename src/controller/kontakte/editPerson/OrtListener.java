package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.Person;
import controller.Kontakte;

public class OrtListener implements KeyListener {

	private JTextField ort;
	private Kontakte kontakte;

	public OrtListener(Kontakte kontakte, JTextField personOrt) {
		this.ort=personOrt;
		this.kontakte=kontakte;
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		Person p = this.kontakte.getSelectedPerson();
		if (p != null) {
			p.ort = this.ort.getText();
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
