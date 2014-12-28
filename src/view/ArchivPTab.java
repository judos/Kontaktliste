package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.archivP.ArchivTreeCellRenderer;
import model.archivP.ArchiveTreeModel;
import model.archivP.ArchivedObject;
import model.archivP.ArchivedObjectComparator;
import controller.ArchivP;

public class ArchivPTab extends JPanel {

	private static final long		serialVersionUID	= -7416032896535651350L;
	private ArchivP					arc;
	private DefaultMutableTreeNode	treeRoot;
	private DefaultTreeModel		treeModel;
	private JTree					tree;
	private ArchivedObject			treeRootObject;
	private JLabel					aktuellAusgeschnitten;
	private JPanel					aktuellAusgeschnittenPanel;
	private JScrollPane				treeScrollPane;

	public ArchivPTab(ArchivP arc, ArchivedObject root) {
		super();
		this.arc = arc;
		this.treeRootObject = root;

		this.setLayout(new GridBagLayout());
		createContent();
	}

	private void createContent() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.insets = new Insets(3, 3, 3, 3);

		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		this.add(new JLabel("Suche:"), c);

		c.gridy = 1;
		JTextField suche = new JTextField();
		suche.setMinimumSize(new Dimension(150, 20));
		suche.setPreferredSize(new Dimension(150, 20));
		suche.addKeyListener(new controller.archivP.SucheListener(this.arc, suche));
		this.add(suche, c);

		c.gridy = 2;
		JLabel x = new JLabel(
			"<html><u>Umbenennen:</u> Klicke ein Element an " + "und drücke F2 um es umzubenennen.</html>");
		x.setPreferredSize(new Dimension(150, 45));
		x.setMinimumSize(new Dimension(150, 45));
		this.add(x, c);

		c.gridy = 3;
		x = new JLabel(
			"<html><u>Hinzufügen/Löschen:</u> Klicke rechts " + "auf ein Element.</html>");
		x.setPreferredSize(new Dimension(150, 60));
		x.setMinimumSize(new Dimension(150, 60));
		this.add(x, c);

		c.gridy = 4;
		x = new JLabel(
			"<html><u>Verschieben:</u> Wähle einen Knoten und " + "schneide ihn aus um ihn einem anderen " + "einzufügen.");
		x.setPreferredSize(new Dimension(150, 60));
		x.setMinimumSize(new Dimension(150, 60));
		this.add(x, c);

		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		aktuellAusgeschnittenPanel = new JPanel();
		aktuellAusgeschnittenPanel.setBackground(Color.green);
		aktuellAusgeschnitten = new JLabel("");
		aktuellAusgeschnittenPanel.add(aktuellAusgeschnitten);
		aktuellAusgeschnittenPanel.setVisible(false);
		this.add(aktuellAusgeschnittenPanel, c);

		c.gridy = 6;
		JButton allesAusklappen = new JButton("Alles Ausklappen");
		allesAusklappen.setActionCommand("expandAll");
		allesAusklappen.addActionListener(this.arc);
		this.add(allesAusklappen, c);

		c.gridy = 7;
		JButton allesEinklappen = new JButton("Alles Einklappen");
		allesEinklappen.setActionCommand("collapseAll");
		allesEinklappen.addActionListener(this.arc);
		this.add(allesEinklappen, c);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 10;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 8;
		this.add(createTree(), c);
	}

	public void setCutNode(ArchivedObject object) {
		if (object != null) {
			this.aktuellAusgeschnittenPanel.setVisible(true);
			this.aktuellAusgeschnitten
				.setText("<html>Ausgeschnittener Knoten:<br>" + object.name + "</html>");
		} else {
			this.aktuellAusgeschnittenPanel.setVisible(false);
		}
	}

	private Component createTree() {
		treeRoot = new DefaultMutableTreeNode(this.treeRootObject);
		treeModel = new ArchiveTreeModel(treeRoot);
		tree = new JTree(treeModel);
		treeModel.addTreeModelListener(new controller.archivP.TreeModelListener());
		tree.addMouseListener(new controller.archivP.TreeMouseListener(this.arc, tree, treeRoot));

		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		tree.setCellRenderer(new ArchivTreeCellRenderer());

		treeScrollPane = new JScrollPane(tree);
		return treeScrollPane;
	}

	public void buildTree(ArchivedObject root, String suchText) {
		suchText = suchText.toLowerCase();
		treeRoot.removeAllChildren();
		for (int index = 0; index < root.list.size(); index++) {
			ArchivedObject obj = root.list.get(index);
			buildTwig(this.treeRoot, obj, suchText);
		}
		treeModel.reload();
		for (int i = 0; i < tree.getRowCount(); i++)
			tree.expandRow(i);
		tree.setRootVisible(true);
	}

	private int buildTwig(DefaultMutableTreeNode parent, ArchivedObject obj, String suchText) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(obj);
		parent.add(node);
		int anzahl = 1;
		Collections.sort(obj.list, new ArchivedObjectComparator());
		for (ArchivedObject child : obj.list) {
			if (obj.name.toLowerCase().contains(suchText))
				anzahl += buildTwig(node, child, "");
			else if (child.list.size() > 0 || child.name.toLowerCase().contains(suchText)) {
				anzahl += buildTwig(node, child, suchText);
			}
		}

		if (obj.list.size() != 0 && anzahl == 1) {
			node.removeFromParent();
			anzahl = 0;
		}
		return anzahl;
	}

	public void expandAll() {
		for (int i = 0; i < tree.getRowCount(); i++)
			tree.expandRow(i);
	}

	public void collapseAll() {
		tree.expandRow(0);
		for (int i = tree.getRowCount(); i >= 1; i--)
			tree.collapseRow(i);
		// optimizeHeight();
	}

	public void optimizeHeight() {
		int rowHeight = 20;
		int spaceAvailable = treeScrollPane.getHeight();
		// int totRows=Swing.getTreeNodesTotal(tree);
		// int totHeight=tree.getPreferredSize().height;
		int displayRows = spaceAvailable / rowHeight;
		int curRows = tree.getRowCount();
		while (curRows > displayRows) {

			int depthColl = treeRoot.getDepth();
			@SuppressWarnings("unchecked")
			Enumeration<DefaultMutableTreeNode> order = treeRoot.postorderEnumeration();
			while (order.hasMoreElements()) {
				DefaultMutableTreeNode nnode = order.nextElement();
				System.out.println("level: " + nnode.getDepth() + " / " + depthColl);
				if (nnode.getDepth() == 1) {
					System.out.println("collapse: " + nnode.toString());
					TreePath p = new TreePath(nnode.getPath());
					tree.collapsePath(p);
					break;
				}

			}
			curRows = tree.getRowCount();
			System.out.println(curRows);
		}

		System.out.println(curRows);
		// int height2 = tree.getPreferredScrollableViewportSize().height;
		// int height = tree.getRowHeight();// returns 0
	}

}
