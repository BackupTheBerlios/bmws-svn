package de.terrainer.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.terrainer.GuiActionDescriptor;
import de.terrainer.MetaInfo;

public class PropertyPanel extends JPanel {
	private static class ComDesc {
		public ComDesc(JTextField com, MetaInfo info) {
			this.com = com;
			this.info = info;
		}

		JTextField com;
		MetaInfo info;
	}

	private JPanel innerPan;
	private List<ComDesc> elements = new ArrayList<ComDesc>();
	private GuiActionDescriptor descr;

	public PropertyPanel() {
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		setLayout(fl);
		innerPan = new JPanel(new GridBagLayout());
		add(innerPan);
	}

	public void update(GuiActionDescriptor desc) {
		descr = desc;
		innerPan.removeAll();
		elements.clear();
		if (desc == null) {
			return;
		}
		MetaInfo[] infos = desc.getMetaInfo();
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.gridy = 0;
		for (MetaInfo info : infos) {
			gc.gridx = 0;
			innerPan.add(new JLabel(info.displayName), gc);
			gc.gridx++;
			IntegerInputField input = new IntegerInputField(getIntProperty(info.codeName));
			elements.add(new ComDesc(input, info));
			input.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					transferValues();
				}

				public void removeUpdate(DocumentEvent e) {
					transferValues();
				}

				public void changedUpdate(DocumentEvent e) {
					transferValues();
				}
			});

			innerPan.add(input, gc);
			gc.gridy++;
		}
	}

	protected void transferValues() {
		for (Iterator<ComDesc> iter = elements.iterator(); iter.hasNext();) {
			ComDesc comdesc = iter.next();
			setIntProperty(comdesc.info.codeName, Integer.parseInt("0" + comdesc.com.getText()),
					descr);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(150, 600);
	}

	private int getIntProperty(String name) {
		Object val = invokeMethod("get" + name, null);
		return ((Integer) val).intValue();
	}

	private Object invokeMethod(String name, Object arg) {
		Object val = null;
		try {
			Class[] args = null;
			Object[] argVals = null;
			if (arg != null && arg.getClass().equals(Integer.class)) {
				args = new Class[] { Integer.TYPE };
				argVals = new Object[] { arg };
			}
			else {
				args = new Class[0];
				argVals = new Object[0];
			}
			Method method = descr.getClass().getMethod(name, args);
			val = method.invoke(descr, argVals);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	private void setIntProperty(String name, int value, GuiActionDescriptor descr) {
		invokeMethod("set" + name, new Integer(value));
	}
}
