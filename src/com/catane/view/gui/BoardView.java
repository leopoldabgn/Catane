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
import com.catane.model.cases.ResourceCase;
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
import com.catane.view.gui.cases.PortView;
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
	
	// Unique methode pour la CLI.
	public static void display(Board board) {
		int size = board.getSize(), maxSize = 15;
		int size2 = size-2; // size sans les ports.
		
		char letter = 'A';
		System.out.print(" ".repeat(18));
		for(int i=0;i<size2;i++) {
			String s = "" + letter;
			if (i % 2 == 0)
				System.out.print(addSpaces(s, 4));
			else
				System.out.print(addSpaces(s, 15));
			System.out.print(" ");
			letter++;
		}
		System.out.println();
		
		int line = 1;
		if(size2%2 == 0)
			line += (size2/2)*5;
		else
			line += (size2/2+1)*5;
		line += (size2/2)*(maxSize+1);

		boolean b = false;
		
		for(int j=0;j<size;j++) {

			if (j == 0 || j == size - 1) {
				System.out.print(" ");
				for (int i = 0; i < size; i++) {
					Case p = board.getCase(i, j);
					if (p instanceof Port) {
						System.out.print("|" + addSpaces(((Port) p).toString(), maxSize) + "|");
					}else {
						if (i % 2 == 0)
							System.out.print(" ".repeat(17));
						else
							System.out.print(" ".repeat(4));
					}
				}
				System.out.println();
			}else {
				System.out.print(j);
				if(j < 10)
					System.out.print(" ");
				Case p = board.getCase(0, j);
				if (p instanceof Port) {
					System.out.print(addSpaces(((Port) p).toString(), maxSize));
				}else {
					System.out.print(" ".repeat(maxSize));
				}
				System.out.print("|");
				
				for(int i=1;i<size-1;i++) {
					Case c = board.getCase(i, j);
					if(c instanceof ResourceCase) {
						System.out.print(addSpaces(c.toString(), maxSize));
					}
					else {
						if(i%2 == 1)
							System.out.print(" "+c+" ");
						else
							System.out.print(addSpaces(c.toString(), maxSize));
					}
					System.out.print("|");
				}
				
				p = board.getCase(size - 1, j);
				if (p instanceof Port)
					System.out.println(addSpaces(((Port) p).toString(), maxSize) + " " + j);
				else
					System.out.println(" ".repeat(16)+j);
			}
			if (j != size - 1) {
				if (j == size - 2 || j == 0)
					System.out.print("  " + " ".repeat(maxSize) +"-".repeat(line));
				else {
					if (b)
						System.out.print("  " + "-".repeat(maxSize) +"-".repeat(line));
					else
						System.out.print("  " + " ".repeat(maxSize) +"-".repeat(line) + "-".repeat(maxSize));
				}
				if (j % 2 == 0)
					b = !b;
			}
			System.out.println();
		}
		
	}
	
	// Si besoin, on rajoute des espaces avant et apres pour que
	// la taille de la string soit egale a newSize
	private static String addSpaces(String str, int newSize) {
		if(str.length() >= newSize)
			return str;
		int reste = newSize-str.length();
		int before = reste/2, after = reste/2;
		if(reste%2 == 1)
			before += 1;
		str = " ".repeat(before)+str+" ".repeat(after);
		return str;
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

