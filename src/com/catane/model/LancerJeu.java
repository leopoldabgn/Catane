package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import com.catane.view.Window;

public class LancerJeu {

	public static void main(String[] args) {
		
		String jouer = "command";

		// Demander taille plateau.
		
		Board board = new Board(4);
		
		board.setPlayers(new ArrayList<Player>(Arrays.asList(new Player[] {
				new Player(Color.RED),
				new Player(Color.BLUE),
				new Player(Color.GREEN)})));
		
		board.display();

		for(int i=0;i<board.getSize();i+=2) {
			board.putColony(i, i);
			board.nextRound();
		}
		board.display();
		
		if(jouer.equals("command")) {
			
		}
		else if(jouer.equals("gui")){
			new Window(600, 600);
		}
		
	}

}
