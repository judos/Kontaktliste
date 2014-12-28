package model.archivP;

import java.util.Comparator;

public class ArchivedObjectComparator implements
		Comparator<ArchivedObject> {

	public int compare(ArchivedObject o1, ArchivedObject o2) {
		int s1=o1.list.size();
		int s2=o2.list.size();
		
		String name1=o1.name;
		String name2=o2.name;
		
		int res=0;
		if (s1>0 && s2==0)
			res=-1;
		else if (s2>0 && s1==0)
			res=1;
		else
			res= (int) Math.signum(name1.compareToIgnoreCase(name2));

		return res;
	}

}
