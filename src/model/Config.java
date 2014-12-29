package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import ch.judos.generic.data.csv.CSVFileReader;
import ch.judos.generic.data.csv.CSVFileWriter;
import controller.Main;

public class Config implements Savable {

	private static final String			file	= "config.csv";

	protected HashMap<String, String>	values;

	public static final String			id		= "id",
			filterPersonWichtig = "filterPersonWichtig";

	private Config() {
		this.values = new HashMap<String, String>();
		load();
	}

	private void load() {
		try {
			CSVFileReader csvread = CSVFileReader.readFile(Main.dataFolder + file);
			for (int i = 0; i < csvread.countEntries(); i++) {
				String[] entry = csvread.getEntry(i);
				this.values.put(entry[0], entry[1]);
			}
		} catch (IOException e2) {
			Main.debugger.debug("Datei nicht gefunden " + Main.dataFolder + file);
		}
	}

	public boolean getB(String key, boolean defaultExpectedValue) {
		if (isSet(key))
			return Boolean.valueOf(this.values.get(key));
		else
			return defaultExpectedValue;
	}

	public int getI(String key, int defaultExpectedValue) {
		if (isSet(key))
			return Integer.valueOf(this.values.get(key));
		else
			return defaultExpectedValue;
	}

	public String get(String key, String defaultExpectedValue) {
		if (isSet(key))
			return this.values.get(key);
		else
			return defaultExpectedValue;
	}

	public boolean isSet(String key) {
		return this.values.containsKey(key);
	}

	public void set(String key, String value) {
		this.values.put(key, value);
	}

	public void set(String key, int value) {
		this.values.put(key, String.valueOf(value));
	}

	public void set(String key, boolean value) {
		this.values.put(key, String.valueOf(value));
	}

	@Override
	public void saveAll() {
		CSVFileWriter wr = new CSVFileWriter(new String[] { "Attribut", "Wert" });
		for (Entry<String, String> entry : this.values.entrySet()) {
			wr.addEntry(new String[] { entry.getKey(), entry.getValue() });
		}
		try {
			wr.writeFile(Main.dataFolder + "config.csv");
		} catch (IOException e1) {
			Main.debugger.debug("Datei konnte nicht geschrieben werden :"
				+ Main.dataFolder + "config.csv");
		}
	}

	private static Config	instance;

	public static Config getInstance() {
		if (instance == null)
			instance = new Config();
		return instance;
	}
}
