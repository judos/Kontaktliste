package model.archivP;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ArchivTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 5418848879724381518L;
	
	final ImageIcon box = new ImageIcon("Data/layout/box.png");
	final ImageIcon folder = new ImageIcon("Data/layout/folder.png");
	final ImageIcon file = new ImageIcon("Data/layout/file.png");
	final ImageIcon bag = new ImageIcon("Data/layout/bag.png");

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) value;
		int childs=currentTreeNode.getChildCount();
		String name;
		 
		try {
			ArchivedObject obj = (ArchivedObject) currentTreeNode
					.getUserObject();
			name = obj.name;
		} catch (ClassCastException e) {
			name = currentTreeNode.toString();
		}
		name = name.toLowerCase();
		ImageIcon x = file;
		if (name.toLowerCase().contains("kiste")
				|| name.toLowerCase().contains("schachtel"))
			x = box;
		if (name.toLowerCase().contains("ordner"))
			x = folder;
		if (name.toLowerCase().contains("sack")
				|| name.toLowerCase().contains("tasche"))
			x = bag;
		if (x.equals(file) && childs>0)
			x=folder;
		setLeafIcon(x);
		setIcon(x);
		setOpenIcon(x);
		setClosedIcon(x);
		

		return super.getTreeCellRendererComponent(tree, value, sel, expanded,
				leaf, row, hasFocus);
	}
}
