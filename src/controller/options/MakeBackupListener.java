package controller.options;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ch.judos.generic.gui.Notification;
import controller.Main;

public class MakeBackupListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final JDialog dialog =
			new JDialog(new JFrame(), "Backup", ModalityType.TOOLKIT_MODAL);

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Main.backup();
				dialog.setVisible(false);
			}
		});
		t.start();

		long start = System.currentTimeMillis();

		dialog.setEnabled(false);
		dialog.setAlwaysOnTop(true);
		dialog.add(new JLabel("Bitte warten während Backup erstellt wird...",
			JLabel.CENTER));
		dialog.getContentPane().setPreferredSize(new Dimension(280, 50));
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		long end = System.currentTimeMillis();
		Notification.notifyInfo("Backup", "Backup wurde erfolgreich innert "
			+ (end - start) + "ms erstellt.");
	}

}
