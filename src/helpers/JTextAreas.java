package helpers;

import java.awt.KeyboardFocusManager;

import javax.swing.JTextArea;

public class JTextAreas {

	public static void setTabChangesFocus(JTextArea component) {
		component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		component.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
	}
}
