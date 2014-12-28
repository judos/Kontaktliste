package controller.kontakte.editPerson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.kontakte.Bild;
import model.kontakte.Person;
import controller.Kontakte;

public class BildEntfernenListener implements ActionListener {

	private Kontakte kontakte;

	public BildEntfernenListener(Kontakte kontakte) {
		this.kontakte=kontakte;
	}

	public void actionPerformed(ActionEvent arg0) {
		Person p=this.kontakte.getSelectedPerson();
		if (p!=null) {
			ArrayList<Bild> bilder=p.getBilder();
			int index=p.preferedImage;
			Bild b=bilder.get(index);
			bilder.remove(index);
			b.delete();
			this.kontakte.refreshBild();
		}
	}

}
