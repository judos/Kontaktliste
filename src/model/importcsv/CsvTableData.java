package model.importcsv;

import ch.judos.generic.data.DynamicList;

/**
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class CsvTableData {

	private DynamicList<String>	atts;
	private String[]			attsExamples;
	private String[][]			values;

	public CsvTableData(String[] atts, String[] attExamples, String[][] values) {
		this.atts = new DynamicList<String>(atts);
		this.attsExamples = attExamples;
		this.values = values;
	}

	public String[] getAttributesArray() {
		return this.atts.toArray(new String[] {});
	}

	public DynamicList<String> getAttributes() {
		return this.atts;
	}

	public String[] getExamplesForAttributes() {
		return this.attsExamples;
	}

	public String[][] getValues() {
		return this.values;
	}

}
