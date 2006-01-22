package de.terrainer.gui;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.apache.log4j.Logger;

public class IntegerInputField extends JTextField {
	private static Logger logger = Logger.getLogger(IntegerInputField.class);

	public IntegerInputField(int val) {
		super(5);
		setText(Integer.toString(val));
	}

	@Override
	protected Document createDefaultModel() {
		return new IntegerDocument();
	}

	public static class IntegerDocument extends PlainDocument {
		@Override
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			logger.info("insert: " + str);
			if (str.matches("[0-9]*")) {
				if (getLength() > 1) {
					getText(0, getLength()).replaceFirst("^0*", "");
				}
				super.insertString(offs, str, a);
			}
		}
	}
}
