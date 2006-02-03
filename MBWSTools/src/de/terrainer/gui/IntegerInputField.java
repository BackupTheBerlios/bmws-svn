package de.terrainer.gui;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.apache.log4j.Logger;

public class IntegerInputField extends JTextField {
	protected static Logger logger = Logger.getLogger(IntegerInputField.class);
	protected int minVal;
	protected int maxVal;

	public IntegerInputField(int val) {
		this(val, 0, Integer.MAX_VALUE);
	}

	public IntegerInputField(int val, int min, int max) {
		super(5);
		minVal = min;
		maxVal = max;
		setText(Integer.toString(val));
	}

	@Override
	protected Document createDefaultModel() {
		return new IntegerDocument();
	}

	public int getValue() {
		return Integer.valueOf(getText());
	}
	protected void checkRange() {
		String content = getText();
		if (!content.equals("")) {
			System.out.println("checking '" + content + "' in [" + minVal + "," + maxVal + "]");
			int val = Integer.parseInt(content);
			if (val > maxVal || val < minVal) {
				setForeground(Color.RED);
				setToolTipText(val > maxVal ? "value exceeds maximum of " + maxVal
						: "value is below minimum of " + minVal);
			}
			else {
				setForeground(Color.BLACK);
				setToolTipText(null);
			}
		}
	}

	public class IntegerDocument extends PlainDocument {
		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str.matches("[0-9]*")) {
				super.insertString(offs, str, a);
				checkRange();
				// if (getLength() > 1) {
				// setText(getText(0, getLength()).replaceFirst("^0*", ""));
				// }
			}
		}
		@Override
		public void remove(int offs,int len) throws BadLocationException {
			super.remove(offs, len);
			checkRange();
		}
	}
}
