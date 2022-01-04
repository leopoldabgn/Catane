package com.catane.model;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cases.Colony;
import com.catane.model.cases.MovableCase;
import com.catane.model.cases.Road;

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
			Colony col = findColony(board, true);
			board.putColony(this, col, true);
		}
		for (int i = 0; i < 2; i++) {
			Road road = findRoad(board, true);
			board.putRoad(this, road, true);
		}
	}

	public void midGame(Game game) {
		Board board = game.getBoard();
		int[] dices = game.rollDices();
		int gain = dices[0] + dices[1];
		if (gain == 7) {
			// Voleur
		}else {
			board.gainResource(gain);
		}

		// Action de l'IA
	}

	public void action(Game game) {
		Random rd = new Random();
		int nbActions = 2;
		switch(rd.nextInt(nbActions)+1) {
			case 0:
				
				break;
			case 1:
				break;
			case 2:
				//drawDevCard(game);
				break;
		}
	}
	
	public DevelopmentCard getUsableDevCard() {
		Random rd = new Random();
		int rand = getDevCards().size();
		if(rand == 0)
			return null;
		return getDevCards().get(rd.nextInt(rand));
	}
	
	public boolean wantsTo() {
		Random rd = new Random();
		return rd.nextInt(2) == 0;
	}
	
	public Colony findColony(Board board, boolean early) {
		List<MovableCase> list = board.findFreeCase(new Colony(), null);
		Collections.shuffle(list);
		for (MovableCase c : list)
			if (canBuildColonyOn(board, board.getIndexesOf(c), early) == 0)
				return (Colony) c;
		return null;
	}

	public Road findRoad(Board board, boolean early) {
		List<MovableCase> list = board.findFreeCase(new Colony(), null);
		Collections.shuffle(list);
		for (MovableCase c : list)
			if (canBuildColonyOn(board, board.getIndexesOf(c), early) == 0)
				return (Road) c;
		return null;
	}

	public Colony findTown(Board board, boolean early) {
		List<MovableCase> list = board.findFreeCase(new Colony(), this);
		Collections.shuffle(list);
		for (MovableCase c : list)
			if (canBuildColonyOn(board, board.getIndexesOf(c), early) == 0)
				return (Colony) c;
		return null;
	}

	public int[] moveThief() {
		int[] coord = new int[2];
		return coord;
	}
	
}
