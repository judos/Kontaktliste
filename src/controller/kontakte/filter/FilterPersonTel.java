package controller.kontakte.filter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import model.kontakte.FilterPerson;

public class FilterPersonTel implements KeyListener {

	private JTextField field;
	private FilterPerson filter;

	public FilterPersonTel(JTextField field,FilterPerson filter) {
		this.filter=filter;
		this.field=field;
	}

	public void keyPressed(KeyEvent arg0) {
		
	}

	public void keyReleased(KeyEvent arg0) {
		String tel=this.field.getText();
		this.filter.setFilterTelefon(tel);
	}

	public void keyTyped(KeyEvent arg0) {
		
	}

}
