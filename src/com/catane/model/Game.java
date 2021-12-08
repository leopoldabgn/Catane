package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.DevelopmentCardsDeck;
import com.catane.view.Window;

public class Game {

	private List<Player> players;
	private Player actualPlayer;
	private Scanner sc;
	private Board board;
	private DevelopmentCardsDeck developmentCardsDeck;
	
	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		
		String jouer = "command";

		// Demander taille plateau.
		
		board = new Board(4);
		developmentCardsDeck = new DevelopmentCardsDeck();
		
		if(jouer.equals("command")) {
			startGame();
		}
		else if(jouer.equals("gui")){
			new Window(600, 600);
		}
		
	}

	public void playRound() {
		
		System.out.println("Au tour de "+actualPlayer.getName());
		boolean endRound;
		char c;
		int[] coord;
		boolean error;
		
		do {
			actualPlayer.printResources();
			actualPlayer.printDevelopmentCards();
			System.out.println();
			endRound = true;
			c = actualPlayer.askAction(sc);
			coord = null;
			error = true;
			
			do {
				switch(c) {
					case 'c':
						if(!actualPlayer.canAffordColony()) {// Si il n'a pas assez d'argent. Ou il n'a pas de colony dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une colonie !");
							endRound = false;
							error = false;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = actualPlayer.canBuildColonyOn(board, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Vous ne pouvez pas poser de colonie ici !");
							else if(ans == 3)
									System.out.println("Il y a deja une colonie ou une ville aux alentours !");
							else {
								error = false;
								board.putColony(actualPlayer, coord[0], coord[1]);
							}
						}
						break;
					case 'v':
						if(!actualPlayer.canAffordTown()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une ville !");
							endRound = false;
							error = false;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = actualPlayer.canBuildTownOn(board, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Cette case n'est pas une colonie !");
							else if(ans == 3)
									System.out.println("Cette colonie n'est pas a vous !");
							else {
								error = false;
								board.putTown(actualPlayer, coord[0], coord[1]);
							}
						}
						break;
					case 'r':
						if(!actualPlayer.canAffordRoad()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une route !");
							endRound = false;
							error = false;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = actualPlayer.canBuildRoadOn(board, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Vous ne pouvez pas poser de route ici !");
							else {
								error = false;
								board.putRoad(actualPlayer, coord[0], coord[1]);
							}
						}
						break;
					case 'd':
						if(!actualPlayer.canAffordDevCard()) {
							System.out.println("Vous n'avez pas les ressources pour acheter une carte de developpement !");
						}
						else {
							int ans = actualPlayer.canBuyDevCard(this);
							if(ans == 2) 
								System.out.println("Il n'y a plus de carte developpement dans le paquet !");
							else
								actualPlayer.getDevCard(this);
						}
						error = false;
						endRound = false; // Le tour continu meme quand on achete une carte de dev ?
						break;
					case 'e':
						error = false;
						break;
				}
				
			} while(error);
			
		} while(!endRound);
		
		nextRound();
	}
	
	public void setPlayers(List<Player> players) { // Peut sûrement être supprimée
		this.players = players;
		if(players != null && players.size() > 1)
			actualPlayer = players.get(0);
	}
	
	public int[] rollDices() {
		Random rd = new Random();
		return new int[] {rd.nextInt(6)+1, rd.nextInt(6)+1};
	}
	
	public int[] askCoord() {
		int[] coord = null;
		String coordStr;
		do {
			if(coord != null) // Si != null, forcement les coordonnees sont outOfBorders.
				System.out.println("Ces coordonnées ne sont pas sur le plateau !");
			System.out.print("Donnez les coordonnées (ex: A8) : ");
			coordStr = actualPlayer.askCoord(sc);
			coord = convertCoord(coordStr);
		} while(board.outOfBorders(coord[0], coord[1]));
		
		return coord;
	}
	
	public int[] convertCoord(String coord) // A modifier quand on rajoutera les ports.
	{
		if(coord == null || coord.length() < 2 || coord.length() > 3)
			return null;
		int[] tab = new int[2];
		try {
			tab[0] = coord.charAt(0)-'A';
			tab[1] = Integer.parseInt(coord.substring(1))-1;
		} catch(Exception e) {
			return null;
		}
		return tab;
	}

	private void setupPlayers(int p) {
		players = new ArrayList<Player>();
		for (int i = 0; i < p; i++) {
			switch (i) {
				case 0:	players.add(new Player(Color.ORANGE));
						break;
				case 1:	players.add(new Player(Color.BLUE));
						break;
				case 2:	players.add(new Player(Color.YELLOW));
						break;
				case 3:	players.add(new Player(Color.RED));
						break;
			}
		}
		actualPlayer = players.get(0);
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
		setupPlayers(p);
		sc.nextLine();

		//ajout des ressources pour test
		/*
		actualPlayer.gainResource(Resource.CLAY);
		actualPlayer.gainResource(Resource.WHEAT);
		actualPlayer.gainResource(Resource.WOOD);
		actualPlayer.gainResource(Resource.SHEEP);
		*/
		for(int i=0;i<5;i++) {
		actualPlayer.gainResource(Resource.WHEAT);
		actualPlayer.gainResource(Resource.STONE);
		actualPlayer.gainResource(Resource.SHEEP);
		}

		board.display();

		//while (true) {
			playRound();
			board.display();
		//}

		closeScan();

	}
	
	public void nextRound() { // On passe au joueur suivant dans la liste.
		if(actualPlayer == null || players == null || players.size() < 3)
			return;
		int index = players.indexOf(actualPlayer);
		if(index == -1)
			return;
		
		actualPlayer = players.get(index  == players.size()-1 ? 0 : index+1);
	}
	
	public DevelopmentCardsDeck getDevCardsDeck() {
		return developmentCardsDeck;
	}
	
	public DevelopmentCard getDevCard() {
		return developmentCardsDeck.getCard();
	}
	
	public void openScan() {
		sc = new Scanner(System.in);
	}
	
	public void closeScan() {
		sc.close();
	}
	
	public Player getActualPlayer() {
		return actualPlayer;
	}

}
