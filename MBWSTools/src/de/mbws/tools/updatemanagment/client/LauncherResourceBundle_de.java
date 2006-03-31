package de.mbws.tools.updatemanagment.client;

import java.util.ListResourceBundle;

public class LauncherResourceBundle_de extends ListResourceBundle {
	Object[][] resource = {
			{ "PLAY", "Spielen" },
			{ "QUIT", "Verlassen" },
			{ "OPTIONS", "Optionen" },
			{ "TITLE", "Starter" },
			{
					"ENTER_DIR",
					"<html>Da dies der erste Start des Programms ist, geben Sie bitte ein Installationsverzeichnis an.<p>Das Spiel wird in einem Unterverzeichnis dieses Pfads installiert.</html>" },
			{ "DIR", "Verzeichnis" }, { "TOTALPROGRESS", "Fortschritt " },
			{ "DOWNLOADTEXT", "Lade herunter: " },{ "ERROR", "Fehler" } };

	protected Object[][] getContents() {
		return resource;
	}
}
