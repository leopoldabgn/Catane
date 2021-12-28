package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.VictoryPoints;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Road;
import com.catane.model.cases.Town;

public class Player {
	public static int nextPlayerNb = 1; // Le premier joueur est J1, le deuxieme J2...
	private int number;
	private int army;
	private Score score;
	private List<Resource> resources;
	private List<Colony> colonies; // Comprend egalement les villes...
	private List<Road> roads;
	private List<DevelopmentCard> developmentCards;
	private String name;

	private Color color;
	
	public Player(Color color, String name) {
		this.name = name;
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

	// On verifie si il y a une ressource qu'il a
	// en nb ou plus quantité.
	public Resource getResourceByNb(int nb) {
		int temp = 0;
		for(Resource r : resources) {
			temp = getResource(r);
			if(temp >= nb)
				return r;
		}
		
		return null;
	}
	
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

	public int getResources() {
		return resources.size();
	}

	public List<Resource> getResourceList() {
		return resources;
	}

	public int getNbDevCard(DevelopmentCard card) {
		int sum = 0;
		for(DevelopmentCard c : developmentCards)
			if(c.equals(card))
				sum++;
		return sum;
	}
	
	public boolean canAffordColony(){ // Le joueur a les ressources nécessaires pour construire une colonie
		return (getResource(Resource.CLAY) >= 1 && getResource(Resource.WOOD) >= 1 &&
				getResource(Resource.WOOL) >= 1 && getResource(Resource.WHEAT) >= 1);
	}

	public boolean canAffordTown(){ // Le joueur a les ressources nécessaires pour construire une ville
		return (getResource(Resource.STONE) >= 3 && getResource(Resource.WHEAT) >= 2);
	}

	public boolean canAffordRoad(){ // Le joueur a les ressources nécessaires pour construire une route
		return (getResource(Resource.CLAY) >= 1 && getResource(Resource.WOOD) >= 1);
	}
	
	public boolean canAffordDevCard() {
		return getResource(Resource.STONE) >= 1 && getResource(Resource.WOOL) >= 1 &&
			   getResource(Resource.WHEAT) >= 1;
	}

	public int canBuildColonyOn(Board board, int[] coord) { // Le joueur peut construire une colonie sur la case donnée
		if(!canAffordColony())// Si il n'a pas assez d'argent.
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
		if(!canAffordTown())// Si il n'a pas assez d'argent.
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
	
	public int canBuildRoadOn(Board board, int[] coord) {
		return canBuildRoadOn(board, coord, false);
	}
	
	// Pour l'interface graphique on appelle celle-la directement.
	public int canBuildRoadOn(Board board, int[] coord, boolean dev) { // Le joueur peut construire une route sur la case donnée
		if(!canAffordRoad() && !dev)// Si il n'a pas assez d'argent.
			return 1;
		else {
			if(!board.isEmptyRoad(coord[0], coord[1]))
				return 2;
		}
		
		return 0;
	}

	// Méthodes interactions avec plateau :
	
	public void gainResource(Resource r) {
		resources.add(r);
	}
	
	public void removeColony(Colony colony) {
		colonies.remove(colony);
	}
	
	public void buildColony(Colony colony) {
		payColony();
		colonies.add(colony);
	}

	public void buildRoad(Road road) {
		payRoad();
		roads.add(road);
	}

	public void buildTown(Town town) {
		payTown();
		colonies.add(town); // La liste colonies contient egalement les villes.
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
		pay(Resource.WOOL);
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

	public void echange(Resource aPayer, int nbAPayer, Resource aGagner){
		for(int i=0;i<nbAPayer;i++)
			pay(aPayer);
		gainResource(aGagner);
	}

	public Resource stealResource(Player p) { // vole une ressource au joueur p
		Random r = new Random();
		int x = r.nextInt(p.getResources());
		Resource res = p.resources.get(x);
		p.pay(res);
		gainResource(res);
		return res;
	}

	public boolean isIn(List<Player> players) {
		for (Player p : players)
			if (this == p)
				return true;
		return false;
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
		pay(Resource.WOOL);
		pay(Resource.WHEAT);
	}
	
	public void getDevCard(Game game) {
		payDevCard();
		DevelopmentCard card = game.getDevCard();
		if(card != null) // Normalement il est impossible que card soit null !
			developmentCards.add(card);
	}

	public void devCardUsed(DevelopmentCard card) {
		DevelopmentCard toRemove = null;
		for (DevelopmentCard c : developmentCards)
			if (c.equals(card))
				toRemove = c;
		if (toRemove != null)
			developmentCards.remove(toRemove);
	}
	
	public int getNbVictoryCards() {
		if(developmentCards.size() == 0)
			return 0;
		int sum = 0;
		for(DevelopmentCard card : developmentCards)
			if(card instanceof VictoryPoints)
				sum++;
		return sum;
	}

	public void armyIncreased() {
		army++;
	}

	public int getArmy() {
		return army;
	}
	
	public int getNbColonies() {
		return colonies.size() - getNbTowns();
	}
	
	public int getNbTowns() {
		if(colonies.size() == 0)
			return 0;
		int nb = 0;
		for(Colony c : colonies)
			if(c instanceof Town)
				nb++;
		return nb;
	}
	
	public boolean hasWon() {
		return getScore() == Score.SCORE_WIN;
	}
	
	public int getScore() {
		if(score == null)
			return 0;
		return score.getScore();
	}
	
	public void setScore(Score score) {
		this.score = score;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getName() {
		return "J"+number;
	}
	
	public List<Colony> getColonies() {
		return colonies;
	}
	
	public List<Road> getRoads() {
		return roads;
	}
	
	@Override
	public String toString() {
		return name + " (" +getName() + ")";
	}
	
}
