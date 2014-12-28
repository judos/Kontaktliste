package model;

import helpers.Basic;
import helpers.FileH;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.kontakte.Gruppe;
import model.kontakte.GruppenComparator;
import model.kontakte.Person;
import model.kontakte.PersonenComparator;
import controller.Main;

public class Data implements Savable {
	// XXX: switch groups/contacts to csv file types

	private ArrayList<Person>	personen;
	private ArrayList<Gruppe>	gruppen;

	private String				personenfile;
	private String				gruppenfile;
	private int					kontaktId	= 1;

	public Data() {
		personen = new ArrayList<Person>();
		gruppen = new ArrayList<Gruppe>();
		this.personenfile = Main.dataFolder + Person.file;
		this.gruppenfile = Main.dataFolder + Gruppe.file;
	}

	public void load() {
		File dataFolder = new File("Data/");
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		long x1 = System.currentTimeMillis();

		this.kontaktId = Main.config.getI(Config.id, 1);
		loadPersonenFromFile();
		loadGruppenFromFile();
		long x2 = System.currentTimeMillis();
		Main.debugger.debug("Data loading: " + (x2 - x1) + "ms");

	}

	public int getNextKontaktId() {
		int result = kontaktId;
		kontaktId++;
		return result;
	}

	private void loadGruppenFromFile() {
		BufferedReader reader;
		try {
			reader = FileH.getReaderForFile(new File(this.gruppenfile));
		} catch (FileNotFoundException e1) {
			Main.debugger.debug("Daten nicht gefunden (" + this.gruppenfile + ")");
			return;
		}

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				ObjectData x = new ObjectData(line);
				if (x.isValid()) {
					if ("Gruppe".equals(x.getName()))
						addGruppe(new Gruppe(x));
					else
						Main.debugger
							.debug("Unbekanntes Objekt (" + x.getName() + ") in Zeile: " + line);
				} else
					Main.debugger.debug("Fehler bei Zeile: " + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadPersonenFromFile() {
		BufferedReader reader;
		try {
			reader = FileH.getReaderForFile(new File(this.personenfile));
		} catch (FileNotFoundException e1) {
			Main.debugger.debug("Daten nicht gefunden (" + this.personenfile + ")");
			return;
		}

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				ObjectData x = new ObjectData(line);
				if (x.isValid()) {
					if ("Person".equals(x.getName()))
						addPerson(new Person(x));
					else
						Main.debugger.debug("Unbekanntes Objekt: " + x.getName());
				} else
					Main.debugger.debug("Fehler bei Zeile: " + line);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param wholename = first name+" "+surname
	 * @return the first found contact or null if none found
	 */
	public Person searchPerson(String wholename) {
		for (Person p : this.personen) {
			if ((p.getVorname() + " " + p.getName()).equals(wholename))
				return p;
		}
		return null;
	}

	public Gruppe searchGruppe(String name) {
		for (Gruppe g : this.gruppen) {
			if (g.getName().equals(name))
				return g;
		}
		return null;
	}

	public ArrayList<Person> getPersonen() {
		return personen;
	}

	public ArrayList<Gruppe> getGruppen() {
		return gruppen;
	}

	public void addGruppe(Gruppe g) {
		this.gruppen.add(g);
		Collections.sort(this.gruppen, new GruppenComparator());
	}

	public void addPerson(Person p) {
		this.personen.add(p);
		Collections.sort(this.personen, new PersonenComparator());
	}

	@Override
	public void saveAll() {
		savePersonenToFile();
		saveGruppenToFile();
		FileH.writeToFile(Main.dataFolder + "letzte änderung.txt", Basic.getDateTime());
	}

	private void saveGruppenToFile() {
		saveListToFile(this.gruppenfile, this.gruppen, new String[] { "name" });
	}

	private void savePersonenToFile() {
		saveListToFile(this.personenfile, this.personen, new String[] { "vorname", "name", "tel" });
	}

	private void saveListToFile(String filename, List<? extends Serializable> liste, String[] order) {
		BufferedWriter writer;
		try {
			writer = FileH.getWriterForFile(new File(filename));
		} catch (IOException e) {
			Main.debugger
				.debug("Daten konnten nicht geschrieben werden. " + e + "\nDateiname: " + filename);
			return;
		}
		for (Serializable object : liste) {
			try {
				writer.write(object.serialize().serialize(order));
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removePerson(Person person) {
		this.personen.remove(person);
		for (Gruppe g : person.getGruppen()) {
			g.getPersonen().remove(person);
		}
	}

	public void removeGruppe(Gruppe gruppe) {
		this.gruppen.remove(gruppe);
	}

}
