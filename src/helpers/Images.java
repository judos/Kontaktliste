package helpers;

import java.awt.Image;

public class Images {

	public static Image fitInto(Image image,int width,int height) {
		int imgwidth=image.getWidth(null);
		int imgheight=image.getHeight(null);
		
		float ratio_is=(float)imgwidth/imgheight;
		float ratio_should=(float)width/height;
		
		if (ratio_is>=ratio_should) {
			return image.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
		}
		else {
			return image.getScaledInstance(-1, height, Image.SCALE_SMOOTH);
		}
		
	}
	
	
	
}
