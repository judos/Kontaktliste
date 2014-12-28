package controller.options.importcsv;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImportDialogButtonListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		new ImportController();
	}
	
}
