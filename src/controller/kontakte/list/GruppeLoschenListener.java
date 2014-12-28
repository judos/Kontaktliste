package controller.kontakte.list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.kontakte.Gruppe;
import controller.Kontakte;

public class GruppeLoschenListener implements ActionListener {

	private Kontakte	kontakte;
	private Gruppe		gruppe;

	public GruppeLoschenListener(Kontakte kontakte, Gruppe g) {
		this.kontakte = kontakte;
		this.gruppe = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null,
			"Bist du sicher, dass du diese Gruppe löschen willst?",
			"Gruppe löschen (" + this.gruppe.getName() + ")", JOptionPane.YES_NO_OPTION,
			JOptionPane.ERROR_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			this.kontakte.deleteGruppe(this.gruppe);
			this.kontakte.setSelectedG(null);
		}
	}

}
