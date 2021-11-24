package com.catane.model;

import com.catane.view.Window;

public class LancerJeu {

	public static void main(String[] args) {
		
		String jouer = "command";
		
		// Demander taille plateau.
		
		Board board = new Board(4);
		board.display();
		
		if(jouer.equals("command")) {
			
		}
		else if(jouer.equals("gui")){
			new Window(600, 600);
		}
		
	}

}
