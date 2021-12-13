package com.catane.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.catane.model.Board;
import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Desert;
import com.catane.model.cases.Field;
import com.catane.model.cases.Forest;
import com.catane.model.cases.Hill;
import com.catane.model.cases.Mountain;
import com.catane.model.cases.Pre;
import com.catane.model.cases.Road;
import com.catane.model.cases.Town;
import com.catane.view.cases.CaseView;
import com.catane.view.cases.ColonyView;
import com.catane.view.cases.DesertView;
import com.catane.view.cases.FieldView;
import com.catane.view.cases.ForestView;
import com.catane.view.cases.HillView;
import com.catane.view.cases.MountainView;
import com.catane.view.cases.PreView;
import com.catane.view.cases.RoadView;
import com.catane.view.cases.TownView;

public class BoardView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	private Board board;
	private CaseView[][] casesView;
	
	public BoardView(Game game) {
		this.game = game;
		this.board = game.getBoard();
		setLayout(new GridLayout(board.getSize(), board.getSize()));
		setBackground(new Color(161, 109, 74));
		casesView = generateAndAddCases();
	}	
		
	private CaseView[][] generateAndAddCases() {
		CaseView[][] cases = new CaseView[board.getSize()][board.getSize()];
			
		// Ajout des cases
		for (int i = 0; i < cases.length; i++) {
			for (int j = 0; j < cases[i].length; j++) {
				cases[i][j] = createViewCase(board.getCase(i, j));
				add(cases[i][j]);
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
		default:
			break;
		}
		
		return cV;
	}
	
	public int[] getIndexesOf(CaseView c) {
		if(c == null)
			return null;
		
		for(int j=0;j<casesView.length;j++)
			for(int i=0;i<casesView[0].length;i++)
				if(casesView[j][i] == c)
					return new int[] {j, i};
		
		return null;
	}
	
	public void replaceCaseBy(CaseView c, CaseView newCase) { // newCase peut etre null, c'est autorisé.
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return;
		casesView[coord[0]][coord[1]] = newCase;
		//newCase.mouseEntered(null); // Sinon le contour s'enleve meme si on a la souris dessus.
		this.remove(c);
		this.add(newCase, coord[0]*casesView.length+coord[1]);
		this.revalidate();
		this.repaint();
	}
	
	// Faire d'abord en sorte qu'on puisse acheter des villes dans le model.
	// Ensuite on corrigera cette fonction.
	public void replaceColonyByTown(ColonyView colony) {
		if(colony == null || colony.isEmpty()) // Si colonie null ou vide on quitte.
			return;
		TownView town = new TownView(this, new Town((Colony)colony.getModelCase())); // TEMPORAIRE !!
		replaceCaseBy(colony, town);
		town.repaint();
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getActualPlayer() {
		return game.getActualPlayer();
	}
	
}

