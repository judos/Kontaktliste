package controller.kontakte.list;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import model.kontakte.Person;
import controller.Kontakte;
import controller.Main;

public class PersonLML implements MouseListener {

	private JList<Person>	list;
	private Kontakte		kontakte;

	public PersonLML(Kontakte kontakte, JList<Person> list) {
		this.list = list;
		this.kontakte = kontakte;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (SwingUtilities.isRightMouseButton(arg0)) {
			Point p = arg0.getPoint();
			int rowNumber = list.locationToIndex(p);
			list.setSelectedIndex(rowNumber);

			Person p1 = Main.data.searchPerson(list.getSelectedValue().toString());

			JPopupMenu menu = new JPopupMenu();
			JMenuItem menuItem = new JMenuItem("Bearbeiten");
			menu.add(menuItem);
			menuItem.addActionListener(new PersonBearbeitenListener(this.kontakte, p1));
			menuItem = new JMenuItem("Löschen");
			menuItem.addActionListener(new PersonLoschenListener(this.kontakte, p1));
			menu.add(menuItem);
			menu.show(arg0.getComponent(), arg0.getX(), arg0.getY());
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
