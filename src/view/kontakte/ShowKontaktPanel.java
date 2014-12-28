package view.kontakte;

import helpers.FocusTraversalPolicyList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import model.kontakte.Bild;
import model.kontakte.Person;
import view.Images;
import view.KontaktTab.aktuelleAuswahl;
import ch.judos.generic.data.DynamicList;
import controller.Kontakte;
import controller.kontakte.editPerson.BeschreibungListener;
import controller.kontakte.editPerson.BildListener;
import controller.kontakte.editPerson.EmailListener;
import controller.kontakte.editPerson.GeburtstagListener;
import controller.kontakte.editPerson.ImageNavigatorListener;
import controller.kontakte.editPerson.LandListener;
import controller.kontakte.editPerson.MSNListener;
import controller.kontakte.editPerson.OrtListener;
import controller.kontakte.editPerson.PLZListener;
import controller.kontakte.editPerson.StrasseListener;
import controller.kontakte.editPerson.TelefonHomeListener;
import controller.kontakte.editPerson.TelefonListener;
import controller.kontakte.editPerson.VIPListener;

public class ShowKontaktPanel extends JPanel implements SupportsEingabeTyp {

	private static final long	serialVersionUID	= -114637527785695430L;
	private JLabel				titleLabel;
	private JTextArea			beschreibung;
	private Kontakte			kontakte;
	private JTextField			personTel;
	private JTextField			personTelHome;
	private JTextField			personEmail;
	private JTextField			personMSN;
	private JTextField			personGeb;
	private JTextField			personStrasse;
	private JTextField			personPLZ;
	private JTextField			personOrt;
	private JTextField			personLand;
	private JLabel				bildLabel;
	private JLabel				personImgLabel;
	private JButton				personWichtig;
	private ImageIcon			anonymousIcon;

	public aktuelleAuswahl		eingabeTyp;

