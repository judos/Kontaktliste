package view;

import helpers.Swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import model.kontakte.FilterPerson;
import model.kontakte.Gruppe;
import model.kontakte.Person;
import view.kontakte.ShowGruppePanel;
import view.kontakte.ShowKontaktPanel;
import view.missingInfo.ButtonListener;
import controller.FilterPersonName;
import controller.FilterPersonWichtig;
import controller.Kontakte;
import controller.Main;
import controller.kontakte.buttons.CreateGruppeListener;
import controller.kontakte.buttons.CreatePersonListener;
import controller.kontakte.filter.FilterGruppe;
import controller.kontakte.filter.FilterPersonTel;
import controller.kontakte.list.GruppeLML;
import controller.kontakte.list.GruppeLSL;
import controller.kontakte.list.PersonLML;
import controller.kontakte.list.PersonLSL;

public class KontaktTab extends JPanel {

	private static final long			serialVersionUID	= 2200063886710162449L;

	private DefaultListModel<Person>	listPersonenModel;
	private JList<Person>				listPersonen;
	private DefaultListModel<String>	listGruppenModel;
	private JList<String>				listGruppen;

	private JRadioButton				buttonWichtigVIP;
	private JRadioButton				buttonWichtigAlle;

	private JPanel						eastPanel;
	private ShowKontaktPanel			eastPanelPerson;
	private ShowGruppePanel				eastPanelGruppe;
	private Component					eastPanelNothing;

	private Kontakte					kontakte;

	private JTextField					filterNameField;
	private JTextField					filterTelefon;
	private JTextField					filterGruppe;

	private FilterPerson				filter;

	private JTabbedPane					eastSelectionTabs;

	public enum aktuelleAuswahl {
		Person, Gruppe, nichts
	};

	public KontaktTab(Kontakte kontakte, FilterPerson filter) {
		this.kontakte = kontakte;
		this.filter = filter;
		GridBagLayout borderLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(borderLayout);
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

		this.filter.setJListModel(listPersonenModel);
		this.filter.refreshList();
	}

