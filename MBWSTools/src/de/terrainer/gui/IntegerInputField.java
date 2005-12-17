package de.terrainer.gui;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class IntegerInputField extends JTextField {

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
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			System.out.println("insert: " +str);
			if (str.matches("[0-9]*")) {
				if (getLength() > 1) {
					getText(0, getLength()).replaceFirst("^0*", "");
				}
				super.insertString(offs, str, a);
			}
		}
	}
}
