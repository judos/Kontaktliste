package view.missingInfo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

import model.kontakte.Person;
import controller.Kontakte;
import controller.Main;

public class ListeBilderListener implements MouseListener {

	private JList<String>	list;
	private Kontakte		kontakte;

	public ListeBilderListener(Kontakte kontakte, JList<String> list) {
		this.list = list;
		this.kontakte = kontakte;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == 1 && arg0.getClickCount() == 2) {
			String person = list.getSelectedValue();
			Person p = Main.data.searchPerson(person);
			if (p != null) {
				Window.deactivate();
				if (!p.wichtig)
					this.kontakte.personenFilter.setFilterWichtig(false);

				this.kontakte.selectListEntry(p);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
