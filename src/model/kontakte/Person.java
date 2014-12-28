package model.kontakte;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Date;
import model.ObjectData;
import model.Serializable;
import controller.Main;

public class Person implements Serializable {
	public static final String	file	= "personen.txt";

	private ArrayList<Gruppe>	gruppen;
	private ArrayList<Bild>		bilder;
	private String				vorname;
	private String				name;
	public String				beschreibung;
	private int					id;
	public Date					geburtstag;
	public String				tel;
	public int					preferedImage;
	public String				homeTel;
	public String				email;
	public String				msn;
	public String				strasse;
	public String				plz;
	public String				ort;
	public String				land;

	public boolean				wichtig;

	public Person(String vorname, String name) {
		constructorInit();

		this.vorname = vorname;
		this.name = name;
		this.id = Main.data.getNextKontaktId();

		bilderSuchen();
	}

	public Person(ObjectData linedata) {
		constructorInit();

		HashMap<String, String> keys = linedata.getValues();
		boolean idSet = false;
		this.name = linedata.getValue("name");
		this.vorname = linedata.getValue("vorname");
		if (keys.containsKey("id")) {
			this.id = Integer.valueOf(linedata.getValue("id"));
			idSet = true;
		}
		this.beschreibung = linedata.getValue("besch");
		if (keys.containsKey("geburtstag")) {
			try {
				this.geburtstag = Date.parse(linedata.getValue("geburtstag"));
			} catch (IllegalArgumentException e) {
				Main.debugger.debug(e);
				this.geburtstag = null;
			}
		} else
			this.geburtstag = new Date();
		this.tel = linedata.getValue("tel");
		this.preferedImage = Integer.valueOf(linedata.getValue("preferedimage"));
		this.homeTel = linedata.getValue("hometel");
		this.email = linedata.getValue("email");
		this.msn = linedata.getValue("msn");
		this.strasse = linedata.getValue("strasse");
		this.plz = linedata.getValue("plz");
		this.ort = linedata.getValue("ort");
		this.land = linedata.getValue("land");
		this.wichtig = Boolean.valueOf(linedata.getValue("wichtig"));

		if (!idSet)
			this.id = Main.data.getNextKontaktId();

		bilderSuchen();
	}

	private void constructorInit() {
		this.gruppen = new ArrayList<Gruppe>();
		this.bilder = new ArrayList<Bild>();
		this.beschreibung = "";
		this.tel = "";
		this.geburtstag = null;
		this.preferedImage = 0;
		this.homeTel = "";
		this.email = "";
		this.msn = "";
		this.strasse = "";
		this.ort = "";
		this.plz = "";
		this.land = "";
		this.wichtig = false;
	}

	public ObjectData serialize() {
		String[] vars = { "Person", "name", this.name, "vorname", this.vorname, "id", String
			.valueOf(this.id), "besch", this.beschreibung, "tel", this.tel, "geburtstag", getBirthdayDateString(), "preferedimage", String
			.valueOf(this.preferedImage), "hometel", this.homeTel, "email", this.email, "msn", this.msn, "strasse", this.strasse, "ort", this.ort, "plz", this.plz, "land", this.land, "wichtig", String
			.valueOf(this.wichtig) };
		ObjectData x = new ObjectData(vars);
		return x;
	}

	public String getBirthdayDateString() {
		if (this.geburtstag != null)
			return this.geburtstag.toString();
		return "";
	}

	public static Person deserialize(ObjectData linedata) {
		return new Person(linedata);
	}

	public boolean isInGruppe(Gruppe g) {
		return this.gruppen.contains(g);
	}

	public boolean isInAnyGruppe(List<Gruppe> testGruppen) {
		List<Gruppe> gross;
		List<Gruppe> klein;
		if (testGruppen.size() < this.gruppen.size()) {
			gross = this.gruppen;
			klein = testGruppen;
		} else {
			gross = testGruppen;
			klein = this.gruppen;
		}

		for (Gruppe g : klein) {
			if (gross.contains(g))
				return true;
		}
		return false;
	}

	public void changeImage(int offset) {
		this.preferedImage += offset;
		if (this.preferedImage >= this.bilder.size())
			this.preferedImage = 0;
		if (this.preferedImage < 0)
			this.preferedImage = this.bilder.size() - 1;
	}

	public ArrayList<Bild> getBilder() {
		return bilder;
	}

	public void addBild(Bild bild) {
		this.bilder.add(bild);
	}

	private void bilderSuchen() {
		int i = 0;
		int failed = 0;
		do {
			Bild b = null;
			try {
				b = new Bild(this.getFullName() + " " + i + ".jpg");
			} catch (IOException e) {}
			if (b != null && b.valid) {
				this.bilder.add(b);
				failed = 0;
			} else {
				failed++;
			}
			i++;
		} while (failed < 3);
	}

	public boolean rename(String wholename) {
		String[] n = wholename.split(" ");
		if (n.length != 2)
			return false;
		this.vorname = n[0];
		this.name = n[1];
		return true;
	}

	public void addToGruppe(Gruppe g) {
		this.gruppen.add(g);
		g.getPersonen().add(this);
	}

	public void removeFromGruppe(Gruppe g) {
		this.gruppen.remove(g);
		g.getPersonen().remove(this);
	}

	public ArrayList<Gruppe> getGruppen() {
		return gruppen;
	}

	public String getVorname() {
		return vorname;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getFullName();
	}

	/**
	 * @return vorname+" "+name
	 */
	public String getFullName() {
		return this.vorname + " " + this.name;
	}

	public String getStatus() {
		if (this.wichtig)
			return "VIP";
		else
			return "-";
	}

	public void delete() {
		for (Bild b : this.bilder) {
			b.delete();
		}
	}

	/**
	 * @param att an attribute of the contact
	 * @return the value for this person
	 */
	public String get(PersonEnum att) {
		try {
			Field f = Person.class.getField(att.name);
			return String.valueOf(f.get(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
