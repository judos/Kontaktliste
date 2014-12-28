package view.missingInfo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.kontakte.Person;
import controller.Kontakte;
import controller.Main;

public class Window extends JFrame {

	private static final long	serialVersionUID	= -7559749823460636386L;

	private static boolean		active				= false;
	private static Window		window;

	public static void deactivate() {
		if (active) {
			active = false;
			window.dispose();
		}
	}

	public static void activate(Kontakte kontakte) {
		if (!active) {
			window = new Window(kontakte);
			active = true;
		} else {
			window.setFocusable(true);
			window.requestFocus();
		}
	}

	private DefaultListModel<String>	model;

	public Window(Kontakte kontakte) {
		this.setName("Fehlende Informationen");
		this.setTitle("Fehlende Informationen");
		JPanel panel = new JPanel();
		this.add(panel);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);

		JLabel bilder = new JLabel("Bilder:");
		bilder.setFont(new Font("Arial", Font.BOLD, 20));
		bilder.setBackground(new Color(192, 220, 160));
		panel.add(bilder, c);
		c.weighty = 1;
		model = new DefaultListModel<String>();
		JList<String> list = new JList<String>(model);
		list.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128)));
		c.gridy = 1;
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setMinimumSize(new Dimension(150, 250));
		listScrollPane.setPreferredSize(new Dimension(150, 250));
		panel.add(listScrollPane, c);

		list.addMouseListener(new ListeBilderListener(kontakte, list));

		refreshList();

		pack();
		helpers.WindowH.centerWindow(this);
		setVisible(true);

		this.addWindowListener(new WindowListener());
	}

	private void refreshList() {
		this.model.removeAllElements();

		ArrayList<Person> personen = Main.data.getPersonen();
		for (Person p : personen) {
			int anz = p.getBilder().size();
			if (anz == 0) {
				this.model.addElement(p.getFullName());
			}
		}
	}

}
