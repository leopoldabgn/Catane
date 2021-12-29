package com.catane.model;

import java.awt.Color;

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
