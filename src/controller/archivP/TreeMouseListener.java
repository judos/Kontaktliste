package controller.archivP;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import controller.ArchivP;

public class TreeMouseListener implements MouseListener {

	private JTree					tree;
	private ArchivP					arc;
	private DefaultMutableTreeNode	root;

	public TreeMouseListener(ArchivP archivP, JTree tree, DefaultMutableTreeNode treeRoot) {
		this.arc = archivP;
		this.tree = tree;
		this.root = treeRoot;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			TreePath x = tree.getPathForLocation(e.getX(), e.getY());
			if (x != null) { // no node was hit by the mouse
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) x.getLastPathComponent();

				TreePath treePath = new TreePath(node.getPath());
				tree.setSelectionPath(treePath);
				tree.scrollPathToVisible(treePath);
				JPopupMenu menu = new JPopupMenu();
				JMenuItem menuItem = new JMenuItem("Neuer Inhalt");
				menu.add(menuItem);
				menuItem.addActionListener(new NeuerEintragListener(this.arc, node));

				if (this.arc.getCutNode() != null) {
					menuItem = new JMenuItem("Einfügen");
					menuItem.addActionListener(new EintragEinfugenListener(this.arc, node));
					menu.add(menuItem);
				}

				if (!node.equals(this.root)) {
					menuItem = new JMenuItem("Ausschneiden");
					menuItem.addActionListener(new EintragAusschneidenListener(this.arc, node));
					menu.add(menuItem);
					menuItem = new JMenuItem("Löschen");
					menuItem.addActionListener(new EintragLoschenListener(this.arc, node));
					menu.add(menuItem);

				}
				menu.show(e.getComponent(), e.getX(), e.getY());

			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
