package com.catane.view;

import java.util.Scanner;

import com.catane.model.Board;
import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cases.ResourceCase;

public class CLI {

	private Game game;
	private Board board;
	private Scanner sc;
	
	public CLI(Game game) {
		this.game = game;
		this.board = game.getBoard();
		startGame();
	}
	
	public void startGame() {

		openScan();

		System.out.println("Bonjour,");
		System.out.println("Vous vous apprêtez à lancer une partie des Colons de Catane.");
		int p;
		do {
			System.out.println("Voulez vous jouer à 3 ou 4 joueurs ? (3/4)");
			p = sc.nextInt();
		} while (p != 3 && p != 4);
		game.setupPlayers(p);
		sc.nextLine();

		//ajout des ressources pour test
		/*
		actualPlayer.gainResource(Resource.CLAY);
		actualPlayer.gainResource(Resource.WHEAT);
		actualPlayer.gainResource(Resource.WOOD);
		actualPlayer.gainResource(Resource.SHEEP);
		*/

		for(int i=0;i<20;i++) {
			game.getActualPlayer().gainResource(Resource.WHEAT);
			game.getActualPlayer().gainResource(Resource.STONE);
			game.getActualPlayer().gainResource(Resource.WOOL);
		}
		
		board.display();
		boolean endGame = game.endGame();
		
		while(!endGame) {
			playRound();
			board.display();
			endGame = game.endGame();
			if(!endGame)
				game.nextRound();
		}
		
		System.out.println(game.getActualPlayer()+" a gagné !"); // J2 non !!! C est J1 !!
		
		closeScan();

	}
	
	public void playRound() {
		Player player = game.getActualPlayer();
		
		System.out.println("Au tour de "+player.getName());
		boolean endRound;
		char c;
		int[] coord;
		boolean error;
		
		do {
			PlayerView.printScore(player);
			PlayerView.printResources(player);
			PlayerView.printDevelopmentCards(player);
			System.out.println();
			endRound = true;
			c = player.askAction(sc);
			coord = null;
			error = true;
			
			do {
				switch(c) {
					case 'c':
						if(!player.canAffordColony()) {// Si il n'a pas assez d'argent. Ou il n'a pas de colony dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une colonie !");
							endRound = false;
							error = false;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = player.canBuildColonyOn(board, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Vous ne pouvez pas poser de colonie ici !");
							else if(ans == 3)
									System.out.println("Il y a deja une colonie ou une ville aux alentours !");
							else {
								error = false;
								board.putColony(player, coord[0], coord[1]);
							}
						}
						break;
					case 'v':
						if(!player.canAffordTown()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une ville !");
							endRound = false;
							error = false;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = player.canBuildTownOn(board, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Cette case n'est pas une colonie !");
							else if(ans == 3)
									System.out.println("Cette colonie n'est pas a vous !");
							else {
								error = false;
								board.putTown(player, coord[0], coord[1]);
							}
						}
						break;
					case 'r':
						if(!player.canAffordRoad()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une route !");
							endRound = false;
							error = false;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = player.canBuildRoadOn(board, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Vous ne pouvez pas poser de route ici !");
							else {
								error = false;
								board.putRoad(player, coord[0], coord[1]);
							}
						}
						break;
					case 'd':
						if(!player.canAffordDevCard()) {
							System.out.println("Vous n'avez pas les ressources pour acheter une carte de developpement !");
						}
						else {
							int ans = player.canBuyDevCard(game);
							if(ans == 2) 
								System.out.println("Il n'y a plus de carte developpement dans le paquet !");
							else
								player.getDevCard(game);
						}
						error = false;
						endRound = false; // Le tour continu meme quand on achete une carte de dev ?
						break;
					case 'e':
						error = false;
						break;
				}
				
			} while(error && !player.hasWon());
			
		} while(!endRound && !player.hasWon());
		
	}
	

	public int[] askCoord() {
		int[] coord = null;
		String coordStr;
		do {
			if(coord != null) // Si != null, forcement les coordonnees sont outOfBorders.
				System.out.println("Ces coordonnées ne sont pas sur le plateau !");
			System.out.print("Donnez les coordonnées (ex: A8) : ");
			coordStr = game.getActualPlayer().askCoord(sc);
			coord = game.convertCoord(coordStr);
		} while(board.outOfBorders(coord[0], coord[1]));
		
		return coord;
	}
	
	public void thiefAction() {
		// plus des 7 cartes ressources : se défausser de la moitié inf (au choix)

		// déplacer le voleur sur une case différente
		System.out.println("Choisissez les nouvelles coordonnées du voleur");
		int[] coord = askCoord();
		while (!(board.getCase(coord[0], coord[1]) instanceof ResourceCase) && ((ResourceCase) board.getCase(coord[0], coord[1])).hasThief()) {
			// Vérifications : la case n'est pas une case ressource ou contient déjà le voleur
			if (!(board.getCase(coord[0], coord[1]) instanceof ResourceCase))
				System.out.println("Ce n'est pas une case ressource");
			else
				System.out.println("Cette case a déjà le voleur");
			coord = askCoord();
		}
		board.switchThief(coord);

		// actualPlayer vole une ressource au hasard à un joueur possédant une colonie autour de la nouvelle case

	}
	
	public void openScan() {
		sc = new Scanner(System.in);
	}
	
	public void closeScan() {
		sc.close();
	}
}

