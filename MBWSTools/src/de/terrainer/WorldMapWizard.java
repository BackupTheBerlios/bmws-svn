package de.terrainer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.terrainer.gui.IntegerInputField;

public class WorldMapWizard extends JDialog {
	WorldMap world;
	IntegerInputField widthField = new IntegerInputField(64, 1, 10000);
	IntegerInputField heightField = new IntegerInputField(64, 1, 10000);
	IntegerInputField resolutionField = new IntegerInputField(6, 5, 10);
	boolean cancelled  = true;

	public WorldMapWizard(JFrame parent, WorldMap world) {
		super(parent, "WorldWizard", true);
		this.world = world;
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createInputPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
		pack();
		allign(parent);
	}

	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(new JLabel("Width"));
		panel.add(widthField);
		panel.add(new JLabel("Height"));
		panel.add(heightField);
		panel.add(new JLabel("Resolution (power of 2)"));
		panel.add(resolutionField);
		panel.setBorder(BorderFactory.createEtchedBorder());
		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 1));
		GridLayout gl = new GridLayout();
		gl.setHgap(5);
		JPanel innerPanel = new JPanel(gl);
		panel.add(innerPanel);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transferData();
				cancelled = false;
				dispose();
			}
		});
		innerPanel.add(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		innerPanel.add(cancelButton);
		return panel;
	}

	protected void transferData() {
		world.setHeight(heightField.getValue());
		world.setWidth(widthField.getValue());
		world.setSectionResolution(resolutionField.getValue());
	}

	public void allign(JFrame parent) {
		setLocation(parent.getWidth() / 2 - getWidth() / 2, parent.getHeight() / 2 - getHeight()
				/ 2);
	}

	public boolean isCancelled() {
		return cancelled;
	}
}
