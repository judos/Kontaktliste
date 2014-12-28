package model.kontakte;

import java.util.Comparator;

public class PersonenComparator implements Comparator<Person> {

	public int compare(Person arg0, Person arg1) {
		String n0=arg0.getVorname();
		String n1=arg1.getVorname();
		
		return n0.compareToIgnoreCase(n1);
	}
}
