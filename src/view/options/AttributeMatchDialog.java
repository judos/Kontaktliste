package view.options;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import model.importcsv.CsvTableData;
import model.kontakte.PersonEnum;
import view.Images;
import ch.judos.generic.data.DynamicList;

public class AttributeMatchDialog extends JDialog {
	private static final long	serialVersionUID	= -3161442417947618540L;
	private DynamicList<String>	atts_csv;
	private JComboBox<String>	name1;
	private JComboBox<String>	name2;
	private JComboBox[]			atts_contactList;
	private JPanel				content;
	protected boolean			result;
	private JButton				okButton;

	public AttributeMatchDialog(CsvTableData data) {
		super();
		this.atts_csv = data.getAttributes();
		this.atts_contactList = new JComboBox[this.atts_csv.size()];

		this.setTitle("Attribute verbinden");

		this.content = new JPanel();
		this.add(this.content);
		this.content.setLayout(new GridBagLayout());

		addContent(data.getExamplesForAttributes());

		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.pack();
		this.setMinimumSize(this.getSize());
		this.setLocationRelativeTo(null);
	}

	public void block() {
		this.setVisible(true);
	}

	private void addContent(String[] examplesForAtts) {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 3;
		c.ipady = 3;

		displayTitleLabel(c);
		addHorizontalSeparator(c);
		createIdentifierAttributes(c);
		addHorizontalSeparator(c);
		createScrollPaneWithAttributesMath(c, examplesForAtts);
		addHorizontalSeparator(c);
		okAndCancelDialog(c);

		checkIdentificationInputOk();
	}

	private void createScrollPaneWithAttributesMath(GridBagConstraints c, String[] examplesForAtts) {
		c.gridy++;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		JScrollPane scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(400, 300));
		scroll.setMinimumSize(new Dimension(400, 80));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setViewportView(matchAttributesToExistingAttributes(examplesForAtts));
		this.content.add(scroll, c);
	}

	private void checkIdentificationInputOk() {
		String prename = (String) this.name1.getSelectedItem();
		String surname = (String) this.name2.getSelectedItem();
		if (this.atts_csv.contains(prename) && this.atts_csv.contains(surname) && !surname
			.equals(prename))
			this.okButton.setEnabled(true);
		else
			this.okButton.setEnabled(false);
	}

	private void okAndCancelDialog(GridBagConstraints c) {
		c.gridy++;
		c.gridwidth = 1;
		this.okButton = new JButton("Ok");
		okButton.setEnabled(false);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				result = true;
				dispose();
			}
		});
		this.content.add(okButton, c);

		c.gridx++;
		c.gridwidth = 2;
		JButton abbrechen = new JButton("Abbrechen");
		this.content.add(abbrechen, c);
		abbrechen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				result = false;
				dispose();
			}
		});
	}

	private JPanel matchAttributesToExistingAttributes(String[] examplesForAtts) {
		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.insets = new Insets(4, 4, 4, 4);
		c.gridwidth = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		DynamicList<Object> selectList = new DynamicList<Object>(PersonEnum.all.toArray());
		selectList.add(0, "-nicht speichern-");
		Object[] select = selectList.toArray();
		for (int i = 0; i < this.atts_csv.size(); i++) {

			c.gridx = 0;
			container.add(new JLabel(this.atts_csv.get(i)), c);
			c.gridx = 1;
			String example = "e.g. \"" + examplesForAtts[i] + "\"";
			container.add(new JLabel(example), c);

			c.gridx = 2;
			JLabel l = new JLabel(Images.arrowRight);
			l.setSize(20, 20);
			l.setPreferredSize(new Dimension(20, 20));
			l.setMinimumSize(new Dimension(20, 20));
			container.add(l, c);

			c.gridx = 3;
			this.atts_contactList[i] = new JComboBox(select);
			this.atts_contactList[i].setRenderer(new DropDownListRenderer());
			container.add(this.atts_contactList[i], c);
			c.gridy++;
		}
		return container;
	}

	private void displayTitleLabel(GridBagConstraints c) {
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		JLabel title = new JLabel(
			"<html>Um die CSV-Datei zu importieren, " + "müssen Sie wählen welche Informationen<br>durch welche " + "Attribute der CSV-Datei ergänzt werden sollen.</html>");
		title.setPreferredSize(new Dimension(750, 80));
		title.setMinimumSize(new Dimension(750, 80));
		title.setFont(new Font("Arial", 1, 18));
		this.content.add(title, c);
	}

	private void addHorizontalSeparator(GridBagConstraints c) {
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.content.add(new JSeparator(JSeparator.HORIZONTAL), c);
	}

	private void createIdentifierAttributes(GridBagConstraints c) {
		c.gridy = 2;
		c.gridwidth = 1;
		DynamicList<String> x = new DynamicList<String>(atts_csv);
		x.add(0, "-");
		String[] atts_identifier = x.toArray(new String[] {});
		name1 = new JComboBox<String>(atts_identifier);
		name1.setSelectedIndex(0);
		int index = -1;
		if ((index = listContains(x,
			new String[] { "vorname", "prename", "givenname", "first name", "firstname" })) > -1) {
			name1.setSelectedIndex(index);
		}
		name1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkIdentificationInputOk();
			}
		});
		name2 = new JComboBox<String>(atts_identifier);
		name2.setSelectedIndex(0);
		if ((index = listContains(x,
			new String[] { "familyname", "nachname", "name", "last name", "lastname" })) > -1) {
			name2.setSelectedIndex(index);
		}
		name2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkIdentificationInputOk();
			}
		});
		c.gridx = 0;
		this.content.add(name1, c);
		c.gridx = 1;
		c.gridwidth = 2;
		this.content.add(name2, c);
		c.gridx = 3;
		c.gridwidth = 1;
		String l1 = "<html>Wähle Attribute aus um die Person zu identifizieren.<br>" + "Um Informationen einer Person hinzuzufügen,<br>" + "muss Vorname und Nachname bekannt sein.</html>";
		JLabel identifier = new JLabel(l1);
		identifier.setMinimumSize(new Dimension(380, 60));
		identifier.setPreferredSize(new Dimension(380, 60));
		identifier.setFont(new Font("Arial", 1, 14));
		this.content.add(identifier, c);

	}

	private int listContains(DynamicList<String> list, String[] strings) {
		for (String match : strings) {
			for (int i = 0; i < list.size(); i++) {
				String t = list.get(i).toLowerCase().replaceAll(" ", "");
				if (t.equals(match))
					return i;
			}
		}
		return -1;
	}

	public HashMap<String, PersonEnum> getCSV2ownAttributeMatching() {
		HashMap<String, PersonEnum> result = new HashMap<String, PersonEnum>();
		for (int i = 0; i < this.atts_csv.size(); i++) {
			String csv = this.atts_csv.get(i);
			Object own = this.atts_contactList[i].getSelectedItem();
			try {
				PersonEnum pe = (PersonEnum) own;
				result.put(csv, pe);
			} catch (ClassCastException e) {
				result.put(csv, null);
			}
		}
		return result;
	}

	/**
	 * @return [0]-> first name [1]-> last name
	 */
	public int[] getIdentificationAttributes() {
		String prename = (String) name1.getSelectedItem();
		String surname = (String) name2.getSelectedItem();
		return new int[] { atts_csv.indexOf(prename), atts_csv.indexOf(surname) };
	}

	/**
	 * @return true is okay was pressed, false if cancel was pressed
	 */
	public boolean getResultOk() {
		return this.result;
	}
}
