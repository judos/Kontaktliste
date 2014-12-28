package controller.archivP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import controller.ArchivP;

public class SucheListener implements KeyListener {

	private ArchivP arc;
	private JTextField suche;

	public SucheListener(ArchivP arc, JTextField suche) {
		this.arc=arc;
		this.suche=suche;
	}

	public void keyReleased(KeyEvent e) {
		this.arc.setSuche(suche.getText());	
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
