package com.catane.model;

public class Score {

	public static int SCORE_WIN = 10;
	
	private Game game;
	private Player player;
	
	public Score(Game game, Player player) {
		this.game = game;
		this.player = player;
	}
	
	public int getScore() {
		if(player == null || game == null)
			return 0;
		int points = 0;
		if(game.longestRoadOwner() == player)
			points += 2;
		if(game.mostPowerfulArmyOwner() == player)
			points += 2;
		points += player.getNbColonies();
		points += player.getNbTowns()*2;
		points += player.getNbVictoryCards();
		points+=7;
		return points;
	}
	
}
