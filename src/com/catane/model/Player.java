package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.catane.model.resources.*;
import com.catane.model.cases.Road;

public class Player {
	private List<Resource> resources;
	private List<Road> roads;
	private Color color;
	
	public Player(Color color) {
		this.resources = new ArrayList<Resource>();
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

	public boolean canBuildColonyOn(char h, char v){ // Le joueur peut construire une colonie sur la case données
		return canAffordColony();
	}

	public boolean canBuildTownOn(char h, char v){ // Le joueur peut construire une ville sur la case données
		return canAffordTown();
	}

	public boolean canBuildRoadOn(char h, char v){ // Le joueur peut construire une route sur la case données
		return (canAffordRoad());
	}

	// Méthodes interactions avec plateau :

	public int throwDice(){
		Random r = new Random();
		int x = r.nextInt(6) + 1;
		int y = r.nextInt(6) + 1;
		return x + y;
	}
	
	public void gainResource(Resource r){
		resources.add(r);
	}

	public void askAction(){
		System.out.println("Choisissez une action à effectuer :");
		System.out.println("- Construire une colonie -> tapez 'c'");
		System.out.println("- Construire une ville -> tapez 'v'");
		System.out.println("- Construire une route -> tapez 'r'");
		System.out.println("- Echanger des ressources -> tapez 'e'");
		Scanner sc = new Scanner(System.in);
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
						askAction();
					break;
		}
		sc.close();
	}

	public void buildColony(){

	}

	public void buildRoad(){

	}

	public void buildTown(){

	}

	public void pay(Resource r){ // Supprime une ressource
		Resource toRemove = null;
		for (Resource r1 : resources)
			if (r1.getClass() == r.getClass())
				toRemove = r1;
		if (toRemove != null)
			resources.remove(toRemove);
	}

	public void payColony(){ // Payer une colonie
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
	
}
