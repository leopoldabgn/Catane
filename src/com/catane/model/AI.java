package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;
import com.catane.model.cases.Colony;
import com.catane.model.cases.ResourceCase;

public class AI extends Player {

	public AI(Color color) {
		super(color);
	}

	public String getName() {
		return "Ordi"+getNumber();
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void setName(String name) {

	}

	public void earlyGame(Game game) {
		Board board = game.getBoard();
		for (int i = 0; i < 2; i++) {
			int[] coord = findColony();
			int x = coord[0];
			int y = coord[1];
			board.putColony(this, x, y, true);
		}
		for (int i = 0; i < 2; i++) {
			int[] coord = findRoad();
			int x = coord[0];
			int y = coord[1];
			board.putRoad(this, x, y, true);
		}
	}

	public History midGame(Game game) {
		History history = new History();
		Board board = game.getBoard();
		
		int[] dices = game.rollDices();
		int gain = dices[0] + dices[1];
		if (gain == 7) {
			// Voleur
		}else {
			board.gainResource(gain);
		}

		// Action de l'IA
		return history;
	}

	public void rollDices(Game game) {
		if(wantsTo()) {
			if(canBuyDevCard(game) == 0) {
				getDevCard(game);
			}
		}
			
		if(wantsTo()) {
			DevelopmentCard card = getUsableDevCard();
			if(card != null) {
				if(card instanceof Knight) {
					game.refreshMostPowerfulArmyOwner();
					// thiefAction();
				}
				else if(card == Progress.MONOPOLY) {
					// Resource r = askResource();
					// game.monopoly(r);
				}
				else if(card == Progress.ROAD_CONSTRUCTION) {
					//board.putRoad(player, coord[0], coord[1], true);
					// game.refreshLongestRoadOwner();
					// player.devCardUsed(Progress.ROAD_CONSTRUCTION);
				}
				else if(card == Progress.INVENTION) {
					/*
						Resource r1 = askResource();
						Resource r2 = askResource();
						game.invention(r1, r2);
					 */			
				}
			}
		}
			
	}

	
			
	public void action(Game game) {
		Board board = game.getBoard();
		Random rd = new Random();
		char[] actions = {'c', 'v', 'r', 'd', 'u', 'e'};
		int rand = rd.nextInt(actions.length);
		int[] coord;
		Colony c;
		switch(actions[rand]) {
			case 'c':
				if(!canAffordColony())
					break;
				c = findColony(board);
				if(c == null)
					break;
				board.putColony(this, c, false);
				break;
			case 'v':
				if(!canAffordTown())
					break;
				c = findTown(board);
				if(c == null)
					break;
				board.putTown(this, c, false);
				break;
			case 'r':
				break;
			case 'd':
				break;
			case 'u':
				break;
			case 'e':
				break;
		}
	}
	
	public DevelopmentCard getUsableDevCard() {
		for(DevelopmentCard card : getDevCards()) {
			if(canUseDev(card))
				return card;
		}
		return null;
	}
	
	public boolean wantsTo() {
		Random rd = new Random();
		return rd.nextInt(2) == 0;
	}
	
	public int[] findColony() {
		int[] coord = new int[2];
		return coord;
	}

	public int[] findRoad() {
		int[] coord = new int[2];
		return coord;
	}

	public int[] findTown() {
		int[] coord = new int[2];
		return coord;
	}

	public int[] moveThief() {
		int[] coord = new int[2];
		return coord;
	}
	
}
