package view.options;

import javax.swing.JOptionPane;

import model.importcsv.CsvDescription;
import ch.judos.generic.data.DynamicList;

/**
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class ChooseSeparatorDialog {

	private String				attLine;
	private DynamicList<String>	separators;
	private String				result;

	public ChooseSeparatorDialog(CsvDescription d) {
		this.separators = d.getSeparators();
		this.attLine = d.getAttributeLine();
	}

	public void block() {
		String separator;
		String choose = separators.concatToString(" ");
		boolean ok = false;
		do {
			separator =
				JOptionPane.showInputDialog("Mehr als ein mögliches Trennzeichen "
					+ "gefunden. Bitte wähle das passende Trennzeichen:\n"
					+ "Möglich sind: " + choose + "\n\nAttributezeile: \"" + this.attLine
					+ "\"");
			if (separator != null) {
				if (this.separators.contains(separator))
					ok = true;
			} else
				ok = true;
		} while (!ok);
		this.result = separator;
	}

	public String getOutput() {
		return this.result;
	}

}
