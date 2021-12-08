package com.catane.model.cards;

import java.util.Collections;
import java.util.LinkedList;

public class DevelopmentCardsDeck extends LinkedList<DevelopmentCard> {
	private static final long serialVersionUID = 1L;

	public DevelopmentCardsDeck() {
		int nb = 2;
		
		DevelopmentCard[] tab;
		tab = VictoryPoints.values();
		addCards(tab, nb);
		tab = Progress.values();
		addCards(tab, nb);
		addCards(new Knight(), nb);
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
