package controller.kontakte.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.kontakte.Gruppe;
import controller.Kontakte;

public class CreateGruppeListener implements ActionListener {

	private Kontakte	kontakte;

	public CreateGruppeListener(Kontakte kontakte) {
		this.kontakte = kontakte;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean ok = false;
		Gruppe g = null;
		String msg = "Hier kannst du den Namen der Gruppe eingeben:";
		int msgTyp = JOptionPane.QUESTION_MESSAGE;
		String name = "";
		do {
			name = (String) JOptionPane.showInputDialog(null, msg, "Gruppe erstellen", msgTyp,
				null, null, name);
			if (name != null) {
				g = this.kontakte.createGruppe(name);
				if (g == null) {
					msg = "Bitte folgendes Format benützen: \"Name\"";
					msgTyp = JOptionPane.ERROR_MESSAGE;
				} else {
					this.kontakte.setFilterGruppeFromListener("");
					this.kontakte.setSelectedG(g);
					ok = true;
				}
			} else
				ok = true;
		} while (!ok);
	}

}
