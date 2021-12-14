package com.catane.view;

import com.catane.model.Game;

public class CLILauncher {

	public static void main(String[] args) {
		String jouer = "gui";
		
		Game game = new Game();
		
		if(jouer.equals("command")) {
			new CLI(game);
		}
		else if(jouer.equals("gui")) {
			new GUI(game, 600, 600);
		}
	}

}
