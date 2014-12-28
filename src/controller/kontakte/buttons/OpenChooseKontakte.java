package controller.kontakte.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.kontakte.FilterPerson;

import view.kontakte.ChooseKontakte;
import controller.Kontakte;

public class OpenChooseKontakte implements ActionListener {

	// static call from main, for testing
	public static OpenChooseKontakte listener;

	private static ChooseKontakte addWindow;
	private static ChooseKontakte removeWindow;

	private Kontakte kontakte;

	public OpenChooseKontakte(Kontakte kontakte) {
		this.kontakte = kontakte;
		listener = this;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("add".equals(cmd)) {
			openAddWindow();
		}
		if ("remove".equals(cmd)) {
			openRemoveWindow();
		}
	}

	private void openRemoveWindow() {
		if (removeWindow != null) {
			if (removeWindow.isDisplayable())
				removeWindow.requestFocus();
			else
				removeWindow = null;
		}
		if (removeWindow == null) {
			FilterPerson filter = FilterPerson
					.createRemoveFromGruppe(this.kontakte.getSelectedGruppe());
			removeWindow=new ChooseKontakte(this.kontakte, false, filter);
		}
	}

	private void openAddWindow() {
		if (addWindow != null) {
			if (addWindow.isDisplayable())
				addWindow.requestFocus();
			else
				addWindow = null;
		}
		if (addWindow == null) {
			FilterPerson filter = FilterPerson.createAddToGruppe(this.kontakte
					.getSelectedGruppe());
			addWindow = new ChooseKontakte(this.kontakte, true, filter);
		}
	}

}
