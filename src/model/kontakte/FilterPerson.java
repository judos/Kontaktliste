package model.kontakte;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;

import model.AudibleObjectA;
import controller.Main;

public class FilterPerson extends AudibleObjectA {

	public static FilterPerson createAddToGruppe(Gruppe g) {
		return new FilterPerson(FilterState.dont_accept, g.getPersonen());
	}

	public static FilterPerson createRemoveFromGruppe(Gruppe g) {
		return new FilterPerson(FilterState.accept, g.getPersonen());
	}

	public static FilterPerson createNormalFilter(List<Person> list) {
		return new FilterPerson(FilterState.dont_accept, list);
	}

	public static FilterPerson createNormalFilter() {
		return new FilterPerson(FilterState.dont_accept, null);
	}

	private static enum FilterState {
		accept, dont_accept
	};

	private FilterState					state;				// current state
	public List<Person>					data;				// accepted or not accepted contacts

	private String						filterTelefon;		// current filter inputs
	private String						filterName;
	private boolean						filterWichtig;		// only persons which are important

	private Gruppe						filterGruppe;		// only persons which are in this group

	private boolean						nameRegexpMatch;	// are you using initials (i.e. j.s)
	private String						vornamePre;
	private String						nachnamePre;

	public boolean						autoUpdateList;	// updates listModel if true after every
	// filterchange
	private DefaultListModel<Person>	jlistModel;
	private Person						selectedPerson;

	private FilterPerson(FilterState state, List<Person> data) {
		super();
		this.state = state;
		this.data = data;
		this.filterWichtig = false;
		this.filterGruppe = null;
		this.autoUpdateList = false;
		this.selectedPerson = null;
		setFilterName("");
		setFilterTelefon("");
	}

	// öffentliche Funktion die aufgerufen wird zum aktualisieren
	public void refreshList() {
		if (this.jlistModel != null) {
			// Da sich refreshList2() rekursiv aufruft, darf die
			// neue Auswahl erst nach der Rekursion gespeichert werden
			this.selectedPerson = refreshList2();
		}
	}

	// rekursive Funktion, falls keine Person gefunden wurde
	// versucht die Funktion den Filter VIP=false um ein Resultat
	// zu erhalten
	private Person refreshList2() {
		Person newSelected = this.selectedPerson;
		boolean foundLast = false;
		jlistModel.clear();
		List<Person> personen = Main.data.getPersonen();
		for (Person p : personen) {
			if (isVisible(p)) {
				if (selectedPerson != null && p.equals(selectedPerson))
					foundLast = true;
				jlistModel.addElement(p);
			}
		}
		boolean resetFilterPersonWichtigToTrue = false;
		if (jlistModel.size() == 1)
			newSelected = (Person) jlistModel.elementAt(0);
		else if (jlistModel.size() == 0 && this.filterWichtig) {
			this.filterWichtig = false;
			newSelected = refreshList2();
			resetFilterPersonWichtigToTrue = true;
		} else if (!foundLast && selectedPerson != null)
			newSelected = null;

		if (!resetFilterPersonWichtigToTrue) {
			if (newSelected != selectedPerson) {
				notifyListeners("selection changed", newSelected);
			} else
				notifyListeners("selection unchanged", newSelected);
			notifyListeners("importance changed", this.filterWichtig);
		} else
			this.filterWichtig = true;

		return newSelected;
	}

	public boolean isFilterWichtig() {
		return filterWichtig;
	}

	public void setFilterWichtig(boolean filterWichtig) {
		this.filterWichtig = filterWichtig;
		if (this.autoUpdateList)
			refreshList();
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName.toLowerCase();
		Pattern pattern = Pattern.compile("(\\w+)[\\. ](\\w+)\\.?");
		Matcher matcher = pattern.matcher(filterName);
		this.nameRegexpMatch = false;
		this.vornamePre = null;
		this.nachnamePre = null;
		if (matcher.matches()) {
			nameRegexpMatch = true;
			vornamePre = matcher.group(1);
			nachnamePre = matcher.group(2);
		}
		if (this.autoUpdateList)
			refreshList();
	}

	public String getFilterTelefon() {
		return filterTelefon;
	}

	public void setFilterTelefon(String tel) {
		this.filterTelefon = tel.toLowerCase();
		this.filterTelefon = filterTelefon.replaceAll(" ", "");
		if (this.autoUpdateList)
			refreshList();
	}

	public void addData(List<Person> data) {
		this.data.addAll(data);
	}

	public boolean isVisible(Person p) {
		return isAccepted(p) && isNameMatched(p) && isTelMatched(p) && isWichtigMatched(p) && isInGroup(p);
	}

	private boolean isInGroup(Person p) {
		if (filterGruppe != null) {
			return p.isInGruppe(filterGruppe);
		}
		return true;
	}

	private boolean isTelMatched(Person p) {
		String t1 = p.tel.replaceAll(" ", "");
		String t2 = p.homeTel.replaceAll(" ", "");
		if (!t1.contains(filterTelefon) && !t2.contains(filterTelefon) && !"".equals(filterTelefon))
			return false;
		return true;
	}

	private boolean isWichtigMatched(Person p) {
		if (this.filterWichtig && !p.wichtig)
			return false;
		return true;
	}

	private boolean isNameMatched(Person p) {
		if (!this.filterName.equals("")) {
			if (this.nameRegexpMatch) {
				if (!p.getVorname().toLowerCase().startsWith(vornamePre))
					return false;
				if (!p.getName().toLowerCase().startsWith(nachnamePre))
					return false;
			} else if (!p.getFullName().toLowerCase().contains(filterName))
				return false;
		}
		return true;
	}

	public boolean isAccepted(Person p) {
		if (this.data != null) {
			boolean contains = this.data.contains(p);
			if (this.state == FilterState.accept)
				return contains;
			else
				return !contains;
		} else {
			return this.state != FilterState.accept;
		}

	}

	public void setJListModel(DefaultListModel<Person> listModel) {
		this.jlistModel = listModel;
	}

	public void setSelection(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public void setFilterGruppe(Gruppe g) {
		this.filterGruppe = g;
		if (this.autoUpdateList)
			refreshList();
	}

}
