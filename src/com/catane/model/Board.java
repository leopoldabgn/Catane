package com.catane.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Desert;
import com.catane.model.cases.Field;
import com.catane.model.cases.Forest;
import com.catane.model.cases.Hill;
import com.catane.model.cases.Mountain;
import com.catane.model.cases.Pre;
import com.catane.model.cases.ResourceCase;
import com.catane.model.cases.Road;
import com.catane.model.cases.Town;

public class Board {
		
	private int size;
	private Case[][] cases;
	private int[] thief;
	private List<Player> players;
	private Player actualPlayer;
	private Scanner sc;
	
	public Board(int size) {
		this.size = size * 2 + 1;
		cases = generateAndAddCases();
		cases = mixCases(cases);
		this.players = new ArrayList<Player>();
	}	
	
	public void display() {
		int space = 4, maxSize = 13;
		char letter = 'A';
		System.out.print(" ".repeat(space));
		for(int i=0;i<size;i++)
			System.out.print((letter++)+" ".repeat(9));
		System.out.println();
		int line = 1;
		if(size%2 == 0)
			line += (size/2)*5;
		else
			line += (size/2+1)*5;
		line += (size/2)*(maxSize+1);
		System.out.println("  "+"-".repeat(line));
		for(int j=0;j<size;j++) {
			
			System.out.print((j+1));
			if(j+1 < 10)
				System.out.print(" ");
			System.out.print("|");
			
			for(int i=0;i<size;i++) {
				Case c = cases[j][i];
		 		if(c instanceof ResourceCase) {
		 			System.out.print(c);
		 		}
		 		else {
		 			space = maxSize-2;
		 			if(i%2 == 0)
		 				System.out.print(" "+c+" ");
		 			else {
		 				System.out.print(" ".repeat(space/2+1)+c+" ".repeat(space/2));
		 			}
		 		}
		 		System.out.print("|");
			 }
			
			System.out.println(" "+(j+1));
			System.out.print("  "+"-".repeat(line));
			System.out.println();
		 }
		
	}

