package de.mbws.tools.updatemanagment.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Properties;

import javax.swing.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import se.datadosen.component.RiverLayout;
import de.mbws.tools.updatemanagment.common.FileListVerifier;

public class Launcher extends JFrame {
	private static Logger logger = Logger.getLogger(Launcher.class);

	private ListResourceBundle resourceBundle;
	private Properties properties;
	// private Properties languageProperties;
	private JButton playBtn;
	private ProgressPanel progressPanel;
	private static String baseURL = "localhost";
	private static int basePort = 8080;
	private SocketChannel channel;
	private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
	private HttpClient client;

	public Launcher() throws HeadlessException {
		super();
		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
				"false");
		System
				.setProperty(
						"org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient",
						"debug");
		BasicConfigurator.configure();
		PropertyConfigurator.configure("log4j.properties");
		client = new HttpClient();
		logger.info("Init log4j ... done");
		logger.info("Launcher started: initializing locale");
		initLocale();
		setTitle(resourceBundle.getString("TITLE"));
	}

	public void start() {
		try {
			initPaths();
			setLayout(new RiverLayout());
			String distributionFiles = getFileListFromServer();
			add("br", getNewsPanel());
			add("br hfill", getUpdatePane());

			int sizeX = 800;
			int sizeY = 600;
			setLocation(
					(Toolkit.getDefaultToolkit().getScreenSize().width - sizeY) / 2,
					(Toolkit.getDefaultToolkit().getScreenSize().height - sizeX) / 2);
			setSize(new Dimension(sizeX, sizeY));
			setVisible(true);
			startUpdate(properties.getProperty("GAME_DIR"), distributionFiles);
			disconnect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			disconnect();
			JOptionPane.showMessageDialog(this, ioe.getMessage(), resourceBundle
					.getString("ERROR"), JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}

	public void disconnect() {

		if (channel != null && channel.isConnected()) {
			try {
				channel.close();
			} catch (IOException e) {
				logger.error("Error closing connection", e);
			}
		}
	}

	// private void initMessagePane() {
	//
	// }

	public void startUpdate(String rootPath, String distributionFiles)
			throws IOException {
		FileListVerifier flv = new FileListVerifier(rootPath, distributionFiles);
		ArrayList<String> filesToDownload = flv.getFilesToDownload();
		if (filesToDownload != null && filesToDownload.size() != 0) {
			progressPanel.setTotalNumberOfFiles(filesToDownload.size());
			for (int i = 0; i < filesToDownload.size(); i++) {
				try {
					getFile(filesToDownload.get(i));
				} catch (IOException e) {
					logger.error("URL Error: ", e);
					progressPanel.setVisible(false);
					throw e;
				}
				progressPanel.fileCompleted();
			}
		}

		// ArrayList<String> filesToDelete = flv.getFilesToDelete();
		// if (filesToDelete != null && filesToDelete.size() != 0) {
		// deleteFile(files)
		// }

		playBtn.setEnabled(true);
	}

	private JScrollPane getNewsPanel() throws IOException {
		// JScrollPane p = new JScrollPane();
		HttpMethod method = new GetMethod("http://www.codeboje.de/mbws");
		logger.info("Status Code for GET Request:" + client.executeMethod(method));
		String response = method.getResponseBodyAsString();

		JEditorPane je = new JEditorPane(new URL("http://www.dpunkt.de/java/Referenz/Das_Paket_javax.swing/80.html"));//http://www.codeboje.de/mbws"));
		
		// p.setLayout(new GridBagLayout());
		// GridBagConstraints gbc = new GridBagConstraints();
		// Insets i = new Insets(2, 2, 2, 2);
		// gbc.insets = i;
		// gbc.gridx = 0;
		// gbc.gridy = 0;
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane p = new JScrollPane(je);
	p.setPreferredSize(new Dimension(780,400));
		// p.add(je, gbc);
		return p;
	}

	private void getFile(String fileToDownload) throws IOException {

		String fileString = "d:/test/" + fileToDownload;
		if (File.separator.equals("\\")) {
			fileString = fileString.replace("/", File.separator);
		} else {
			fileString = fileString.replace("\\", File.separator);
		}

		fileToDownload = fileToDownload.replace("\\", "/");
		fileToDownload = fileToDownload.replace(" ", "%20");

		HttpMethod method = new GetMethod("http://" + baseURL + ":" + basePort
				+ "/" + fileToDownload);
		logger.info("Status Code for GET Request:" + client.executeMethod(method));
		progressPanel.setCurrentFile(fileToDownload);
		InputStream input = method.getResponseBodyAsStream();
		File file = new File(fileString);
		int endIndex = file.getAbsolutePath().lastIndexOf(File.separator);

		File dir = new File(file.getAbsolutePath().substring(0, endIndex));
		dir.mkdirs();
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		byte[] b = new byte[1024];
		int read;
		while ((read = input.read(b)) != -1) {
			fos.write(b, 0, read);
		}
		input.close();
		method.releaseConnection();
		fos.close();
	}

	private JPanel getUpdatePane() {

		JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		Insets i = new Insets(2, 2, 2, 2);
		gbc.insets = i;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		progressPanel = new ProgressPanel(resourceBundle);
		p.add(progressPanel, gbc);
		// gbc.gridx = 4;
		gbc.gridy += 1;
		p.add(getButtonPane(), gbc);
		return p;
	}

	private void initPaths() throws IOException {
		File f = new File("config.properties");
		properties = new Properties();
		// TODO: exists and cant read ? what then ?
		if (f.exists() && f.canRead()) {
			logger.info("found config file, loading....");
			InputStream in = new FileInputStream(f);
			properties.load(in);
		} else {
			logger
					.info("didnt find config file, creating one and installing game anew....");
			f.createNewFile();
			showStorageLocationDialog(f);
		}

	}

	/**
	 * retrieves the list of files for the currect release from a server. This is
	 * a simple http call
	 * 
	 * @return
	 */
	private String getFileListFromServer() throws MalformedURLException,
			IOException {

		logger.info("getting filelist from server");
		// Create a URL for the desired page
		URL url = new URL("http://localhost:8080/files.xml");

		// Read all the text returned by the server
		BufferedReader in = new BufferedReader(new InputStreamReader(url
				.openStream()));
		String str;
		StringBuffer fileList = new StringBuffer();
		while ((str = in.readLine()) != null) {
			fileList.append(str);
		}
		in.close();
		logger.info("got filelist from server");
		logger.info(fileList.toString());
		return fileList.toString();

	}

	private void showStorageLocationDialog(File f) throws IOException {
		boolean success = false;

		String defaultDirectory = System.getProperty("user.dir"
				+ File.pathSeparator + "mbws" + File.pathSeparator);
		DirectoryDialog df = new DirectoryDialog(this, resourceBundle
				.getString("DIR"), defaultDirectory, resourceBundle
				.getString("ENTER_DIR"));
		logger.info("selected dir: " + df.getDirectory());
		File dir = new File(df.getDirectory());
		success = dir.mkdirs();
		while (!success) {
			logger.info("Failed to Create the directory");
			df.setVisible(true);
			dir = new File(df.getDirectory());
			logger.info("new selected dir: " + df.getDirectory());
			success = dir.mkdirs();
		}
		properties.setProperty("GAME_DIR", dir.getAbsolutePath());
		FileOutputStream fout = new FileOutputStream(f);
		properties.store(fout, "Directory where game is stored");
	}

	private void initLocale() {
		Locale l = Locale.getDefault();
		if (l.getLanguage().equals(Locale.GERMAN.getLanguage())) {
			resourceBundle = new LauncherResourceBundle_de();
		} else {
			resourceBundle = new LauncherResourceBundle();
		}
	}

	private JPanel getButtonPane() {
		JPanel p = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		p.setLayout(new GridBagLayout());
		playBtn = new JButton(resourceBundle.getString("PLAY"));
		playBtn.setEnabled(false);
		JButton exit = new JButton(resourceBundle.getString("QUIT"));
		p.add(playBtn, gbc);
		gbc.gridx += 1;
		p.add(exit, gbc);
		return p;
	}

	public static void main(String[] args) {
		Launcher l = new Launcher();
		l.start();
	}

	private class DirectoryDialog extends JDialog implements ActionListener {

		private String dir;
		private JTextField dirTf;

		public String getDirectory() {
			return dir;
		}

		public DirectoryDialog(Frame f, String dirLabel, String directory,
				String dirPath) {
			super(f);
			setSize(new Dimension(400, 150));
			setModal(true);
			setLayout(new BorderLayout());
			add(getDirectoyPanel(dirLabel, directory, dirPath), BorderLayout.CENTER);
			add(getButtonPanel(), BorderLayout.SOUTH);
			setVisible(true);
		}

		private JPanel getDirectoyPanel(String dirLabel, String directory,
				String dirPath) {
			JPanel p = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(1, 2, 1, 2);
			// gbc.weightx = 1.0;
			// gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			JLabel dirLb = new JLabel(dirLabel);
			dirTf = new JTextField(directory);
			dirTf.addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == KeyEvent.VK_ENTER) {
						actionPerformed(null);
					} else
						System.out.println(e.getKeyCode());
				}

				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

			});
			gbc.gridwidth = 1;
			gbc.gridy += 1;
			p.add(dirLb, gbc);
			// gbc.weightx = 1.0;
			gbc.gridwidth = 4;
			gbc.gridx += 1;
			p.add(dirTf, gbc);
			JLabel text = new JLabel(dirPath);
			gbc.weightx = 1.0f;
			gbc.gridx = 0;
			gbc.gridy += 1;
			gbc.gridwidth = 4;
			p.add(text, gbc);
			return p;
		}

		private JPanel getButtonPanel() {
			JPanel p = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(1, 2, 1, 2);
			// gbc.weightx = 1.0;
			gbc.gridwidth = 1;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			JButton okBtn = new JButton("OK");
			okBtn.addActionListener(this);
			okBtn.setEnabled(false);
			// new ActionListener() {
			// public void actionPerformed(ActionEvent event) {
			// if (dirTf.getText() != null
			// && !dirTf.getText().trim().equals("")) {
			// dir = dirTf.getText().trim();
			// setVisible(false);
			// }
			// }
			// });
			// okBtn.requestFocus();
			p.add(okBtn, gbc);
			return p;
		}

		public void actionPerformed(ActionEvent event) {
			if (dirTf.getText() != null && !dirTf.getText().trim().equals("")) {
				dir = dirTf.getText().trim();
				setVisible(false);
			}
		}
	}
}
