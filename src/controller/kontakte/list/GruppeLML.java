package controller.kontakte.list;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import model.kontakte.Gruppe;
import controller.Kontakte;
import controller.Main;

public class GruppeLML implements MouseListener {

	private Kontakte		kontakte;
	private JList<String>	listGruppen;

	public GruppeLML(Kontakte kontakte, JList<String> listGruppen) {
		this.kontakte = kontakte;
		this.listGruppen = listGruppen;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (SwingUtilities.isRightMouseButton(arg0)) {
			Point p = arg0.getPoint();
			int rowNumber = listGruppen.locationToIndex(p);
			listGruppen.setSelectedIndex(rowNumber);

			Gruppe g = Main.data.searchGruppe(listGruppen.getSelectedValue().toString());
			if (g != null) {
				JPopupMenu menu = new JPopupMenu();
				JMenuItem menuItem = new JMenuItem("Name ändern");
				menu.add(menuItem);
				menuItem.addActionListener(new GruppeBearbeitenListener(this.kontakte, g));
				menuItem = new JMenuItem("Löschen");
				menuItem.addActionListener(new GruppeLoschenListener(this.kontakte, g));
				menu.add(menuItem);
				menu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
