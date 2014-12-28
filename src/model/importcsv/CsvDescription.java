package model.importcsv;

import java.util.ArrayList;

import model.CsvSignDetector;
import ch.judos.generic.data.DynamicList;

/**
 * captures description information like possible separators.<br>
 * can save the chosen separator for later
 * 
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class CsvDescription {

	private ArrayList<String>	values;
	private String				atts;
	private DynamicList<String>	seps;
	private String				separator;

	public CsvDescription(String atts, ArrayList<String> values) {
		this.atts = atts;
		this.values = values;
		findSeparators();
	}

	private void findSeparators() {
		CsvSignDetector det = new CsvSignDetector();
		det.firstLine(atts);
		for (String line : values) {
			det.addLine(line);
			if (det.countPossibilities() == 0)
				break;
		}

		this.seps = new DynamicList<String>(det.getPossibleSeparators());
		// try again only using values
		if (seps.size() == 0) {
			det = new CsvSignDetector();
			det.firstLine(values.get(0));
			for (String line : values)
				det.addLine(line);
			seps = new DynamicList<String>(det.getPossibleSeparators());
		}
	}

	public int getCoundOfPossibleSeparators() {
		return this.seps.size();
	}

	public String getSeparator(int index) {
		return this.seps.get(index);
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public DynamicList<String> getSeparators() {
		return this.seps;
	}

	public String getAttributeLine() {
		return this.atts;
	}

	public CsvTableData getCsvTableData() {
		String[] atts = this.atts.split(this.separator, -1);
		DynamicList<DynamicList<String>> valuesList = new DynamicList<DynamicList<String>>();
		for (String value : this.values) {
			valuesList.add(new DynamicList<String>(value.split(this.separator, -1)));
		}
		CsvRawData x = new CsvRawData(atts, valuesList);
		x.removeUnusedAttributes();
		return x.toCsvTableData();
	}
}
