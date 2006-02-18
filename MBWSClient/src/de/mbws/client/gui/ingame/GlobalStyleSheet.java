package de.mbws.client.gui.ingame;

import java.awt.Color;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.StyleSheet;

import org.apache.log4j.Logger;

public class GlobalStyleSheet extends StyleSheet{

	private static Logger logger = Logger.getLogger(GlobalStyleSheet.class);

	private static GlobalStyleSheet instance;
	public static String ALL = "all";
	public static String ADMIN = "admin";
	public static String PM = "privatemessage";
	public static String GROUP = "group";

	
	private GlobalStyleSheet() {
		setDefaultCSS();
	}

	public static GlobalStyleSheet getInstance() {
		if (instance == null) {
			instance = new GlobalStyleSheet();
		}
		return instance;
	}

	private void setDefaultCSS() {
		String css = "body {color: #A49E84; background-color: #484132; font-size: 10pt; "
				+ "font-family: verdana,tahoma,helvetica; } "
				+ "body a:link, body a:visited, body a:active { color: yellow; text-decoration: underline; }"
				+ "body a:hover { color: #FFFFFF; text-decoration: none; }"
				+ "input { color: #333333; background-color: #E7E9ED; border : 1px solid #333333; }";
		try {
			loadRules(new StringReader(css), null);
		} catch (IOException e) {
			logger.error("StyleSheet couldnt be loaded: ", e);
		}
	}

	public void setupStyles(StyledDocument doc) {
		Style def = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);
		Style st = doc.addStyle("roman", def);
		st = doc.addStyle("italic", def);
		StyleConstants.setItalic(st, true);
		st = doc.addStyle("bold", def);
		StyleConstants.setBold(st, true);
		st = doc.addStyle("underline", def);
		StyleConstants.setUnderline(st, true);
		st = doc.addStyle("all", def);
		StyleConstants.setForeground(st, Color.BLACK);
		st = doc.addStyle("admin", def);
		StyleConstants.setForeground(st, Color.ORANGE);
		st = doc.addStyle("privatemessage", def);
		StyleConstants.setForeground(st, Color.GREEN);
		st = doc.addStyle("group", def);
		StyleConstants.setForeground(st, Color.BLUE);
	}

}
