package controller;

import helpers.Basic;

import java.awt.event.KeyEvent;

import model.Savable;
import view.OptionsTab;
import view.Window;

public class Options implements Savable {

	private Window		window;
	private OptionsTab	tab;

	public Options(Window window) {
		this.window = window;
		tab = new OptionsTab(this);

		window.addTab("Optionen", tab, KeyEvent.VK_O);
	}

	@Override
	public void saveAll() {

	}

	public void packWindow() {
		this.window.setSize(725, 480);
		// this.window.pack();
	}

	public void showWindowSize() {
		Basic.notify("Fenstergrösse", this.window.getSize().toString());
	}
}
