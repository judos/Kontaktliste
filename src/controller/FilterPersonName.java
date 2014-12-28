package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.FilterPerson;

public class FilterPersonName implements KeyListener {

	private FilterPerson filter;
	private JTextField field;

	public FilterPersonName(JTextField field,FilterPerson filter) {
		this.filter=filter;
		this.field=field;
	}

	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent arg0) {
		String name = this.field.getText();
		this.filter.setFilterName(name);
	}

	public void keyTyped(KeyEvent arg0) {

	}

}
