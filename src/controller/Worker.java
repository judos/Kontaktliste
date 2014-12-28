package controller;

import java.util.ArrayList;

import model.work.Work;

public class Worker {

	public ArrayList<Work> todo;

	public Worker() {
		this.todo = new ArrayList<Work>();
	}

	public boolean doWork() {
		if (this.todo.size() > 0) {
			Work w = this.todo.get(0);
			this.todo.remove(0);
			w.doWork();
			return false;
		}
		else
			return true;
	}

}
