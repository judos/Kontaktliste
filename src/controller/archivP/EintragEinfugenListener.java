package controller.archivP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.tree.DefaultMutableTreeNode;

import model.archivP.ArchivedObject;

import controller.ArchivP;

public class EintragEinfugenListener implements ActionListener {

	private ArchivP arc;
	private ArchivedObject insertNode;

	public EintragEinfugenListener(ArchivP arc, DefaultMutableTreeNode node) {
		this.arc=arc;
		this.insertNode=(ArchivedObject) node.getUserObject();
	}

	public void actionPerformed(ActionEvent e) {
		ArchivedObject cutNode=this.arc.getCutNode();
		if (cutNode!=null) {
			
			cutNode.parent.removeSubObject(cutNode);
			this.insertNode.addSubObject(cutNode);
			this.arc.setCutNode(null);
			this.arc.refreshTree();
		}
	}

}
