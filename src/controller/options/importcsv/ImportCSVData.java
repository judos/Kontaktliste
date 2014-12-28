package controller.options.importcsv;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import model.PersonDisplay;
import model.importcsv.CsvTableData;
import model.kontakte.FilterPerson;
import model.kontakte.Person;
import model.kontakte.PersonEnum;
import view.options.FindPersonDialog;
import ch.judos.generic.data.DynamicList;
import ch.judos.generic.data.StringUtils;
import controller.Main;

/**
 * @created 05.01.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 05.01.2012
 * @dependsOn
 * 
 */
public class ImportCSVData {

	private static final int	NUMBER_OF_SIMILAR_CONTACTS	= 5;
	private boolean				cancelImport;

	/**
	 * @param atts_csv
	 * @param values
	 * @param identificationAtts [0]->first name, [1]->last name
	 * @param att_hash
	 */
	public void performImport(CsvTableData data, int[] identificationAtts,
			HashMap<String, PersonEnum> att_hash) {
		DynamicList<String> atts_csv = data.getAttributes();
		String[][] values = data.getValues();

		for (String[] kontakt : values) {
			performImportKontakt(atts_csv, kontakt, identificationAtts, att_hash);
			if (this.cancelImport)
				break;
		}
	}

	private void performImportKontakt(DynamicList<String> atts_csv, String[] kontakt,
			int[] identificationAtts, HashMap<String, PersonEnum> att_hash) {

		String[] name = getKontaktName(kontakt, identificationAtts);
		Person p = Main.data.searchPerson(name[0] + " " + name[1]);
		if (p == null)
			p = findContactOrCreateIt(name, atts_csv, kontakt);
		if (p == null)
			return;
		HashMap<String, String> kontaktH = mapValuesToAttributes(atts_csv, kontakt);
		fillContactInformation(kontaktH, att_hash, p);
	}

	/**
	 * @param kontaktH new information
	 * @param att_hash maps the attributes onto the contact information
	 * @param existing contact
	 */
	private void fillContactInformation(HashMap<String, String> newInfo,
			HashMap<String, PersonEnum> att_hash, Person p) {
		for (String key : newInfo.keySet()) {
			String newValue = newInfo.get(key);
			PersonEnum att = att_hash.get(key);
			if (att != null) {
				String oldValue = p.get(att);

				newValue = att.format(newValue);
				oldValue = att.format(oldValue);
				System.out.println("new: " + newValue + ", old: " + oldValue);
			}
		}
	}

	/**
	 * uses the two parameters to create a hashmap
	 * 
	 * @param atts_csv
	 * @param kontakt
	 * @return maps attribute names to values of the contact
	 */
	private HashMap<String, String> mapValuesToAttributes(DynamicList<String> atts_csv,
			String[] kontakt) {
		if (atts_csv.size() != kontakt.length)
			System.err.println("Mismatched Array length");
		HashMap<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < kontakt.length; i++)
			result.put(atts_csv.get(i), kontakt[i]);
		return result;
	}

	/**
	 * @param name
	 * @return
	 */
	private Person findContactOrCreateIt(String[] name, DynamicList<String> atts, String[] kontakt) {
		FilterPerson f = FilterPerson.createNormalFilter();
		PersonDisplay[] nearest = findClosestPersonTo(name[0] + " " + name[1]);
		String[] attA = atts.toArray(new String[] {});
		FindPersonDialog dialog = new FindPersonDialog(name, f, nearest, attA, kontakt);
		f.autoUpdateList = true;
		f.refreshList();
		dialog.block();
		int t = dialog.getResultType();
		if (t == FindPersonDialog.RESULT_CLOSE_IMPORT) {
			this.cancelImport = true;
			return null;
		} else if (t == FindPersonDialog.RESULT_DONT_IMPORT)
			return null;
		Person p = dialog.getResult();
		if (t == FindPersonDialog.RESULT_CREATE_CONTACT) {
			Main.data.addPerson(p);
		}
		return p;
	}

	private PersonDisplay[] findClosestPersonTo(String fullname) {
		SortedMap<Integer, Person> matches = new TreeMap<Integer, Person>();
		for (Person p : Main.data.getPersonen()) {
			int d = StringUtils.getLevenshteinDistance(fullname, p.getFullName());
			matches.put(d, p);
		}
		PersonDisplay[] result = new PersonDisplay[NUMBER_OF_SIMILAR_CONTACTS];
		int index = 0;
		for (Integer i : matches.keySet()) {
			result[index] = new PersonDisplay(i + ") ", matches.get(i), "");
			index++;
			if (index == NUMBER_OF_SIMILAR_CONTACTS)
				break;
		}
		return result;
	}

	/**
	 * @param atts_csv
	 * @param kontakt
	 * @param identificationAtts
	 * @return [0]->first name, [1]->surname
	 */
	private String[] getKontaktName(String[] kontakt, int[] identificationAtts) {
		String prename = kontakt[identificationAtts[0]];
		String surname = kontakt[identificationAtts[1]];
		return new String[] { prename, surname };
	}
}
