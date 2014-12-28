package controller.kontakte.editPerson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.kontakte.Person;
import controller.Kontakte;

public class ImageNavigatorListener implements ActionListener {

	private int direction;
	private Kontakte kontakte;
	
	public ImageNavigatorListener(Kontakte kontakte, int direction) {
		this.direction=direction;
		this.kontakte=kontakte;
	}

	public void actionPerformed(ActionEvent arg0) {
		Person p=this.kontakte.getSelectedPerson();
		if (p!=null) {
			p.changeImage(this.direction);
			this.kontakte.refreshBild();
		}
	}

}
