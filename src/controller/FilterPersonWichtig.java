package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.kontakte.FilterPerson;

public class FilterPersonWichtig implements ActionListener {

	private FilterPerson filter;

	public FilterPersonWichtig(FilterPerson filter) {
		this.filter = filter;
	}

	public void actionPerformed(ActionEvent arg0) {
		boolean wichtig = Boolean.valueOf(arg0.getActionCommand());
		this.filter.setFilterWichtig(wichtig);
	}

}
