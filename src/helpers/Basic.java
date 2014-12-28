package helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class Basic {
	
	public static int PROCEED_YES=0; 
	public static int PROCEED_NO=1;
	public static int PROCEED_CANCEL=2;
	
	public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public static void print(List<Object> list) {
		StringBuffer buf=new StringBuffer();
		buf.append("List (s:"+list.size()+"): ");
		if (list.size()>0) {
			for(Object o:list)
				buf.append(o.toString()+",");
			buf.setLength(buf.length()-1);
		}
		else {
			buf.append("<<leer>>");
		}
		System.out.println(buf);
	}
	
	
	public static int proceed(String title,String message) {
		String[] options = {"Ja","Nein","Abbrechen!"};
		return proceed(title,message,options);
	}
	
	private static int proceed(String title, String message, String[] options) {
		return proceed(title,message,options,0);
	}
	
	public static int proceed(String title,String message,String[]options,int selected) {
		return JOptionPane.showOptionDialog(null,message,title,
			JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
			null,options, options[selected]);
	}
	
	public static void notify(String title,String message) {
		JOptionPane.showMessageDialog(null, message,
				title, JOptionPane.ERROR_MESSAGE);
	}

	

}
