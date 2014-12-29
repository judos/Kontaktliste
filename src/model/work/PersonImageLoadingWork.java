package model.work;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import model.kontakte.Bild;
import ch.judos.generic.graphics.ImageUtils;

public class PersonImageLoadingWork implements Work {

	private Bild	bild;
	private String	uri;

	public PersonImageLoadingWork(Bild bild, String pathAndFileName) {
		this.bild = bild;
		this.uri = pathAndFileName;
	}

	@Override
	public void doWork() {
		Image img = Toolkit.getDefaultToolkit().getImage(this.uri);
		ImageIcon stdIcon = new ImageIcon(ImageUtils.fitInto(img, 100, 150));
		this.bild.stdIcon = stdIcon;
	}
}