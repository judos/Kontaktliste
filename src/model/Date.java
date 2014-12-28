package model;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @created 2011
 * @author Julian Schelker
 * @version 1.1
 * @lastUpdate 07.03.2012
 * @dependsOn
 * 
 */
public class Date implements Cloneable, Comparable<Date> {

	// some identifier and helper variables
	private static GregorianCalendar	c				= new GregorianCalendar();
	private static final int			DAY				= 1;
	private static final int			MONTH			= 2;
	private static final int			YEAR			= 3;

	// Assumes that Calendar.Sunday = 1, Calendar.Monday = 2 and so on...
	public static String[]				weekdayNames	= new String[] { "Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag" };
	public static String[]				monthNames		= new String[] { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" };

	/**
	 * @param year
	 * @param month
	 * @return the number of days the given month in the given year has
	 */
	public static int daysInMonth(int year, int month) {
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param year
	 * @return true if the given year is a leap year
	 */
	public static boolean isLeapYear(int year) {
		return c.isLeapYear(year);
	}

	/**
	 * tries to parse the given line to following formats:<br>
	 * day.month.year<br>
	 * year-month-day<br>
	 * month/day/year
	 * 
	 * @param line the date as string
	 * @return null if the date could not be parsed
	 */
	public static Date parse(String line) {
		line = line.trim().replaceAll(" ", "");
		Pref[] formats = { new Pref(".", DAY, MONTH, YEAR), new Pref("/", MONTH, DAY, YEAR), new Pref(
			"-", YEAR, MONTH, DAY) };
		String rnumber = "([0-9]*)";
		String rsep = genRegexSeparators(formats);
		Pattern p = Pattern.compile(rnumber + rsep + rnumber + rsep + rnumber,
			Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(line);
		if (m.matches()) {
			for (Pref form : formats) {
				if (form.matches(m))
					return form.convertToDate(m);
			}
		}
		return null;
	}

	public static Comparator<Date> getComparator() {
		return new Comparator<Date>() {
			@Override
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		};
	}

	protected int	day;
	protected int	month;
	protected int	year;

	/**
	 * the date will save the current day, month and year
	 */
	public Date() {
		c.setTime(new java.util.Date());
		this.year = c.get(Calendar.YEAR);
		this.month = c.get(Calendar.MONTH) + 1;
		this.day = c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * see also the static parse method for parsing dates
	 * 
	 * @param year give some values to initialize this date
	 * @param month
	 * @param day
	 */
	public Date(int year, int month, int day) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	/**
	 * @return the date a day after this date
	 */
	public Date nextDay() {
		Date date = this.clone();
		date.day++;
		if (date.day > daysInMonth(date.year, date.month)) {
			date.day = 1;
			date.month++;
			if (date.month > 12) {
				date.month = 1;
				date.year++;
			}
		}
		return date;
	}

	/**
	 * @return the date a day before this date
	 */
	public Date prevDay() {
		Date date = this.clone();
		date.day--;
		if (date.day < 1) {
			date.month--;
			if (date.month < 1) {
				date.month = 12;
				date.year--;
			}
			date.day = daysInMonth(date.year, date.month);
		}
		return date;
	}

	/**
	 * @return the date a month before this date
	 */
	public Date prevMonth() {
		Date date = this.clone();
		date.month--;
		if (date.month < 1) {
			date.month = 12;
			date.year--;
		}
		int x = daysInMonth(date.year, date.month);
		if (date.day > x)
			date.day = x;
		return date;
	}

	/**
	 * @return the date a month after this date
	 */
	public Date nextMonth() {
		Date date = this.clone();
		date.month++;
		if (date.month > 12) {
			date.month = 1;
			date.year++;
		}
		int x = daysInMonth(date.year, date.month);
		if (date.day > x)
			date.day = x;
		return date;
	}

	/**
	 * @param date another date
	 * @return true if this date is in the past relatively to the given date
	 */
	public boolean isBefore(Date date) {
		if (year < date.year)
			return true;
		if (year > date.year)
			return false;
		if (month < date.month)
			return true;
		if (month > date.month)
			return false;
		if (day < date.day)
			return true;
		if (day > date.day)
			return false;
		return false;
	}

	/**
	 * @param date another date
	 * @return true if this date is in the futur relatively to the given date
	 */
	public boolean isAfter(Date date) {
		return date.isBefore(this);
	}

	/**
	 * Returns toString("d.m.Y")
	 */
	@Override
	public String toString() {
		return toString("d.m.Y");
	}

	/**
	 * Like the php Function date (format follows the same standards, but only for dates) See: <a
	 * href="http://php.net/manual/de/function.date.php">PHP date</a>
	 * 
	 * @param format : one char for every format, use / as escape char
	 */
	public String toString(String format) {
		setCalenderDate();
		int weekdayNr = c.get(Calendar.DAY_OF_WEEK);
		// See assumes of the att weekdayNames
		String weekdayName = weekdayNames[weekdayNr - 1];
		// Day of year (starts at 1)
		int dayOfYear = c.get(Calendar.DAY_OF_YEAR);
		// Week of year
		int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
		// days in month
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		// leapyear
		int leapYear = (c.isLeapYear(year) ? 1 : 0);

		StringBuffer r = new StringBuffer("");

		int anzahlZeichen = format.length();
		for (int pos = 0; pos < anzahlZeichen; pos++) {
			String c = format.substring(pos, pos + 1);
			if (c.equals("d")) {
				if (day < 10)
					r.append("0");
				r.append(day);
			} else if (c.equals("D"))
				r.append(weekdayName).substring(0, 3);
			else if (c.equals("j"))
				r.append(day);
			else if (c.equals("l"))
				r.append(weekdayName);
			else if (c.equals("N")) {
				if (weekdayNr == 0)
					r.append(7);
				else
					r.append(weekdayNr);
			} else if (c.equals("S")) {
				String[] postfix = new String[] { "st", "nd", "rd", "th" };
				if (day < 4)
					r.append(postfix[day - 1]);
				else
					r.append(postfix[3]);
			} else if (c.equals("w"))
				r.append(weekdayNr);
			else if (c.equals("z"))
				r.append(dayOfYear - 1);

			else if (c.equals("W"))
				r.append(weekOfYear);
			else if (c.equals("F"))
				r.append(monthNames[month - 1]);
			else if (c.equals("m")) {
				if (month < 10)
					r.append(0);
				r.append(month);
			} else if (c.equals("M"))
				r.append(monthNames[month - 1].substring(0, 3));
			else if (c.equals("n"))
				r.append(month);
			else if (c.equals("t"))
				r.append(daysInMonth);

			else if (c.equals("L"))
				r.append(leapYear);
			else if (c.equals("Y")) {
				if (year < 1000) {
					r.append(0);
					if (year < 100)
						r.append(0);
					if (year < 10)
						r.append(0);
				}
				r.append(year);
			} else if (c.equals("y"))
				r.append(String.valueOf(year).substring(2));
			else if (c.equals("\\") || c.equals("/")) {
				pos++;
				c = format.substring(pos, pos + 1);
				r.append(c);
			} else {
				r.append(c);
			}
		}
		return r.toString();
	}

	/**
	 * @return true if date is valid
	 */
	public boolean isValid() {
		return day > 0 && day <= daysInMonth(year, month) && month > 0 && month < 13;
	}

	/**
	 * Returns new date representing the same day, month, year
	 */
	@Override
	public Date clone() {
		Date d = new Date();
		d.year = year;
		d.month = month;
		d.day = day;
		return d;
	}

	/**
	 * @return 1 if this.isAfter(date) <br>
	 *         -1 if this.isBefore(date) <br>
	 *         0 if the two dates represent the same day, month and year
	 */
	@Override
	public int compareTo(Date date) {
		if (this.isAfter(date))
			return 1;
		else if (this.isBefore(date))
			return -1;
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Date))
			return false;
		Date d = (Date) o;
		return d.year == this.year && d.month == this.month && d.day == this.day;
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return true if this represents the given date
	 */
	public boolean equals(int year, int month, int day) {
		return this.year == year && this.month == month && this.day == day;
	}

	private static String genRegexSeparators(Pref[] seps) {
		StringBuffer r = new StringBuffer("([");
		for (Pref s : seps)
			r.append(Pattern.quote(s.sep));
		r.append("])");
		return r.toString();
	}

	private void setCalenderDate() {
		c.set(year, month, day);
	}

	private static class Pref {
		public String	sep;
		public int[]	prefs;

		private Pref(String sep, int... prefs) {
			this.sep = sep;
			this.prefs = prefs;
		}

		public Date convertToDate(Matcher m) {
			int year = 0, month = 0, day = 0;
			for (int i = 0; i <= 2; i++) {
				int v = Integer.valueOf(m.group(i * 2 + 1));
				if (prefs[i] == DAY)
					day = v;
				if (prefs[i] == MONTH)
					month = v;
				if (prefs[i] == YEAR)
					year = v;
			}
			return new Date(year, month, day);
		}

		public boolean matches(Matcher m) {
			return m.group(2).equals(sep) && m.group(4).equals(sep) && checkValues(m);
		}

		private boolean checkValues(Matcher m) {
			for (int i = 0; i < 3; i++) {
				if (!checkTypeOk(prefs[i], m.group(i * 2 + 1)))
					return false;
			}
			return true;
		}

		private boolean checkTypeOk(int type, String value) {
			try {
				int number = Integer.valueOf(value);
				if (type == DAY)
					return number >= 1 && number <= 31;
				else if (type == MONTH)
					return number >= 1 && number <= 12;
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

}
