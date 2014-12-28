package helpers;

import java.util.Iterator;
import java.util.regex.Pattern;

public class StringH implements Iterable<String> {

	public static String newline = System.getProperty("line.separator");

	public String s;

	public StringH(String string) {
		this.s = string;
	}

	public Iterator<String> iterator() {
		return new StringsIterator(this.s);
	}

	private class StringsIterator implements Iterator<String> {

		private StringBuffer s;

		public StringsIterator(String s) {
			this.s = new StringBuffer();
			this.s.append(s);
		}

		public boolean hasNext() {
			return this.s.length() > 0;
		}

		public String next() {
			String result = this.s.substring(0, 1);
			this.s.deleteCharAt(0);
			return result;
		}

		public void remove() {
		}
	}
	
	public static String capitalize(String text) {
		return Character.toUpperCase(text.charAt(0))+text.substring(1);
	}

	public static String replaceAll(String text, String[] find, String replace) {
		for (String findX : find) {
			findX=Pattern.quote(findX);
			text = text.replaceAll(findX, replace);
		}
		return text;
	}
	
	public static String shorten(String text,int charCount) {
		if (text.length()>charCount)
			return text.substring(0, charCount)+"...";
		else
			return text;
	}

	public static String replaceAll(String text, String[] find, String[] replace) {
		if (find.length != replace.length && replace.length != 1)
			throw new IllegalArgumentException(
					"length has to be equal or 1 for replace array.");
		if (replace.length == 1)
			return replaceAll(text, find, replace[0]);
		for (int index = 0; index < find.length; index++) {
			find[index]=Pattern.quote(find[index]);
			text = text.replaceAll(find[index], replace[index]);
		}
		return text;
	}

	public static String substr(String s, int start, int length) {
		if (start < 0)
			start += s.length();
		s = s.substring(start);
		if (length < 0)
			length += s.length();
		if (length > s.length())
			length = s.length();
		return s.substring(0, length);
	}

	public static String substr(String s, int start) {
		return substr(s, start, s.length());
	}
	
	
	public static String extend(String s,int length) {
		StringBuffer b=new StringBuffer(s);
		int index=0;
		while (b.length()<length) {
			b.append(s.charAt(index));
			index= (index+1)%s.length();
		}
		return b.toString();
	}

}
