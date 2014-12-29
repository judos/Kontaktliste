package controller.kontakte.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.kontakte.Person;
import ch.judos.generic.data.StringUtils;
import controller.Kontakte;

public class CreatePersonListener implements ActionListener {

	private Kontakte	kontakte;

	public CreatePersonListener(Kontakte kontakte) {
		this.kontakte = kontakte;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean ok = false;
		Person p = null;
		String msg = "Gib hier \"Vorname Nachname\" ein:";
		int msgTyp = JOptionPane.QUESTION_MESSAGE;
		String name = "";
		do {
			name = (String) JOptionPane.showInputDialog(null, msg, "Kontakt erstellen", msgTyp,
				null, null, name);
			if (name != null) {
				int l1 = name.length();
				name = StringUtils.replaceAll(name,
					new String[] { "\\", "/", ":", "|", "*", "?", "\"", "<", ">" }, "");
				if (l1 != name.length()) {
					msg = "Keine dieser Sonderzeichen sind erlaubt \\ | / : * ? < >";
					msgTyp = JOptionPane.ERROR_MESSAGE;
				} else if (this.kontakte.personExists(name)) {
					msg = "Es existiert bereits eine Person mit diesem Namen.";
					msgTyp = JOptionPane.ERROR_MESSAGE;
				} else {
					p = this.kontakte.createPerson(name, false);
					if (p == null) {
						msg = "Bitte folgendes Format benützen: \"Vorname Nachname\"";
						msgTyp = JOptionPane.ERROR_MESSAGE;
					} else {
						this.kontakte.personenFilter.setFilterName("");
						this.kontakte.personenFilter.setFilterTelefon("");
						this.kontakte.personenFilter.setFilterWichtig(false);
						this.kontakte.setSelectedP(p);
						ok = true;
					}
				}
			} else
				ok = true;
		} while (!ok);

	}
}
