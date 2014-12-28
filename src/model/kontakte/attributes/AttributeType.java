package model.kontakte.attributes;

/**
 * @created 06.03.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 06.03.2012
 * @dependsOn
 * 
 */
public abstract class AttributeType {
	
	protected AttributeType() {
		
	}
	
	public abstract String format(String newValue);
}
