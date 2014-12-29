package controller.kontakte.editPerson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import model.kontakte.Bild;
import model.kontakte.Person;
import ch.judos.generic.files.FileUtils;
import controller.Kontakte;
import controller.Main;

public class BildHinzufugenListener implements ActionListener {

	private static File	currentPath	= new File(System.getProperty("user.home")
										+ "\\Desktop");
	private Kontakte	kontakte;

	public BildHinzufugenListener(Kontakte kontakte) {
		this.kontakte = kontakte;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] filetypes = { "jpg" };
		File file = FileUtils.requestFile(currentPath, "Bilddatei (.jpg)", filetypes);
		if (file == null)
			return;

		Person person = this.kontakte.getSelectedPerson();
		int bilder = person.getBilder().size();

		// test if bilder directory exists
		File bilderDir = new File(Main.dataFolder + "Bilder\\");
		if (!bilderDir.exists())
			bilderDir.mkdir();

		// copy file to bilder directory
		boolean ok =
			FileUtils.copyFile(file, new File(Main.dataFolder + "Bilder\\"
				+ person.getFullName() + " " + bilder + ".jpg"));

		try {
			if (!ok)
				throw new IOException("File could not be copied.");
			Bild bild = new Bild(person.getFullName() + " " + bilder + ".jpg");
			person.addBild(bild);
			person.preferedImage = person.getBilder().size() - 1;
			this.kontakte.refreshBild();

		} catch (IOException exception) {
			String msg = "Das Bild konnte nicht hinzugefügt werden.\n";
			JOptionPane.showMessageDialog(null, msg);
		}

	}

}
