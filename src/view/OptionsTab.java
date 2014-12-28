package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Options;
import controller.options.MakeBackupListener;
import controller.options.ResetWindowSizeListener;
import controller.options.ShowWindowSizeListener;
import controller.options.importcsv.ImportDialogButtonListener;

public class OptionsTab extends JPanel {

	private static final long	serialVersionUID	= 7858937486640783817L;
	private Options				options;

	public OptionsTab(Options options) {
		super();
		this.options = options;

		this.setLayout(new GridBagLayout());
		createContent();

	}

	private void createContent() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;

		// Button to resize Window
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(5, 5, 5, 5);
		JButton packWindow = new JButton("Ursprüngliche Fenstergrösse");
		packWindow.addActionListener(new ResetWindowSizeListener(options));
		add(packWindow, c);

		c.gridy = 1;
		JButton showSize = new JButton("Fenstergrösse anzeigen");
		showSize.addActionListener(new ShowWindowSizeListener(options));
		add(showSize, c);

		c.gridy = 2;
		JButton makeBackup = new JButton("BackUp machen");
		makeBackup.addActionListener(new MakeBackupListener());
		add(makeBackup, c);

		c.gridy = 3;
		JButton importieren = new JButton("CSV-Datei Importieren");
		importieren.addActionListener(new ImportDialogButtonListener());
		add(importieren, c);

	}

}
