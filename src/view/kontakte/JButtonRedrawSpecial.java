package view.kontakte;

import java.awt.Component;

import javax.swing.JButton;

//unused
public class JButtonRedrawSpecial extends JButton {
	
	private static final long serialVersionUID = -4139202777297059952L;
	private Component parentComponent;


	public JButtonRedrawSpecial(String title) {
		super(title);
	}

	public void setEnabled(boolean b) {
		super.setEnabled(b);
		if (this.parentComponent!=null)
			this.parentComponent.repaint();
	}

	public void setParentComponent(Component cmp) {
		this.parentComponent=cmp;
	}
}
