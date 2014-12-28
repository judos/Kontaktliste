package controller;

import java.util.ArrayList;

import model.Config;
import model.MyActionListenerI;
import model.Savable;
import model.kontakte.FilterPerson;
import model.kontakte.Gruppe;
import model.kontakte.Person;
import view.KontaktTab;
import view.KontaktTab.aktuelleAuswahl;
import view.Window;

public class Kontakte implements Savable, MyActionListenerI {

	// View correspondant
	private KontaktTab		tab;
	private Window			window;

	// prevent multiple calls to refresh method
	private boolean			refreshingFilter;

	// Selected items
	private Person			selectedPerson;
	private Gruppe			selectedGruppe;

	// Filter object to handle the current values of filter
	public FilterPerson		personenFilter;

	private String			filterGruppe;

	public aktuelleAuswahl	aktuelleAuswahlTyp;

	public Kontakte(Window window) {
		this.refreshingFilter = false;
		this.window = window;

		personenFilter = FilterPerson.createNormalFilter();
		personenFilter.autoUpdateList = true;
		personenFilter.addActionListener(this);
		tab = new KontaktTab(this, personenFilter);
		window.addTab("Kontakte", tab, 0);

		Config c = Main.config;
		if (c.isSet(Config.filterPersonWichtig)) {
			boolean filterPersonWichtig = c.getB(Config.filterPersonWichtig, false);
			this.tab.setWichtigAuswahl(filterPersonWichtig);
			personenFilter.setFilterWichtig(filterPersonWichtig);
		}

		this.filterGruppe = "";
		refreshFilter();
		this.tab.setSelectedPerson(null);
	}

	public Window getWindow() {
		return this.window;
	}

	public int anzahlPersonenInListe() {
		return this.tab.anzahlPersonenInListe();
	}

	public int anzahlGruppenInListe() {
		return this.tab.anzahlGruppenInListe();
	}

	public void refreshFilter() {
		if (!this.refreshingFilter) {
			this.refreshingFilter = true;
			selectedGruppe = refreshGruppenListe();
			this.refreshingFilter = false;
			this.refreshWindow();
		}
	}

	private Gruppe refreshGruppenListe() {
		boolean foundLast = false;
		filterGruppe = filterGruppe.toLowerCase();

		ArrayList<Gruppe> gruppenList = Main.data.getGruppen();
		ArrayList<Gruppe> foundList = new ArrayList<Gruppe>();
		foundList.add(null); // Gruppe <Alle>
		for (Gruppe g : gruppenList) {
			String name = g.getName().toLowerCase();
			boolean show = true;
			// Name filtern
			if (!"".equals(filterGruppe) && !name.contains(filterGruppe))
				show = false;

			if (show) {
				foundList.add(g);
				if (selectedGruppe != null && g == selectedGruppe)
					foundLast = true;
			}
		}
		Gruppe newSelected = selectedGruppe;
		if (foundList.size() == 1)
			newSelected = foundList.get(0);
		else if (foundLast == false)
			newSelected = null;

		this.tab.refreshGruppenListe(foundList, newSelected);

		return newSelected;
	}

	// Speichert alle Filter Informationen
	@Override
	public void saveAll() {
		Main.config.set(Config.filterPersonWichtig, this.personenFilter.isFilterWichtig());
	}

	// Wählt eine Person an und zeigt deren Infos an
	public void setSelectedP(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
		this.personenFilter.setSelection(selectedPerson);
		this.tab.setSelectedPerson(selectedPerson);
		this.tab.selectListEntry(selectedPerson);

		this.aktuelleAuswahlTyp = KontaktTab.aktuelleAuswahl.Person;
	}

	// Die Auswahl wird verworfen
	public void unselectAll() {
		setSelectedP(null);
		setSelectedG(null);
		this.aktuelleAuswahlTyp = KontaktTab.aktuelleAuswahl.nichts;
	}

