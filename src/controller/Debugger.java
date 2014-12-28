package controller;


public class Debugger {
	public Debugger() {
		
	}
	
	public void debug(String msg) {
		System.err.println(msg);
	}
	public void debug(String [] msg) {
		for(int i=0;i<msg.length;i++) {
			System.err.print("["+i+"]=> "+msg[i]);
			if (i<msg.length-1) System.err.print(", ");
		}
		System.err.println();
	}

	public void debug(Object arg0) {
		System.err.println(arg0.toString());
		
	}
	
}
