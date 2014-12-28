package model.kontakte.attributes;

/**
 * @created 06.03.2012
 * @author Julian Schelker
 * @version 1.0
 * @lastUpdate 06.03.2012
 * @dependsOn
 * 
 */
public class StringAttribute extends AttributeType {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see model.kontakte.attributes.AttributeType#format(java.lang.String)
	 */
	@Override
	public String format(String value) {
		return value;
	}
	
}
