package model;

import helpers.StringH;

import java.io.BufferedReader;
import java.io.IOException;

import model.kontakte.Person;

public class SMS {
	
	public static SMS read(Person kontakt, BufferedReader reader) throws IOException,
		NoSMSFoundException {
		String line;
		String datum = "", text = "";
		int anzahl = 0;
		
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("-----") && line.endsWith("-----"))
				break;
			if (anzahl == 0)
				datum = line;
			else if (anzahl == 1)
				text = line;
			else
				text += "\n" + line;
			anzahl++;
		}
		if (anzahl < 2)
			throw new NoSMSFoundException("index:" + anzahl + " Date:" + datum + " text:"
				+ text);
		datum = datum.toLowerCase().replace("empfangen:", "");
		datum = datum.trim();
		Date empfang = Date.parse(datum);
		return new SMS(kontakt, empfang, text);
	}
	
	public Person von;
	public Date empfang;
	
	public String text;
	
	public SMS(Person von, Date empfang, String text) {
		this.von = von;
		this.empfang = empfang;
		this.text = text;
	}
	
	/**
	 * @return output of sms in the format:\n date\n text\n --------\n
	 */
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		if (this.empfang == null)
			b.append("-kein Datum-");
		else
			b.append(this.empfang.toString("H:i:s d.m.Y"));
		b.append(StringH.newline);
		b.append(this.text);
		b.append(StringH.newline);
		b.append(StringH.extend("-", 25));
		b.append(StringH.newline);
		return b.toString();
	}
	
}
