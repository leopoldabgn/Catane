package com.catane.view.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.catane.model.Player;
import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.VictoryPoints;
import com.catane.view.gui.CardView.DevCardView;

public class DeckView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Player player;
	private GameView gameView;
	private List<DevCardView> cards;
	private boolean victoryPoints;
	private int upSpace = 17, bottomSpace = 6;
	
	// Si victoryPoints : true -> on met que les victory points cards.
	// Sinon, on met le reste -> progressCard et Knight
	public DeckView(boolean victoryPoints, GameView gameView) {
		this.gameView = gameView;
		this.player = gameView.getGame().getActualPlayer();
		this.victoryPoints = victoryPoints;
		this.setLayout(null);
		if(victoryPoints)
			this.setBorder(BorderFactory.createTitledBorder("Cartes victoire"));
		else
			this.setBorder(BorderFactory.createTitledBorder("Cartes progrès"));
		this.setPreferredSize(new Dimension(300, CardView.HEIGHT+upSpace+bottomSpace));
		cards = new ArrayList<DevCardView>();
		refresh();
	}
	
	public void refresh() {
		this.removeAll();
		cards.removeAll(cards);
		int x = 10;
		DevCardView cardView;
		for(DevelopmentCard card : player.getDevCards()) {
			// On skip les cartes qui ne nous interesse pas
			if(victoryPoints && !(card instanceof VictoryPoints) ||
			  !victoryPoints &&   card instanceof VictoryPoints)
				continue;
			cardView = new DevCardView(card, gameView);
			cards.add(cardView);
			add(cardView);
			cardView.setLocation(x, upSpace);
			//x += CardView.WIDTH-15;
			x += getCoeff();
		}
		revalidate();
		repaint();
	}
	
	public int totalCards() {
		int size = 0;
		for(DevelopmentCard card : player.getDevCards()) {
			// On skip les cartes qui ne nous interesse pas
			if(victoryPoints && !(card instanceof VictoryPoints) ||
			  !victoryPoints &&   card instanceof VictoryPoints)
				continue;
			size++;
		}
		return size;
	}
	
	public int getCoeff() {
		int totalCards = totalCards();
		int start = 10;
		int coeff = CardView.WIDTH-15;
		
		while(start+coeff*totalCards+CardView.WIDTH > getWidth()-20) {
			coeff -= 5;
		}
		
		return coeff;
	}

	public boolean isUsable() {
		for (DevCardView cardView : cards)
			if (cardView.getDevCard().isUsable())
				return true;
		return false;
	}
	
	public void changePlayer(Player player) {
		this.player = player;
	}
	
}
