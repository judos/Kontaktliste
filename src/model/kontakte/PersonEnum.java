package model.kontakte;

import helpers.StringH;

import java.util.Collections;
import java.util.Comparator;

import model.kontakte.attributes.AttributeType;
import model.kontakte.attributes.DateAttribute;
import model.kontakte.attributes.StringAttribute;
import ch.judos.generic.data.DynamicList;

public enum PersonEnum {
	PRENAME("vorname", str()), NAME("name", str()), DESCRIPTION("beschreibung", str()), EMAIL(
			"email", str()), MSN("msn", str()), PHONE("tel", str()), PHONE2("hometel", str()), STREET(
			"strasse", str()), PLZ("plz", str()), CITY("ort", str()), COUNTRY("land", str()), DATE(
			"geburtstag", new DateAttribute());

	public static DynamicList<PersonEnum>	all	= new DynamicList<PersonEnum>();
	static {
		for (PersonEnum e : PersonEnum.values())
			all.add(e);
		Collections.sort(all, getComparator());
	}

	private static AttributeType str() {
		return new StringAttribute();
	}

	public String			name;
	private AttributeType	type;

	PersonEnum(String name, AttributeType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return StringH.capitalize(this.name);
	}

	public static Comparator<PersonEnum> getComparator() {
		return new Comparator<PersonEnum>() {
			@Override
			public int compare(PersonEnum o1, PersonEnum o2) {
				return o1.name.compareToIgnoreCase(o2.name);
			}

		};
	}

	public String format(String newValue) {
		return this.type.format(newValue);
	}

}
