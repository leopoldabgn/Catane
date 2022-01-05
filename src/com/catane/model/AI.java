package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;
import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Port;
import com.catane.model.cases.ResourceCase;
import com.catane.model.cases.Road;
import com.catane.view.cli.CLI;
import com.catane.view.gui.GameView;

public class AI extends Player {

	private CLI cli;
	private GameView gameView;

	public AI(Color color) {
		super(color);
	}

	public void setCLI(CLI cli) {
		this.cli = cli;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
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

	public History midGame(Game game) {
		int beginIndexHist = game.getHistory().size();
		rollDices(game);
		
		action(game);

		return game.getHistory().cutHistory(beginIndexHist);
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
				useDev(game, card);
			}
		}
		
		int[] d = game.rollDices();
		int gain = d[0] + d[1];
		if (gain == 7) {
			if (cli != null)
				cli.discard();
			else
				gameView.discard();
			thiefAction(game);
		}
		else
			game.getBoard().gainResource(gain);
	}

	public void useDev(Game game, DevelopmentCard card) {
		if(card instanceof Knight) {
			game.refreshMostPowerfulArmyOwner();
			thiefAction(game);
		}
		else if(card == Progress.MONOPOLY) {
			Resource r = askResource();
			game.monopoly(r);
		}
		else if(card == Progress.ROAD_CONSTRUCTION) {
			Board board = game.getBoard();
			Road road = null;
			for (int i = 0; i < 2; i++) {
				road = findRoad(board, true);
				if (road != null)
					board.putRoad(this, road, true);
			}
			game.refreshLongestRoadOwner();
			devCardUsed(Progress.ROAD_CONSTRUCTION);
		}
		else if(card == Progress.INVENTION) {
			Resource r1 = askResource();
			Resource r2 = askResource();
			game.invention(r1, r2);	
		}
	}

	public void action(Game game) {
		Board board = game.getBoard();
		Random rd = new Random();
		char[] actions = {'v', 'r', 'd', 'u', 'e'};
		Case c;
		
		if (canAffordColony()) {
			c = findColony(board, false);
			if (c != null)
				board.putColony(this, (Colony) c, false);
		}
		//for (int i = 0; i < 2; i++) {
			int rand = rd.nextInt(actions.length);
			List<Port> ports;
			switch(actions[rand]) {
				case 'v':
					if(!canAffordTown())
						break;
					c = findTown(board);
					if(c == null)
						break;
					board.putTown(this, (Colony)c);
					break;
				case 'r':
					if(!canAffordRoad())
						break;
					c = findRoad(board, false);
					if(c == null)
						break;
					board.putRoad(this, (Road)c, false);
					break;
				case 'd':
					if(canBuyDevCard(game) == 0) {
						getDevCard(game);
					}
					break;
				case 'u':
					DevelopmentCard card = getUsableDevCard();
					if(card != null) {
						useDev(game, card);
					}
					break;
				case 'e':
					ports = board.getPorts(this);
					List<Resource> resources;
					Resource r;
					if(ports.isEmpty()) {
						resources = getResourcesByNb(4);
						if(!resources.isEmpty()) {
							r = resources.get(0);
							trade(r, 4, askResource(r));
						}
					}
					else {
						for(Port p : ports) {
							if(p.getResourceType() == null) {
								resources = getResourcesByNb(p.getResourcesToGive());
								if(resources.isEmpty())
									continue;
								r = resources.get(0);
							}
							else {
								int nb = getResource(p.getResourceType());
								if(nb < p.getResourcesToGive())
									continue;
								r = p.getResourceType();
							}

							trade(r, p.getResourcesToGive(), askResource(r));
						}
					}
					break;
				}
		//}
	}

	public void discard() {
		int n = getResourceList().size() / 2;
		for (int i = 0; i < n; i++)
			pay(askResourceMax());
		addHistory(this + " s'est défaussé de " + n + " cartes");
	}
	
	public void thiefAction(Game game) {
		Board board = game.getBoard();
		ResourceCase rC = findResourceCase(board);
		int[] newThief = board.getIndexesOf(rC);
		board.switchThief(newThief);
		List<Colony> col = board.getColonies(newThief[0], newThief[1]);
		List<Player> players = new ArrayList<Player>();
		for (Colony c : col)
			if (c.getPlayer() != this && !players.contains(c.getPlayer()))
				players.add(c.getPlayer());
		if (players.isEmpty())
			return;
		Random r = new Random();
		stealResource(players.get(r.nextInt(players.size())));
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
	
	public Colony findColony(Board board, boolean early) {
		List<Case> list = board.findFreeCase(new Colony(), null);
		Collections.shuffle(list);
		for (Case c : list)
			if (canBuildColonyOn(board, board.getIndexesOf(c), early) == 0)
				return (Colony) c;
		return null;
	}

	public Road findRoad(Board board, boolean early) {
		List<Case> list = board.findFreeCase(new Road(), null);
		Collections.shuffle(list);
		for (Case c : list)
			if (canBuildRoadOn(board, board.getIndexesOf(c), early) == 0)
				return (Road) c;
		return null;
	}

	public Colony findTown(Board board) {
		List<Case> list = board.findFreeCase(new Colony(), this);
		Collections.shuffle(list);
		for (Case c : list)
			if (canBuildTownOn(board, board.getIndexesOf(c)) == 0)
				return (Colony) c;
		return null;
	}

	public ResourceCase findResourceCase(Board board) {
		List<Case> list = board.findFreeCase(new ResourceCase(-1, null), null);
		Collections.shuffle(list);
		// Liste forcement non vide.
		return (ResourceCase)list.get(0);
	}
	
	// Retourne une ressource au hasard
	public Resource askResource() {
		return askResource(null);
	}

	// Retournz la ressource qu'il a le moins
	public Resource askResource(Resource re) {
		Resource[] res = Resource.values();
		Resource resource;
		if (res[0] != re)
			resource = res[0];
		else
			resource = res[1];
		for (Resource r : res)
			if (getResource(r) < getResource(resource) && r != re)
				resource = r;
		return resource;
	}

	public Resource askResourceMax() {
		Resource[] res = Resource.values();
		Resource resource = res[0];
		for (Resource r : res)
			if (getResource(r) > getResource(resource))
				resource = r;
		return resource;
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
	
}
