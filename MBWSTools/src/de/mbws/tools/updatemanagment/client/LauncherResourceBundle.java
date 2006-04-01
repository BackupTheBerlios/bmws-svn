package de.mbws.tools.updatemanagment.client;

import java.util.ListResourceBundle;

public class LauncherResourceBundle extends ListResourceBundle {
	Object[][] resource = {
			{ "PLAY", "Play" },
			{ "QUIT", "Quit" },
			{ "OPTIONS", "Options" },
			{ "TITLE", "Launcher" },
			{
					"ENTER_DIR",
					"Since this is the first time you start the client plase provide an installation path.\n The game will be installed in a subfolder of that path." },
			{ "DIR", "Path" }, { "TOTALPROGRESS", "total progress " },
			{ "DOWNLOADTEXT", "Currently Downloading: " }, { "READY", "Ready ! " },
			{ "ASSEMBLE_FILE", "assembling list of files" }, { "ERROR", "Error" },
			{ "FAILED_CREATE_DIRECTORY", "Failed to create this directory" }
			

	};

	protected Object[][] getContents() {
		return resource;
	}
}
