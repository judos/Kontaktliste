package controller;

import helpers.FileH;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import model.Config;
import model.Data;
import model.Savable;
import view.Images;
import view.Window;
import ch.judos.generic.data.DynamicList;

public class Main {

	public static final float	VERSION				= 1.53f;
	public static final boolean	RELEASE				= false;

	public static String		dataFolder			= "Data/";
	public static Debugger		debugger;
	public static Config		config;
	public static Main			main;
	public static Data			data;
	public static Worker		worker;

	public static boolean		showMissingInfos	= true;

	public static void main(String[] args) throws Exception {
		try {
			Window window = new Window();
			window.finishCenterAndShow();
			// backup();
			long start = System.currentTimeMillis();
			worker = new Worker();
			debugger = new Debugger();
			config = new Config();
			data = new Data();
			data.load();
			main = new Main(window);
			long end = System.currentTimeMillis();
			debugger.debug("Loading Time: " + (end - start) + "ms");
			System.out.println("Work Todo:" + worker.todo.size());
			boolean wait = false;
			boolean lastWaited;
			while (true) {
				lastWaited = wait;
				wait = worker.doWork();
				if (wait) {
					if (wait != lastWaited)
						System.out.println("Worker is idle.");
				}
			}
		} catch (Exception e) {
			if (RELEASE)
				JOptionPane.showMessageDialog(null, "Application failed.\n" + e);
			else
				throw e;
		} catch (Error e) {
			if (RELEASE)
				JOptionPane.showMessageDialog(null, "Application failed.\n" + e);
			else
				throw e;
		}
	}

	public static void backup() {
		File file = new File("Backup");
		if (!file.exists())
			file.mkdir();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		Date heute = new Date();
		String signatur = format.format(heute);

		FileH.copyDirectory("Data", "Backup\\Data" + signatur);
	}

	public static void exit() {
		if (main != null)
			main.saveAll();
		System.exit(1);
	}

	private Kontakte				kontakte;
	private Kalender				kalender;
	private DynamicList<Savable>	savableObjects;
	private Options					options;
	private Tasks					tasks;
	private ArchivP					archivP;

	public Main(Window window) {
		savableObjects = new DynamicList<Savable>();
		Images.load();
		kontakte = new Kontakte(window);
		kalender = new Kalender(window);
		archivP = new ArchivP(window);
		tasks = new Tasks(window);
		options = new Options(window);

		window.finishCenterAndShow();

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {}
		kontakte.requestStartFocus();

		savableObjects.addAll(window, kontakte, kalender, options, tasks, archivP);
		savableObjects.addAll(Main.config, Main.data);
	}

	public Kalender getKalender() {
		return this.kalender;
	}

	private void saveAll() {
		for (Savable object : savableObjects)
			if (object != null)
				object.saveAll();
	}

}
