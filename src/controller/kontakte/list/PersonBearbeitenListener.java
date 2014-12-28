package controller.kontakte.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.kontakte.Person;
import controller.Kontakte;

public class PersonBearbeitenListener implements ActionListener {

	private Person		person;
	private Kontakte	kontakte;

	public PersonBearbeitenListener(Kontakte kontakte, Person person) {
		this.kontakte = kontakte;
		this.person = person;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean ok = false;
		String msg = "Hier kannst du den Namen der Person bearbeiten:";
		int msgTyp = JOptionPane.QUESTION_MESSAGE;
		do {
			String name = (String) JOptionPane.showInputDialog(null, msg,
				"Namen ändern (" + this.person.getFullName() + ")", msgTyp, null, null, this.person
					.getFullName());
			if (name != null) {
				ok = this.person.rename(name);
				if (!ok) {
					msg = "Bitte folgendes Format benützen: \"Vorname Nachname\"";
					msgTyp = JOptionPane.ERROR_MESSAGE;
				}
				if (ok)
					this.kontakte.setSelectedP(this.person);
			} else
				ok = true;
		} while (!ok);

	}
}
