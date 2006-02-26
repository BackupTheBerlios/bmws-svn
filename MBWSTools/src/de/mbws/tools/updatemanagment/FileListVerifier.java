package de.mbws.tools.updatemanagment;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * This class compares to FileLists and sees which files are not up to date. It
 * will allways check the list of the server by calculating the checksum for
 * each file on the client !
 * 
 * @author Kerim
 * 
 */
public class FileListVerifier {
	private String rootPath;
	private Document serverDocument;
	private Document clientDocument;
	private ArrayList<String> filesToDownload = new ArrayList<String>();
	private ArrayList<String> filesToDelete = new ArrayList<String>();

	// TODO: change the serverfile to an already assembled doc ?
	public FileListVerifier(String theRootPath, String theServerFile) {
		rootPath = theRootPath;

		assembleClientDocument(rootPath);

		readInFileList(theServerFile);
		assembleListToDelete();
		assembleListToDownload();
	}

	private void assembleClientDocument(String rootPath2) {
		FileListAssembler fla = new FileListAssembler(rootPath, true);
		SAXBuilder builder = new SAXBuilder();
		InputStream is = new ByteArrayInputStream(fla.getFileList().getBytes());
		try {
			clientDocument = builder.build(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void assembleListToDownload() {
		List childs = serverDocument.getRootElement().getChildren();
		Iterator iter = childs.iterator();
		while (iter.hasNext()) {
			Element e = (Element) iter.next();

			FileEntry fe = new FileEntry(e.getAttributeValue("name"), Long
					.parseLong(e.getAttributeValue("size")), Long.parseLong(e
					.getAttributeValue("checksum")));
			File f = new File(rootPath + fe.getFileName());
			if (f.exists() && f.length() == fe.getSize()
					&& fe.getChecksum() == assembleCheckSumForFile(f)) {
				continue;
			}
			filesToDownload.add(fe.getFileName());
		}
	}

	/**
	 * now we must check for those files that the client has but the server not.
	 * Those must be deleted !
	 */
	private void assembleListToDelete() {
		Element serverRoot = serverDocument.getRootElement();
		Element clientRoot = clientDocument.getRootElement();
		List clientChilds = clientRoot.getChildren();
		Iterator clientIterator = clientChilds.iterator();
		while (clientIterator.hasNext()) {
			Element clientElement = (Element) clientIterator.next();
			List serverChilds = serverRoot.getChildren();
			Iterator serverIterator = serverChilds.iterator();
			boolean foundInServer = false;
			while (serverIterator.hasNext()) {
				Element serverElement = (Element) serverIterator.next();
				if (serverElement.getAttributeValue("name").equals(
						clientElement.getAttributeValue("name"))) {
					foundInServer = true;
					break;
				}
			}
			if (!foundInServer) {
				filesToDelete.add(clientElement.getAttributeValue("name"));
			}
		}

	}

	private long assembleCheckSumForFile(File f) {
		try {
			FileInputStream fis = new FileInputStream(f);
			byte[] data = new byte[1024];
			CRC32 crc = new CRC32();
			while (fis.available() > 0) {
				int readBytes = fis.read(data);
				if (readBytes < 1) {
					break;
				}
				crc.update(data);
			}
			fis.close();
			return crc.getValue();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return 0;
		}
	}

	// TODO: change that to a bytearray after we got the file from the server !
	private void readInFileList(String theClientFile) {
		SAXBuilder builder = new SAXBuilder();
		try {
			serverDocument = builder.build(new File(theClientFile));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * this is just for testing
	 * 
	 * @param args
	 */
	// TODO: REMOVE ME LATER !
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out
					.println("you didnt give me the two files and the scanmode (full,simple) you sucker !");
		}
		BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		// logger.info("Init log4j ... done");
		// FileListVerifier flv = new FileListVerifier(args[0], args[1], false);
		FileListVerifier flv = new FileListVerifier(
				"d:\\Programmierung\\Projekte\\MBWSClient", "d:\\files.xml");
		flv.start();
	}

	private void start() {
		// TODO Auto-generated method stub

	}
}
