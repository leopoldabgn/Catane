package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.DevelopmentCardsDeck;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;

public class Game {

	private List<Player> players;
	private Player actualPlayer;
	private Player longestRoadOwner, mostPowerfulArmyOwner; // On sait qui detient les cartes.
	private Board board;
	private DevelopmentCardsDeck developmentCardsDeck;

	public Game() {
		Player.nextPlayerNb = 1; // On remet a 1 au cas ou il s'agirait d'une nouvelle partie.
		developmentCardsDeck = new DevelopmentCardsDeck();
	}

	public void setBoard(int n) {
		if (n == 4 || n == 6)
			board = new Board(n);
		else
			board = new Board(4);
	}
	
	public void setPlayers(List<Player> players) { // Peut sûrement être supprimée
		this.players = players;
		if(players != null && players.size() > 1)
			actualPlayer = players.get(0);
	}
	
	public int[] rollDices() {
		Random rd = new Random();
		return new int[] {rd.nextInt(6)+1, rd.nextInt(6)+1};
	}
	
	public int[] convertCoord(String coord) {
		if(coord == null || coord.length() < 2 || coord.length() > 3)
			return null;
		int[] tab = new int[2];
		try {
			tab[0] = coord.charAt(0)-'A'+1; // +1 car on saute la ligne avec les ports
			tab[1] = Integer.parseInt(coord.substring(1)); // Pas de -1 car on saute la colonne avec les ports.
		} catch(Exception e) {
			return null;
		}
		return tab;
	}

	public void setupPlayers(int p, int ia) {
		players = new ArrayList<Player>();
		Player player = null;
		for (int i = 0; i < p - ia; i++) {
			switch (i) {
				case 0:	player = new Player(Color.WHITE);
						break;
				case 1:	player = new Player(Color.BLUE);
						break;
				case 2:	player = new Player(Color.YELLOW);
						break;
				default:player = new Player(Color.RED);
						break;
			}
			player.setScore(new Score(this, player));
			players.add(player);
		}
		for (int i = 0 + p; i < ia + p; i++) {
			switch (i) {
				case 0:	player = new AI(Color.ORANGE);
						break;
				case 1:	player = new AI(Color.BLUE);
						break;
				case 2:	player = new AI(Color.YELLOW);
						break;
				default:player = new AI(Color.RED);
						break;
			}
			player.setScore(new Score(this, player));
			players.add(player);
		}
		
		actualPlayer = players.get(0);
	}
	
	public void nextRound() { // On passe au joueur suivant dans la liste.
		if(actualPlayer == null || players == null || players.size() < 3)
			return;
		int index = players.indexOf(actualPlayer);
		if(index == -1)
			return;
		
		actualPlayer = players.get(index  == players.size()-1 ? 0 : index+1);
	}
	
	public boolean endGame() { // Ou alors on dit que celui qui vient de jouer a gagné ? actualPlayer.hasWon() ?
		for(Player p : players)
			if(p.hasWon())
				return true;
		return false;
	}

	public void refreshMostPowerfulArmyOwner() {
		actualPlayer.armyIncreased();
		if (actualPlayer.getArmy() >= 3) {
			if (mostPowerfulArmyOwner == null)
				mostPowerfulArmyOwner = actualPlayer;
			else
				if (mostPowerfulArmyOwner.getArmy() < actualPlayer.getArmy())
					mostPowerfulArmyOwner = actualPlayer;
		}
		actualPlayer.devCardUsed(new Knight());
	}

	public void monopoly(Resource r) {
		List<Player> otherPlayers = new ArrayList<Player>();
		for (Player p : players)
			if (!p.equals(actualPlayer))
				otherPlayers.add(p);
		for (Player p : otherPlayers) {
			int n = p.getResource(r);
			for (int i = 0; i < n; i++) {
				p.pay(r);
				actualPlayer.gainResource(r);
			}
		}
		actualPlayer.devCardUsed(Progress.MONOPOLY);
	}

	public void invention(Resource r1, Resource r2) {
		actualPlayer.gainResource(r1);
		actualPlayer.gainResource(r2);
		actualPlayer.devCardUsed(Progress.INVENTION);
	}
	
	// On verifie qui a la plus grande route, et on lui donne la carte.
	public void refreshLongestRoadOwner() {
		Player p;
		int maxSize = 0;
		int[] roads = new int[players.size()];
		longestRoadOwner = null;
		for(int i=0;i<roads.length;i++) {
			p = players.get(i);
			roads[i] = board.getLongestRoad(p);
			
			if(roads[i] >= 5 && roads[i] > maxSize) {
					maxSize = roads[i];
					
					longestRoadOwner = p;
			}
		}
		
		if(longestRoadOwner == null)
			return;
		
		for(int i=0;i<roads.length;i++) {
			p = players.get(i);
			if(p != longestRoadOwner && roads[i] == maxSize) {
				
				longestRoadOwner = null;
			}
		}
	}
	// Si deux joueurs ont la même longueur de route la carte n'est pas donnée (longestRoadOwner = null)
	
	public Player longestRoadOwner() {
		return longestRoadOwner;
	}
	
	public Player mostPowerfulArmyOwner() {
		return mostPowerfulArmyOwner;
	}
	
	public DevelopmentCardsDeck getDevCardsDeck() {
		return developmentCardsDeck;
	}
	
	public DevelopmentCard getDevCard() {
		return developmentCardsDeck.getCard();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Player getActualPlayer() {
		return actualPlayer;
	}

	public List<Player> getPlayers() {
		return players;
	}

}
