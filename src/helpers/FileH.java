package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileH {

	public static Charset	currentCharset	= StandardCharsets.UTF_8;

	public static BufferedReader readFile(java.io.File f) throws FileNotFoundException {
		return new BufferedReader(new FileReader(f));
	}

	public static BufferedReader getReaderForFile(File f) throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(f), currentCharset));
	}

	public static BufferedWriter getWriterForFile(File f) throws FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),
			StandardCharsets.UTF_8));
	}

	public static ArrayList<String> readFileContent(java.io.File f) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
			ArrayList<String> result = new ArrayList<String>();
			String line;
			while ((line = reader.readLine()) != null) {
				result.add(line);
			}
			return result;
		}
	}

	public static BufferedReader readFile(String name) throws FileNotFoundException {
		return new BufferedReader(new FileReader(new java.io.File(name)));
	}

	public static BufferedWriter writeFile(java.io.File f) throws IOException {
		return new BufferedWriter(new FileWriter(f));
	}

	public static BufferedWriter writeFile(String name) throws IOException {
		return new BufferedWriter(new FileWriter(new java.io.File(name)));
	}

	public static File getDesktopDir() {
		FileSystemView filesys = FileSystemView.getFileSystemView();
		// File[] roots = filesys.getRoots();
		return filesys.getHomeDirectory();
	}

	public static File RequestFile(String description, String extensions[]) {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else
			return null;
	}

	public static File RequestFile() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else
			return null;
	}

	public static File RequestFile(File startPath, String description, String[] extensions) {
		return RequestFile(startPath.getPath(), description, extensions);
	}

	public static File SaveFile(String description, String extensions[]) {

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("..\\Data\\Maps\\"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else
			return null;
	}

	public static boolean writeToFile(String filename, String content) {
		File file = new File(filename);
		return writeToFile(file, content);
	}

	public static boolean writeToFile(File file, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static File RequestFile(String startPath, String description, String extensions[]) {
		JFileChooser chooser = new JFileChooser(startPath);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extensions);
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else
			return null;
	}

	public static boolean copyFile(String source, String destination) {
		return copyFile(new File(source), new File(destination));
	}

	public static boolean copyFile(File f1, File f2) {
		try {
			InputStream in = new FileInputStream(f1);
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			return true;
		} catch (FileNotFoundException ex) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean copyDirectory(String source, String target) {
		return copyDirectory(new File(source), new File(target));
	}

	public static boolean copyDirectory(File sourceLocation, File targetLocation) {
		boolean ok = true;
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation,
					children[i]));
			}
		} else {
			boolean g = copyFile(sourceLocation, targetLocation);
			if (!g)
				ok = false;
		}
		return ok;
	}

}
