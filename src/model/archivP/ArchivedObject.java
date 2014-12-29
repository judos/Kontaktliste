package model.archivP;

import java.util.ArrayList;
import java.util.Collections;

import ch.judos.generic.data.StringUtils;

public class ArchivedObject {

	public String						name;
	public ArrayList<ArchivedObject>	list;
	public ArchivedObject				parent;

	public ArchivedObject(String name) {
		this.list = new ArrayList<ArchivedObject>();
		this.name = name;
	}

	public void addSubObject(ArchivedObject obj) {
		this.list.add(obj);
		obj.parent = this;
		if (this.list.size() == 1 && this.parent != null)
			this.parent.sort();
		if (this.list.size() > 1)
			sort();
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String toString(int indent) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < indent; i++)
			b.append("\t");
		b.append(this.name);
		if (this.list.size() == 0) {
			b.append(StringUtils.newline);
		} else {
			b.append(" {" + StringUtils.newline);
			for (ArchivedObject obj : this.list)
				b.append(obj.toString(indent + 1));
			for (int i = 0; i < indent; i++)
				b.append("\t");
			b.append("}" + StringUtils.newline);
		}
		return b.toString();
	}

	public void removeSubObject(ArchivedObject objekt) {

		this.list.remove(objekt);
		objekt.parent = null;
		Collections.sort(this.list, new ArchivedObjectComparator());
		if (this.list.size() == 0 && this.parent != null)
			this.parent.sort();
	}

	public void sort() {
		Collections.sort(this.list, new ArchivedObjectComparator());
	}

	public void sortRec() {
		Collections.sort(this.list, new ArchivedObjectComparator());
		for (ArchivedObject o : this.list)
			o.sortRec();
	}

}
