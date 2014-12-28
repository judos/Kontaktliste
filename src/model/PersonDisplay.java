package model;

import model.kontakte.Person;

/**
 * @created 28.02.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 28.02.2012
 * @dependsOn
 * 
 */
public class PersonDisplay {
	
	private String prefix;
	public Person person;
	private String postfix;
	
	public PersonDisplay(String prefix, Person p, String postfix) {
		this.prefix = prefix;
		this.person = p;
		this.postfix = postfix;
	}
	
	public String toString() {
		return this.prefix + this.person.toString() + this.postfix;
	}
	
}
