package controller;

import helpers.StringH;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Date;
import model.Savable;
import model.kontakte.Person;
import view.KalenderTab;
import view.Window;

public class Kalender implements Savable {

	private KalenderTab	tab;

	public Kalender(Window window) {
		tab = new KalenderTab(this);

		window.addTab("Kalender", tab, KeyEvent.VK_K);

		refreshGeburtstageListe();
	}

	public void refreshGeburtstageListe() {
		ArrayList<Person> personen = Main.data.getPersonen();
		HashMap<String, List<Person>> map = new HashMap<String, List<Person>>();
		// Personen nach ihrem Geburtstag in HashMap eintragen
		for (Person p : personen) {
			if (p.geburtstag != null) {
				if (p.geburtstag.isValid()) {
					String gebDMY = p.geburtstag.toString();
					// 5 Zeichen hinten entfernen (.YYYY)
					String geburtstag = StringH.substr(gebDMY, 0, -5);

					List<Person> list = map.get(geburtstag);
					if (list == null) {
						list = new ArrayList<Person>();
						map.put(geburtstag, list);
					}
					list.add(p);
				}
			}
		}

		// Die nächsten X Tage durchgehen und Personen in Endliste setzen
		ArrayList<String> result = new ArrayList<String>();
		Date d = new Date();
		for (int i = 0; i < 30; i++) { // nächste 30 Tage anschauen
			String search = d.toString("j.n");
			List<Person> found = map.get(search);
			if (found != null) {
				for (Person p : found) {
					result.add("<html><table><tr><td width=\"40px\">" + search + "</td><td>" + p
						.toString() + "</td></tr></table></html>");
				}
			}
			d = d.nextDay();
		}
		d = d.prevDay();
		result
			.add("<html><p style=\"color:#BBBBBB;\"> (Bis und mit " + d.toString("j.n") + " aufgelistet)</p></html>");

		result
			.add("<html><p style=\"color:red;text-decoration:underline;\">Vergangene Geburtstage:</p></html>");
		d = new Date();
		d = d.prevDay();
		for (int i = 0; i < 90; i++) { // letzte 30 Tage anschauen
			String search = d.toString("j.n");
			List<Person> found = map.get(search);
			if (found != null) {
				for (Person p : found) {
					result.add("<html><table><tr><td width=\"40px\">" + search + "</td><td>" + p
						.toString() + "</td></tr></table></html>");
				}
			}
			d = d.prevDay();
		}
		d = d.nextDay();
		result
			.add("<html><p style=\"color:#BBBBBB;\"> (Von heute bis " + d.toString("j.n") + " aufgelistet)</p></html>");
		this.tab.placePersonsInList(result);
	}

	@Override
	public void saveAll() {

	}

}
