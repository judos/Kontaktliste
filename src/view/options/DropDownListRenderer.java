package view.options;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class DropDownListRenderer extends DefaultListCellRenderer {
	
	private static final long serialVersionUID = -7777304114202693691L;
	
	public Component getListCellRendererComponent(JList list, Object value,
		int index, boolean isSelected, boolean cellHasFocus) {
		JLabel x = (JLabel) super.getListCellRendererComponent(list, value,
			index, isSelected, cellHasFocus);
		
		if (isSelected) {
			x.setForeground(list.getSelectionForeground());
			x.setBackground(list.getSelectionBackground());
		} else {
			x.setBackground(list.getBackground());
			x.setForeground(list.getForeground());
		}
		if (value.equals("-nicht speichern-")) {
			x.setForeground(Color.LIGHT_GRAY);
		}
		
		return x;
	}
	
}
