package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.catane.model.resources.*;
import com.catane.model.cases.*;

public class Player {
	public static int nextPlayerNb = 1; // Le premier joueur est J1, le deuxieme J2...
	private int number;
	private List<Resource> resources;
	private List<Colony> colonies; // Comprend egalement les villes...
	private List<Road> roads;
	private Color color;
	
	public Player(Color color) {
		this.number = nextPlayerNb++;
		this.resources = new ArrayList<Resource>();
		this.colonies = new ArrayList<Colony>();
		this.roads = new ArrayList<Road>();
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	// Méthodes pour vérification des actions

	public int getResource(Resource r1){

		/***************************************
		*  exemple pour avoir nombre de bois :
		*  getResource(new Wood())
		*  retourne le nombre de bois 
		***************************************/

		int x = 0;
		for (Resource r2 : resources)
			if (r2.getClass() == r1.getClass())
				x++;
		return x;
	}

	public boolean canAffordColony(){ // Le joueur a les ressources nécessaires pour construire une colonie
		return (getResource(new Clay()) >= 1 && getResource(new Wood()) >= 1 && getResource(new Sheep()) >= 1 && getResource(new Wheat()) >= 1);
	}

	public boolean canAffordTown(){ // Le joueur a les ressources nécessaires pour construire une ville
		return (getResource(new Stone()) >= 3 && getResource(new Wheat()) >= 2);
	}

	public boolean canAffordRoad(){ // Le joueur a les ressources nécessaires pour construire une route
		return (getResource(new Clay()) >= 1 && getResource(new Wood()) >= 1);
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
		System.out.println("- Echanger des ressources -> tapez 'e'");
		char action = sc.nextLine().charAt(0);
		switch (action){
			case 'c':	System.out.println("c");

					break;
			case 'v':	System.out.println("v");
					break;
			case 'r':	System.out.println("r");
					break;
			case 'e':	System.out.println("e");
					break;
			default:	System.out.println("Valeur invalide !");
						askAction(sc);
					break;
		}
		
		return action;
	}


	public String askCoord(Scanner sc) { // exemple : A12
		return sc.nextLine();
	}
	
	public void buildColony(Colony colony){
		payColony();
		colonies.add(colony);
	}

	public void buildRoad(Road road){
		payRoad();
		roads.add(road);
	}

	public void buildTown(Colony colony){ // Comment transformer une colonie en ville ?
		payTown();						// Problème puisqu'une ville est de type Town et Colony
		colony = new Town(colony);
	}

	public void pay(Resource r){ // Supprime une ressource
		Resource toRemove = null;
		for (Resource r1 : resources)
			if (r1.getClass() == r.getClass())
				toRemove = r1;
		if (toRemove != null)
			resources.remove(toRemove);
	}

	public void payColony() { // Payer une colonie
		pay(new Clay());
		pay(new Wood());
		pay(new Sheep());
		pay(new Wheat());
	}

	public void payTown(){ // Payer une ville
		pay(new Stone());
		pay(new Stone());
		pay(new Stone());
		pay(new Wheat());
		pay(new Wheat());
	}
	
	public void payRoad(){ // Payer une route
		pay(new Wood());
		pay(new Clay());
	}

	public void echange(Resource aPayer, Resource aGagner){
		pay(aPayer);
		pay(aPayer);
		pay(aPayer);
		gainResource(aGagner);
	}

	public void buyDev(){

	}
	
	public int getNumber() {
		return number;
	}
	
	public String getName() {
		return "J"+number;
	}
	
}
