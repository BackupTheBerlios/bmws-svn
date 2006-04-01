package de.mbws.tools.updatemanagment.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ListResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressPanel extends JPanel {

	private JProgressBar totalProgress;
	private JProgressBar fileProgress;
	private int totalNumberOfFile = 0;
	private int currentFileSize = 0;
	private int processedFiles = 0;
	private ListResourceBundle languageResource;
	private JLabel acticityLabel;
	private String activityText;

	public ProgressPanel(ListResourceBundle resource) {
		super();
		languageResource = resource;
		setLayout(new GridBagLayout());// new RiverLayout());
		totalProgress = new JProgressBar(0, 100);
		// fileProgress = new JProgressBar(0, 100);
		initPanel();
	}

	private void initPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		activityText = new String(languageResource.getString(("ASSEMBLE_FILE")));
		acticityLabel = new JLabel(activityText);
		gbc.gridwidth = 4;
		add(acticityLabel, gbc);
		gbc.gridy += 1;
		gbc.gridwidth = 1;
		// JLabel totalProgressLb = new JLabel(languageResource
		// .getString("TOTALPROGRESS"));
		// add(totalProgressLb, gbc);
		// gbc.gridx += 1;
		gbc.gridwidth = 4;
		add(totalProgress, gbc);
	}

	public void resetCurrentFileProgress() {
		fileProgress.setValue(0);
		currentFileSize = 0;
	}

	public void setCurrentFile(String file) {
		acticityLabel.setText(activityText + file);
	}

	public void addFileProgress(int amount) {
		int realAmount = 100 * amount / currentFileSize;
		fileProgress.setValue(realAmount);
	}

	public void fileCompleted() {
		processedFiles += 1;
		int realAmount = (int) (100f / (float) totalNumberOfFile * (float) processedFiles);
		totalProgress.setValue(realAmount);

	}

	public void setTotalNumberOfFiles(int size) {
		activityText = new String(languageResource.getString(("DOWNLOADTEXT")));
		totalNumberOfFile = size;
	}

	public void setCurrentFileSize(int size) {
		currentFileSize = size;
	}

	public void finished() {
		acticityLabel.setText(languageResource.getString("READY"));
	}

}
