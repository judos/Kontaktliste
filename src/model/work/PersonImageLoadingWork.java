package model.work;

import helpers.Images;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import model.kontakte.Bild;

public class PersonImageLoadingWork implements Work {
	
	private Bild bild;
	private String uri;

	public PersonImageLoadingWork(Bild bild,String pathAndFileName) {
		this.bild=bild;
		this.uri=pathAndFileName;
	}

	public void doWork() {
		Image img = Toolkit.getDefaultToolkit().getImage(this.uri);
		ImageIcon stdIcon=new ImageIcon(Images.fitInto(img, 100, 150));
		this.bild.stdIcon=stdIcon;
	}
}