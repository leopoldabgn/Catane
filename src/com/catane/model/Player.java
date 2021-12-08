package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;
import com.catane.model.cards.VictoryPoints;
import com.catane.model.cases.*;

public class Player {
	public static int nextPlayerNb = 1; // Le premier joueur est J1, le deuxieme J2...
	private int number;
	private List<Resource> resources;
	private List<Colony> colonies; // Comprend egalement les villes...
	private List<Road> roads;
	private List<DevelopmentCard> developmentCards;

	private Color color;
	
	public Player(Color color) {
		this.number = nextPlayerNb++;
		this.resources = new ArrayList<Resource>();
		this.colonies = new ArrayList<Colony>();
		this.roads = new ArrayList<Road>();
		this.developmentCards = new ArrayList<DevelopmentCard>();
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	// Méthodes pour vérification des actions

	public int getResource(Resource r1) {

		/***************************************
		*  exemple pour avoir nombre de bois :
		*  getResource(Resource.WOOD)
		*  retourne le nombre de bois 
		***************************************/

		int x = 0;
		for (Resource r2 : resources)
			if (r2.equals(r1))
				x++;
		return x;
	}

	public int getNbDevCard(DevelopmentCard card) {
		int sum = 0;
		for(DevelopmentCard c : developmentCards)
			if(c.equals(card))
				sum++;
		return sum;
	}
	
	public void printResources() {
		System.out.print("Ressources : ");
		Resource[] resources = Resource.values();
		boolean comma = false;
		int count = 0;
		for(Resource r : resources) {
			System.out.print(r+" = "+getResource(r));
			if(++count < resources.length)
				System.out.print(", ");
		}
		
		System.out.println();
	}

	public void printDevelopmentCards() {
		System.out.print("Cartes de developpement : ");
		DevelopmentCard[] tab;
		boolean nothing = true, comma = false;
		int nb = getNbDevCard(new Knight());
		if(nb != 0) {
			System.out.print(nb+" "+(new Knight()));
			comma = true;
			nothing = false;
		}
		for(int i=0;i<2;i++) {
			if(i == 0)
				tab = VictoryPoints.values();
			else
				tab = Progress.values();
			for(DevelopmentCard card : tab) {
				nb = getNbDevCard(card);
				if(nb != 0) {
					if(nothing)
						nothing = false;
					if(comma)
						System.out.print(", ");
					System.out.print(nb+" "+card);
					if(!comma)
						comma = true;
				}
			}
		}
		
		if(nothing)
			System.out.print(" Aucune carte.");
	}
	
	public boolean canAffordColony(){ // Le joueur a les ressources nécessaires pour construire une colonie
		return (getResource(Resource.CLAY) >= 1 && getResource(Resource.WOOD) >= 1 &&
				getResource(Resource.SHEEP) >= 1 && getResource(Resource.WHEAT) >= 1);
	}

	public boolean canAffordTown(){ // Le joueur a les ressources nécessaires pour construire une ville
		return (getResource(Resource.STONE) >= 3 && getResource(Resource.WHEAT) >= 2);
	}

	public boolean canAffordRoad(){ // Le joueur a les ressources nécessaires pour construire une route
		return (getResource(Resource.CLAY) >= 1 && getResource(Resource.WOOD) >= 1);
	}
	
	public boolean canAffordDevCard() {
		return getResource(Resource.STONE) >= 1 && getResource(Resource.SHEEP) >= 1 &&
			   getResource(Resource.WHEAT) >= 1;
	}
	
	// Pour l'interface graphique on appelle celle-la directement.
	public int canBuildColonyOn(Board board, int[] coord) { // Le joueur peut construire une colonie sur la case donnée
		if(!canAffordColony())// Si il n'a pas assez d'argent. Ou il n'a pas de colonie dans son inventaire.
			return 1;
		else {
			if(!board.isEmptyColony(coord[0], coord[1]))
				return 2;
			else if(board.checkColoniesAround(coord[0], coord[1]))
				return 3;
		}
		
		return 0;
	}

	// Pour l'interface graphique on appelle celle-la directement.
	public int canBuildTownOn(Board board, int[] coord) { // Le joueur peut construire une ville sur la case donnée
		if(!canAffordTown())// Si il n'a pas assez d'argent. Ou il n'a pas de ville dans son inventaire.
			return 1;
		else {
			if(!board.isColony(coord[0], coord[1]))
				return 2;
			Colony c = (Colony)board.getCase(coord[0], coord[1]);
			if(c.getPlayer() != this) // Si ce n'est pas ma colonie.
				return 3;
		}
		
		return 0;
	}
	
	// Pour l'interface graphique on appelle celle-la directement.
	public int canBuildRoadOn(Board board, int[] coord) { // Le joueur peut construire une route sur la case donnée
		if(!canAffordRoad())// Si il n'a pas assez d'argent. Ou il n'a pas de route dans son inventaire.
			return 1;
		else {
			if(!board.isEmptyRoad(coord[0], coord[1]))
				return 2;
		}
		
		return 0;
	}
	
	public boolean canBuildTownOn(Colony colony){ // Le joueur peut construire une ville sur la case donnée
		return canAffordTown() && colony.getPlayer() == this;
	}

	public boolean canBuildRoadOn(Road road){ // Le joueur peut construire une route sur la case donnée
		return canAffordRoad() && road.isEmpty();
	}

	// Méthodes interactions avec plateau :
	
	public void gainResource(Resource r) {
		resources.add(r);
	}

	public char askAction(Scanner sc) {
		System.out.println("Choisissez une action à effectuer :");
		System.out.println("- Construire une colonie -> tapez 'c'");
		System.out.println("- Construire une ville -> tapez 'v'");
		System.out.println("- Construire une route -> tapez 'r'");
		System.out.println("- Acheter une carte de developpement -> tapez 'd'");
		System.out.println("- Echanger des ressources -> tapez 'e'");
		char c = sc.nextLine().charAt(0);
		while (!charAction(c)) {
			System.out.println("Caractère non reconnu\nRetapez un caractère (c, v, r, d ou e)");
			c = sc.nextLine().charAt(0);
		}
		return c;
	}

	private boolean charAction(char c) {
		if (c != 'c' && c != 'v' && c != 'r' && c != 'd' && c != 'e')
			return false;
		return true;
	}

	public String askCoord(Scanner sc) {
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
	
	public void buildColony(Colony colony) {
		payColony();
		colonies.add(colony);
	}

	public void buildRoad(Road road) {
		payRoad();
		roads.add(road);
	}

	public void buildTown(Colony colony) { // Comment transformer une colonie en ville ?
		payTown();						// Problème puisqu'une ville est de type Town et Colony
		colony = new Town(colony);
	}

	public void pay(Resource r) { // Supprime une ressource
		Resource toRemove = null;
		for (Resource r1 : resources)
			if (r1.equals(r))
				toRemove = r1;
		if (toRemove != null)
			resources.remove(toRemove);
	}

	public void payColony() { // Payer une colonie
		pay(Resource.CLAY);
		pay(Resource.WOOD);
		pay(Resource.SHEEP);
		pay(Resource.WHEAT);
	}

	public void payTown() { // Payer une ville
		pay(Resource.STONE);
		pay(Resource.STONE);
		pay(Resource.STONE);
		pay(Resource.WHEAT);
		pay(Resource.WHEAT);
	}
	
	public void payRoad() { // Payer une route
		pay(Resource.WOOD);
		pay(Resource.CLAY);
	}

	public void echange(Resource aPayer, Resource aGagner){
		pay(aPayer);
		pay(aPayer);
		pay(aPayer);
		gainResource(aGagner);
	}

	public int canBuyDevCard(Game game) {
		if(!canAffordDevCard())
			return 1;
		else if(game.getDevCardsDeck().isEmpty())
			return 2;
		return 0;
	}
	
	public void payDevCard() {
		pay(Resource.STONE);
		pay(Resource.SHEEP);
		pay(Resource.WHEAT);
	}
	
	public void getDevCard(Game game) {
		payDevCard();
		DevelopmentCard card = game.getDevCard();
		if(card != null) // Normalement il est impossible que card soit null !
			developmentCards.add(card);
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getName() {
		return "J"+number;
	}
	
}
