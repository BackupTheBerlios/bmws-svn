package de.mbws.server.payloadcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * PayloadCreator creates data classes from a simple text description. Using
 * templates the created classes provide:
 * <li>members</li>
 * <li>getters and setters for the members</li>
 * <li>a serialize and a deserialize method</li>
 * 
 * @author Axel Sammet
 */
public class PayloadCreator {
	BufferedReader reader;
	int currentLineNo = 0;
	Map<String, ClassDeclaration> classes = new HashMap<String, ClassDeclaration>();
	Properties props = new Properties();
	String type;

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
		classes.put("int", null);
		classes.put("float", null);
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
		}
		catch (RuntimeException e) {
			System.err.println("ERROR in line " + currentLineNo + ": " + e.getMessage());
			e.printStackTrace();
		}
		// search for template types
		Set<String> typeSet = new HashSet<String>();
		for (Iterator iter = props.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (key.startsWith("temp.")) {
				typeSet.add(key.substring(5, key.indexOf('.', 8)));
			}
		}
		// generate classes
		for (Iterator<String> iter = typeSet.iterator(); iter.hasNext();) {
			String gentype = iter.next();
			type = gentype;
			System.out.println("Generating classes for type " + gentype);
			generateClasses();
		}
	}

	/**
	 * 
	 */
	private void generateClasses() {
		Iterator<String> it = classes.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (key.equals("String") || key.startsWith("int") || key.startsWith("float"))
				continue;
			ClassDeclaration classDecl = classes.get(key);
			System.out.println("INFO: generating " + classDecl.name);
			// generate class
			String templ = getProp("class");
			String classcontents = templ.replace("$name$", classDecl.name).replace("$fields$",
					generateFields(classDecl)).replace("$getandset$", generateGetterAndSetter(classDecl)).replace("$deserialize$",
					generateDeserialization(classDecl)).replace("$serialize$",
					generateSerialization(classDecl));
			generateFile(classDecl.name + "." + type, classcontents);
		}
	}

	/**
	 * @param string
	 * @param classcontents
	 */
	private void generateFile(String name, String classcontents) {
		String path = getProp("targetpath");
		try {
			FileWriter fw = new FileWriter(path + File.separator + name);
			fw.write(classcontents);
			fw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param classDecl
	 * @return
	 */
	private String generateFields(ClassDeclaration classDecl) {
		String fields = "";
		for (Iterator<FieldDeclaration> iter = classDecl.fields.iterator(); iter.hasNext();) {
			FieldDeclaration field = iter.next();
			fields += getProp("field").replace("$type$", field.type).replace("$name$", field.name)
					+ "\n";
		}
		return fields;
	}
	
	private String generateGetterAndSetter(ClassDeclaration classDecl) {
		String fields = "\n";
		for (Iterator<FieldDeclaration> iter = classDecl.fields.iterator(); iter.hasNext();) {
			FieldDeclaration field = iter.next();
			fields += getProp("get").replace("$type$", field.type).replace("$upname$",
					startWithUpper(field.name)).replace("$name$", field.name)
					+ "\n\n";
			fields += getProp("set").replace("$type$", field.type).replace("$upname$",
					startWithUpper(field.name)).replace("$name$", field.name)
					+ "\n\n";

		}
		return fields;
	}

	private String generateSerialization(ClassDeclaration classDecl) {
		String code = "";
		for (Iterator<FieldDeclaration> iter = classDecl.fields.iterator(); iter.hasNext();) {
			FieldDeclaration field = iter.next();
			if (field.type.equals("String")) {
				code += "\t\twriteString(payload, " + field.name + ");\n";
			}
			else if (field.type.equals("int")) {
				code += "\t\tpayload.putInt(" + field.name + ");\n";
			}
			else if (field.type.equals("float")) {
				code += "\t\tpayload.putFloat(" + field.name + ");\n";
			}
			else if (classes.containsKey(field.type)) {
				code += "\t\t" + field.name + getProp("classSep") + "serialize(payload);\n";
			}
			else {
				throw new RuntimeException("ERROR: unknown type: " + type);
			}
		}
		return code + "\t";
	}

	private String generateDeserialization(ClassDeclaration classDecl) {
		String code = "";
		for (Iterator<FieldDeclaration> iter = classDecl.fields.iterator(); iter.hasNext();) {
			FieldDeclaration field = iter.next();
			if (field.type.equals("String")) {
				code += "\t\t" + field.name + " = readString(payload);\n";
			}
			else if (field.type.equals("int")) {
				code += "\t\t" + field.name + " = payload.getInt();\n";
			}
			else if (field.type.equals("float")) {
				code += "\t\t" + field.name + " = payload.getFloat();\n";
			}
			else if (classes.containsKey(field.type)) {
				code += "\t\t" + field.name + " = "
						+ getProp("creator").replace("$type$", field.type);
				code += "\t\t" + field.name + getProp("classSep")
						+ "deserialize(payload);\n";
			}
			else {
				throw new RuntimeException("ERROR: unknown type: " + type);
			}
		}
		return code + "\t";

	}

	/**
	 * @param string
	 * @return
	 */
	private String getProp(String name) {
		String key = "temp." + type + "." + name;
		String ret = props.getProperty(key);
		if (ret == null)
			throw new RuntimeException("Unknown property " + key);
		return ret;
	}

	private String startWithUpper(String in) {
		return in.substring(0, 1).toUpperCase() + in.substring(1);
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
			StringTokenizer tokenizer = new StringTokenizer(line, " \t");
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
			classdecl.fields.add(fieldDecl);
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
