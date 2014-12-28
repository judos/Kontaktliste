package model;

import java.util.Comparator;

/**
 * compares sms between each other with their dates
 * 
 * @since 01.07.2013
 * @author Julian Schelker
 * @version 1.0 / 01.07.2013
 */
public class SMSComparator implements Comparator<SMS> {
	
	/**
	 * (non-Javadoc)
	 * 
	 * @param sms1
	 * @param sms2
	 * @return order by date of sms
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SMS sms1, SMS sms2) {
		if (sms2.empfang == null) {
			System.err.println(sms2 + " has no date.");
			return 0;
		}
		if (sms1.empfang == null) {
			System.err.println(sms1 + " has no date.");
			return 0;
		}
		return sms2.empfang.compareTo(sms1.empfang);
	}
	
}
