package com.catane.view.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.catane.model.Board;
import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Port;
import com.catane.model.cases.ResourceCase.Desert;
import com.catane.model.cases.ResourceCase.Field;
import com.catane.model.cases.ResourceCase.Forest;
import com.catane.model.cases.ResourceCase.Hill;
import com.catane.model.cases.ResourceCase.Mountain;
import com.catane.model.cases.ResourceCase.Pre;
import com.catane.model.cases.Road;
import com.catane.model.cases.Sea;
import com.catane.model.cases.Town;
import com.catane.view.gui.cases.CaseView;
import com.catane.view.gui.cases.ColonyView;
import com.catane.view.gui.cases.MovableCaseView;
import com.catane.view.gui.cases.PortView;
import com.catane.view.gui.cases.ResourceCaseView;
import com.catane.view.gui.cases.ResourceCaseView.DesertView;
import com.catane.view.gui.cases.ResourceCaseView.FieldView;
import com.catane.view.gui.cases.ResourceCaseView.ForestView;
import com.catane.view.gui.cases.ResourceCaseView.HillView;
import com.catane.view.gui.cases.ResourceCaseView.MountainView;
import com.catane.view.gui.cases.ResourceCaseView.PreView;
import com.catane.view.gui.cases.RoadView;
import com.catane.view.gui.cases.SeaView;
import com.catane.view.gui.cases.TownView;

