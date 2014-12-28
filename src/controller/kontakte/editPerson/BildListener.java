package controller.kontakte.editPerson;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

import model.kontakte.Bild;
import model.kontakte.Person;
import controller.Kontakte;

public class BildListener implements MouseListener {

	public class PopUpHider implements ActionListener {
		private Popup	popup;
		private Timer	timer;

		public PopUpHider(Popup popup) {
			this.popup = popup;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.timer.stop();
			this.popup.hide();
		}

		public void setTimer(Timer timer) {
			this.timer = timer;
		}
	}

	private Kontakte	kontakte;

	public BildListener(Kontakte kontakte) {
		this.kontakte = kontakte;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Person p = this.kontakte.getSelectedPerson();
		if (p != null) {
			List<Bild> bilder = p.getBilder();

			JPopupMenu menu = new JPopupMenu();
			JMenuItem menuItem;
			if (bilder.size() > 0) {
				menuItem = new JMenuItem("Alle Anzeigen");
				menuItem.setEnabled(false);
				menu.add(menuItem);
			}
			menuItem = new JMenuItem("Bild hinzufügen");
			menuItem.addActionListener(new BildHinzufugenListener(this.kontakte));
			menu.add(menuItem);
			if (bilder.size() > 0) {
				menuItem = new JMenuItem("Bild löschen");
				menuItem.addActionListener(new BildEntfernenListener(this.kontakte));
				menu.add(menuItem);
			}
			menu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
		} else {

			JPanel panel = new JPanel();
			panel.add(new JLabel("Person auswählen um Bild Möglichkeiten hier anzuzeigen."));
			panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

			/* for jre1.6 */
			int x = arg0.getXOnScreen();
			int y = arg0.getYOnScreen();

			/*
			 * for jre1.5 * int x=arg0.getX(); int y=arg0.getY();
			 */
			PopupFactory factory = PopupFactory.getSharedInstance();
			Popup popup = factory.getPopup(null, panel, x + 10, y + 10);
			popup.show();

			PopUpHider h = new PopUpHider(popup);
			Timer timer = new Timer(1500, h);
			timer.start();
			h.setTimer(timer);

		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
