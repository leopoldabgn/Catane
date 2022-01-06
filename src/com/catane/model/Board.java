package com.catane.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Port;
import com.catane.model.cases.ResourceCase;
import com.catane.model.cases.ResourceCase.Desert;
import com.catane.model.cases.ResourceCase.Field;
import com.catane.model.cases.ResourceCase.Forest;
import com.catane.model.cases.ResourceCase.Hill;
import com.catane.model.cases.ResourceCase.Mountain;
import com.catane.model.cases.ResourceCase.Pre;
import com.catane.model.cases.Road;
import com.catane.model.cases.Sea;
import com.catane.model.cases.Town;

public class Board {
		
	private int size;
	private Case[][] cases;
	private int[] thief;
	private History history;
	
	public Board(History history, int size) {
		this.history = history;
		this.size = size * 2 + 3;
		cases = generateAndAddCases();
		cases = mixCases(cases);
	}	

	private Case[][] generateAndAddCases() {
		Case[][] cases = new Case[size][size];
		Port[] ports = getPorts(getRealSize()); // On recupere la vrai taille du plateau (4x4, 6x6)
		int index = 0;
		
		// On ajoute les ports.
		for(int j=4;j<size;j+=4)
			cases[j][0] = ports[index++];
		
		boolean port = true;
		for(int j=2;j<size-2;j+=2, port = !port)
			for(int i=0;i<size;i+=size-1, port = !port)
				if(port)
					cases[i][j] = ports[index++];
		
		for(int j=size-5;j>=0;j-=4)
			cases[j][size-1] = ports[index++];
		
		index = 0;

		// Ajout des cases ressource
		for (int i = 2; i < size-2; i += 2){
			for (int j = 2; j < size-2; j += 2) {
				cases[i][j] = createCase(index++);
				if(cases[i][j] instanceof Desert) {
					((ResourceCase)cases[i][j]).setThief(true);
					thief = new int[] {i, j}; // on retient les coordonnées du voleur.
				}
			}
		}
			
		// Ajout des routes et villes
		for (int i = 1; i < size-1; i++) {
			for (int j = 1; j < size-1; j++) {
				if (i % 2 == 1 && j % 2 == 1)
					cases[i][j] = createColony();
				if (i % 2 == 0 && j % 2 == 1)
					cases[i][j] = createRoad(false);
				if (i % 2 == 1 && j % 2 == 0)
					cases[i][j] = createRoad(true);
			}
		}
		
		// Ajout des cases Sea (toutes les cases null restantes)
		for(int j=0;j<size;j++)
			for(int i=0;i<size;i++)
				if(cases[j][i] == null)
					cases[j][i] = createSea();
		
		return cases;
	}
	
