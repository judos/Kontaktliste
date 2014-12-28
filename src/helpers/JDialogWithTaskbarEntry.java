package helpers;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class JDialogWithTaskbarEntry extends JDialog {
	
	private static final long serialVersionUID = -8635486826953215847L;
	
	public JDialogWithTaskbarEntry(String title) {
		super(new DummyFrame(title));
	}
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (!visible) {
			((DummyFrame) getParent()).dispose();
		}
	}
	
	static class DummyFrame extends JFrame {
		private static final long serialVersionUID = 6035300590783149332L;
		
		DummyFrame(String title) {
			super(title);
			setUndecorated(true);
			setVisible(true);
			setLocationRelativeTo(null);
		}
	}
	
}
