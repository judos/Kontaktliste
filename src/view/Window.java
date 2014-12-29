package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import ch.judos.generic.gui.WindowUtils;
import model.Savable;
import controller.Main;
import controller.WindowListener;

public class Window extends JFrame implements Savable {

	private static final long	serialVersionUID	= -4183004091386075672L;

	public static Color			titledBorderColor	= new Color(0xAAAAAA);
	public static Font			hintFont			= new Font("Arial", Font.ITALIC, 12);

	private JTabbedPane			tabbedPane;

	private long				lockedForRepaint;

	private boolean				hasLoaded;

	public Window() {
		super();
		this.hasLoaded = false;
		getContentPane().add(new JLabel("Bitte warten, Programm lädt...", JLabel.CENTER));
		getContentPane().setPreferredSize(new Dimension(200, 40));

		addWindowListener(new WindowListener());
		setTitle("Kontaktliste " + Main.VERSION);
		setIconImage(Images.windowIcon);
	}

	public void addTab(String title, Component tab, int mnemonic) {
		if (!this.hasLoaded)
			load();
		this.tabbedPane.addTab(title, tab);
		if (mnemonic > 0) {
			int pos = this.tabbedPane.getTabCount() - 1;
			this.tabbedPane.setMnemonicAt(pos, mnemonic);
		}
	}

	private void load() {
		getContentPane().removeAll();
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.setVisible(false);
		getContentPane().add(this.tabbedPane);
		this.hasLoaded = true;
	}

	public void finishCenterAndShow() {
		// Fenster vollenden
		pack();
		WindowUtils.centerWindow(this);
		int width, height;

		if (Main.config != null) {
			if (Main.config.isSet("window_width") && Main.config.isSet("window_height")) {
				width = Main.config.getI("window_width", 600);
				height = Main.config.getI("window_height", 400);
				setSize(width, height);
			} else {
				Dimension size = getSize();
				width = size.width;
				height = size.height;
			}
			if (Main.config.isSet("window_x") && Main.config.isSet("window_y")) {
				int x = Main.config.getI("window_x", 0);
				int y = Main.config.getI("window_y", 0);
				if (x < 0)
					x = 0;
				if (y < 0)
					y = 0;
				Toolkit tool = Toolkit.getDefaultToolkit();
				Dimension screen = tool.getScreenSize();
				if (x + width > screen.width)
					x = screen.width - width;
				if (y + height > screen.height)
					y = screen.height - height;
				setLocation(x, y);
			}
			if (Main.config.isSet("window_tab_selected")) {
				showTab(Main.config.get("window_tab_selected", "Kontakte"));
			}
		}
		if (this.tabbedPane != null)
			this.tabbedPane.setVisible(true);
		setVisible(true);
	}

	@Override
	public void saveAll() {
		Point pos = getLocation();
		Main.config.set("window_x", pos.x);
		Main.config.set("window_y", pos.y);
		Dimension size = getSize();
		Main.config.set("window_width", size.width);
		Main.config.set("window_height", size.height);
		Main.config.set("window_tab_selected", this.tabbedPane.getTitleAt(this.tabbedPane
			.getSelectedIndex()));
	}

	public void showTab(String string) {
		this.tabbedPane.setSelectedIndex(this.tabbedPane.indexOfTab(string));
	}

	/**
	 * Does repaint the Window but locks repainting for the next 250ms
	 */
	public void repaintWithCare() {
		if (this.lockedForRepaint < System.currentTimeMillis() - 250) {
			this.lockedForRepaint = System.currentTimeMillis();
			this.repaint();
		}
	}
}
