package de.mwbs.server.payloadcreator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

public class PayloadCreator {
	BufferedReader reader;
	int currentLineNo = 0;
	Map<String, ClassDeclaration> classes = new HashMap<String, ClassDeclaration>();
	Properties props = new Properties();

	private static class FieldDeclaration {
		String type;
		String name;

		public FieldDeclaration(String type, String name) {
			this.type = type;
			this.name = name;
		}
	}

	private PayloadCreator() {
		// dummy classes for primitives
		classes.put("String", null);
		classes.put("int16", null);
		classes.put("int32", null);
	}

	private static class ClassDeclaration {
		String name;
		List<FieldDeclaration> fields = new LinkedList<FieldDeclaration>();

		public ClassDeclaration(String name) {
			this.name = name;
		}
	}

	public String readLine() {
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				currentLineNo++;
				line = line.trim();
				// ignore line comments
				if (!line.startsWith("#") && !line.equals(""))
					break;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return line;

	}

	public void generate() {
		try {
			String source = openFile();
			parseClasses();
			generateClasses();
		}
		catch (RuntimeException e) {
			System.err.println("ERROR in line " + currentLineNo + ": " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	private void generateClasses() {
		Iterator<String> it = classes.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			ClassDeclaration classDecl = classes.get(key);
			// generate class
			String templ = getTemplate("java", "class");
			
		}
	}

	/**
	 * @param string
	 * @return
	 */
	private String getTemplate(String type, String name) {
		return props.getProperty("temp."+type+"."+name);
	}

	/**
	 * @return
	 */
	private String openFile() {
		String source = "";
		try {
			props.load(new FileInputStream("mapping\\payloadcreator.properties"));
			reader = new BufferedReader(new FileReader("mapping\\Payloads.cfg"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return source;
	}

	/**
	 * @param source
	 */
	private void parseClasses() {
		String line;
		while ((line = readLine()) != null) {
			// System.out.println(line);
			StringTokenizer tokenizer = new StringTokenizer(line, " ");
			String beginClassToken;
			try {
				beginClassToken = tokenizer.nextToken();
			}
			catch (NoSuchElementException e) {
				throw new RuntimeException("Expected token 'begin_class'");
			}
			if (!"begin_class".equals(beginClassToken)) {
				throw new RuntimeException("Expected token 'begin_class' but found '"
						+ beginClassToken + "' ");
			}
			String className = tokenizer.nextToken();
			System.out.println("INFO: creating class " + className);
			ClassDeclaration classdecl = new ClassDeclaration(className);
			classes.put(className, classdecl);
			// creating class template
			parseFields(classdecl);
		}
	}

	/**
	 * 
	 */
	private void parseFields(ClassDeclaration classdecl) {
		String line;
		while ((line = readLine()) != null) {
			StringTokenizer tokenizer = new StringTokenizer(line, " ");
			String type = tokenizer.nextToken();
			if ("end_class".equals(type)) {
				return;
			}
			String fieldName = tokenizer.nextToken();
			System.out.println("INFO: creating field " + fieldName + " of type " + type);
			// creating field template
			if (!classes.containsKey(type))
				throw new RuntimeException("Unknown type '" + type + "'");
			FieldDeclaration fieldDecl = new FieldDeclaration(type, fieldName);
		}
		throw new RuntimeException("Unfinished class declaration ");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new PayloadCreator()).generate();
	}

}
