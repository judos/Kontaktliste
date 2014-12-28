package model.kontakte;

import java.util.ArrayList;
import controller.Main;
import model.ObjectData;
import model.Serializable;

public class Gruppe implements Serializable {

	public static String file = "gruppen.txt";

	private ArrayList<Person> personen;
	private String name;
	public String beschreibung;

	public Gruppe(String name) {
		constructorInit();
		this.name = name;
	}

	public Gruppe(ObjectData x) {
		constructorInit();
		this.name = x.getValue("name");
		this.beschreibung = x.getValue("besch");
		ArrayList<String> members = x.getEmptyValues();
		for (String member : members) {
			Person person = Main.data.searchPerson(member);
			if (person != null)
				addPerson(person);
		}
	}

	private void constructorInit() {
		this.name = "";
		this.beschreibung = "";
		this.personen = new ArrayList<Person>();
	}

	public static Gruppe deserialize(ObjectData linedata) {
		return new Gruppe(linedata);
	}

	public ObjectData serialize() {
		ArrayList<String> vars = new ArrayList<String>();
		vars.add("Gruppe");
		vars.add("name");
		vars.add(this.name);
		vars.add("besch");
		vars.add(this.beschreibung);
		for (int i = 0; i < this.personen.size(); i++) {
			vars.add(this.personen.get(i).getFullName());
			vars.add("");
		}
		String[] var = vars.toArray(new String[] {});
		ObjectData x = new ObjectData(var);
		return x;
	}

	public void addPerson(Person p) {
		this.personen.add(p);
		p.getGruppen().add(this);
	}

	public void removePerson(Person p) {
		this.personen.remove(p);
		p.getGruppen().remove(this);
	}

	public ArrayList<Person> getPersonen() {
		return personen;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.name;
	}

	public boolean rename(String name) {
		if (!"".equals(name)) {
			if (name.length()>1) {
				this.name=name;
				return true;
			}
		}
		return false;
	}
}
