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

		// idées pour gui (si possible) :
		// - mettre le jeu en plein écran directement
		// - afficher les ressources de tous les joueurs (deux de chaque côté)

	}

}
