package controller.kontakte.editPerson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.kontakte.Person;
import controller.Kontakte;

public class VIPListener implements ActionListener {

	private JButton button;
	private Kontakte kontakte;

	public VIPListener(Kontakte kontakte, JButton button) {
		this.button=button;
		this.kontakte=kontakte;
	}

	public void actionPerformed(ActionEvent arg0) {
		Person p=this.kontakte.getSelectedPerson();
		if (p!=null) {
			p.wichtig=!p.wichtig;
			button.setText(p.getStatus());
		}
	}

}
