package view.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * @since 03.01.2015
 * @author Julian Schelker
 */
public class AskForFillingAttributes extends JDialog {

	private static final long	serialVersionUID	= 7897796878215771120L;

	public static void main(String[] args) {
		new AskForFillingAttributes().setVisible(true);
	}

	public AskForFillingAttributes() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.add(new JLabel(
			"Bitte wähle welche Attribute automatisch ausgefüllt werden\n"
				+ "sollen und wann nachgefragt werden sollte."), c);

		this.setTitle("Attribute ausfüllen");

		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.pack();
		this.setMinimumSize(this.getSize());
		this.setLocationRelativeTo(null);
	}

}
