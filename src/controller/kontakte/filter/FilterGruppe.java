package controller.kontakte.filter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import controller.Kontakte;

public class FilterGruppe implements KeyListener {

	private JTextField	filterGruppe;
	private Kontakte	kontakte;

	public FilterGruppe(Kontakte kontakte, JTextField filterGruppe) {
		this.filterGruppe = filterGruppe;
		this.kontakte = kontakte;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		String filter = this.filterGruppe.getText();
		this.kontakte.setFilterGruppeFromListener(filter);

		int anz = this.kontakte.anzahlGruppenInListe();
		int code = arg0.getKeyCode();

		if (code == 10 && filter.length() > 0 && anz == 0) { // Enter gedrückt
			this.kontakte.createGruppe(filter);
			this.filterGruppe.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
