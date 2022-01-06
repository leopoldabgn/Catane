package com.catane.view.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.catane.model.AI;
import com.catane.model.Board;
import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;
import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Port;
import com.catane.model.cases.ResourceCase;

public class CLI {

	private Game game;
	private Board board;
	private Scanner sc;
	
	public CLI() {
		this.game = new Game();
		startGame();
	}
	
	public void startGame() {

		openScan();

		System.out.println("Bonjour,");
		System.out.println("Vous vous apprêtez à lancer une partie des Colons de Catane.\n");
		int size;
		do {
			System.out.println("Combien de case doit comporter le plateau ? (4x4 -> 4, 6x6 -> 6)");
			size = askNumber();
		}while (size != 4 && size != 6);
		game.setBoard(size);
		board = game.getBoard();
		System.out.println();
		int p;
		int ia;
		do {
			System.out.println("Voulez vous jouer à 3 ou 4 joueurs ? (3/4)");
			p = askNumber();
		} while (p != 3 && p != 4);
		do {
			System.out.println("Combien voulez-vous d'ordinateur ? (0/1/2/3/4)");
			ia = askNumber();
		} while (ia != 0 && ia != 1 && ia != 2 && ia != 3 && ia != 4);
		game.setupPlayers(p, Math.min(ia, p));
		System.out.println();

		for (Player player : game.getPlayers()) {
			if (!(player instanceof AI))
				do {
					System.out.println("Choisissez un nom pour " + player.getName());
					String s = sc.nextLine();
					if (!s.isBlank()) {
						player.setName(s);
						break;
					}
				}while (true);
			else
				((AI) player).setCLI(this);
		}

		//ajout des ressources pour test
		/*
		actualPlayer.gainResource(Resource.CLAY);
		actualPlayer.gainResource(Resource.WHEAT);
		actualPlayer.gainResource(Resource.WOOD);
		actualPlayer.gainResource(Resource.SHEEP);
		

		for(int i=0;i<2;i++) {
			game.getActualPlayer().gainResource(Resource.WHEAT);
			game.getActualPlayer().gainResource(Resource.STONE);
			game.getActualPlayer().gainResource(Resource.WOOL);
			game.getActualPlayer().gainResource(Resource.STONE);
			game.getActualPlayer().gainResource(Resource.CLAY);
			game.getActualPlayer().gainResource(Resource.WOOD);
		}
		*/
		
		displayBoard(board);
		boolean endGame = game.endGame();

		// Mise en place du jeu
		for (Player player : game.getPlayers()) {
			if (player.isAI()) {

				try {
					Thread.sleep(500);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(player + " pose ses premières constructions\n" + ((AI) player).earlyGame(game));

			}else {

				System.out.println(player + " doit choisir où poser ses premières constructions");
				int[] coord = new int[2];
				for (int i = 0; i < 2; i++) {
					int n = i + 1;
					System.out.println("Où voulez-vous poser la colonie " + n);
					int ans = 2;
					while (ans != 0) {
						coord = askCoord(); // Coordonnees forcement dans le plateau.
						ans = player.canBuildColonyOn(board, coord, true);
						if(ans != 0)
							System.out.println("Vous ne pouvez pas poser de route ici !");
					}
					board.putColony(player, coord[0], coord[1], true);
				}
				for (int i = 0; i < 2; i++) {
					int n = i + 1;
					System.out.println("Où voulez-vous poser la route " + n);
					int ans = 2;	
					while (ans == 2) {
						coord = askCoord(); // Coordonnees forcement dans le plateau.
						ans = player.canBuildRoadOn(board, coord, true);
						if(ans == 2)
							System.out.println("Vous ne pouvez pas poser de route ici !");
					}
					board.putRoad(player, coord[0], coord[1], true);
				}
			}

			System.out.println();

		}
		
		while(!endGame) {
			playRound();
			displayBoard(board);
			endGame = game.endGame();
			if(!endGame)
				game.nextRound();
		}
		
		System.out.println(game.getActualPlayer()+" a gagné !");
		
		closeScan();

	}
	
	public void playRound() {
		Player player = game.getActualPlayer();

		if (player.isAI()) {

			try {
				Thread.sleep(500);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Au tour de " + player + "\n" + ((AI) player).midGame(game));

		}else {
		
			System.out.println("Au tour de "+player+"\n");
			boolean endRound;
			char c;
			int[] coord;
			boolean error;
			
			dices();
			
			do {
				PlayerView.printScore(player);
				PlayerView.printResources(player);
				PlayerView.printDevelopmentCards(player);
				System.out.println();
				if (game.longestRoadOwner() != null)
					System.out.println("Route la plus longue : " + game.longestRoadOwner());
				if (game.mostPowerfulArmyOwner() != null)
					System.out.println("Armée la plus puissante : " + game.mostPowerfulArmyOwner());
				System.out.println();
				endRound = false;

				// print mostPowerfulArmy et longestRoad ?

				// A enlever
				// System.out.println("éteindre ? (o/n)");
				// if (sc.nextLine().charAt(0) == 'o')
				// 	System.exit(0);
				//thiefAction();
				//

				c = askAction(player.hasDrawDev());
				System.out.println();
				coord = null;
				error = true;
				
				do {
					switch(c) {
						case 'c': // Construire une colonie
							if(!player.canAffordColony()) {// Si il n'a pas assez d'argent. Ou il n'a pas de colony dans son inventaire.
								System.out.println("Vous n'avez pas les ressources pour construire une colonie !");
								error = false;
							}
							else {
								coord = askCoord(); // Coordonnees forcement dans le plateau.
								int ans = player.canBuildColonyOn(board, coord, false); // ans ne peut pas etre egale a 1 ici.
								if(ans == 2)
										System.out.println("Vous ne pouvez pas poser de colonie ici !");
								else if(ans == 3)
										System.out.println("Il y a deja une colonie ou une ville aux alentours !");
								else {
									error = false;
									board.putColony(player, coord[0], coord[1], false);
								}
							}
							break;
						case 'v': // Construire une ville
							if(!player.canAffordTown()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
								System.out.println("Vous n'avez pas les ressources pour construire une ville !");
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
						case 'r': // Construire une route
							if(!player.canAffordRoad()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
								System.out.println("Vous n'avez pas les ressources pour construire une route !");
								error = false;
							}
							else {
								coord = askCoord(); // Coordonnees forcement dans le plateau.
								int ans = player.canBuildRoadOn(board, coord, false); // ans ne peut pas etre egale a 1 ici.
								if(ans == 2)
										System.out.println("Vous ne pouvez pas poser de route ici !");
								else {
									error = false;
									board.putRoad(player, coord[0], coord[1], false);
									game.refreshLongestRoadOwner();
								}
							}
							break;
						case 'd': // Acheter une carte de développement
							if(!player.canAffordDevCard()) {
								System.out.println("Vous n'avez pas les ressources pour acheter une carte de développement !");
							}
							else {
								int ans = player.canBuyDevCard(game);
								if(ans == 2) 
									System.out.println("Il n'y a plus de carte développement dans le paquet !");
								else {
									player.getDevCard(game);
								}
							}
							error = false;
							break;
						case 'u': // Utiliser une carte de développement
							if (!player.canUseDev(new Knight()) && !player.canUseDev(Progress.ROAD_CONSTRUCTION) && !player.canUseDev(Progress.INVENTION) && !player.canUseDev(Progress.MONOPOLY))
								System.out.println("Vous n'avez pas de carte de développement utilisable !");
							else
								useDev(player);
							error = false;
							break;
						case 'e': // Echanger des ressources
							error = !portAction();
							break;
						case 't': // Passer au tour suivant
							error = false;
							endRound = true;
							player.refreshDevCards();
							break;
					}
					System.out.println();
					
				} while(error && !player.hasWon());
				
			} while(!endRound && !player.hasWon());

		}
		
	}

	public static void displayBoard(Board board) {
		int size = board.getSize(), maxSize = 15;
		int size2 = size-2; // size sans les ports.
		
		char letter = 'A';
		System.out.print(" ".repeat(18));
		for(int i=0;i<size2;i++) {
			String s = "" + letter;
			if (i % 2 == 0)
				System.out.print(addSpaces(s, 4));
			else
				System.out.print(addSpaces(s, 15));
			System.out.print(" ");
			letter++;
		}
		System.out.println();
		
		int line = 1;
		if(size2%2 == 0)
			line += (size2/2)*5;
		else
			line += (size2/2+1)*5;
		line += (size2/2)*(maxSize+1);

		boolean b = false;
		
		for(int j=0;j<size;j++) {

			if (j == 0 || j == size - 1) {
				System.out.print(" ");
				for (int i = 0; i < size; i++) {
					Case p = board.getCase(i, j);
					if (p instanceof Port) {
						System.out.print("|" + addSpaces(((Port) p).toString(), maxSize) + "|");
					}else {
						if (i % 2 == 0)
							System.out.print(" ".repeat(17));
						else
							System.out.print(" ".repeat(4));
					}
				}
				System.out.println();
			}else {
				System.out.print(j);
				if(j < 10)
					System.out.print(" ");
				Case p = board.getCase(0, j);
				if (p instanceof Port) {
					System.out.print(addSpaces(((Port) p).toString(), maxSize));
				}else {
					System.out.print(" ".repeat(maxSize));
				}
				System.out.print("|");
				
				for(int i=1;i<size-1;i++) {
					Case c = board.getCase(i, j);
					if(c instanceof ResourceCase) {
						System.out.print(addSpaces(c.toString(), maxSize));
					}
					else {
						if(i%2 == 1)
							System.out.print(" "+c+" ");
						else
							System.out.print(addSpaces(c.toString(), maxSize));
					}
					System.out.print("|");
				}
				
				p = board.getCase(size - 1, j);
				if (p instanceof Port)
					System.out.println(addSpaces(((Port) p).toString(), maxSize) + " " + j);
				else
					System.out.println(" ".repeat(16)+j);
			}
			if (j != size - 1) {
				if (j == size - 2 || j == 0)
					System.out.print("  " + " ".repeat(maxSize) +"-".repeat(line));
				else {
					if (b)
						System.out.print("  " + "-".repeat(maxSize) +"-".repeat(line));
					else
						System.out.print("  " + " ".repeat(maxSize) +"-".repeat(line) + "-".repeat(maxSize));
				}
				if (j % 2 == 0)
					b = !b;
			}
			System.out.println();
		}
		
	}
	
	// Si besoin, on rajoute des espaces avant et apres pour que
	// la taille de la string soit egale a newSize
	private static String addSpaces(String str, int newSize) {
		if(str.length() >= newSize)
			return str;
		int reste = newSize-str.length();
		int before = reste/2, after = reste/2;
		if(reste%2 == 1)
			before += 1;
		str = " ".repeat(before)+str+" ".repeat(after);
		return str;
	}
	
	public void useDev(Player player) {
		char c = ' ';
		boolean hasUsed = true;
		do {
			hasUsed = true;
			do {
				System.out.println("Choisissez une carte développement à utiliser (c -> Chevalier / m -> Monopole / r -> Construction de route / i -> Invention)");
				System.out.println("annuler -> a");
				String s = sc.nextLine();
				if (!s.isBlank())
					c = s.charAt(0);
				else
					c = ' ';
			}while (c != 'c' && c != 'm' && c != 'r' && c != 'i' && c != 'a');
			switch (c) {
				case 'a':
					break;
				case 'c': // Chevalier
					if (!player.canUseDev(new Knight())) {
						System.out.println("Vous n'avez pas de carte de développement 'Chevalier' utilisable");
						hasUsed = false;
					}
					game.refreshMostPowerfulArmyOwner();
					thiefAction();
					break;
				case 'm': // Monopole
					if (!player.canUseDev(Progress.MONOPOLY)) {
						System.out.println("Vous n'avez pas de carte de développement 'Monopole'");
						hasUsed = false;
					}
					// Demander quelle ressource
					System.out.println("De quelle ressource voulez-vous avoir le monopole ?");
					Resource r = askResource();
					// Prendre les ressources
					game.monopoly(r);
					break;
				case 'r': // Construction de route
					if (!player.canUseDev(Progress.ROAD_CONSTRUCTION)) {
						System.out.println("Vous n'avez pas de carte de développement 'Construction de route'");
						hasUsed = false;
					}
					int[] coord = new int[2];
					for (int i = 0; i < 2; i++) {
						int n = i + 1;
						System.out.println("Où voulez-vous poser la route " + n);
						int ans = 2;
						while (ans == 2) {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							ans = player.canBuildRoadOn(board, coord, true);
							if(ans == 2)
								System.out.println("Vous ne pouvez pas poser de route ici !");
						}
						board.putRoad(player, coord[0], coord[1], true);
					}
					game.refreshLongestRoadOwner();
					player.devCardUsed(Progress.ROAD_CONSTRUCTION);
					break;
				case 'i': // Invention
					if (!player.canUseDev(Progress.INVENTION)) {
						System.out.println("Vous n'avez pas de carte de développement 'Invention'");
						hasUsed = false;
					}
					System.out.println("Choisissez la première ressource");
					Resource r1 = askResource();
					System.out.println("Choisissez la deuxième ressource");
					Resource r2 = askResource();
					game.invention(r1, r2);
					break;
			}
		}while (!hasUsed);
	}

	public void dices() {
		// Achat facultatif d'une carte de développement avant le lancement des dés
		char c;
		do {
			System.out.println("Voulez-vous acheter une carte développement ? (o/n)");
			String s = sc.nextLine();
			if (!s.isBlank())
				c = s.charAt(0);
			else
				c = ' ';
		}
		while (c != 'o' && c != 'n');
		if (c == 'o') {
			if(!game.getActualPlayer().canAffordDevCard()) {
				System.out.println("Vous n'avez pas les ressources pour acheter une carte de developpement !");
			}
			else {
				int ans = game.getActualPlayer().canBuyDevCard(game);
				if(ans == 2) 
					System.out.println("Il n'y a plus de carte developpement dans le paquet !");
				else {
					game.getActualPlayer().getDevCard(game);
				}
			}
		}
		System.out.println();

		// Utiliser une carte de développement
		c = ' ';
		do {
			System.out.println("Voulez-vous utiliser une carte de développement ? (o/n)");
			String s = sc.nextLine();
			if (!s.isBlank())
				c = s.charAt(0);
			else
				c = ' ';
		}while (c != 'o' && c != 'n');
		if (c == 'o') {
			if (!game.getActualPlayer().canUseDev(new Knight()) && !game.getActualPlayer().canUseDev(Progress.ROAD_CONSTRUCTION) && !game.getActualPlayer().canUseDev(Progress.INVENTION) && !game.getActualPlayer().canUseDev(Progress.MONOPOLY))
				System.out.println("Vous n'avez pas de carte de développement utilisable !");
			else
				useDev(game.getActualPlayer());
		}


		// Lance les dés
		int[] d = game.rollDices();
		int gain = d[0] + d[1];
		System.out.println("\nLes dés ont été lancés\nLe résultat est : "+gain+"\n");
		if (gain == 7) {
			discard();
			thiefAction();
		}
		else
			board.gainResource(gain);
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
			coord = Game.convertCoord(coordStr);
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
	
	public char askAction(boolean devBought) {
		System.out.println("Choisissez une action à effectuer :");
		System.out.println("- Construire une colonie -> tapez 'c'");
		System.out.println("- Construire une ville -> tapez 'v'");
		System.out.println("- Construire une route -> tapez 'r'");
		if (!devBought)
			System.out.println("- Acheter une carte de développement -> tapez 'd'");
		System.out.println("- Utiliser une carte de développement -> tapez 'u'");
		System.out.println("- Echanger des ressources -> tapez 'e'");
		System.out.println("- Passer au tour suivant -> tapez 't'");
		String s = sc.nextLine();
		char c;
			if (!s.isBlank())
				c = s.charAt(0);
			else
				c = ' ';
		while (!charAction(c, devBought)) {
			System.out.println("Caractère non reconnu\nRetapez un caractère (c, v, r, d, u, e ou t)");
			s = sc.nextLine();
			if (!s.isBlank())
				c = s.charAt(0);
			else
				c = ' ';
		}
		return c;
	}

	private boolean charAction(char c, boolean devBought) {
		if (devBought) {
			if (c != 'c' && c != 'v' && c != 'r' && c != 'e' && c != 't' && c != 'u')
				return false;
		}else {
			if (c != 'c' && c != 'v' && c != 'r' && c != 'd' && c != 'e' && c != 't' && c != 'u')
				return false;
		}
		return true;
	}

	public void discard() {
		System.out.println("Le voleur est déclenché");
		// plus des 7 cartes ressources : se défausser de la moitié inf (au choix)
		for (Player p : game.getPlayers())
			if (p.getResources() > 7) {
				if (p instanceof AI) {
					((AI) p).discard();
				}else {
					int nb = p.getResources()/2;
					System.out.println(p.getName() + "doit se défausser de " + nb + " ressources");
					while (nb != 0) {
						PlayerView.printResources(p);
						System.out.println("De quelle ressource voulez-vous vous défausser ? (encore " + nb + ")");
						Resource res = askResource();
						if (p.getResourceList().contains(res)) {
							p.pay(res);
							nb--;
						}else {
							System.out.println("Ressource insuffisante");
						}
					}
				}
			}
	}
	
	public void thiefAction() {

		// déplacer le voleur sur une case différente
		System.out.println("Choisissez les nouvelles coordonnées du voleur");
		int[] coord = askCoord();
		while (!(board.getCase(coord[0], coord[1]) instanceof ResourceCase) || ((ResourceCase) board.getCase(coord[0], coord[1])).hasThief()) {
			// Vérifications : la case n'est pas une case ressource ou contient déjà le voleur
			if (!(board.getCase(coord[0], coord[1]) instanceof ResourceCase))
				System.out.println("Ce n'est pas une case ressource");
			else
				System.out.println("Le voleur est déjà sur cette case");
			coord = askCoord();
		}
		board.switchThief(coord);

		// actualPlayer vole une ressource au hasard à un joueur possédant une colonie autour de la nouvelle case
		List<Colony> col = board.getColonies(coord[0], coord[1]);
		List<Player> players = new ArrayList<Player>();
		for (Colony c : col)
			if (game.getActualPlayer() != c.getPlayer() && !players.contains(c.getPlayer()))
				players.add(c.getPlayer());
		if (players.isEmpty())
			return;
		System.out.println("Vous allez voler une carte ressource");
		Player p = null;
		do {
			System.out.println("Choisissez un des joueurs suivants :");
			for (Player p2 : players) {				
				System.out.println(p2.getName());
			}
			p = askPlayer();
		} while (!p.isIn(players));
		Resource r = game.getActualPlayer().stealResource(p);
		if (r == null)
			System.out.println(game.getActualPlayer() + "n'a pas pu voler de ressource à " + p);
		else
			System.out.println(game.getActualPlayer() + " vient de voler 1 " + r + " à " + p);
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
			if(game.getActualPlayer().getResourcesByNb(port.getResourcesToGive()).isEmpty()) {
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
			game.getActualPlayer().trade(resStock, nbResToGive, resGain);
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

