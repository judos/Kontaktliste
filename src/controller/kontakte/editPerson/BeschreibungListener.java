package controller.kontakte.editPerson;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import model.kontakte.Gruppe;
import model.kontakte.Person;
import view.KontaktTab.aktuelleAuswahl;
import controller.Kontakte;
import controller.Main;

public class BeschreibungListener implements KeyListener {

	private JTextArea		beschreibung;
	private Kontakte		kontakte;
	private aktuelleAuswahl	typ;

	public BeschreibungListener(Kontakte kontakte, JTextArea beschreibung,
			aktuelleAuswahl erzeugtvon) {
		this.beschreibung = beschreibung;
		this.kontakte = kontakte;
		this.typ = erzeugtvon;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (typ.equals(aktuelleAuswahl.Person)) {
			Person p = this.kontakte.getSelectedPerson();
			if (p != null)
				p.beschreibung = this.beschreibung.getText();
			else
				Main.debugger
					.debug("Beschreibung geändert, BeschreibungsListener Typ=Person, keine Person ausgewählt.");
		}
		if (typ.equals(aktuelleAuswahl.Gruppe)) {
			Gruppe p = this.kontakte.getSelectedGruppe();
			if (p != null)
				p.beschreibung = this.beschreibung.getText();
			else
				Main.debugger
					.debug("Beschreibung geändert, BeschreibungsListener Typ=Gruppe, keine Person ausgewählt.");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
