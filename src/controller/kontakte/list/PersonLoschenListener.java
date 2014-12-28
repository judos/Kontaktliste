package controller.kontakte.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.kontakte.Person;
import controller.Kontakte;

public class PersonLoschenListener implements ActionListener {

	private Person		person;
	private Kontakte	kontakte;

	public PersonLoschenListener(Kontakte kontakte, Person p1) {
		this.person = p1;
		this.kontakte = kontakte;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int choice = JOptionPane.showConfirmDialog(null,
			"Bist du sicher, dass du diese Person löschen willst?",
			"Person löschen (" + this.person.getFullName() + ")", JOptionPane.YES_NO_OPTION,
			JOptionPane.ERROR_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			this.kontakte.deletePerson(person);
			kontakte.refreshWindow();
		}
	}

}
