package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.Date;
import controller.Kontakte;
import controller.Main;

public class GeburtstagListener implements KeyListener {
	
	private JTextField personGeb;
	private Kontakte kontakte;
	
	public GeburtstagListener(Kontakte kontakte, JTextField personGeb) {
		this.personGeb = personGeb;
		this.kontakte = kontakte;
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		try {
			this.kontakte.getSelectedPerson().geburtstag = Date
				.parse(this.personGeb.getText());
			this.kontakte.setGeburtstagValid(true);
		} catch (IllegalArgumentException error) {
			// this.kontakte.getSelectedPerson().geburtstag=new Date();
			this.kontakte.setGeburtstagValid(false);
		}
		Main.main.getKalender().refreshGeburtstageListe();
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
}
