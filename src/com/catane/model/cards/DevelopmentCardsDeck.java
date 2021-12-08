package com.catane.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DevelopmentCardsDeck {
	
	private List<DevelopmentCard> cards;
	
	public DevelopmentCardsDeck() {
		this.cards = new ArrayList<DevelopmentCard>();
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
			cards.add(c);
	}
	
	private void mixCards() {
		Collections.shuffle(cards);
	}
	
	public DevelopmentCard getCard() {
		if(cards.size() == 0)
			return null;
		int last = cards.size()-1;
		DevelopmentCard card = cards.get(last);
		cards.remove(cards.get(last));
		
		return card;
	}
	
}
