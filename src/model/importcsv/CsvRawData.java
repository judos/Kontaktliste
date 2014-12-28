package model.importcsv;

import ch.judos.generic.data.DynamicList;

/**
 * saves all csv data in lists to allow processing the data.<br>
 * use toCsvTableData() for accessing data on a read-only object.
 * 
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class CsvRawData {

	private DynamicList<DynamicList<String>>	values;
	private DynamicList<String>					atts;
	private int									anzAtt;
	private DynamicList<String>					attsExamples;

	public CsvRawData(String[] atts, DynamicList<DynamicList<String>> valuesList) {
		this.atts = new DynamicList<String>(atts);
		this.values = valuesList;
		this.anzAtt = this.atts.size();
		this.attsExamples = new DynamicList<String>();
	}

	private void removeSpecial() {
		for (int i = 0; i < this.anzAtt; i++) {
			atts.set(i, rm(atts.get(i)));
			attsExamples.set(i, rm(attsExamples.get(i)));
			for (DynamicList<String> x : this.values) {
				x.set(i, rm(x.get(i)));
			}
		}
	}

	private String rm(String value) {
		value = value.trim();
		if (value.startsWith("\"") && value.endsWith("\""))
			value = value.substring(1, value.length() - 1);
		return value;
	}

	public void removeUnusedAttributes() {
		boolean[] unused = new boolean[this.anzAtt];
		for (int indexAtt = 0; indexAtt < this.anzAtt; indexAtt++) {
			unused[indexAtt] = true;
			String example = "";
			for (DynamicList<String> value : values) {
				if (value.size() > indexAtt) {
					if (!value.get(indexAtt).equals("")) {
						example = value.get(indexAtt);
						unused[indexAtt] = false;
						break;
					}
				}
			}
			this.attsExamples.add(example);
		}
		removeAttributesAndValues(unused);
	}

	private void removeAttributesAndValues(boolean[] unused) {
		// Reduce Attributes
		for (int i = this.anzAtt - 1; i >= 0; i--) {
			if (unused[i]) {
				atts.remove(i);
				attsExamples.remove(i);
			}
		}
		// Reduce values
		for (DynamicList<String> value : values) {
			for (int i = this.anzAtt - 1; i >= 0; i--) {
				if (unused[i] && value.size() > i)
					value.remove(i);
			}
		}
		// Count atts again
		this.anzAtt = atts.size();

	}

	public CsvTableData toCsvTableData() {
		removeSpecial();
		String[] atts = this.atts.toArray(new String[] {});
		String[] examples = this.attsExamples.toArray(new String[] {});
		String[][] values = new String[this.values.size()][];
		for (int i = 0; i < this.values.size(); i++)
			values[i] = this.values.get(i).toArray(new String[] {});
		return new CsvTableData(atts, examples, values);
	}

}
