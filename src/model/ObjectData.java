package model;

import helpers.StringH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Set;

import controller.Main;

public class ObjectData {

	private String					name;
	private HashMap<String, String>	attribute;
	private boolean					valid;

	public ObjectData(String line) {
		this.valid = true;
		attribute = new HashMap<String, String>();

		int pos1 = line.indexOf("={");
		if (pos1 < 0) {
			this.valid = false;
			return;
		}
		if (!StringH.substr(line, -1).equals("}")) {
			line += "}";
			Main.debugger.debug("Kein } am Objektende gefunden: " + line);
		}

		this.name = StringH.substr(line, 0, pos1);

		String attGroup = line.substring(pos1 + 2, line.length() - 1);

		String[] atts = attGroup.split(",");

		for (String segment : atts) {
			String[] x = segment.split("=", 2);
			if (x.length == 2)
				attribute.put(x[0], unStuffChars(x[1]));
			else if (x.length == 1)
				attribute.put(x[0], "");
			else
				Main.debugger.debug("Konnte dieses Array nicht gebrauchen: " + x);

		}
	}

	public ObjectData(String[] vars) {
		for (int i = 0; i < vars.length; i++)
			vars[i] = vars[i].trim();
		this.valid = true;
		this.attribute = new HashMap<String, String>();
		this.name = vars[0];
		if (vars.length % 2 == 0) {
			this.valid = false;
			throw new InputMismatchException("Ungültige Anzahl Elemente im String");
		}
		for (int i = 1; i < vars.length; i += 2) {
			vars[i + 1] = stuffChars(vars[i + 1]);
			this.attribute.put(vars[i], vars[i + 1]);
		}
	}

	public ArrayList<String> getEmptyValues() {
		Set<String> keys = this.attribute.keySet();
		ArrayList<String> result = new ArrayList<String>();
		for (String key : keys) {
			if (this.attribute.get(key).equals(""))
				result.add(key);
		}
		return result;
	}

	public boolean isValid() {
		return valid;
	}

	public String getName() {
		return this.name;
	}

	public String getValue(String key) {
		if (this.attribute.containsKey(key))
			return this.attribute.get(key);
		else
			return "";
	}

	public HashMap<String, String> getValues() {
		return this.attribute;
	}

	public String serialize() {
		return serialize(new String[] {});
	}

	public String serialize(String[] order) {
		StringBuffer result = new StringBuffer(this.name + "={");
		Set<String> x = this.attribute.keySet();
		boolean first = true;

		for (String key : order) {
			if (x.contains(key)) {
				if (!first)
					result.append(",");
				first = false;
				result.append(key + "=" + this.attribute.get(key));
				x.remove(key);
			}
		}

		for (String key : x) {
			if (!first)
				result.append(",");
			first = false;
			result.append(key + "=" + this.attribute.get(key));
		}
		result.append("}");
		return result.toString();
	}

	@Override
	public String toString() {
		return this.serialize();
	}

	private String stuffChars(String line) {
		line = line.replaceAll("@", "@@");
		line = line.replaceAll("\n", "@n");
		line = line.replaceAll(",", "@k");
		line = line.replaceAll("=", "@g");
		return line;
	}

	private String unStuffChars(String line) {
		line = line.replaceAll("@@", "-specialchar-");
		// prevent converting @@g into @g and so into =
		line = line.replaceAll("@g", "=");
		line = line.replaceAll("@k", ",");
		line = line.replaceAll("@n", "\n");
		// finally convert "@@" into @
		line = line.replaceAll("-specialchar-", "@");
		return line;
	}

}
