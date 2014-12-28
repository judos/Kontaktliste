package controller.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Options;

public class ShowWindowSizeListener implements ActionListener {

	private Options	options;

	public ShowWindowSizeListener(Options options) {
		this.options = options;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.options.showWindowSize();
	}

}
