package view.kontakte;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import model.kontakte.FilterPerson;
import model.kontakte.Person;
import view.Window;
import controller.FilterPersonName;
import controller.FilterPersonWichtig;
import controller.Kontakte;
import controller.Main;
import controller.kontakte.chooseKontakte.ChooseKontakteWindowListener;
import controller.kontakte.chooseKontakte.ModifyKontakteInGruppe;

public class ChooseKontakte extends JDialog {

	private static final long			serialVersionUID	= -1961607832316005554L;
	private Kontakte					kontakte;
	private DefaultListModel<Person>	listModel;
	private JList<Person>				list;
	private FilterPerson				filter;
	private boolean						commandAdd;

	public ChooseKontakte(Kontakte kontakte, boolean commandAdd, FilterPerson filter) {
		super(kontakte.getWindow());

		this.commandAdd = commandAdd;
		this.kontakte = kontakte;
		this.filter = filter;
		this.filter.autoUpdateList = true;

		setModalityType(JDialog.ModalityType.MODELESS);
		this.add(createContent());
		this.addWindowListener(new ChooseKontakteWindowListener(this));
		String title = "Hinzufügen";
		if (!commandAdd)
			title = "Entfernen";

		setTitle(title + " (" + this.kontakte.getSelectedGruppe() + ")");
		pack();
		setLocationRelativeTo(kontakte.getWindow());
		setVisible(true);
	}

	public ChooseKontakte(boolean commandAdd, FilterPerson filter) {
		super();
		this.commandAdd = commandAdd;
		this.filter = filter;
		this.filter.autoUpdateList = true;

		setModalityType(JDialog.ModalityType.MODELESS);
		this.add(createContent());
		this.addWindowListener(new ChooseKontakteWindowListener(this));
		String title = "Hinzufügen";
		if (!commandAdd)
			title = "Entfernen";

		setTitle(title);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private Component createContent() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.weightx = 1;
		c.weighty = 0;

		c.gridx = 0;
		c.gridy = 0;
		panel.add(createFilterButtons(), c);

		c.fill = GridBagConstraints.BOTH;
		c.gridy = 1;
		c.weighty = 1;
		panel.add(createListContent(), c);

		c.gridy = 2;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(createButtons(), c);

		return panel;
	}

	private Component createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.ipadx = 5;
		c.gridx = 0;
		c.weightx = 1;
		c.insets = new Insets(2, 2, 2, 2);

		String title = "Hinzufügen";
		if (!commandAdd)
			title = "Entfernen";
		JButton ok = new JButton(title);

		ok.setMinimumSize(new Dimension(100, 20));
		ok.setPreferredSize(new Dimension(100, 20));
		ok.addActionListener(new ModifyKontakteInGruppe(this.kontakte, this));

		String cmd = "add";
		if (!commandAdd)
			cmd = "remove";
		ok.setActionCommand(cmd);
		ok.setMargin(new Insets(0, 0, 0, 0));
		panel.add(ok, c);
		c.gridx = 1;
		JButton cancel = new JButton("Abbrechen");
		cancel.setMinimumSize(new Dimension(100, 20));
		cancel.setPreferredSize(new Dimension(100, 20));
		cancel.addActionListener(new ModifyKontakteInGruppe(this.kontakte, this));
		cancel.setActionCommand("cancel");
		cancel.setMargin(new Insets(0, 0, 0, 0));
		panel.add(cancel, c);
		return panel;
	}

	public List<Person> getSelected() {
		List<Person> result = new ArrayList<Person>();
		for (Person o : this.list.getSelectedValuesList())
			result.add(o);
		return result;
	}

	private Component createListContent() {
		JPanel panel = new JPanel();
		TitledBorder border = BorderFactory.createTitledBorder("Auswahl");
		border.setTitleColor(Window.titledBorderColor);
		border.setBorder(BorderFactory.createLineBorder(Window.titledBorderColor));
		panel.setBorder(border);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 5;
		c.ipady = 5;
		c.weightx = 1;
		c.weighty = 1;

		listModel = new DefaultListModel<Person>();
		list = new JList<Person>(listModel);
		this.filter.setJListModel(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// list.addListSelectionListener(new ListSelectionListener(list));
		JScrollPane scroll = new JScrollPane(list);
		scroll.setMinimumSize(new Dimension(150, 200));
		scroll.setPreferredSize(new Dimension(150, 200));
		panel.add(scroll, c);
		this.refreshListContent(Main.data.getPersonen());

		c.gridy = 1;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.NONE;
		JLabel hint = new JLabel("<html>Benutze die Ctrl-Taste für Mehrfachselektion.</html>");
		hint.setPreferredSize(new Dimension(250, 40));
		hint.setMinimumSize(new Dimension(250, 40));
		hint.setFont(Window.hintFont);
		panel.add(hint, c);

		return panel;
	}

	public void refreshListContent(List<Person> kontakte) {
		listModel.clear();
		for (Person p : kontakte) {
			if (this.filter.isVisible(p))
				listModel.addElement(p);
		}
	}

	private JPanel createFilterButtons() {
		JPanel panel = new JPanel();
		TitledBorder border = BorderFactory.createTitledBorder("Filter");
		border.setTitleColor(Window.titledBorderColor);
		border.setBorder(BorderFactory.createLineBorder(Window.titledBorderColor));
		panel.setBorder(border);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.weightx = 1;
		c.weighty = 1;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		panel.add(new JLabel("Name:"), c);

		c.gridx = 1;
		c.weightx = 1;
		JTextField nameFilter = new JTextField();
		nameFilter.setMinimumSize(new Dimension(150, 20));
		nameFilter.setPreferredSize(new Dimension(150, 20));
		nameFilter.addKeyListener(new FilterPersonName(nameFilter, this.filter));
		panel.add(nameFilter, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		panel.add(createButtonPanel(), c);

		return panel;
	}

	private Component createButtonPanel() {
		JPanel panel = new JPanel();

		FilterPersonWichtig x = new FilterPersonWichtig(this.filter);

		JRadioButton buttonWichtigVIP = new JRadioButton("Häufigste");
		buttonWichtigVIP.setMinimumSize(new Dimension(80, 15));
		buttonWichtigVIP.setPreferredSize(new Dimension(80, 15));
		buttonWichtigVIP.setActionCommand(String.valueOf(true));
		buttonWichtigVIP.addActionListener(x);
		panel.add(buttonWichtigVIP);

		JRadioButton buttonWichtigAlle = new JRadioButton("Alle");
		buttonWichtigAlle.setMinimumSize(new Dimension(50, 15));
		buttonWichtigAlle.setPreferredSize(new Dimension(50, 15));
		buttonWichtigAlle.setActionCommand(String.valueOf(false));
		buttonWichtigAlle.addActionListener(x);
		panel.add(buttonWichtigAlle);

		ButtonGroup group = new ButtonGroup();
		group.add(buttonWichtigVIP);
		group.add(buttonWichtigAlle);
		buttonWichtigAlle.setSelected(true);

		return panel;
	}

}
