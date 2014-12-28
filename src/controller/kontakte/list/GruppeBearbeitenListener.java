package controller.kontakte.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.kontakte.Gruppe;
import controller.Kontakte;

public class GruppeBearbeitenListener implements ActionListener {

	private Kontakte	kontakte;
	private Gruppe		gruppe;

	public GruppeBearbeitenListener(Kontakte kontakte, Gruppe g) {
		this.kontakte = kontakte;
		this.gruppe = g;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		boolean ok = false;
		String msg = "Hier kannst du den Namen der Gruppe ändern:";
		int msgTyp = JOptionPane.QUESTION_MESSAGE;
		do {
			String name = (String) JOptionPane.showInputDialog(null, msg,
				"Namen ändern (" + this.gruppe.getName() + ")", msgTyp, null, null, this.gruppe
					.getName());
			if (name != null) {
				ok = this.gruppe.rename(name);
				if (!ok) {
					msg = "Bitte folgendes Format benützen: \"Vorname Nachname\"";
					msgTyp = JOptionPane.ERROR_MESSAGE;
				}
				if (ok)
					this.kontakte.setSelectedG(this.gruppe);
			} else
				ok = true;
		} while (!ok);
	}

}
