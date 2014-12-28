package controller.kontakte.list;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.kontakte.Person;
import controller.Kontakte;

public class PersonLSL implements ListSelectionListener {

	private JList<Person>	list;
	private Kontakte		kontakte;

	public PersonLSL(Kontakte kontakte, JList<Person> list) {
		this.list = list;
		this.kontakte = kontakte;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (arg0.getValueIsAdjusting()) {
			// wenn ein Eintrag markiert ist:
			if (list.getSelectedIndex() > -1) {
				Person p = list.getSelectedValue();
				this.kontakte.setSelectedP(p);
			}
		}
	}

}
