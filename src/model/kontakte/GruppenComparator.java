package model.kontakte;

import java.util.Comparator;


public class GruppenComparator implements Comparator<Gruppe> {

	public int compare(Gruppe o1, Gruppe o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
