package com.catane.launcher;

import com.catane.view.cli.CLI;
import com.catane.view.gui.GUI;

public class Launcher {

	public static void main(String[] args) {
		String jouer = "command";
		if(args != null)
			if(args.length > 0)
				if(args[0].toUpperCase().equals("GUI"))
					jouer = "gui";
		
		if(jouer.equals("command")) {
			new CLI();
		}
		else if(jouer.equals("gui")) {
			new GUI(1000, 800);
		}

	}

}
