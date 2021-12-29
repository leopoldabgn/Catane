package com.catane.launcher;

import com.catane.model.Game;
import com.catane.view.cli.CLI;
import com.catane.view.gui.GUI;

public class Launcher {

	public static void main(String[] args) {
		String jouer = "gui";
		if(args != null)
			if(args.length > 0)
				if(args[0].toUpperCase().equals("GUI"))
					jouer = "gui";
		
		Game game = new Game();
		
		if(jouer.equals("command")) {
			new CLI(game);
		}
		else if(jouer.equals("gui")) {
			new GUI(game, 1000, 800);
		}

	}

}