	public ShowKontaktPanel(Kontakte kontakte) {
		super();
		this.kontakte = kontakte;
		this.eingabeTyp = aktuelleAuswahl.Person;

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.insets = new Insets(1, 1, 1, 1);

		add(createNameLabel(), c);

		c.gridy = 1;
		add(new JLabel("Beschreibung:"), c);

		c.gridy = 2;
		add(createBeschreibungField(), c);

		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0;
		add(new JLabel("Tel:"), c);

		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 2;
		add(createTelefonField(), c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0;
		add(new JLabel("HomeTel:"), c);

		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 2;
		add(createHomeTelField(), c);

		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 0;
		c.gridwidth = 1;
		add(new JLabel("Email:"), c);

		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		add(createEmailField(), c);

		c.gridx = 0;
		c.gridy = 6;
		c.weightx = 0;
		c.gridwidth = 1;
		add(new JLabel("MSN:"), c);

		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		add(createMSNField(), c);

		c.gridx = 0;
		c.gridy = 7;
		c.weightx = 0;
		c.gridwidth = 1;
		add(new JLabel("Geburtstag:"), c);

		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 2;
		add(createGeburtstagField(), c);

		c.gridx = 3;
		c.gridwidth = 1;
		JLabel hinweis = new JLabel("(dd.mm.YYYY)");
		hinweis.setForeground(new Color(150, 150, 150));
		add(hinweis, c);

		// Strasse
		c.gridx = 0;
		c.gridy = 8;
		c.weightx = 0;
		c.gridwidth = 1;
		add(new JLabel("Strasse:"), c);

		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		add(createStrasseField(), c);

		// Ort
		c.gridx = 0;
		c.gridy = 9;
		c.weightx = 0;
		c.gridwidth = 1;
		add(new JLabel("PLZ/Ort:"), c);

		c.gridx = 1;
		c.weightx = 0;
		c.gridwidth = 1;
		add(createPLZField(), c);

		c.gridx = 2;
		c.weightx = 5;
		c.gridwidth = 2;
		add(createOrtField(), c);

		// Land
		c.gridx = 0;
		c.gridy = 10;
		c.weightx = 0;
		c.gridwidth = 1;
		add(new JLabel("Land:"), c);

		c.gridx = 1;
		c.gridwidth = 2;
		add(createLandField(), c);

		// Bild zur Person
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		c.anchor = GridBagConstraints.SOUTH;
		add(createPersonBild(), c);

		// Bild Navigation
		c.gridx = 3;
		c.gridy = 3;
		c.anchor = GridBagConstraints.NORTH;
		c.gridheight = 1;
		add(createImageNavigation(), c);

		// VIP oder unwichtiger Kontakt
		c.gridx = 3;
		c.gridy = 4;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		add(createVIPButton(), c);

		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;

		// Focus Traversal Policy
		FocusTraversalPolicyList tabOrder = new FocusTraversalPolicyList(
			new Component[] { beschreibung, personTel, personTelHome, personEmail, personMSN, personGeb, personStrasse, personPLZ, personOrt, personLand });
		setFocusTraversalPolicy(tabOrder);
		setFocusTraversalPolicyProvider(true);
	}

	private Component createVIPButton() {
		personWichtig = new JButton("-");
		personWichtig.setMinimumSize(new Dimension(80, 20));
		personWichtig.setPreferredSize(new Dimension(80, 20));
		personWichtig.addActionListener(new VIPListener(this.kontakte, personWichtig));

		return personWichtig;
	}

	private Component createImageNavigation() {
		JPanel panelImg = new JPanel();
		panelImg.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		JButton prev = new JButton(Images.arrowLeft);
		prev.setPreferredSize(new Dimension(20, 20));
		prev.setMinimumSize(new Dimension(20, 20));
		prev.addActionListener(new ImageNavigatorListener(this.kontakte, -1));
		panelImg.add(prev, c);

		c.gridx = 1;
		personImgLabel = new JLabel("-");
		personImgLabel.setHorizontalAlignment(JLabel.CENTER);
		panelImg.add(personImgLabel, c);

		c.gridx = 2;
		JButton next = new JButton(Images.arrowRight);
		next.setPreferredSize(new Dimension(20, 20));
		next.setMinimumSize(new Dimension(20, 20));
		next.addActionListener(new ImageNavigatorListener(this.kontakte, 1));
		panelImg.add(next, c);
		return panelImg;
	}

	private Component createPersonBild() {
		Image image = Images.anonymousIcon;
		anonymousIcon = new ImageIcon(image.getScaledInstance(100, 140, Image.SCALE_SMOOTH));

		bildLabel = new JLabel(anonymousIcon);
		bildLabel.addMouseListener(new BildListener(this.kontakte));
		bildLabel.setPreferredSize(new Dimension(100, 140));
		bildLabel.setMinimumSize(new Dimension(100, 140));

		JPanel bildLabelOuter = new JPanel();
		bildLabelOuter.setLayout(new BorderLayout());
		bildLabelOuter.add(bildLabel);
		bildLabelOuter.setBackground(new Color(220, 220, 220));
		bildLabelOuter.setBorder(new LineBorder(new Color(128, 128, 128)));
		return bildLabelOuter;
	}

	private Component createLandField() {
		personLand = new JTextField();
		personLand.setMinimumSize(new Dimension(75, 20));
		personLand.setPreferredSize(new Dimension(75, 20));
		personLand.addKeyListener(new LandListener(this.kontakte, personLand));
		return personLand;
	}

	private Component createOrtField() {
		personOrt = new JTextField();
		personOrt.setMinimumSize(new Dimension(75, 20));
		personOrt.setPreferredSize(new Dimension(75, 20));
		personOrt.addKeyListener(new OrtListener(this.kontakte, personOrt));
		return personOrt;
	}

	private Component createPLZField() {
		personPLZ = new JTextField();
		personPLZ.setMinimumSize(new Dimension(45, 20));
		personPLZ.setPreferredSize(new Dimension(45, 20));
		personPLZ.addKeyListener(new PLZListener(this.kontakte, personPLZ));
		return personPLZ;
	}

	private Component createStrasseField() {
		personStrasse = new JTextField();
		personStrasse.setMinimumSize(new Dimension(75, 20));
		personStrasse.setPreferredSize(new Dimension(75, 20));

		personStrasse.addKeyListener(new StrasseListener(this.kontakte, personStrasse));
		return personStrasse;
	}

	private Component createGeburtstagField() {
		personGeb = new JTextField();
		personGeb.setBackground(new Color(255, 255, 255));
		personGeb.setMinimumSize(new Dimension(75, 20));
		personGeb.setPreferredSize(new Dimension(75, 20));

		personGeb.addKeyListener(new GeburtstagListener(this.kontakte, personGeb));
		return personGeb;
	}

	private Component createMSNField() {
		personMSN = new JTextField();
		personMSN.setMinimumSize(new Dimension(75, 20));
		personMSN.setPreferredSize(new Dimension(75, 20));
		personMSN.addKeyListener(new MSNListener(this.kontakte, personMSN));
		return personMSN;
	}

	private Component createEmailField() {
		personEmail = new JTextField();
		personEmail.setMinimumSize(new Dimension(75, 20));
		personEmail.setPreferredSize(new Dimension(75, 20));
		personEmail.addKeyListener(new EmailListener(this.kontakte, personEmail));
		return personEmail;
	}

	private Component createHomeTelField() {
		personTelHome = new JTextField();
		personTelHome.setMinimumSize(new Dimension(70, 20));
		personTelHome.setPreferredSize(new Dimension(70, 20));
		personTelHome.addKeyListener(new TelefonHomeListener(this.kontakte, personTelHome));
		return personTelHome;
	}

	private Component createTelefonField() {
		personTel = new JTextField();
		personTel.setMinimumSize(new Dimension(70, 20));
		personTel.setPreferredSize(new Dimension(70, 20));
		personTel.addKeyListener(new TelefonListener(this.kontakte, personTel));
		return personTel;
	}

	private Component createBeschreibungField() {
		beschreibung = new JTextArea();
		helpers.JTextAreas.setTabChangesFocus(beschreibung);

		beschreibung.setLineWrap(true);
		beschreibung.setWrapStyleWord(true);
		beschreibung.addKeyListener(new BeschreibungListener(this.kontakte, beschreibung,
			aktuelleAuswahl.Person));
		JScrollPane textPane = new JScrollPane(beschreibung);
		textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textPane.setPreferredSize(new Dimension(175, 100));
		textPane.setMinimumSize(new Dimension(175, 100));
		textPane.setAutoscrolls(false);
		return textPane;
	}

	private Component createNameLabel() {
		titleLabel = new JLabel("Vorname Name");
		Font font = new Font("Arial", Font.BOLD, 16);
		titleLabel.setFont(font);
		return titleLabel;
	}

	public boolean setBild(Person p, int index) {
		if (p != null) {
			ArrayList<Bild> bilder = p.getBilder();
			if (bilder.size() > 0) {
				if (index == -1)
					index = p.preferedImage;
				if (index < 0)
					index = 0;
				if (index > bilder.size() - 1)
					index = bilder.size() - 1;
				p.preferedImage = index;
				personImgLabel.setText((index + 1) + "/" + bilder.size());

				Bild b = bilder.get(index);
				bildLabel.setIcon(b.getStdIcon());

				return true;
			}
		}
		// else:
		personImgLabel.setText("-");
		bildLabel.setIcon(anonymousIcon);
		bildLabel.setBackground(new Color(50, 50, 50));
		return false;
	}

	public void showPerson(Person p) {
		DynamicList<JTextComponent> list = new DynamicList<JTextComponent>();
		list.addAll(this.beschreibung, this.personTel, this.personTelHome, this.personEmail,
			this.personMSN, this.personGeb, this.personStrasse, this.personPLZ, this.personOrt,
			this.personLand);
		if (p == null) {
			disableFields(list);
			this.titleLabel.setText("Vorname Nachname");
			this.personWichtig.setText("-");
			this.personWichtig.setEnabled(false);
		} else {
			enableFields(list);
			this.titleLabel.setText(p.getFullName());
			this.beschreibung.setText(p.beschreibung);
			this.personGeb.setText(p.getBirthdayDateString());
			this.personTel.setText(p.tel);
			this.personTelHome.setText(p.homeTel);
			this.personStrasse.setText(p.strasse);
			this.personPLZ.setText(p.plz);
			this.personOrt.setText(p.ort);
			this.personLand.setText(p.land);
			this.personWichtig.setEnabled(true);
			this.personWichtig.setText(p.getStatus());
			this.personEmail.setText(p.email);
			this.personMSN.setText(p.msn);
		}

		this.repaint();
		setGeburtstagValid(true);
		setBild(p, -1);
	}

	public void setGeburtstagValid(boolean valid) {
		if (!valid)
			personGeb.setBackground(new Color(250, 192, 192));
		else
			personGeb.setBackground(new Color(255, 255, 255));
	}

	private void disableFields(List<JTextComponent> list) {
		for (JTextComponent c : list) {
			c.setEnabled(false);
			c.setText("");
		}
	}

	private void enableFields(List<JTextComponent> list) {
		for (JTextComponent c : list) {
			c.setEnabled(true);
		}
	}

	@Override
	public aktuelleAuswahl getEingabeTyp() {
		return this.eingabeTyp;
	}

}
