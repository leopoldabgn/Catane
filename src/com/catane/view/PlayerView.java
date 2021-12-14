package com.catane.view;

import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;
import com.catane.model.cards.VictoryPoints;

public class PlayerView {

	public static void printResources(Player player) {
		System.out.print("Ressources : ");
		Resource[] resources = Resource.values();
		int count = 0;
		for(Resource r : resources) {
			System.out.print(r+" = "+player.getResource(r));
			if(++count < resources.length)
				System.out.print(", ");
		}
		
		System.out.println();
	}

	public static void printDevelopmentCards(Player player) {
		System.out.print("Cartes de developpement : ");
		DevelopmentCard[] tab;
		boolean nothing = true, comma = false;
		int nb = player.getNbDevCard(new Knight());
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
				nb = player.getNbDevCard(card);
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
	
	public static void printScore(Player player) {
		System.out.println("Score : "+player.getScore());
	}
	
}
