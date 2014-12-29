package view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import controller.Main;

public class Images {

	public static String	imagesFolder	= "Layout/";

	public static ImageIcon	arrowLeft;
	public static ImageIcon	arrowRight;
	public static Image		windowIcon;
	public static Image		anonymousIcon;

	public static void load() {
		String path = Main.softwareFolder + imagesFolder;

		arrowLeft = new ImageIcon(path + "arrowLeft.png");
		arrowRight = new ImageIcon(path + "arrowRight.png");
		windowIcon = Toolkit.getDefaultToolkit().createImage(path + "icon.png");
		anonymousIcon = Toolkit.getDefaultToolkit().createImage(path + "anonymous.jpg");
	}

}
