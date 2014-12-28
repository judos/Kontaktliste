package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.Person;

import controller.Kontakte;

public class PLZListener implements KeyListener {

	private Kontakte kontakte;
	private JTextField personPLZ;

	public PLZListener(Kontakte kontakte, JTextField personPLZ) {
		this.kontakte=kontakte;
		this.personPLZ=personPLZ;
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		Person p=this.kontakte.getSelectedPerson();
		if (p!=null) {
			p.plz=this.personPLZ.getText();
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
