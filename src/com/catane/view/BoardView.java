package com.catane.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.catane.model.Board;
import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.ResourceCase;
import com.catane.model.cases.ResourceCase.Desert;
import com.catane.model.cases.ResourceCase.Field;
import com.catane.model.cases.ResourceCase.Forest;
import com.catane.model.cases.ResourceCase.Hill;
import com.catane.model.cases.ResourceCase.Mountain;
import com.catane.model.cases.ResourceCase.Pre;
import com.catane.model.cases.Road;
import com.catane.model.cases.Town;
import com.catane.view.cases.CaseView;
import com.catane.view.cases.ColonyView;
import com.catane.view.cases.ResourceCaseView.DesertView;
import com.catane.view.cases.ResourceCaseView.FieldView;
import com.catane.view.cases.ResourceCaseView.ForestView;
import com.catane.view.cases.ResourceCaseView.HillView;
import com.catane.view.cases.ResourceCaseView.MountainView;
import com.catane.view.cases.ResourceCaseView.PreView;
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
	
	// Unique methode pour la CLI.
	public static void display(Board board) {
		int size = board.getSize(), space = 4, maxSize = 13;
		char letter = 'A';
		System.out.print(" ".repeat(space));
		for(int i=0;i<size;i++)
			System.out.print((letter++)+" ".repeat(9));
		System.out.println();
		int line = 1;
		if(size%2 == 0)
			line += (size/2)*5;
		else
			line += (size/2+1)*5;
		line += (size/2)*(maxSize+1);
		System.out.println("  "+"-".repeat(line));
		for(int j=0;j<size;j++) {
			
			System.out.print((j+1));
			if(j+1 < 10)
				System.out.print(" ");
			System.out.print("|");
			
			for(int i=0;i<size;i++) {
				Case c = board.getCase(i, j);
		 		if(c instanceof ResourceCase) {
		 			System.out.print(c);
		 		}
		 		else {
		 			space = maxSize-2;
		 			if(i%2 == 0)
		 				System.out.print(" "+c+" ");
		 			else {
		 				System.out.print(" ".repeat(space/2+1)+c+" ".repeat(space/2));
		 			}
		 		}
		 		System.out.print("|");
			 }
			
			System.out.println(" "+(j+1));
			System.out.print("  "+"-".repeat(line));
			System.out.println();
		 }
		
	}
	
	private CaseView[][] generateAndAddCases() {
		CaseView[][] cases = new CaseView[board.getSize()][board.getSize()];
			
		// Ajout des cases
		for (int i = 0; i < cases[0].length; i++) {
			for (int j = 0; j < cases.length; j++) {
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
		default:
			break;
		}
		
		return cV;
	}
	
	public int[] getIndexesOf(CaseView c) {
		if(c == null)
			return null;
		
		for(int j=0;j<casesView[0].length;j++)
			for(int i=0;i<casesView.length;i++)
				if(casesView[i][j] == c)
					return new int[] {i, j};
		
		return null;
	}
	
	public void replaceCaseBy(CaseView c, CaseView newCase) { // newCase peut etre null, c'est autorisé.
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return;
		casesView[coord[0]][coord[1]] = newCase;
		//newCase.mouseEntered(null); // Sinon le contour s'enleve meme si on a la souris dessus.
		this.remove(c);
		this.add(newCase, coord[1]*casesView.length+coord[0]); // coord[1] -> line, coord[0] -> column.
		this.revalidate();
		this.repaint();
	}
	
	public void putColony(Player player, ColonyView colonyView) { // colonyView -> vide au depart
		board.putColony(player, colonyView.getModelCase()); // On indique au model le changement
		// La colonie est desormais reliee a un joueur.
		// La view sera donc affichee differement.
	}
	
	public void putTown(Player player, ColonyView colonyView) {// colonyView -> correspond a une vrai colonie au depart
		Town town = board.putTown(player, colonyView.getModelCase()); // On indique au model le changement
		TownView townView = new TownView(this, town); // On cree une case TownView pour la nouvelle ville.
		replaceCaseBy(colonyView, townView); // On remplace : colonyView -> townView.
	}
	
	public void putRoad(Player player, RoadView roadView) { // roadView -> vide au depart
		board.putRoad(player, roadView.getModelCase()); // On indique au model le changement
		// La view va interroger le modele et changera son apparence.
	}
	
	public Game getGame() {
		return game;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Player getActualPlayer() {
		return game.getActualPlayer();
	}
	
}