	private Component createEastPanel() {
		eastPanel = new JPanel();
		eastPanel.setBorder(Swing.createTitledLineBorder("Auswahl", Window.titledBorderColor));

		eastPanel.setLayout(new GridBagLayout());

		eastPanelPerson = new ShowKontaktPanel(kontakte);
		eastPanelGruppe = new ShowGruppePanel(kontakte);
		eastPanelNothing = createEastPanelNothing();

		Dimension d = eastPanelPerson.getPreferredSize();
		eastPanel.setPreferredSize(d);
		eastPanelNothing.setPreferredSize(d);
		eastPanelNothing.setMinimumSize(d);
		eastPanelGruppe.setPreferredSize(d);
		eastPanelGruppe.setMinimumSize(d);

		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(1, 1, 1, 1));
		UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);

		buildUpEastPanelContent();

		return eastPanel;
	}

	private int buildUpEastPanelContent() {

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;

		eastPanel.removeAll();
		eastPanel.validate();

		Gruppe g = this.kontakte.getSelectedGruppe();
		Person p = this.kontakte.getSelectedPerson();

		int anz = (g == null ? 0 : 1) + (p == null ? 0 : 1);

		// System.out.println("buildUpEastPanel: anz:"+anz+" person:"+p+" gruppe:"+g);

		if (anz == 0) {
			eastPanel.add(eastPanelNothing, c);
		} else if (anz == 1) {
			if (g != null)
				eastPanel.add(eastPanelGruppe, c);
			else
				eastPanel.add(eastPanelPerson, c);
		} else {
			eastSelectionTabs = new JTabbedPane();
			eastSelectionTabs.setBorder(BorderFactory.createEmptyBorder());
			eastSelectionTabs.addTab("Person", eastPanelPerson);
			eastSelectionTabs.addTab("Gruppe", eastPanelGruppe);
			eastPanel.add(eastSelectionTabs, c);
		}
		// Refreshes displayed content
		this.validate();

		return anz;
	}

	private Component createEastPanelNothing() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.NONE;
		c.weighty = 1;

		panel.add(new JLabel("Keine Auswahl"), c);
		return panel;
	}

	public boolean setBild(Person p) {
		return this.eastPanelPerson.setBild(p, -1);
	}

	private Component createCenterPanel() {
		JPanel panelMitte = new JPanel();
		panelMitte.setBorder(Swing.createTitledLineBorder("Kontakte", Window.titledBorderColor));

		panelMitte.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;

		c.weightx = 1;
		c.weighty = 0;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 10;
		c.gridwidth = 1;

		c.gridy = 0;
		c.gridx = 0;
		ActionListener chooseWichtig = new FilterPersonWichtig(this.filter);

		buttonWichtigVIP = new JRadioButton("Häufigste");
		buttonWichtigVIP.setMinimumSize(new Dimension(80, 15));
		buttonWichtigVIP.setPreferredSize(new Dimension(80, 15));
		buttonWichtigVIP.setActionCommand(String.valueOf(true));
		buttonWichtigVIP.addActionListener(chooseWichtig);
		panelMitte.add(buttonWichtigVIP, c);

		c.gridx = 1;
		c.weightx = 2.5;
		buttonWichtigAlle = new JRadioButton("Alle");
		buttonWichtigAlle.setMinimumSize(new Dimension(80, 15));
		buttonWichtigAlle.setPreferredSize(new Dimension(80, 15));
		buttonWichtigAlle.setActionCommand(String.valueOf(false));
		buttonWichtigAlle.addActionListener(chooseWichtig);
		panelMitte.add(buttonWichtigAlle, c);

		ButtonGroup group = new ButtonGroup();
		group.add(buttonWichtigVIP);
		group.add(buttonWichtigAlle);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		listPersonenModel = new DefaultListModel<Person>();
		listPersonen = new JList<Person>(listPersonenModel);
		this.filter.setJListModel(listPersonenModel);
		listPersonen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPersonen.addListSelectionListener(new PersonLSL(this.kontakte, listPersonen));
		listPersonen.setVisibleRowCount(20);
		JScrollPane listScrollPane = new JScrollPane(listPersonen);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setMinimumSize(new Dimension(150, 250));
		listScrollPane.setPreferredSize(new Dimension(150, 250));
		panelMitte.add(listScrollPane, c);

		listPersonen.addMouseListener(new PersonLML(this.kontakte, listPersonen));

		c.gridy = 2;
		c.weighty = 0;
		c.ipady = 0;
		c.ipadx = 0;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		JPanel panelGruppe = new JPanel();
		panelMitte.add(panelGruppe, c);
		panelGruppe.add(new JLabel("Neu:"));

		JButton neuerKontakt = new JButton("Kontakt");
		neuerKontakt.setMinimumSize(new Dimension(60, 20));
		neuerKontakt.setPreferredSize(new Dimension(60, 20));
		neuerKontakt.setMargin(new Insets(0, 0, 0, 0));
		neuerKontakt.addActionListener(new CreatePersonListener(this.kontakte));
		panelGruppe.add(neuerKontakt);
		JButton neueGruppe = new JButton("Gruppe");
		neueGruppe.setMinimumSize(new Dimension(60, 20));
		neueGruppe.setPreferredSize(new Dimension(60, 20));
		neueGruppe.setMargin(new Insets(0, 0, 0, 0));
		neueGruppe.addActionListener(new CreateGruppeListener(this.kontakte));
		panelGruppe.add(neueGruppe);

		return panelMitte;
	}

	private JPanel createWestPanel() {
		JPanel panelLeft = new JPanel();
		panelLeft.setBorder(Swing.createTitledLineBorder("Filter", Window.titledBorderColor));

		panelLeft.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		// Person Filter/ Eingabefeld
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 4;
		panelLeft.add(new JLabel("Person:"), c);
		c.gridx = 1;
		c.weightx = 1;
		filterNameField = new JTextField();
		filterNameField.setMinimumSize(new Dimension(80, 20));
		filterNameField.setPreferredSize(new Dimension(80, 20));
		filterNameField.addKeyListener(new FilterPersonName(filterNameField, this.filter));
		panelLeft.add(filterNameField, c);

		// Telefon Filter/ Eingabefeld
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		panelLeft.add(new JLabel("Telefon:"), c);
		c.gridx = 1;
		c.weightx = 1;
		filterTelefon = new JTextField();
		filterTelefon.setMinimumSize(new Dimension(80, 20));
		filterTelefon.setPreferredSize(new Dimension(80, 20));
		filterTelefon.addKeyListener(new FilterPersonTel(filterTelefon, this.filter));
		panelLeft.add(filterTelefon, c);

		// Gruppen Filter/ Eingabefeld und Liste
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		panelLeft.add(new JLabel("Gruppe:"), c);
		c.gridx = 1;
		c.weightx = 1;
		filterGruppe = new JTextField();
		filterGruppe.setMinimumSize(new Dimension(80, 20));
		filterGruppe.setPreferredSize(new Dimension(80, 20));
		filterGruppe.addKeyListener(new FilterGruppe(this.kontakte, filterGruppe));
		panelLeft.add(filterGruppe, c);

		// Liste
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.weighty = 1;
		listGruppenModel = new DefaultListModel<String>();
		listGruppen = new JList<String>(listGruppenModel);
		listGruppen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGruppen
			.addListSelectionListener(new GruppeLSL(this.kontakte, listGruppen, this.filter));
		listGruppen.addMouseListener(new GruppeLML(this.kontakte, listGruppen));
		JScrollPane listScrollPane = new JScrollPane(listGruppen);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setMinimumSize(new Dimension(150, 250));
		listScrollPane.setPreferredSize(new Dimension(150, 250));
		panelLeft.add(listScrollPane, c);

		// Fehlende Informationen
		if (Main.showMissingInfos) {
			c.weighty = 0;
			c.gridx = 0;
			c.gridy = 5;
			c.gridwidth = 2;
			JButton button = new JButton("Fehlende Information");
			button.addActionListener(new ButtonListener(this.kontakte));
			panelLeft.add(button, c);
		}

		return panelLeft;
	}

	public void refreshGruppenListe(List<Gruppe> gruppen, Gruppe selected) {
		listGruppenModel.clear();
		int index = 0;
		for (Gruppe g : gruppen) {
			if (g != null)
				listGruppenModel.addElement(g.getName());
			else
				listGruppenModel.addElement("<Alle>");

			if (selected == g)
				listGruppen.setSelectedIndex(index);
			index++;
		}
		if (selected == null)
			listGruppen.setSelectedIndex(0);

	}

	public int anzahlPersonenInListe() {
		return this.listPersonenModel.size();
	}

	public int anzahlGruppenInListe() {
		return this.listGruppenModel.size();
	}

	public void refreshPersonenListe(List<Person> personen, Person selected) {
		listPersonenModel.clear();
		int index = 0;
		for (Person p : personen) {
			listPersonenModel.addElement(p);

			if (selected == p)
				listPersonen.setSelectedIndex(index);
			index++;
		}
		if (selected == null)
			listPersonen.clearSelection();
	}

	public void setSelectedPerson(Person p) {
		this.eastPanelPerson.showPerson(p);
		this.eastPanelPerson.repaint();
		if (p != null)
			setSelected(aktuelleAuswahl.Person);
		else
			setSelected(aktuelleAuswahl.nichts);
		this.repaint();
	}

	private void setSelected(aktuelleAuswahl auswahl) {
		int anz = buildUpEastPanelContent();
		if (anz == 2) {
			if (auswahl == aktuelleAuswahl.Person)
				eastSelectionTabs.setSelectedIndex(0);
			else if (auswahl == aktuelleAuswahl.Gruppe)
				eastSelectionTabs.setSelectedIndex(1);
		}
	}

	public void setSelectedGruppe(Gruppe g) {
		this.eastPanelGruppe.setSelected(g);
		if (g != null)
			setSelected(aktuelleAuswahl.Gruppe);
		else
			setSelected(aktuelleAuswahl.nichts);

	}

	public void selectListEntry(Gruppe g) {
		listGruppen.setSelectedValue(g.getName(), true);
	}

	public void selectListEntry(Person p) {
		if (p != null)
			listPersonen.setSelectedValue(p, true);
		else
			listPersonen.clearSelection();
	}

	public void setWichtigAuswahl(boolean b) {
		if (b)
			this.buttonWichtigVIP.setSelected(true);
		else
			this.buttonWichtigAlle.setSelected(true);
	}

	public void requestStartFocus() {
		this.filterNameField.requestFocusInWindow();
	}

	public void setFilter(String gruppeName) {
		this.filterGruppe.setText(gruppeName);
	}

	public void setFilterPersonName(String personName) {
		this.filterNameField.setText(personName);
	}

	public void setGeburtstagValid(boolean valid) {
		this.eastPanelPerson.setGeburtstagValid(valid);
	}
}