public class BoardView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GameView gameView;
	private Game game;
	private Board board;
	private CaseView[][] casesView;
	
	public BoardView(GameView gameView) {
		this.gameView = gameView;
		this.game = gameView.getGame();
		this.board = game.getBoard();
		setLayout(new GridLayout(board.getSize(), board.getSize()));
		setBackground(new Color(255, 158, 0));
		casesView = generateAndAddCases();
	}	
	
	private CaseView[][] generateAndAddCases() {
		CaseView[][] cases = new CaseView[board.getSize()][board.getSize()];
			
		// Ajout des cases
		for (int i = 0; i < board.getSize(); i++) {
			for (int j = 0; j < board.getSize(); j++) {
				cases[j][i] = createViewCase(board.getCase(j, i));
				add(cases[j][i]);
				// Attention, on va de gauche a droite. Puis on va a la ligne suivante etc..
				// j -> x et i -> y. On a decider que la premiere coordonnees est x et la seconde y.
				// Au moment du add() on a doit d'abord augmenter x de 0 a size puis y++ etc..
				// Et pas l'inverse !
			}
		}

		return cases;
	}
	
	private CaseView createViewCase(Case c) {
		CaseView cV = null;
		switch(c.getClass().getSimpleName()) {
		case "Desert":
			cV = new DesertView(this, (Desert)c);
			break;
		case "Field":
			cV = new FieldView(this, (Field)c);
			break;
		case "Forest":
			cV = new ForestView(this, (Forest)c);
			break;
		case "Hill":
			cV = new HillView(this, (Hill)c);
			break;
		case "Mountain":
			cV = new MountainView(this, (Mountain)c);
			break;
		case "Pre":
			cV = new PreView(this, (Pre)c);
			break;
		case "Colony":
			cV = new ColonyView(this, (Colony)c);
			break;
		case "Town":
			cV = new TownView(this, (Town)c);
			break;
		case "Road":
			cV = new RoadView(this, (Road)c);
			break;
		case "Port":
			cV = new PortView(this, (Port)c);
			break;
		case "Sea":
			cV = new SeaView(this, (Sea)c);
			break;
		default:
			break;
		}
		
		return cV;
	}
	
	public int[] getIndexesOf(CaseView c) {
		if(c == null)
			return null;
		
		for(int j=0;j<board.getSize();j++)
			for(int i=0;i<board.getSize();i++)
				if(casesView[i][j] == c)
					return new int[] {i, j};
		
		return null;
	}
	
	public void changeSelectableCases(CaseView c, Player player, boolean onlyEmpty) {
		changeSelectableCases(c, player, onlyEmpty, false);
	}
	
	public void changeSelectableCases(CaseView c, Player player, boolean onlyEmpty, boolean early) {
		boolean selectable = false;
		for(CaseView[] cases : casesView)
			for(CaseView ca : cases) {
				
				if(ca instanceof ColonyView && c instanceof ColonyView && player != null) {
					if(((MovableCaseView)ca).isEmpty() && onlyEmpty) {
						selectable = player.canBuildColonyOn(board, getIndexesOf(ca), early) == 0;
					}
					else if(!onlyEmpty && ((MovableCaseView)ca).getPlayer() == player) {
						selectable = true;
					}
				}
				else if(ca instanceof RoadView && c instanceof RoadView && player != null) {
					if(((MovableCaseView)ca).isEmpty() && onlyEmpty) {
						selectable = player.canBuildRoadOn(board, getIndexesOf(ca), early) == 0;
					}
					else if(!onlyEmpty && ((MovableCaseView)ca).getPlayer() == player) {
						selectable = true;
					}
				}
				else if(ca instanceof ResourceCaseView && c instanceof ResourceCaseView) {
					selectable = !((ResourceCaseView)ca).hasThief();
				}
				else {
					selectable = c != null && ca.getClass().equals(c.getClass());
					if(selectable && ca instanceof MovableCaseView) {
						if(!(((MovableCaseView)ca).isEmpty() && onlyEmpty)) {
							selectable = false;
						}
					}
				}

				if(selectable)
					ca.setSelectable(true);
				else
					ca.setSelectable(false);
			}
		revalidate();
		repaint();
	}
	
	public void replaceCaseBy(CaseView c, CaseView newCase) { // newCase peut etre null, c'est autorisé.
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return;
		casesView[coord[0]][coord[1]] = newCase;
		//newCase.mouseEntered(null); // Sinon le contour s'enleve meme si on a la souris dessus.
		this.remove(c);
		this.add(newCase, coord[1]*board.getSize()+coord[0]); // coord[1] -> line, coord[0] -> column.
		this.revalidate();
		this.repaint();
	}
	
	public void putColony(Player player, ColonyView colonyView, boolean early) { // colonyView -> vide au depart
		board.putColony(player, colonyView.getModelCase(), early); // On indique au model le changement
		// La colonie est desormais reliee a un joueur.
		// La view sera donc affichee differement.
		gameView.refreshInfos();
	}
	
	public void putTown(Player player, ColonyView colonyView) {// colonyView -> correspond a une vrai colonie au depart
		Town town = board.putTown(player, colonyView.getModelCase()); // On indique au model le changement
		TownView townView = new TownView(this, town); // On cree une case TownView pour la nouvelle ville.
		replaceCaseBy(colonyView, townView); // On remplace : colonyView -> townView.
		gameView.refreshInfos();
	}
	
	public void putRoad(Player player, RoadView roadView, boolean early) { // roadView -> vide au depart
		board.putRoad(player, roadView.getModelCase(), early); // On indique au model le changement
		// La view va interroger le modele et changera son apparence.
		gameView.refreshInfos();
	}
	
	public void switchThief(ResourceCaseView resCase) {
		int[] coord = getIndexesOf(resCase);
		if(coord == null)
			return;
		int[] old = board.getThiefCoord();
		board.switchThief(coord); // On switch le voleur dans le model board
		ResourceCaseView oldThief = (ResourceCaseView)casesView[old[0]][old[1]];
		oldThief.refreshThiefView(); // On rafraichit les voleurs dans la view
		resCase.refreshThiefView(); // On rafraichit les voleurs dans la view
		changeSelectableCases(null, null, false);
	}
	
	public void reset() {
		//this.removeAll();
		//casesView = generateAndAddCases();
		for (CaseView[] tab : casesView)
			for (CaseView c : tab) {
				if (c instanceof MovableCaseView)
					((MovableCaseView) c).reset();
				if (c instanceof ResourceCaseView)
					((ResourceCaseView) c).refreshThiefView();
			}
		revalidate();
		repaint();
	}
	
	public GameView getGameView() {
		return gameView;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Board getBoardModel() {
		return board;
	}
	
	public Player getActualPlayer() {
		return game.getActualPlayer();
	}
	
}

