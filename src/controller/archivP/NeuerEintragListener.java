package controller.archivP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import model.archivP.ArchivedObject;
import controller.ArchivP;

public class NeuerEintragListener implements ActionListener {

	private DefaultMutableTreeNode	parentNode;
	private ArchivP					arc;

	public NeuerEintragListener(ArchivP archivP, DefaultMutableTreeNode node) {
		this.arc = archivP;
		this.parentNode = node;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String msg = "Hier kannst du den Namen des Objektes eingeben:";
		int msgTyp = JOptionPane.QUESTION_MESSAGE;
		String name = "";

		name = (String) JOptionPane.showInputDialog(null, msg, "Objekt hinzufügen", msgTyp, null,
			null, name);
		if (name != null) {
			ArchivedObject objekt = new ArchivedObject(name);
			parentNode.add(new DefaultMutableTreeNode(objekt));
			ArchivedObject parent = (ArchivedObject) parentNode.getUserObject();
			parent.addSubObject(objekt);
			arc.refreshTree();
		}

	}

}
