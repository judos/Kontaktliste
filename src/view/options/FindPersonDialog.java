package view.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.PersonDisplay;
import model.kontakte.FilterPerson;
import model.kontakte.Person;
import ch.judos.generic.swing.JDialogWithTaskbarEntry;

/**
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class FindPersonDialog extends JDialogWithTaskbarEntry {

	private static final int		DISPLAY_PER_ROW		= 3;

	private static final long		serialVersionUID	= -3474466547782429015L;
	private final JPanel			contentPanel		= new JPanel();
	private final ButtonGroup		buttonGroup			= new ButtonGroup();
	private JTextField				textFieldPrename;
	private JTextField				textFieldName;
	private JTextField				textFieldFilter;

	private JRadioButton			rdbtnSearchContact;
	private JRadioButton			rdbtnNewContact;
	private JRadioButton			rdbtnSimilarContacts;
	private JButton					submitButton;

	private int						resultType;
	private Person					result;
	private PersonDisplay[]			nearest;
	private String[]				name;
	private String					fullname;
	private FilterPerson			searchFilter;
	private JList<Person>			listSearch;
	private JList<PersonDisplay>	listSimilarContacts;
	private String[]				values;
	private String[]				atts;

	private ActionListener			checkListener;

	private void checkIfFormIsOk() {
		boolean ok = false;
		if (rdbtnSimilarContacts.isSelected()) {
			listSearch.setSelectedIndices(new int[] {});
			if (listSimilarContacts.getSelectedValue() != null) {
				resultType = RESULT_USE_CONTACT;
				result = listSimilarContacts.getSelectedValue().person;
				ok = true;
			}
		}
		if (rdbtnNewContact.isSelected()) {
			listSearch.setSelectedIndices(new int[] {});
			listSimilarContacts.setSelectedIndices(new int[] {});
			String prename = textFieldPrename.getText();
			String name = textFieldName.getText();
			if (!"".equals(prename) && !"".equals(name)) {
				resultType = RESULT_CREATE_CONTACT;
				// to be created when ok is pressed
				result = null;
				ok = true;
			}
		}
		if (rdbtnSearchContact.isSelected()) {
			listSimilarContacts.setSelectedIndices(new int[] {});
			if (listSearch.getSelectedValue() != null) {
				resultType = RESULT_USE_CONTACT;
				result = (Person) listSearch.getSelectedValue();
				ok = true;
			}
		}
		this.submitButton.setEnabled(ok);
	}

	public static final int	RESULT_USE_CONTACT		= 1;
	public static final int	RESULT_DONT_IMPORT		= 2;
	public static final int	RESULT_CREATE_CONTACT	= 3;
	public static final int	RESULT_CLOSE_IMPORT		= 4;

	public static void main(String[] args) {
		FindPersonDialog dialog =
			new FindPersonDialog(new String[] { "Julian", "Schelker" }, FilterPerson
				.createNormalFilter(), new PersonDisplay[] {}, new String[] { "email",
				"Git" }, new String[] { "judos@gmx.ch", "igit!" });
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}

	public FindPersonDialog(String[] name, FilterPerson f, PersonDisplay[] nearest,
			String[] atts, String[] values) {
		super("Kontaktliste Import");
		this.name = name;
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.fullname = name[0] + " " + name[1];
		this.searchFilter = f;
		this.nearest = nearest;
		this.atts = atts;
		this.values = values;
		this.checkListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkIfFormIsOk();
			}
		};
		initDialog();
	}

	public void block() {
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	protected void initDialog() {
		setTitle("Kontakt nicht gefunden");
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(createWindowListener());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, 1.0, 1 };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JPanel panelInfos = new JPanel();
			panelInfos.setBorder(new TitledBorder(
				new LineBorder(new Color(128, 128, 128)), "\"" + this.fullname
					+ "\" wurde nicht gefunden. Informationen des Kontaktes:",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
			GridBagConstraints gbc_panelInfos = new GridBagConstraints();
			gbc_panelInfos.weightx = 1.0;
			gbc_panelInfos.weighty = 1.0;
			gbc_panelInfos.gridwidth = 3;
			gbc_panelInfos.insets = new Insets(0, 0, 5, 5);
			gbc_panelInfos.fill = GridBagConstraints.BOTH;
			gbc_panelInfos.gridx = 0;
			gbc_panelInfos.gridy = 0;
			contentPanel.add(panelInfos, gbc_panelInfos);
			GridBagLayout gbl_panelInfos = new GridBagLayout();
			panelInfos.setLayout(gbl_panelInfos);
			for (int i = 0; i < this.atts.length; i++) {
				if (!"".equals(this.values[i])) {
					{
						JLabel label = new JLabel(this.atts[i]);
						GridBagConstraints gbc_label = new GridBagConstraints();
						gbc_label.insets = new Insets(0, 0, 5, 5);
						gbc_label.fill = GridBagConstraints.BOTH;
						gbc_label.weighty = 1.0;
						gbc_label.weightx = 1.0;
						gbc_label.anchor = GridBagConstraints.NORTHWEST;
						gbc_label.gridx = i % DISPLAY_PER_ROW;
						gbc_label.gridy = (int) Math.floor(i / DISPLAY_PER_ROW) * 2;
						panelInfos.add(label, gbc_label);
					}
					{
						JTextField textField = new JTextField();
						textField.setEditable(false);
						textField.setText(this.values[i]);
						GridBagConstraints gbc_textField = new GridBagConstraints();
						gbc_textField.insets = new Insets(0, 0, 0, 5);
						gbc_textField.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField.gridx = i % DISPLAY_PER_ROW;
						gbc_textField.gridy =
							(int) Math.floor(i / DISPLAY_PER_ROW) * 2 + 1;
						panelInfos.add(textField, gbc_textField);
						textField.setColumns(10);
					}
				}
			}
		}
		{
			JLabel info = new JLabel("Bitte wählen Sie eine auszuführende Aktion:");
			info.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_notFound = new GridBagConstraints();
			gbc_notFound.insets = new Insets(0, 0, 5, 0);
			gbc_notFound.anchor = GridBagConstraints.WEST;
			gbc_notFound.gridwidth = 3;
			gbc_notFound.gridx = 0;
			gbc_notFound.gridy = 1;
			contentPanel.add(info, gbc_notFound);
		}
		{
			JPanel simililarContacts = new JPanel();
			simililarContacts.setBorder(new CompoundBorder(new LineBorder(new Color(192,
				192, 192)), new EmptyBorder(5, 5, 5, 5)));
			GridBagConstraints gbc_simililarContacts = new GridBagConstraints();
			gbc_simililarContacts.anchor = GridBagConstraints.NORTHWEST;
			gbc_simililarContacts.insets = new Insets(0, 0, 0, 5);
			gbc_simililarContacts.fill = GridBagConstraints.BOTH;
			gbc_simililarContacts.gridx = 0;
			gbc_simililarContacts.gridy = 2;
			contentPanel.add(simililarContacts, gbc_simililarContacts);
			GridBagLayout gbl_simililarContacts = new GridBagLayout();
			gbl_simililarContacts.columnWidths = new int[] { 129, 0 };
			gbl_simililarContacts.rowHeights = new int[] { 25, 0, 0 };
			gbl_simililarContacts.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_simililarContacts.rowWeights =
				new double[] { 0.0, 1.0, Double.MIN_VALUE };
			simililarContacts.setLayout(gbl_simililarContacts);
			{
				this.rdbtnSimilarContacts = new JRadioButton("Kontakt w\u00E4hlen:");
				this.rdbtnSimilarContacts.addActionListener(this.checkListener);
				buttonGroup.add(rdbtnSimilarContacts);
				GridBagConstraints gbc_rdbtnSimilarContacts = new GridBagConstraints();
				gbc_rdbtnSimilarContacts.ipady = 5;
				gbc_rdbtnSimilarContacts.ipadx = 5;
				gbc_rdbtnSimilarContacts.fill = GridBagConstraints.HORIZONTAL;
				gbc_rdbtnSimilarContacts.insets = new Insets(5, 5, 5, 0);
				gbc_rdbtnSimilarContacts.anchor = GridBagConstraints.NORTHWEST;
				gbc_rdbtnSimilarContacts.gridx = 0;
				gbc_rdbtnSimilarContacts.gridy = 0;
				simililarContacts.add(rdbtnSimilarContacts, gbc_rdbtnSimilarContacts);
			}
			{
				JScrollPane scrollPaneSimilarContacts = new JScrollPane();
				scrollPaneSimilarContacts.setPreferredSize(new Dimension(150, 200));
				scrollPaneSimilarContacts.setMinimumSize(new Dimension(150, 200));
				GridBagConstraints gbc_scrollPaneSimilarContacts =
					new GridBagConstraints();
				gbc_scrollPaneSimilarContacts.fill = GridBagConstraints.BOTH;
				gbc_scrollPaneSimilarContacts.gridx = 0;
				gbc_scrollPaneSimilarContacts.gridy = 1;
				simililarContacts.add(scrollPaneSimilarContacts,
					gbc_scrollPaneSimilarContacts);
				{
					this.listSimilarContacts = new JList<PersonDisplay>(this.nearest);
					listSimilarContacts
						.addListSelectionListener(createSimilarListListener());
					listSimilarContacts
						.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPaneSimilarContacts.setViewportView(listSimilarContacts);
				}
			}
		}
		{
			JPanel newContact = new JPanel();
			newContact.setBorder(new CompoundBorder(new LineBorder(new Color(192, 192,
				192)), new EmptyBorder(5, 5, 5, 5)));
			GridBagConstraints gbc_newContact = new GridBagConstraints();
			gbc_newContact.anchor = GridBagConstraints.NORTHWEST;
			gbc_newContact.insets = new Insets(0, 0, 0, 5);
			gbc_newContact.fill = GridBagConstraints.BOTH;
			gbc_newContact.gridx = 1;
			gbc_newContact.gridy = 2;
			contentPanel.add(newContact, gbc_newContact);
			GridBagLayout gbl_newContact = new GridBagLayout();
			gbl_newContact.rowWeights = new double[] { 0, 0, 0, 1 };
			gbl_newContact.rowHeights = new int[] { 0, 0, 0, 0 };
			gbl_newContact.columnWeights = new double[] { 0, 1 };
			gbl_newContact.columnWidths = new int[] { 0, 0 };
			newContact.setLayout(gbl_newContact);
			{
				this.rdbtnNewContact = new JRadioButton("Kontakt neu erstellen:");
				this.rdbtnNewContact.addActionListener(this.checkListener);
				buttonGroup.add(rdbtnNewContact);
				GridBagConstraints gbc_rdbtnNewContact = new GridBagConstraints();
				gbc_rdbtnNewContact.ipady = 5;
				gbc_rdbtnNewContact.ipadx = 5;
				gbc_rdbtnNewContact.anchor = GridBagConstraints.NORTHWEST;
				gbc_rdbtnNewContact.fill = GridBagConstraints.HORIZONTAL;
				gbc_rdbtnNewContact.gridwidth = 2;
				gbc_rdbtnNewContact.insets = new Insets(5, 5, 5, 0);
				gbc_rdbtnNewContact.gridx = 0;
				gbc_rdbtnNewContact.gridy = 0;
				newContact.add(rdbtnNewContact, gbc_rdbtnNewContact);
			}
			{
				JLabel lblPreName = new JLabel("Vorname:");
				GridBagConstraints gbc_lblPreName = new GridBagConstraints();
				gbc_lblPreName.ipady = 10;
				gbc_lblPreName.anchor = GridBagConstraints.EAST;
				gbc_lblPreName.insets = new Insets(0, 0, 0, 5);
				gbc_lblPreName.gridx = 0;
				gbc_lblPreName.gridy = 1;
				newContact.add(lblPreName, gbc_lblPreName);
			}
			{
				textFieldPrename = new JTextField();
				textFieldPrename.addKeyListener(createKeyListener());
				textFieldPrename.setText(this.name[0]);
				textFieldPrename.setPreferredSize(new Dimension(100, 20));
				textFieldPrename.setMinimumSize(new Dimension(100, 20));
				GridBagConstraints gbc_textFieldPrename = new GridBagConstraints();
				gbc_textFieldPrename.weightx = 1.0;
				gbc_textFieldPrename.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldPrename.gridx = 1;
				gbc_textFieldPrename.gridy = 1;
				newContact.add(textFieldPrename, gbc_textFieldPrename);
				textFieldPrename.setColumns(10);
			}
			{
				JLabel lblName = new JLabel("Name:");
				GridBagConstraints gbc_lblName = new GridBagConstraints();
				gbc_lblName.ipady = 10;
				gbc_lblName.anchor = GridBagConstraints.EAST;
				gbc_lblName.insets = new Insets(0, 0, 0, 5);
				gbc_lblName.gridx = 0;
				gbc_lblName.gridy = 2;
				newContact.add(lblName, gbc_lblName);
			}
			{
				textFieldName = new JTextField();
				textFieldName.addKeyListener(createKeyListener());
				textFieldName.setText(this.name[1]);
				textFieldName.setPreferredSize(new Dimension(100, 20));
				textFieldName.setMinimumSize(new Dimension(100, 20));
				GridBagConstraints gbc_textFieldName = new GridBagConstraints();
				gbc_textFieldName.weightx = 1.0;
				gbc_textFieldName.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldName.gridx = 1;
				gbc_textFieldName.gridy = 2;
				newContact.add(textFieldName, gbc_textFieldName);
				textFieldName.setColumns(10);
			}
		}
		{
			JPanel panelSearch = new JPanel();
			panelSearch.setBorder(new CompoundBorder(new LineBorder(new Color(192, 192,
				192)), new EmptyBorder(5, 5, 5, 5)));
			GridBagConstraints gbc_panelSearch = new GridBagConstraints();
			gbc_panelSearch.anchor = GridBagConstraints.NORTHWEST;
			gbc_panelSearch.fill = GridBagConstraints.BOTH;
			gbc_panelSearch.gridx = 2;
			gbc_panelSearch.gridy = 2;
			contentPanel.add(panelSearch, gbc_panelSearch);
			GridBagLayout gbl_panelSearch = new GridBagLayout();
			gbl_panelSearch.rowWeights = new double[] { 0.0, 0.0, 1.0 };
			gbl_panelSearch.columnWeights = new double[] { 0.0, 1.0 };
			panelSearch.setLayout(gbl_panelSearch);
			{
				this.rdbtnSearchContact = new JRadioButton("Kontakt suchen:");
				this.rdbtnSearchContact.addActionListener(this.checkListener);
				buttonGroup.add(rdbtnSearchContact);
				GridBagConstraints gbc_rdbtnSearchContact = new GridBagConstraints();
				gbc_rdbtnSearchContact.ipady = 5;
				gbc_rdbtnSearchContact.ipadx = 5;
				gbc_rdbtnSearchContact.insets = new Insets(5, 5, 5, 0);
				gbc_rdbtnSearchContact.fill = GridBagConstraints.HORIZONTAL;
				gbc_rdbtnSearchContact.anchor = GridBagConstraints.NORTHWEST;
				gbc_rdbtnSearchContact.gridwidth = 2;
				gbc_rdbtnSearchContact.gridx = 0;
				gbc_rdbtnSearchContact.gridy = 0;
				panelSearch.add(rdbtnSearchContact, gbc_rdbtnSearchContact);
			}
			{
				JLabel lblFilter = new JLabel("Filter:");
				GridBagConstraints gbc_lblFilter = new GridBagConstraints();
				gbc_lblFilter.insets = new Insets(0, 0, 5, 5);
				gbc_lblFilter.ipady = 10;
				gbc_lblFilter.anchor = GridBagConstraints.EAST;
				gbc_lblFilter.gridx = 0;
				gbc_lblFilter.gridy = 1;
				panelSearch.add(lblFilter, gbc_lblFilter);
			}
			{
				textFieldFilter = new JTextField();
				textFieldFilter.addKeyListener(createFilterListener());
				GridBagConstraints gbc_textFieldFilter = new GridBagConstraints();
				gbc_textFieldFilter.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldFilter.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldFilter.weightx = 1.0;
				gbc_textFieldFilter.gridx = 1;
				gbc_textFieldFilter.gridy = 1;
				panelSearch.add(textFieldFilter, gbc_textFieldFilter);
				textFieldFilter.setColumns(10);
			}
			{
				JScrollPane scrollPaneSearch = new JScrollPane();
				scrollPaneSearch.setPreferredSize(new Dimension(150, 180));
				scrollPaneSearch.setMinimumSize(new Dimension(150, 180));
				GridBagConstraints gbc_scrollPaneSearch = new GridBagConstraints();
				gbc_scrollPaneSearch.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPaneSearch.gridwidth = 2;
				gbc_scrollPaneSearch.fill = GridBagConstraints.BOTH;
				gbc_scrollPaneSearch.gridx = 0;
				gbc_scrollPaneSearch.gridy = 2;
				panelSearch.add(scrollPaneSearch, gbc_scrollPaneSearch);
				{
					DefaultListModel<Person> listModel = new DefaultListModel<Person>();
					this.searchFilter.setJListModel(listModel);
					this.listSearch = new JList<Person>(listModel);
					listSearch.addListSelectionListener(createSearchListener());
					listSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPaneSearch.setViewportView(listSearch);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			GridBagLayout gbl_buttonPane = new GridBagLayout();
			gbl_buttonPane.columnWidths = new int[] {};
			gbl_buttonPane.rowHeights = new int[] { 25, 0 };
			gbl_buttonPane.columnWeights = new double[] { 0.0, 0.0 };
			gbl_buttonPane.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			buttonPane.setLayout(gbl_buttonPane);
			{
				this.submitButton = new JButton("Best\u00E4tigen");
				submitButton.setEnabled(false);
				submitButton.setActionCommand("ok");
				submitButton.addActionListener(createSubmitListener());
				GridBagConstraints gbc_submitButton = new GridBagConstraints();
				gbc_submitButton.insets = new Insets(0, 0, 0, 5);
				gbc_submitButton.gridx = 0;
				gbc_submitButton.gridy = 0;
				buttonPane.add(submitButton, gbc_submitButton);
				getRootPane().setDefaultButton(submitButton);
			}
			{
				JButton dontImportButton = new JButton("Kontakt nicht importieren");
				dontImportButton.addActionListener(createDontImportListener());
				dontImportButton.setActionCommand("dontImport");
				GridBagConstraints gbc_dontImportButton = new GridBagConstraints();
				gbc_dontImportButton.gridx = 1;
				gbc_dontImportButton.gridy = 0;
				buttonPane.add(dontImportButton, gbc_dontImportButton);
			}
		}
	}

	private ActionListener createSubmitListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (resultType == RESULT_CREATE_CONTACT)
					result =
						new Person(textFieldPrename.getText(), textFieldName.getText());
				if (resultType != 0)
					setVisible(false);
			}
		};
	}

	private WindowListener createWindowListener() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				askToCloseForSure();
			}
		};
	}

	private void askToCloseForSure() {
		int option =
			JOptionPane.showConfirmDialog(this,
				"Wollen Sie den Import wirklich abbrechen?", "Import",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			this.resultType = RESULT_CLOSE_IMPORT;
			this.result = null;
			this.setVisible(false);
		}
	}

	private KeyListener createKeyListener() {
		return new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent e) {
				rdbtnNewContact.setSelected(true);
				checkIfFormIsOk();
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

		};
	}

	private ListSelectionListener createSimilarListListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listSimilarContacts.getSelectedValue() != null) {
					rdbtnSimilarContacts.setSelected(true);
					checkIfFormIsOk();
				}
			}
		};
	}

	private ActionListener createDontImportListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resultType = RESULT_DONT_IMPORT;
				result = null;
				setVisible(false);
			}
		};
	}

	private ListSelectionListener createSearchListener() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (listSearch.getSelectedValue() != null) {
					rdbtnSearchContact.setSelected(true);
					checkIfFormIsOk();
				}
			}
		};
	}

	private KeyListener createFilterListener() {
		return new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent arg0) {
				searchFilter.setFilterName(textFieldFilter.getText());
			}

			@Override
			public void keyTyped(KeyEvent arg0) {}
		};
	}

	/**
	 * use public fields RESULT_* to process info
	 * 
	 * @return type of action the user selected in the dialog
	 */
	public int getResultType() {
		return this.resultType;
	}

	/**
	 * @return the person which was selected or created using this dialog
	 */
	public Person getResult() {
		return this.result;
	}
}
