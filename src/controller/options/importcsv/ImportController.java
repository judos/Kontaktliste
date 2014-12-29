package controller.options.importcsv;

import helpers.Basic;
import helpers.FileH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.importcsv.CsvDescription;
import model.importcsv.CsvTableData;
import model.kontakte.PersonEnum;
import view.options.AttributeMatchDialog;
import view.options.ChooseSeparatorDialog;
import controller.Main;

/**
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * 
 */
public class ImportController {

	private int[]						identificationAtts;
	private HashMap<String, PersonEnum>	att_hash;

	public ImportController() {
		File file = selectFile();
		if (file == null)
			return;
		CsvDescription desc = analyzeFile(file);
		if (!checkAndChooseSeparator(desc))
			return;
		CsvTableData data = desc.getCsvTableData();
		boolean goOn = findIdentificationAndMatchAttributes(data);
		if (!goOn)
			return;
		ImportCSVData im = new ImportCSVData();
		im.performImport(data, identificationAtts, att_hash);
	}

	private boolean findIdentificationAndMatchAttributes(CsvTableData data) {
		AttributeMatchDialog dialog = new AttributeMatchDialog(data);
		dialog.block();
		this.att_hash = dialog.getCSV2ownAttributeMatching();
		this.identificationAtts = dialog.getIdentificationAttributes();
		return dialog.getResultOk();
	}

	private boolean checkAndChooseSeparator(CsvDescription d) {
		int anz = d.getCountOfPossibleSeparators();
		if (anz > 1) {
			ChooseSeparatorDialog dialog = new ChooseSeparatorDialog(d);
			dialog.block();
			if (dialog.getOutput() == null)
				return false;
			d.setSeparator(dialog.getOutput());
			return true;
		} else if (anz == 1) {
			d.setSeparator(d.getSeparator(0));
			return true;
		} else {
			Basic
				.notify(
					"Ungültige Datei",
					"In dieser CSV-Datei konnte kein gültiges Trennzeichen" + " ermittelt werden. Prüfen Sie die Datei mit einem" + " Editor auf Fehler.");
			return false;
		}
	}

	private CsvDescription analyzeFile(File f) {
		try {
			ArrayList<String> values = FileH.readFileContent(f);
			String atts = values.remove(0);
			return new CsvDescription(atts, values);
		} catch (IOException e) {
			Main.debugger.debug("Konnte csv nicht ordnungsgemäss lesen.");
			return null;
		}
	}

	private File selectFile() {
		// File desktop = FileH.getDesktopDir();
		// XXX: AFTER IMPORT reset initial path
		File f = FileH.RequestFile("D:\\Julian\\eclipse workspace juno\\Kontaktliste\\insert",
			"CSV-Datei", new String[] { "csv" });
		return f;
	}

}
