package controller.archivP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import model.archivP.ArchivedObject;
import controller.ArchivP;

public class EintragLoschenListener implements ActionListener {

	private ArchivP					arc;
	private DefaultMutableTreeNode	node;

	public EintragLoschenListener(ArchivP arc, DefaultMutableTreeNode node) {
		this.arc = arc;
		this.node = node;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane
			.showConfirmDialog(
				null,
				"Bist du sicher, dass du dieses Objekt löschen willst? \nEs werden damit alle Unterobjekte gelöscht.",
				"Objekt löschen (" + this.node + ")", JOptionPane.YES_NO_OPTION,
				JOptionPane.ERROR_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			ArchivedObject objekt = (ArchivedObject) node.getUserObject();
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
			ArchivedObject parent = (ArchivedObject) parentNode.getUserObject();
			node.removeFromParent();
			parent.removeSubObject(objekt);
			this.arc.refreshTree();
		}
	}

}
