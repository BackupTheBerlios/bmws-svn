package org.mbws.blp2tgaconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class BNP2TGAConverter {

	ArrayList blpfiles = new ArrayList();
	ArrayList tgafiles = new ArrayList();

	private static String pathToExe;

	public BNP2TGAConverter(String exePath, String rootPath) {
		System.out.println("Using : " + exePath + " as path to executable");
		pathToExe = exePath;
		System.out.println("Assembling file list for blps");
		visitAllFiles(new File(rootPath), ".blp");
		debugAllFiles(false);
		System.out.println("Converting");
		convertBLPs();
		System.out.println("Assembling new file list for tgas");
		visitAllFiles(new File(rootPath), ".tga");
		System.out.println("Comparing the two filelists");
		if (blpfiles.size() != tgafiles.size()) {
			System.out.println("Not all BLPs were converted");
			System.out.println("Number of blps: " + blpfiles.size());
			System.out.println("Number of tgas: " + tgafiles.size());
			debugAllFiles(true);
		} else {
			System.out.println("Size is equal");
		}

	}

	// Recurse through all dirs and add all blp files
	public void visitAllFiles(File file, String suffix) {
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				visitAllFiles(new File(file, children[i]), suffix);
			}
		} else {
			if (suffix.equals(".blp")) {
				if (file.getAbsolutePath().endsWith(".blp")) {
					blpfiles.add(file.getAbsolutePath());
				}
			} else if (file.getAbsolutePath().endsWith(".tga")) {
				tgafiles.add(file.getAbsolutePath());
			}
		}
	}

	public void debugAllFiles(boolean tgaAsWell) {
		Iterator iter = blpfiles.iterator();
		while (iter.hasNext()) {
			System.out.println((String) iter.next());
		}

		if (tgaAsWell) {
			System.out
					.println("*****************************now the tga***************************");
			iter = tgafiles.iterator();
			while (iter.hasNext()) {
				System.out.println((String) iter.next());
			}
		}
	}

	public void convertBLPs() {
		Iterator iter = blpfiles.iterator();
		while (iter.hasNext()) {
			executeProcess((String) iter.next());
		}
	}

	public void executeProcess(String file) {
		try {

			String cmd = pathToExe + "\\BLP2toTGA.exe ";
			Runtime rt = Runtime.getRuntime();
			System.out.println("Executing " + cmd + " " + "\"" + file + "\"");
			Process proc = rt.exec(cmd + "\"" + file + "\"");
			// any error message?
			StreamCatcher errors = new StreamCatcher(proc.getErrorStream(),
					"ERROR");
			// any output?
			StreamCatcher output = new StreamCatcher(proc.getInputStream(),
					"OUTPUT");
			output.setProcess(proc);
			// kick them off
			errors.start();
			output.start();
			// any error???
			int exitVal = proc.waitFor();
			System.out.println("ExitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out
					.println("Usage: java BLP2TGAConverter PATH_TO_BLP2toTGA.exe ROOTPATHOFBLPS");
			System.exit(0);
		}
		BNP2TGAConverter converter = new BNP2TGAConverter(args[0], args[1]);
		System.out.println("done");
		System.exit(0);
	}

	class StreamCatcher extends Thread {
		InputStream is;
		Process process;
		String type;
		StringBuffer strbuffer = new StringBuffer();

		StreamCatcher(InputStream is, String type) {
			this.is = is;
			this.type = type;
		}

		public void setProcess(Process proc) {
			process = proc;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					strbuffer.append(line + "\n");
					if (line.contains("Success")) {
						process.destroy();
					}
					if (line.contains("Not a") || line.contains("Error")) {
						System.out.println(strbuffer);
					}
					// if (type.equals("ERROR") && line != null) {
					// System.out.println("Error " + line);
					// }
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public ArrayList getBlpfiles() {
		return blpfiles;
	}

	public void setBlpfiles(ArrayList blpfiles) {
		this.blpfiles = blpfiles;
	}

	public ArrayList getTgafiles() {
		return tgafiles;
	}

	public void setTgafiles(ArrayList tgafiles) {
		this.tgafiles = tgafiles;
	}
}
