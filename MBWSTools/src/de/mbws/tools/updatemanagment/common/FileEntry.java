package de.mbws.tools.updatemanagment.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

/**
 * The class FileEntry holds all important information of a file. This is the
 * name, the size and the crc32 checksum
 * 
 * @author Kerim Mansour
 * 
 */
// TODO: should we store filename and path seperately ?
public class FileEntry {
	private String fileName;
	private long size;
	private long checksum;

	private String scannedRootDir;

	public FileEntry(String aFileName, long aSize, long aChecksum) {
		fileName = aFileName;
		size = aSize;
		checksum = aChecksum;
	}

	public FileEntry(File f, String rootDir) throws IOException {
		scannedRootDir = rootDir;
		fileName = getRelativeFilePath(f);// f.getAbsolutePath();
		size = f.length();
		CRC32 crc = new CRC32();
		FileInputStream fis;

		fis = new FileInputStream(f);
		byte[] data = new byte[1024];
		while (fis.available() > 0) {
			int readBytes = fis.read(data);
			if (readBytes < 1) {
				break;
			}
			crc.update(data);
		}
		fis.close();
		checksum = crc.getValue();

	}

	/**
	 * This method serializes the data of the fileentry into a bytearray usable
	 * for fileoutput
	 * 
	 * @return
	 */
	public byte[] toByteArray() {
		return toString().getBytes();
	}
	
	public String toString() {
		return "<file name=\"" + fileName + "\" size=\"" + size
		+ "\" checksum=\"" + checksum + "\" />\n";
	}

	/**
	 * will retrieve the path relative to the rootDir
	 */
	private String getRelativeFilePath(File file) {
		String filePath = file.getAbsolutePath();
		String relativePath = filePath.substring(scannedRootDir.length() + 1);
		return relativePath;
	}

	public long getChecksum() {
		return checksum;
	}

	public String getFileName() {
		return fileName;
	}

	public long getSize() {
		return size;
	}
}