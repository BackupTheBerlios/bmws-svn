package de.mbws.tools.updatemanagment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class FileListAssembler {
	private static Logger logger = Logger.getLogger(FileListAssembler.class);

	private String rootDirToScan;
	private String target;
	private String targetFileName;
	private boolean inMemoryMode;

	private TreeMap allFiles = new TreeMap();

	public FileListAssembler(String rootDir, String aTarget,
			String aTargetFileName) {
		super();
		rootDirToScan = rootDir;
		target = aTarget;
		targetFileName = aTargetFileName;
	}

	public FileListAssembler(String rootDir, boolean memoryMode) {
		super();
		rootDirToScan = rootDir;
		inMemoryMode = memoryMode;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		logger.info("Init log4j ... done");

		if (args.length != 3) {
			logger
					.info("Normal usage is 'java FileListAssembler rootDirToScan targetDir filename'");
			System.exit(1);
		} else {
			FileListAssembler fla = new FileListAssembler(args[0], args[1],
					args[2]);
			fla.startFileAssembly();
		}

	}

	public void startFileAssembly() {
		scanFiles(rootDirToScan);
		writeFileList();
		logger.info("Done !");
	}

	public String getFileList() {
		scanFiles(rootDirToScan);
		return toString();
	}

	private void scanFiles(String pathToScan) {
		File directory = new File(pathToScan);
		File[] files = directory.listFiles();
		if (files == null) {
			logger.error("This was no directory: "
					+ directory.getAbsolutePath());
		} else if (files.length == 0) {
			logger.info("Directory is empty, should be deleted: "
					+ directory.getAbsolutePath());
		} else {
			for (File f : files) {
				if (isToBeIgnored(f.getAbsolutePath())) {
					continue;
				}
				if (f.isDirectory()) {
					scanFiles(f.getAbsolutePath());
				} else {
					FileEntry fileEntry;
					try {
						fileEntry = new FileEntry(f, rootDirToScan);
						allFiles.put(f.getAbsolutePath(), fileEntry);
					} catch (IOException e) {
						logger.error("File problems: ", e);
					}

				}
			}
		}
	}

	/**
	 * Will LATER make use of a config file where you can define ignore patterns
	 * 
	 * @param absolutePath
	 * @return
	 */
	// TODO: add some comfiguration here
	private boolean isToBeIgnored(String absolutePath) {
		if (inMemoryMode) {
			return false;
		}
		if (absolutePath.contains(".svn")
				|| absolutePath.contains("src" + File.separator)) {
			return true;
		}
		return false;
	}

	/**
	 * writes an xml like file containing the list of fileentries for a release
	 */
	private void writeFileList() {
		File fileList = new File(target + targetFileName);
		try {
			FileOutputStream fos = new FileOutputStream(fileList);
			fos.write(toString().getBytes());
			fos.close();
		} catch (Exception e) {
			logger.error("Error writing file ", e);
		}

	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("<filelist>\n");
		Set allKeys = allFiles.keySet();
		Iterator iter = allKeys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			FileEntry fe = (FileEntry) allFiles.get(key);
			stringBuffer.append(fe.toString());
		}
		stringBuffer.append("</filelist>");
		return stringBuffer.toString();
	}

}
