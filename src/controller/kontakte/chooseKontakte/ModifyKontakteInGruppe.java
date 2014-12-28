package controller.kontakte.chooseKontakte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.kontakte.Gruppe;
import model.kontakte.Person;
import view.kontakte.ChooseKontakte;
import controller.Kontakte;

public class ModifyKontakteInGruppe implements ActionListener {

	private Kontakte kontakte;
	private ChooseKontakte chooseKontakte;

	public ModifyKontakteInGruppe(Kontakte kontakte,
			ChooseKontakte chooseKontakte) {
		this.kontakte = kontakte;
		this.chooseKontakte = chooseKontakte;
	}

	public void actionPerformed(ActionEvent e) {
		// actioncommand is "add" or "remove" or "cancel"
		List<Person> personen = this.chooseKontakte.getSelected();
		Gruppe g = this.kontakte.getSelectedGruppe();
		if (g != null && personen != null) {
			if ("add".equals(e.getActionCommand())) {
				for (Person p : personen) {
					g.addPerson(p);
				}
				this.kontakte.personenFilter.refreshList();
			}
			if ("remove".equals(e.getActionCommand())) {
				for (Person p : personen) {
					g.removePerson(p);
				}
				this.kontakte.personenFilter.refreshList();
			}
		}
		this.chooseKontakte.dispose();
	}

}
