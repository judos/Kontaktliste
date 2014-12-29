package model;

import java.util.ArrayList;
import java.util.HashMap;

import ch.judos.generic.data.StringUtils;

public class CsvSignDetector {

	private HashMap<String, Integer>	liste;

	public CsvSignDetector() {
		this.liste = new HashMap<String, Integer>();
	}

	/**
	 * uses the first line to find possible separation characters
	 * 
	 * @param line
	 * @return number of possible characters acting as separation
	 */
	public int firstLine(String line) {
		for (String c : StringUtils.getCharIterableString(line)) {
			if (this.liste.containsKey(c))
				this.liste.put(c, this.liste.get(c) + 1);
			else
				this.liste.put(c, 1);
		}
		return this.liste.keySet().size();
	}

	/**
	 * checks the possibilities with the information of further lines<br>
	 * removes candidates
	 * 
	 * @param line
	 * @return number of possible separation characters
	 */
	public int addLine(String line) {
		HashMap<String, Integer> cur = new HashMap<String, Integer>();
		for (String c : StringUtils.getCharIterableString(line)) {
			if (cur.containsKey(c))
				cur.put(c, cur.get(c) + 1);
			else
				cur.put(c, 1);
		}
		ArrayList<String> removalKeys = new ArrayList<String>();
		for (String key : this.liste.keySet()) {
			if (!cur.containsKey(key))
				removalKeys.add(key);
			else if (this.liste.get(key).intValue() != cur.get(key).intValue())
				removalKeys.add(key);
		}
		for (String key : removalKeys)
			this.liste.remove(key);
		return this.liste.keySet().size();
	}

	public String[] getPossibleSeparators() {
		return (String[]) this.liste.keySet().toArray(new String[] {});
	}

	/**
	 * @return
	 */
	public int countPossibilities() {
		return this.liste.size();
	}

}
