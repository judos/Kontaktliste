package controller.archivP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.tree.DefaultMutableTreeNode;

import model.archivP.ArchivedObject;

import controller.ArchivP;

public class EintragAusschneidenListener implements ActionListener {

	private ArchivP arc;
	private DefaultMutableTreeNode node;

	public EintragAusschneidenListener(ArchivP arc, DefaultMutableTreeNode node) {
		this.arc=arc;
		this.node=node;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (this.node!=null) {
			this.arc.setCutNode((ArchivedObject) this.node.getUserObject());
		}
	}

}
