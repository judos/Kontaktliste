package model.kontakte;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import model.work.PersonImageLoadingWork;
import model.work.Work;
import ch.judos.generic.graphics.ImageUtils;
import controller.Main;

public class Bild {
	public String			filename;

	public String			beschreibung;
	public boolean			valid;

	// saves the work which can be done later, when window is opened and user is
	// doing some mousy stuff
	private Work			todo;
	public ImageIcon		stdIcon;

	private static String	path	= Main.dataFolder + "Bilder/";

	public Bild(String filename) throws IOException {
		constructor(filename, "");
	}

	public Bild(String filename, String beschreibung) throws IOException {
		constructor(filename, beschreibung);
	}

	private void constructor(String filename, String beschreibung) throws IOException {
		this.valid = true;
		this.filename = filename;
		File f = new File(path + this.filename);
		if (!f.exists()) {
			this.valid = false;
			throw new IOException("Failed to load Image: " + filename);
		}
		this.todo = new PersonImageLoadingWork(this, path + this.filename);
		Main.worker.todo.add(this.todo);
		this.beschreibung = beschreibung;
	}

	public ImageIcon getStdIcon() {
		if (this.stdIcon == null) {
			Main.worker.todo.remove(this.todo);
			Image img = Toolkit.getDefaultToolkit().getImage(path + this.filename);
			this.stdIcon = new ImageIcon(ImageUtils.fitInto(img, 100, 150));
		}
		return this.stdIcon;
	}

	public void delete() {
		File f = new File(path + this.filename);
		f.delete();
	}
}
