package com.catane.model.cards;

import java.util.Collections;
import java.util.LinkedList;

public class DevelopmentCardsDeck extends LinkedList<DevelopmentCard> {
	private static final long serialVersionUID = 1L;

	public DevelopmentCardsDeck() {
		// Nombre de cartes de Catane d'apres le site internet :
		// http://jeuxstrategie.free.fr/Siedler_complet.php
		
		DevelopmentCard[] tab;
		tab = VictoryPoints.values();
		addCards(tab, 1); // combien ?
		tab = Progress.values();
		addCards(tab, 2); // en deux exemplaires
		addCards(new Knight(), 14);
		mixCards();
	}
	
	private void addCards(DevelopmentCard[] tab, int nb) {
		for(DevelopmentCard card : tab)
			addCards(card, nb);
	}
	
	private void addCards(DevelopmentCard c, int nb) {
		for(int i=0;i<nb;i++)
			this.push(c);
	}
	
	private void mixCards() {
		Collections.shuffle(this);
	}
	
	public DevelopmentCard getCard() {
		return this.poll();
	}
	
}
