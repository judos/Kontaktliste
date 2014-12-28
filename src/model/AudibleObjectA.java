package model;

import java.util.ArrayList;
import java.util.List;

public abstract class AudibleObjectA {
	
	private List<MyActionListenerI> listeners;
	
	public AudibleObjectA() {
		this.listeners=new ArrayList<MyActionListenerI>();
	}
	
	public void addActionListener(MyActionListenerI l) {
		this.listeners.add(l);
	}
	
	public void removeActionListener(MyActionListenerI l) {
		this.listeners.remove(l);
	}
	
	protected void notifyListeners(String msg,Object value) {
		for(MyActionListenerI l : this.listeners) {
			l.actionPerformed(this, msg, value);
		}
	}
	
	
	
}
