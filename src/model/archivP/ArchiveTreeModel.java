package model.archivP;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ArchiveTreeModel extends DefaultTreeModel {
	
	private static final long serialVersionUID = -7634179496359589375L;

	public ArchiveTreeModel(TreeNode arg0) {
		super(arg0);
	}
	
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		DefaultMutableTreeNode node=(DefaultMutableTreeNode) path.getLastPathComponent();
		
		ArchivedObject obj=(ArchivedObject) node.getUserObject();
		obj.name=(String)newValue;
		this.reload(node);
	}
	
}
