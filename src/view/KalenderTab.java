package view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Date;
import controller.Kalender;

public class KalenderTab extends JPanel {

	private DefaultListModel<String>	personenGeburtstagListModel;

	public KalenderTab(Kalender kalender) {
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.NORTH;
		c.ipadx = 0;
		c.ipady = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		this.add(createWestPanel(), c);
		c.weightx = 1;
		c.gridx = 1;

		this.add(createCenterPanel(), c);
		c.weightx = 2;
		c.gridx = 2;
		this.add(createEastPanel(), c);

	}

	private Component createEastPanel() {
		JPanel panel = new JPanel();
		return panel;
	}

	private Component createCenterPanel() {
		return new JPanel();
	}

	private Component createWestPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5, 5, 5, 5);

		Date d = new Date();
		String heute = d.toString("l, /d/e/r d.m.Y");
		panel.add(new JLabel("Heute ist " + heute), c);
		c.gridy = 1;
		panel.add(new JLabel("Nächste Geburtstage:"), c);
		c.weighty = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		personenGeburtstagListModel = new DefaultListModel<String>();
		JScrollPane gebuPane = new JScrollPane(new JList<String>(personenGeburtstagListModel));
		panel.add(gebuPane, c);

		return panel;
	}

	public void placePersonsInList(List<String> entries) {
		this.personenGeburtstagListModel.clear();
		for (String s : entries)
			this.personenGeburtstagListModel.addElement(s);
	}

	private static final long	serialVersionUID	= -255129350791614963L;

}
