package view.missingInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Kontakte;

public class ButtonListener implements ActionListener {
	

	private Kontakte kontakte;

	public ButtonListener(Kontakte kontakte) {
		this.kontakte=kontakte;
	}

	public void actionPerformed(ActionEvent arg0) {
		Window.activate(this.kontakte);
	}

}
