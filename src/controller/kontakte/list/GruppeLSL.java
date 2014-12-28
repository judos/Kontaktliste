package controller.kontakte.list;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.kontakte.FilterPerson;
import model.kontakte.Gruppe;
import controller.Kontakte;
import controller.Main;

public class GruppeLSL implements ListSelectionListener {

	private JList<String>	list;
	private Kontakte		kontakte;
	private boolean			actionPerforming	= false;
	private FilterPerson	filter;

	public GruppeLSL(Kontakte kontakte, JList<String> list, FilterPerson filter) {
		this.filter = filter;
		this.list = list;
		this.kontakte = kontakte;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (arg0.getValueIsAdjusting()) {
			if (!actionPerforming) {
				actionPerforming = true;
				if (list.getSelectedIndex() > -1) {
					String x = list.getSelectedValue();

					Gruppe g = Main.data.searchGruppe(x);
					this.filter.setFilterGruppe(g);
					this.kontakte.setSelectedG(g);
				}
				actionPerforming = false;
			}
		}
	}

}
