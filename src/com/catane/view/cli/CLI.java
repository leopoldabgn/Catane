package com.catane.view.cli;

import java.util.List;
import java.util.Scanner;

import com.catane.model.Board;
import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Port;
import com.catane.model.cases.ResourceCase;
import com.catane.view.gui.BoardView;

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
			game.getActualPlayer().gainResource(Resource.STONE);
			game.getActualPlayer().gainResource(Resource.CLAY);
			game.getActualPlayer().gainResource(Resource.WOOD);
		}
		
		BoardView.display(board);
		boolean endGame = game.endGame();
		
		while(!endGame) {
			playRound();
			BoardView.display(board);
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
			c = askAction();
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
								player.payColony();
								player.buildColony(board.putColony(player, coord[0], coord[1]));
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
								player.payTown();
								player.buildTown(board.putTown(player, coord[0], coord[1]));
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
								player.payRoad();
								player.buildRoad(board.putRoad(player, coord[0], coord[1]));
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
						error = !portAction();
						endRound = false;
						break;
					case 't':
						error = false;
						endRound = true;
						break;
				}
				
			} while(error && !player.hasWon());
			
		} while(!endRound && !player.hasWon());
		
	}
	
	public int askNumber() {
		int nb;
		String str = sc.nextLine();
		try {
			nb = Integer.parseInt(str);
		} catch(Exception e) {
			System.out.println("Merci d'écrire un nombre valide !");
			return askNumber();
		}
		return nb;
	}
	
	public int[] askCoord() {
		int[] coord = null;
		String coordStr;
		do {
			if(coord != null) // Si != null, forcement les coordonnees sont outOfBorders.
				System.out.println("Ces coordonnées ne sont pas sur le plateau !");
			System.out.print("Donnez les coordonnées (ex: A8) : ");
			coordStr = checkCoord();
			coord = game.convertCoord(coordStr);
		} while(board.outOfBorders(coord[0], coord[1]));
		
		return coord;
	}

	public String checkCoord() {
		String s = sc.nextLine();
		while (!coord(s)) {
			System.out.println("Coordonnées incorrectes\nDonnez les coordonnées (ex: A8) : ");
			s = sc.nextLine();
		}
		return s.substring(0, (s.length() == 2 ? 2 : 3)).toUpperCase(); // Le string a forcement une taille >= 2
	}

	private boolean coord(String s) {
		if(s == null || s.length() < 2)
			return false;
		if (!Character.isLetter(s.charAt(0)) || !Character.isDigit(s.charAt(1)))
			return false;
		if (s.length() > 2 && !Character.isDigit(s.charAt(2)))
			return false;
		return true;
	}
	
	public Player askPlayer() {
		String s = sc.nextLine();
		for (Player p : game.getPlayers())
			if (s.equals(p.getName()))
				return p;
		return null;
	}
	
	public char askAction() {
		System.out.println("Choisissez une action à effectuer :");
		System.out.println("- Construire une colonie -> tapez 'c'");
		System.out.println("- Construire une ville -> tapez 'v'");
		System.out.println("- Construire une route -> tapez 'r'");
		System.out.println("- Acheter une carte de developpement -> tapez 'd'");
		System.out.println("- Echanger des ressources -> tapez 'e'");
		System.out.println("- Passer au tour suivant -> tapez 't'");
		char c = sc.nextLine().charAt(0);
		while (!charAction(c)) {
			System.out.println("Caractère non reconnu\nRetapez un caractère (c, v, r, d, e ou t)");
			c = sc.nextLine().charAt(0);
		}
		return c;
	}

	private boolean charAction(char c) {
		if (c != 'c' && c != 'v' && c != 'r' && c != 'd' && c != 'e' && c != 't')
			return false;
		return true;
	}
	
	public void thiefAction() {
		// plus des 7 cartes ressources : se défausser de la moitié inf (au choix)
		for (Player p : game.getPlayers())
			if (p.getResources() > 7) {
				int nb = p.getResources()/2;
				System.out.println(p.getName() + "doit se défausser de " + nb + " ressources");
				for (Resource r : p.getResourceList())
					System.out.println(r);
				while (nb != 0) {
					System.out.println("De quelle ressource voulez-vous vous défausser ?");
					System.out.println("(argile / bois / pierre / blé / laine)");
					switch (sc.nextLine()) {
						case "argile":	if (p.getResourceList().contains(Resource.CLAY)) {
											p.pay(Resource.CLAY);
											nb--;
										}else {
											System.out.println("Ressource insuffisante");
										}
										break;
						case "bois":	if (p.getResourceList().contains(Resource.WOOD)) {
											p.pay(Resource.WOOD);
											nb--;
										}else {
											System.out.println("Ressource insuffisante");
										}
						break;
						case "pierre":	if (p.getResourceList().contains(Resource.STONE)) {
											p.pay(Resource.STONE);
											nb--;
										}else {
											System.out.println("Ressource insuffisante");
										}
						break;
						case "blé":		if (p.getResourceList().contains(Resource.WHEAT)) {
											p.pay(Resource.WHEAT);
											nb--;
										}else {
											System.out.println("Ressource insuffisante");
										}
						break;
						case "laine":	if (p.getResourceList().contains(Resource.WOOL)) {
											p.pay(Resource.WOOL);
											nb--;
										}else {
											System.out.println("Ressource insuffisante");
										}
						break;
						default:		System.out.println("Ressource non reconnue");
										break;
					}
				}
			}

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
		List<Colony> col = board.getColonies(coord[0], coord[1]);
		if (col.isEmpty())
			return;
		System.out.println("Vous allez voler une carte ressource");
		Player p = null;
		do {
			System.out.println("Choisissez un des joueurs suivants :");
			for (Colony c : col) {				
				System.out.println(c.getPlayer());
			}
			p = askPlayer();
		} while (!p.isIn(col));
		game.getActualPlayer().stealResource(p);
	}
	
	public Resource askResource() {
		String rep;
		System.out.print("(");
		Resource[] resources = Resource.values();
		for(int i=0;i<resources.length;i++) {
			System.out.print(resources[i].getFrenchName().toLowerCase());
			if(i != resources.length-1)
				System.out.print(" / ");
		}
		System.out.println(") :");
		while(true) {
			rep = sc.nextLine();
			for(Resource r : Resource.values()) {
				if(rep.equals(r.toString().toLowerCase())) {
					return r;
				}
			}
			System.out.println("Ressource non reconnue");
		}
	}
	
	public boolean makeTrade(Port port) {
		if(port == null)
			return false;
		if(port.getResourcesToGive() < 4)
			System.out.println("Vous avez choisi le port suivant : ("+port+")");
		else
			System.out.println("Vous allez faire l'echange suivant : ("+port+")");
		if(port.getResourceType() == null)
			System.out.println("Vous devez préciser une resource que vous avez en "+
						   		port.getResourcesToGive()+" exemplaires.");
		else
			System.out.println("Vous devez donner "+port.getResourcesToGive()+" "
						       +port.getResourceType());
		System.out.println("Vous obtiendrez la ressource de votre choix en échange.");
		Resource resStock, resGain;
		if(port.getResourceType() == null) {
			if(game.getActualPlayer().getResourceByNb(port.getResourcesToGive()) == null) {
				System.out.println("Vous n'avez aucune ressource en "+
							       port.getResourcesToGive()+" exemplaires.");
				return false;
			}
			resStock = askResource();
		}
		else
			resStock = port.getResourceType();
		int nbRes = game.getActualPlayer().getResource(resStock);
		int nbResToGive = port.getResourcesToGive();
		if(nbRes >= nbResToGive) {
			if(port.getResourceType() == null)
				System.out.println("Vous voulez donner "+nbResToGive+" "+resStock+".");
			System.out.println("Quelle ressource voulez-vous en échange ?");
			resGain = askResource();
			game.getActualPlayer().echange(resStock, nbResToGive, resGain);
			System.out.println("Vous avez échangé "+nbResToGive+" "+resStock+" contre 1 "+resGain);
			return true;
		}
		else {
			System.out.println("Vous n'avez pas assez de "+resStock+".");
			return false;
		}
	}
	
	public boolean portAction() {
		List<Port> ports = board.getPorts(game.getActualPlayer());
		System.out.println("0 : annuler");
		System.out.println("1 : Faire un echange (4:1)");
		if(ports.isEmpty()) {
			System.out.println("Il n'y a pas de port à proximité.");
		}
		else {
			System.out.println("Voici la liste des ports disponibles :");
			for(int i=0;i<ports.size();i++)
				System.out.println((i+2)+" : ("+ports.get(i)+")");
		}
		int nb = askNumber();
		while(nb < 0 || nb > ports.size()+1) {
			System.out.println("Le nombre doit être entre 0 et "+(ports.size()+1)+" !");
			nb = askNumber();
		}
		if(nb == 0)
			return true;
		if(nb == 1)
			makeTrade(new Port(4));
		else
			makeTrade(ports.get(nb-2));
		
		System.out.println();
		
		return nb > 0;
	}
	
	public void openScan() {
		sc = new Scanner(System.in);
	}
	
	public void closeScan() {
		sc.close();
	}
}

