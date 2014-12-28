package controller.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Options;

public class ResetWindowSizeListener implements ActionListener {

	private Options options;

	public ResetWindowSizeListener(Options options) {
		this.options = options;
	}

	public void actionPerformed(ActionEvent e) {
		options.packWindow();
	}

}
