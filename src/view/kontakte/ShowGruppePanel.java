package view.kontakte;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.kontakte.Gruppe;
import view.KontaktTab.aktuelleAuswahl;
import controller.Kontakte;
import controller.kontakte.buttons.OpenChooseKontakte;
import controller.kontakte.editPerson.BeschreibungListener;

public class ShowGruppePanel extends JPanel implements SupportsEingabeTyp {

	private static final long	serialVersionUID	= 3908051412209100109L;

	private Kontakte			kontakte;

	private JLabel				titleLabel;
	private JTextArea			beschreibung;

	public aktuelleAuswahl		eingabeTyp;

	public ShowGruppePanel(Kontakte kontakte) {
		this.kontakte = kontakte;
		this.eingabeTyp = aktuelleAuswahl.Gruppe;

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 1, 1, 1);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 2;

		add(createName(), c);

		c.gridy = 1;
		add(new JLabel("Beschreibung:"), c);

		c.gridy = 2;
		add(createBeschreibungField(), c);

		c.gridy = 3;
		add(new JLabel("Mitglieder:"), c);

		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0;
		c.gridwidth = 1;
		add(createAddButton(), c);

		c.gridx = 1;
		add(createDelButton(), c);

		// RestPanel, damit Inhalt nicht verzogen wird
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 6;
		c.weightx = 0;
		c.weighty = 1;
		add(new JPanel(), c);
	}

	private Component createDelButton() {
		JButton button = new JButton("entfernen");
		button.setMinimumSize(new Dimension(80, 20));
		button.setPreferredSize(new Dimension(80, 20));
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setActionCommand("remove");
		button.addActionListener(new OpenChooseKontakte(this.kontakte));
		return button;
	}

	private Component createAddButton() {
		JButton button = new JButton("hinzufügen");
		button.setMinimumSize(new Dimension(80, 20));
		button.setPreferredSize(new Dimension(80, 20));
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setActionCommand("add");
		button.addActionListener(new OpenChooseKontakte(this.kontakte));
		return button;
	}

	private Component createBeschreibungField() {
		beschreibung = new JTextArea();
		helpers.JTextAreas.setTabChangesFocus(beschreibung);
		beschreibung.setLineWrap(true);
		beschreibung.setWrapStyleWord(true);
		beschreibung.addKeyListener(new BeschreibungListener(this.kontakte, beschreibung,
			aktuelleAuswahl.Gruppe));
		JScrollPane textPane = new JScrollPane(beschreibung);
		textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textPane.setPreferredSize(new Dimension(175, 200));
		textPane.setMinimumSize(new Dimension(175, 200));
		textPane.setAutoscrolls(false);
		return textPane;
	}

	private Component createName() {
		titleLabel = new JLabel("GruppenName");
		Font font = new Font("Arial", Font.BOLD, 16);
		titleLabel.setFont(font);
		return titleLabel;
	}

	public void setSelected(Gruppe g) {
		if (g != null) {
			beschreibung.setText(g.beschreibung);
			this.beschreibung.setEnabled(true);
			this.titleLabel.setText(g.getName());
		}
		if (g == null) {
			this.beschreibung.setText("");
			this.beschreibung.setEnabled(false);
			this.titleLabel.setText("(Gruppe)");
		}
	}

	@Override
	public aktuelleAuswahl getEingabeTyp() {
		return this.eingabeTyp;
	}

}
