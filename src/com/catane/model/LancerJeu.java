package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import com.catane.model.resources.Clay;
import com.catane.model.resources.Sheep;
import com.catane.model.resources.Wheat;
import com.catane.model.resources.Wood;
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
		
		if(jouer.equals("command")) {
			board.openScan();
			//while(true) {
			// Ressources pour pouvoir construire une colonie
			board.getActualPlayer().gainResource(new Clay());
			board.getActualPlayer().gainResource(new Sheep());
			board.getActualPlayer().gainResource(new Wood());
			board.getActualPlayer().gainResource(new Wheat());
			
			board.playRound();
			board.display();
			
			//}
			
			board.closeScan();
		}
		else if(jouer.equals("gui")){
			new Window(600, 600);
		}
		
	}

}
