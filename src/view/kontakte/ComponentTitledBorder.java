package view.kontakte;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/** Changed by Julian Schelker, 2010 
 * added the possibility to aligne the component 
 */
/**
 * MySwing: Advanced Swing Utilites 3 * Copyright (C) 2005 Santhosh Kumar T
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

public class ComponentTitledBorder implements Border, MouseListener,
		MouseMotionListener, SwingConstants {
	int offset = 5;

	alignement componentAlignement;

	enum alignement {
		LEFT, CENTER, RIGHT
	}

	private int left;
	private Component titleComponent;
	private JComponent container;
	private Rectangle rect;
	private Border border;
	private boolean mouseEntered = false;

	public ComponentTitledBorder(Component titleComponent, JComponent container,
			Border border) {
		this(titleComponent, container, border, alignement.LEFT);
	}

	public ComponentTitledBorder(Component titleComponent, JComponent container,
			Border border, alignement compAlignement) {
		this.titleComponent = titleComponent;
		this.componentAlignement = compAlignement;
		this.container = container;
		this.border = border;
		left = offset;
		container.addMouseMotionListener(this);
		container.addMouseListener(this);
	}

	public boolean isBorderOpaque() {
		return true;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		
		
		
		Insets borderInsets = border.getBorderInsets(c);
		Insets insets = getBorderInsets(c);
		int temp = (insets.top - borderInsets.top) / 2;
		border.paintBorder(c, g, x, y + temp, width, height - temp);
		Dimension size = titleComponent.getPreferredSize();
		left = offset;
		switch (this.componentAlignement) {
		case CENTER:
			left = width / 2 - size.width / 2;
			break;
		case RIGHT:
			left = width - size.width - offset;
		}
		rect = new Rectangle(left, 3, size.width, size.height);
		SwingUtilities.paintComponent(g, titleComponent, (Container) c, rect);
		
	}

	public Insets getBorderInsets(Component c) {
		Dimension size = titleComponent.getPreferredSize();
		Insets insets = border.getBorderInsets(c);
		insets.top = Math.max(insets.top, size.height);
		return insets;
	}

	private void dispatchEvent(MouseEvent me) {
		if (rect != null && rect.contains(me.getX(), me.getY())) {
			dispatchEvent(me, me.getID());
		} else
			dispatchEvent(me, MouseEvent.MOUSE_EXITED);
	}

	private void dispatchEvent(MouseEvent me, int id) {
		Point pt = me.getPoint();
		pt.translate(-rect.x, 0);

		titleComponent.setSize(rect.width, rect.height);
		titleComponent.dispatchEvent(new MouseEvent(titleComponent, id, me.getWhen(), me
				.getModifiers(), pt.x, pt.y, me.getClickCount(), me
				.isPopupTrigger(), me.getButton()));
		if (!titleComponent.isValid()) {
			container.repaint();
		}
	}

	public void mouseClicked(MouseEvent me) {
		dispatchEvent(me);
	}

	public void mouseExited(MouseEvent me) {
		if (mouseEntered) {
			mouseEntered = false;
			dispatchEvent(me, MouseEvent.MOUSE_EXITED);
		}
	}

	public void mousePressed(MouseEvent me) {
		dispatchEvent(me);
	}

	public void mouseReleased(MouseEvent me) {
		dispatchEvent(me);
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent me) {
		if (rect == null) {
			return;
		}

		if (mouseEntered == false && rect.contains(me.getX(), me.getY())) {
			mouseEntered = true;
			dispatchEvent(me, MouseEvent.MOUSE_ENTERED);
		} else if (mouseEntered == true) {
			if (rect.contains(me.getX(), me.getY()) == false) {
				mouseEntered = false;
				dispatchEvent(me, MouseEvent.MOUSE_EXITED);
			} else {
				dispatchEvent(me, MouseEvent.MOUSE_MOVED);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

}