	private Case[][] generateAndAddCases() {
		Case[][] cases = new Case[size][size];
		int x = 0;
			
		// Ajout des cases de ressources
		for (int i = 1; i < size; i += 2){
			for (int j = 1; j < size; j += 2) {
				cases[i][j] = createCase(x);
				if(cases[i][j] instanceof Desert) {
					((ResourceCase)cases[i][j]).setThief(true);
					thief = new int[] {i, j}; // on retient les coordonnées du voleur.
				}
				x++;
			}
		}
			
		// Ajout des routes et villes
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i % 2 == 0 && j % 2 == 0)
					cases[i][j] = createColony();
				if (i % 2 == 0 && j % 2 == 1)
					cases[i][j] = createRoad(false);
				if (i % 2 == 1 && j % 2 == 0)
					cases[i][j] = createRoad(true);
			}
		}
		return cases;
	}
		
	private Case createCase(int x) {
		switch (x) {
		case 0: return new Forest(6);
		case 1: return new Pre(10);
		case 2: return new Field(11);
		case 3: return new Pre(8);
		case 4: return new Field(4);
		case 5: return new Hill(9);
		case 6: return new Forest(5);
		case 7: return new Mountain(12);
		case 8: return new Mountain(3);
		case 9: return new Desert();
		case 10: return new Field(10);
		case 11: return new Hill(6);
		case 12: return new Hill(9);
		case 13: return new Mountain(8);
		case 14: return new Pre(5);
		case 15: return new Forest(2);
		case 16: return new Forest(5);
		case 17: return new Pre(5);
		case 18: return new Mountain(5);
		case 19: return new Field(6);
		case 20: return new Forest(5);
		case 21: return new Hill(5);
		case 22: return new Field(5);
		case 23: return new Mountain(5);
		case 24: return new Hill(5);
		case 25: return new Pre(5);
		case 26: return new Field(5);
		case 27: return new Mountain(5);
		case 28: return new Forest(5);
		case 29: return new Pre(5);
		case 30: return new Hill(5);
		case 31: return new Mountain(5);
		case 32: return new Field(5);
		case 33: return new Hill(5);
		case 34: return new Forest(5);
		case 35: return new Pre(5);
		}
		return null;
	}
	
	private Case createRoad(boolean up) {
		return new Road(null, up);
	}
		
	private Case createColony() {
		return new Colony(null);
	}
	
	private Case[][] mixCases(Case[][] cases){
		Random r = new Random();
		int i1 = 0;
		int i2 = 0;
		int j1 = 0;
		int j2 = 0;
		Case tmp = null;
		for (int i = 0; i < size * size; i++) {
			i1 = r.nextInt(size - 1);
			if (i1 % 2 == 0) i1++;
			i2 = r.nextInt(size - 1);
			if (i2 % 2 == 0) i2++;
			j1 = r.nextInt(size - 1);
			if (j1 % 2 == 0) j1++;
			j2 = r.nextInt(size - 1);
			if (j2 % 2 == 0) j2++;
			tmp = cases[i1][j1];
			cases[i1][j1] = cases[i2][j2];
			cases[i2][j2] = tmp;
		}
		
		return cases;
	}
	
	public int[] getIndexesOf(Case c) {
		if(c == null)
			return null;
		
		for(int j=0;j<cases.length;j++)
			for(int i=0;i<cases[0].length;i++)
				if(cases[j][i] == c)
					return new int[] {j, i};
		
		return null;
	}
	
	public void replaceCaseBy(Case c, Case newCase) { // newCase peut etre null, c'est autorisé.
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return;
		cases[coord[0]][coord[1]] = newCase;
		//newCase.mouseEntered(null); // Sinon le contour s'enleve meme si on a la souris dessus.
	}
	
	public void replaceColonyByTown(Colony colony) {
		if(colony == null || colony.getPlayer() == null) // Il faut que le joueur associé à la colonie soit également non null.
			return;
		Town town = new Town(colony);
		replaceCaseBy(colony, town);
	}
	
	public int[] rollDices() {
		Random rd = new Random();
		return new int[] {rd.nextInt(6)+1, rd.nextInt(6)+1};
	}
	
	public boolean outOfBorders(int x, int y) {
		return !((x >= 0 && x < size) && (y >= 0 && y < size));
	}
	
	public void putColony(Player player, int x, int y) {
		Colony c = (Colony)cases[x][y];
		c.setPlayer(player);
	}
	
	public void putTown(Player player, int x, int y) {
		
	}
	
	public void putRoad(Player player, int x, int y) {
		
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
		if(players != null && players.size() > 1)
			actualPlayer = players.get(0);
	}
	
	public boolean checkColoniesAround(int x, int y) { // Colonie ou Ville autour de ces coordonnees.
		int[] coord = {x-2, y, x, y-2, x+2, y, x, y+2}; // gauche, haut, droite, bas.
		
		for(int i=0;i<coord.length;i+=2) {
			if(outOfBorders(coord[i], coord[i+1]))
				continue;
			Case c = cases[coord[i]][coord[i+1]];
			if(c instanceof Colony) {
				if(!((Colony)c).isEmpty())
					return true;
			}
		}
		
		return false;
	}
	
	public int[] askCoord() {
		int[] coord = null;
		String coordStr;
		do {
			if(coord != null) // Si != null, forcement les coordonnees sont outOfBorders.
				System.out.println("Ces coordonnées ne sont pas sur le plateau !");
			coordStr = actualPlayer.askCoord(sc);
			coord = convertCoord(coordStr);
		} while(outOfBorders(coord[0], coord[1]));
		
		return coord;
	}
	
	public void playRound() {
		
		System.out.println("Au tour de "+actualPlayer.getName());
		boolean endRound;
		char c;
		int[] coord;
		boolean error;
		
		do {
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
							break;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = actualPlayer.canBuildColonyOn(this, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Vous ne pouvez pas poser de colonie ici !");
							else if(ans == 3)
									System.out.println("Il y a deja une colonie ou une ville aux alentours !");
							else {
								error = false;
								putColony(actualPlayer, coord[0], coord[1]);
							}
						}
						break;
					case 'v':
						if(!actualPlayer.canAffordTown()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une ville !");
							endRound = false;
							break;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = actualPlayer.canBuildTownOn(this, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Cette case n'est pas une colonie !");
							else if(ans == 3)
									System.out.println("Cette colonie n'est pas a vous !");
							else {
								error = false;
								putTown(actualPlayer, coord[0], coord[1]);
							}
						}
						break;
					case 'r':
						if(!actualPlayer.canAffordRoad()) {// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
							System.out.println("Vous n'avez pas les ressources pour construire une route !");
							endRound = false;
							break;
						}
						else {
							coord = askCoord(); // Coordonnees forcement dans le plateau.
							int ans = actualPlayer.canBuildRoadOn(this, coord); // ans ne peut pas etre egale a 1 ici.
							if(ans == 2)
									System.out.println("Vous ne pouvez pas poser de route ici !");
							else {
								error = false;
								putRoad(actualPlayer, coord[0], coord[1]);
							}
						}
						break;
					case 'e':
						break;
				}
				
			} while(error);
			
		} while(!endRound);
		
		nextRound();
	}
	
	public int[] convertCoord(String coord) // A modifier quand on rajoutera les ports.
	{
		if(coord == null || coord.length() != 2)
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
	
	public void nextRound() { // On passe au joueur suivant dans la liste.
		if(actualPlayer == null || players == null || players.size() < 3)
			return;
		int index = players.indexOf(actualPlayer);
		if(index == -1)
			return;
		
		actualPlayer = players.get(index  == players.size()-1 ? 0 : index+1);
	}
	
	public Case getCase(int x, int y) {
		if(outOfBorders(x, y))
			return null;
		return cases[x][y];
	}
	
	// Les coordonnees sont forcement sur le plateau lorsqu'on
	// appelle les fonctions suivantes :
	
	// Une colonie est une case de type colonie et non vide.
	public boolean isColony(int x, int y) {
		return cases[x][y].getClass() == Colony.class && !((Colony)cases[x][y]).isEmpty();
	}
	
	public boolean isEmptyColony(int x, int y) {
		return cases[x][y].isEmptyColony();
	}
	
	public boolean isEmptyRoad(int x, int y) {
		return cases[x][y].isEmptyRoad();
	}
	
	///////////////////////////////////////////
	
	public void openScan() {
		this.sc = new Scanner(System.in);
	}
	
	public void closeScan() {
		this.sc.close();
	}
	
	public Player getActualPlayer() {
		return actualPlayer;
	}
	
	public int getSize() {
		return size;
	}
	
}