	private static Port[] getPorts(int boardSize) {
		if(boardSize == 4) { // Alors c'est un plateau 4x4
			return new Port[] {new Port(2, Resource.WOOL), new Port(3), new Port(3),
				new Port(2, Resource.WOOD), new Port(2, Resource.WHEAT),
				new Port(3), new Port(2, Resource.STONE), new Port(2, Resource.CLAY)};
		}
		else { // Pour 6x6
			return new Port[] {new Port(2, Resource.WOOL), new Port(3), new Port(3),
				   new Port(2, Resource.WOOD), new Port(2, Resource.WHEAT),
				   new Port(3), new Port(2, Resource.STONE), new Port(2, Resource.CLAY),
				   new Port(3), new Port(2, Resource.WOOL), new Port(3),
				   new Port(2, Resource.WOOD)};
		}
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
		case 16: return new Forest(6);
		case 17: return new Pre(8);
		case 18: return new Mountain(11);
		case 19: return new Field(12);
		case 20: return new Forest(2);
		case 21: return new Hill(4);
		case 22: return new Field(10);
		case 23: return new Mountain(9);
		case 24: return new Hill(8);
		case 25: return new Pre(4);
		case 26: return new Field(4);
		case 27: return new Mountain(2);
		case 28: return new Forest(12);
		case 29: return new Pre(11);
		case 30: return new Hill(5);
		case 31: return new Mountain(3);
		case 32: return new Field(6);
		case 33: return new Hill(9);
		case 34: return new Forest(8);
		case 35: return new Pre(5);
		}
		return null;
	}
	
	private Case createSea() {
		return new Sea();
	}
	
	private Case createRoad(boolean up) {
		return new Road(null, up);
	}
		
	private Case createColony() {
		return new Colony(null);
	}
	
	private Case[][] mixCases(Case[][] cases){
		Random r = new Random();
		int i1, i2, j1, j2;
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
		for(int j=0;j<size;j++)
			for(int i=0;i<size;i++)
				if(cases[j][i] == c)
					return new int[] {j, i};
		
		return null;
	}
	
	public void replaceCaseBy(Case c, Case newCase) {
		if(newCase == null)
			return;
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return;
		cases[coord[0]][coord[1]] = newCase;
	}
	
	public void replaceColonyByTown(Colony colony) {
		if(colony == null || colony.getPlayer() == null) // Il faut que le joueur associé à la colonie soit également non null.
			return;
		Town town = new Town(colony);
		replaceCaseBy(colony, town);
	}
	
	public boolean outOfBorders(int x, int y) {
		// On met x,y >= 1, car on saute la ligne et la colonne avec les ports.
		// On met x,y < size-1 car (meme raison).
		return !((x >= 1 && x < size-1) && (y >= 1 && y < size-1));
	}
	
	public Colony putColony(Player player, int x, int y, boolean early) {
		history.add(player+" a construit une colonie en ("+Game.convertCoord(x, y)+")");
		Colony c = (Colony)cases[x][y];
		c.setPlayer(player);
		player.buildColony(c, early);
		return c;
	}
	
	public Colony putColony(Player player, Colony colony, boolean early) { // colonie vide
		int[] coord = getIndexesOf(colony);
		return putColony(player, coord[0], coord[1], early);
	}

	public List<Case> findFreeCase(Case ca, Player player) {
		List<Case> list = new ArrayList<Case>();
		for (Case[] tab : cases)
			for (Case c : tab) {
				if (ca instanceof Colony && c instanceof Colony) {
					if (player == null) { // On cherche une colonie libre
						if (((Colony) c).isEmpty())
							list.add(c);
					}else { // On cherche une colonie pour poser une ville
						if (!((Colony) c).isEmpty() && ((Colony) c).getPlayer().equals(player))
							list.add(c);
					}
				}
				if (ca instanceof Road && c instanceof Road) { // On cherche une route libre
					if (((Road) c).isEmpty())
							list.add(c);
				}
				if (ca instanceof ResourceCase && c instanceof ResourceCase) { // On cherche une case ressource case vide
					if (!((ResourceCase)c).hasThief())
							list.add(c);
				}
			}
		return list;
	}
	
	// Retourne la ville. Important uniquement pour la GUI.
	public Town putTown(Player player, int x, int y) {
		history.add(player+" a construit une ville en ("+Game.convertCoord(x, y)+")");
		Colony c = (Colony)cases[x][y];
		Town t = new Town(c);
		cases[x][y] = t;
		player.removeColony(c); // Important ! On retire la colonie de la liste du joueur.
		player.buildTown(t);
		return t;
	}
	
	public Town putTown(Player player, Colony colony) { // colonie d'un joueur
		int[] coord = getIndexesOf(colony);
		return putTown(player, coord[0], coord[1]);
	}
	
	public Road putRoad(Player player, int x, int y, boolean early) {
		history.add(player+" a construit une route en ("+Game.convertCoord(x, y)+")");
		Road r = (Road)cases[x][y];
		r.setPlayer(player);
		player.buildRoad(r, early);
		return r;
	}
	
	public Road putRoad(Player player, Road road, boolean early) { // Route vide
		int[] coord = getIndexesOf(road);
		return putRoad(player, coord[0], coord[1], early);
	}
	
	public boolean checkColoniesAround(Case c) {
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return false;
		return checkColoniesAround(coord[0], coord[1]);
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
	
	public Case getCase(int x, int y) {
		return cases[x][y];
	}

	public void switchThief(int[] newThief) { // Change le voleur de case
		history.add("Le voleur a été déplacé de "+
				Game.convertCoord(thief[0], thief[1])+" à "+
				Game.convertCoord(newThief[0], newThief[1]));
		((ResourceCase) getCase(newThief[0], newThief[1])).setThief(true);
		((ResourceCase) getCase(thief[0], thief[1])).setThief(false);
		thief = newThief;
	}

	public int[] getThiefCoord() {
		return thief;
	}
	
	public List<Colony> getColonies(Case c) {
		 int[] coord = getIndexesOf(c);
		 if(coord == null)
			 return new ArrayList<Colony>();
		 return getColonies(coord[0], coord[1]);
	}
	
	public List<Colony> getColonies(int x, int y) {
		List<Colony> col = new ArrayList<Colony>();
		int[] coord = {x-1, y-1, x+1, y-1, x+1, y+1, x-1, y+1};
		for (int i = 0; i < coord.length; i += 2) {
			if(outOfBorders(coord[i], coord[i+1]))
				continue;
			Colony c = (Colony) getCase(coord[i], coord[i+1]);
			if (!c.isEmpty())
				col.add(c);
		}
		return col;
	}
	
	// On fait la liste de tous les ports du joueur
	public List<Port> getPorts(Player player) {
		List<Port> list = new ArrayList<Port>();
		if(player == null)
			return list;
		Port p;
		for(Colony c : player.getColonies()) {
			p = getPortAround(c);
			
			if(p != null && !list.contains(p))
				list.add(p);
		}
		return list;
	}
	
	private Port getPortAround(Colony colony) {
		if(!colony.isColony() && !colony.isTown()) // On verifie si c'est bien une instance de Colony
			return null;
		int[] coord = getIndexesOf(colony);
		
		int x = coord[0], y = coord[1];
		coord = new int[] {x-1, y-1, x+1, y-1, x+1, y+1, x-1, y+1}; // Les 4 diagonales
		for(int i=0;i<coord.length;i+=2) {
			Case c = cases[coord[i]][coord[i+1]];
			if(c instanceof Port)
				return (Port)c;
		}
		return null;
	}
	
	/////////////////////////////////////////////////////
	// Fonctions pour calculer la route la plus longue
	// On utilise le backtracking pour y arriver
	
	private boolean isValidRoad(Player player, boolean[][] visited, int x, int y) {
		return !outOfBorders(x, y) && !visited[x][y] &&
			    cases[x][y].isRoad() && ((Road)cases[x][y]).getPlayer() == player;
	}
	
	private boolean isValidColony(Player player, boolean[][] visited, int x, int y) {
		return !outOfBorders(x, y) && !visited[x][y] && (cases[x][y].isColony() || cases[x][y].isTown()) &&
				( ((Colony)cases[x][y]).getPlayer() == player || ((Colony)cases[x][y]).isEmpty() );
	}
	
	public int getLongestRoad(Player player) {
		if(player == null)
			return 0;
		boolean[][] visited = new boolean[size][size];
		int max = 0;
		for(Road r : player.getRoads()) {
			int[] coord = getIndexesOf(r);
			max = Math.max(max, getLongestRoadAux(player, visited, coord[0], coord[1]));
		}
		return max;
	}
	
	private int getLongestRoadAux(Player player, boolean[][] visited, int j, int i) {
		
		int max = 0;
		Road r = (Road)cases[j][i];
		boolean vertical = r.isVertical();
		int[] colonies, roads;
		
		visited[j][i] = true;
		
		if(vertical) {
			colonies = new int[] {0, 1};
			roads = new int[] {0, 2, -1, 1, 1, 1};
		}
		else {
			colonies = new int[] {1, 0};
			roads = new int[] {2, 0, 1, -1, 1, 1};
		}
		
		int m = 1, f = 1; // Varie entre 1 et -1
		
		for(int l=0;l<2;l++) {
			if(isValidColony(player, visited, j+m*colonies[0], i+f*colonies[1])) {
				visited[j+m*colonies[0]][i+f*colonies[1]] = true;
				for(int k=0;k<roads.length;k+=2)
					if(isValidRoad(player, visited, j+m*roads[k], i+f*roads[k+1]))
						max = Math.max(max, getLongestRoadAux(player, visited, j+m*roads[k], i+f*roads[k+1]));
				visited[j+m*colonies[0]][i+f*colonies[1]] = false;
			}
			if(vertical)
				f *= -1;
			else
				m *= -1;
		}
		
		visited[j][i] = false;
		
		return max+1;
		
		// La boucle for au dessus procède de la façon suivante :
		/*
		if(vertical) {
			if(isValidColony(player, visited, j, i+1)) {
				visited[j][i+1] = true;
				if(isValidRoad(player, visited, j, i+2)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j, i+2));
				}
				if(isValidRoad(player, visited, j-1, i+1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j-1, i+1));
				}
				if(isValidRoad(player, visited, j+1, i+1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j+1, i+1));
				}
				visited[j][i+1] = false;
			}
			if(isValidColony(player, visited, j, i-1)) {
				visited[j][i-1] = true;
				if(isValidRoad(player, visited, j, i-2)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j, i-2));
				}
				if(isValidRoad(player, visited, j-1, i-1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j-1, i-1));
				}
				if(isValidRoad(player, visited, j+1, i-1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j+1, i-1));
				}
				visited[j][i-1] = false;
			}
		}
		else {
			if(isValidColony(player, visited, j+1, i)) {
				visited[j+1][i] = true;
				if(isValidRoad(player, visited, j+2, i)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j+2, i));
				}
				if(isValidRoad(player, visited, j+1, i-1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j+1, i-1));
				}
				if(isValidRoad(player, visited, j+1, i+1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j+1, i+1));
				}
				visited[j+1][i] = false;
			}
			if(isValidColony(player, visited, j-1, i)) {
				visited[j-1][i] = true;
				if(isValidRoad(player, visited, j-2, i)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j-2, i));
				}
				if(isValidRoad(player, visited, j-1, i-1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j-1, i-1));
				}
				if(isValidRoad(player, visited, j-1, i+1)) {
					max = Math.max(max, getLongestRoadAux(player, visited, j-1, i+1));
				}
				visited[j-1][i] = false;
			}
		}
		*/
		
		/*
		 	int max = 0;
			boolean vertical = false;
			int[] colonies, roads;
			if(vertical) {
				colonies = new int[] {0, 1};
				roads = new int[] {0, 2, -1, 1, 1, 1};
			}
			else {
				colonies = new int[] {1, 0};
				roads = new int[] {2, 0, 1, -1, 1, 1};
			}
			
			int m = 1, f = 1; // Varie entre 1 et -1
			
			for(int l=0;l<2;l++) {
				System.out.println(m*colonies[0]+" "+f*colonies[1]);
				for(int k=0;k<roads.length;k+=2) {
					System.out.println(m*roads[k]+" "+f*roads[k+1]);
				}
				if(vertical)
					f *= -1;
				else
					m *= -1;
			}
		 */
		
	}
	
	/////////////////////////////////////////////////////
	
	
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
	
	/////////////////////////////::
	
	public int getSize() {
		return size;
	}
	
	public int getRealSize() {
		return (size-3)/2;
	}

	public void gainResource(int n) {
		for (Case[] i : cases)
			for (Case c : i) {
				if (c instanceof ResourceCase && ((ResourceCase) c).getNumber() == n)
					((ResourceCase) c).giveResources(getColonies(c));
		}
	}
	
}
