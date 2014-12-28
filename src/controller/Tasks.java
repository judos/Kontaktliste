package controller;

import java.awt.event.KeyEvent;

import model.Savable;
import view.TasksTab;
import view.Window;

public class Tasks implements Savable {

	private TasksTab	tab;

	public Tasks(Window window) {
		this.tab = new TasksTab(this);

		window.addTab("Tasks", tab, KeyEvent.VK_T);
	}

	@Override
	public void saveAll() {

	}

}
