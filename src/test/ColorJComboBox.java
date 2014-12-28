package test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

public class ColorJComboBox extends JComboBox<String> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * This method initializes
	 * 
	 */
	public ColorJComboBox() {
		super();
		initialize();
	}

	/**
	 * This Method will return true if active is showing false otherwise
	 * 
	 * @return
	 */
	public boolean getIsActive() {
		return "Active".equals(super.getSelectedItem());
	}

	/**
	 * This method will set the default state of the Dropdown
	 * 
	 * @param isActive
	 */
	public void setIsActive(boolean isActive) {
		super.setSelectedItem(isActive ? "Active" : "InActive");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(109, 24));
		super.addItem("Active");
		super.addItem("InActive");
		super.setEditable(false);
		super.setRenderer(new DefaultListCellRenderer() {
			private static final long	serialVersionUID	= 3231706195087516815L;

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JLabel retval = (JLabel) super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);
				retval.setFont(retval.getFont().deriveFont(Font.BOLD));
				retval.setHorizontalAlignment(JLabel.CENTER);
				if ("Active".equals(value)) {
					retval.setForeground(Color.green);
				} else {
					retval.setForeground(Color.red);
				}
				return (JLabel) retval;
			}
		});
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// Create the GUI and show it. For thread safety,
				// this is invoked from the event-dispatching thread.

				// setup Look and Feel
				try {
					javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
						.getSystemLookAndFeelClassName());
					// javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {}

				// Create and set up the window.
				javax.swing.JFrame frame = new javax.swing.JFrame("JTimestampComboBox");
				frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

				// Create and set up the content pane.
				javax.swing.JPanel newContentPane = new javax.swing.JPanel();
				newContentPane.setOpaque(true); // content panes must be opaque

				// create a JIsActiveComboBox for test
				ColorJComboBox test = new ColorJComboBox();
				newContentPane.add(test);

				// put in a normal JComboBox for comparison
				String[] tmp = { "JComboBox", "Standard" };
				JComboBox<String> tmp2 = new JComboBox<String>(tmp);
				newContentPane.add(tmp2);

				// put the new Content Pane on the frame
				frame.setContentPane(newContentPane);

				// Display the window.
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}