	// Wählt eine Gruppe an und zeigt deren Infos an
	public void setSelectedG(Gruppe selectedGruppe) {
		this.selectedGruppe = selectedGruppe;
		this.tab.setSelectedGruppe(selectedGruppe);
		if (selectedGruppe != null)
			this.tab.selectListEntry(selectedGruppe);
		refreshFilter();
		this.aktuelleAuswahlTyp = KontaktTab.aktuelleAuswahl.Gruppe;
	}

	// Liefert aktuell ausgewählte Person
	public Person getSelectedPerson() {
		return selectedPerson;
	}

	// Liefert aktuell ausgewählte Gruppe
	public Gruppe getSelectedGruppe() {
		return selectedGruppe;
	}

	// Aktualisiert das angezeigte Bild
	public void refreshBild() {
		this.tab.setBild(this.selectedPerson);
	}

	// Wählt einen Eintrag in der Liste an
	public void selectListEntry(Gruppe g) {
		this.tab.selectListEntry(g);
		setSelectedG(g);
	}

	// Wählt einen Eintrag in der Liste an
	public void selectListEntry(Person p) {
		this.tab.selectListEntry(p);
		setSelectedP(p);
	}

	// Löscht eine Person
	public void deletePerson(Person p) {
		Main.data.removePerson(p);
		p.delete();
		this.refreshFilter();
	}

	// wholename is expected to be "prename surname"
	public boolean personExists(String wholename) {
		String[] name = wholename.split(" ");
		if (name.length == 2) {
			String vorname = name[0].trim();
			String nachname = name[1].trim();
			return personExists(vorname, nachname);
		}
		return false;
	}

	// Testet ob eine Person mit diesem Namen schon existiert
	public boolean personExists(String vorname, String nachname) {
		for (Person p : Main.data.getPersonen()) {
			if (p.getVorname().equals(vorname) && p.getName().equals(nachname))
				return true;
		}
		return false;
	}

	// Erstellt eine neue Person
	// wholename is expected to be "prename surname"
	public Person createPerson(String wholename, boolean setSelectedPerson) {
		String[] name = wholename.split(" ");
		if (name.length == 2) {
			String vorname = name[0].trim();
			String nachname = name[1].trim();
			if (personExists(vorname, nachname))
				return null;
			Person p = new Person(vorname, nachname);
			Main.data.addPerson(p);

			if (setSelectedPerson) {
				this.tab.setWichtigAuswahl(false);
				setSelectedP(p);
			}
			return p;
		}
		return null;
	}

	// Erstellt eine neue Gruppe
	public Gruppe createGruppe(String name) {
		name = name.trim();
		if (name.length() > 0) {
			Gruppe g = new Gruppe(name);
			Main.data.addGruppe(g);

			setSelectedG(g);
			return g;
		}
		return null;
	}

	// Setzt den neuen Text des Gruppenfilters
	public void setFilter(String gruppeName) {
		this.filterGruppe = gruppeName;
		this.tab.setFilter(gruppeName);
		this.refreshFilter();
	}

	public void setFilterGruppeFromListener(String filter) {
		this.filterGruppe = filter;
		this.refreshFilter();
	}

	// ändert die Korrektheit des Geburtsdatums (bg farbe ändert)
	public void setGeburtstagValid(boolean valid) {
		this.tab.setGeburtstagValid(valid);
	}

	// Aktiviert Fokus
	public void requestStartFocus() {
		this.tab.requestStartFocus();
	}

	// Löscht eine Gruppe
	public void deleteGruppe(Gruppe gruppe) {
		Main.data.removeGruppe(gruppe);
		this.refreshFilter();
	}

	@Override
	public void actionPerformed(Object sender, String message, Object value) {
		if (tab != null) {
			if (sender.equals(this.personenFilter)) {
				if ("selection changed".equals(message)) {
					selectListEntry((Person) value);
					setSelectedP((Person) value);
				} else if ("selection unchanged".equals(message))
					selectListEntry((Person) value);
				else if ("importance changed".equals(message))
					this.tab.setWichtigAuswahl((Boolean) value);
				refreshWindow();
			}
		}
	}

	public void refreshWindow() {
		this.window.repaint();

	}

	public void setEingabeTyp(aktuelleAuswahl eingabeTyp) {
		this.aktuelleAuswahlTyp = eingabeTyp;
	}

}
