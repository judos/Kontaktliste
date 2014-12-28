package controller;

import helpers.FileH;
import helpers.StringH;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

import model.Savable;
import model.archivP.ArchivedObject;
import view.ArchivPTab;
import view.Window;

public class ArchivP implements Savable, ActionListener {

	private ArchivPTab tab;
	private ArchivedObject root;
	private String suchText;
	private ArchivedObject currentNodeToCut;

	public ArchivP(Window window) {

		this.suchText = "";

		load();
		tab = new ArchivPTab(this, root);
		tab.buildTree(root, "");

		window.addTab("ArchivP", tab, KeyEvent.VK_A);
	}

	public void saveAll() {
		BufferedWriter x;
		try {
			x = FileH.writeFile(Main.dataFolder + "ArchivP.txt");
		} catch (IOException e) {
			Main.debugger.debug("ArchivP file could not be saved.");
			Main.debugger.debug(e);
			return;
		}

		try {
			x.write(root.toString(0));
		} catch (IOException e) {
			Main.debugger.debug("Error writing ArchivP: " + e);
		}

		try {
			x.close();
		} catch (IOException e) {
		}
	}

	public void load() {
		BufferedReader x;
		try {
			x = FileH.readFile(Main.dataFolder + "ArchivP.txt");
		} catch (FileNotFoundException e) {
			Main.debugger.debug("ArchivP file not found.");
			this.root = new ArchivedObject("-unnamed root-");
			return;
		}
		String line;
		Stack<ArchivedObject> stack = new Stack<ArchivedObject>();
		boolean container;
		String name;
		try {
			while ((line = x.readLine()) != null) {
				line = line.trim();
				if (line.equals("}"))
					stack.pop();
				else if (!line.equals("")) {
					container = false;
					if (StringH.substr(line, -1).equals("{")) {
						container = true;
						name = StringH.substr(line, 0, -1).trim();
					} else
						name = line;
					ArchivedObject obj = new ArchivedObject(name);
					if (stack.size() == 0) {
						if (this.root != null)
							Main.debugger
									.debug("ArchivP found a second root. This is invalid semantics");
						this.root = obj;
					} else
						stack.peek().addSubObject(obj);
					if (container)
						stack.push(obj);
				}
			}
		} catch (IOException e) {
			Main.debugger.debug("Error while reading ArchivP file.");
		}
		this.root.sortRec();
	}

	public void refreshTree() {
		tab.buildTree(this.root, this.suchText);
	}

	public void setSuche(String suchText) {
		this.suchText = suchText;
		tab.buildTree(this.root, suchText);
	}

	public void setCutNode(ArchivedObject node) {
		this.currentNodeToCut = node;
		this.tab.setCutNode(node);
	}

	public ArchivedObject getCutNode() {
		return this.currentNodeToCut;
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("expandAll"))
			this.tab.expandAll();
		if (arg0.getActionCommand().equals("collapseAll"))
			this.tab.collapseAll();
	}
}
