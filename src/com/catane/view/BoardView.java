package com.catane.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;

import com.catane.model.Player;
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
		
	private int size;
	private CaseView[][] cases;
	private Player actualPlayer;
	
	public BoardView(int size) {
		this.size = size * 2 + 1;
		setLayout(new GridLayout(this.size, this.size));
		cases = generateAndAddCases();
		cases = mixCases(cases);
		this.actualPlayer = new Player(Color.BLUE);
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
//				if (cases[i][j] != null) {
//					if (cases[i][j] instanceof Road)
//						System.out.print(" R ");
//					if (cases[i][j] instanceof Colony)
//						System.out.print(" C ");
//					if (!(cases[i][j] instanceof Road) && !(cases[i][j] instanceof Colony))
//						System.out.print(" T ");
//				}else {
//					System.out.print(" N ");
//				}
				this.add(cases[i][j]);
			}
			//System.out.println();
		}
	}	
		
	private CaseView[][] generateAndAddCases() {
		CaseView[][] cases = new CaseView[size][size];
		int x = 0;
			
		// Ajout des cases de ressources
		for (int i = 1; i < size; i += 2){
			for (int j = 1; j < size; j += 2) {
					cases[i][j] = createCase(x);
				x++;
			}
		}
			
		// Ajout des routes et villes
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i % 2 == 0 && j % 2 == 0)
					cases[i][j] = createColony();
				if (i % 2 == 0 && j % 2 == 1)
					cases[i][j] = createRoad(false);
				if (i % 2 == 1 && j % 2 == 0)
					cases[i][j] = createRoad(true);
			}
		}
		return cases;
	}
		
	private CaseView createCase(int x) {
		switch (x) {
		case 0: return new ForestView(this, 6);
		case 1: return new PreView(this, 10);
		case 2: return new FieldView(this, 11);
		case 3: return new PreView(this, 8);
		case 4: return new FieldView(this, 4);
		case 5: return new HillView(this, 9);
		case 6: return new ForestView(this, 5);
		case 7: return new MountainView(this, 12);
		case 8: return new MountainView(this, 3);
		case 9: return new DesertView(this);
		case 10: return new FieldView(this, 10);
		case 11: return new HillView(this, 6);
		case 12: return new HillView(this, 9);
		case 13: return new MountainView(this, 8);
		case 14: return new PreView(this, 5);
		case 15: return new ForestView(this, 2);
		case 16: return new ForestView(this, 5);
		case 17: return new PreView(this, 5);
		case 18: return new MountainView(this, 5);
		case 19: return new FieldView(this, 6);
		case 20: return new ForestView(this, 5);
		case 21: return new HillView(this, 5);
		case 22: return new FieldView(this, 5);
		case 23: return new MountainView(this, 5);
		case 24: return new HillView(this, 5);
		case 25: return new PreView(this, 5);
		case 26: return new FieldView(this, 5);
		case 27: return new MountainView(this, 5);
		case 28: return new ForestView(this, 5);
		case 29: return new PreView(this, 5);
		case 30: return new HillView(this, 5);
		case 31: return new MountainView(this, 5);
		case 32: return new FieldView(this, 5);
		case 33: return new HillView(this, 5);
		case 34: return new ForestView(this, 5);
		case 35: return new PreView(this, 5);
		}
		return null;
	}
	
	private CaseView createRoad(boolean up) {
		return new RoadView(this, null, up);
	}
		
	private CaseView createColony() {
		return new ColonyView(this, null);
	}
	
	private CaseView[][] mixCases(CaseView[][] cases){
		Random r = new Random();
		int i1 = 0;
		int i2 = 0;
		int j1 = 0;
		int j2 = 0;
		CaseView tmp = null;
		for (int i = 0; i < size * size; i++) {
			i1 = r.nextInt(size - 1);
			if (i1 % 2 == 0) i1++;
			i2 = r.nextInt(size - 1);
			if (i2 % 2 == 0) i2++;
			j1 = r.nextInt(size - 1);
			if (j1 % 2 == 0) j1++;
			j2 = r.nextInt(size - 1);
			if (j2 % 2 == 0) j2++;
			tmp = cases[i1][j1];
			cases[i1][j1] = cases[i2][j2];
			cases[i2][j2] = tmp;
		}
		
		return cases;
	}
	
	public int[] getIndexesOf(CaseView c) {
		if(c == null)
			return null;
		
		for(int j=0;j<cases.length;j++)
			for(int i=0;i<cases[0].length;i++)
				if(cases[j][i] == c)
					return new int[] {j, i};
		
		return null;
	}
	
	public void replaceCaseBy(CaseView c, CaseView newCase) { // newCase peut etre null, c'est autorisé.
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return;
		cases[coord[0]][coord[1]] = newCase;
		//newCase.mouseEntered(null); // Sinon le contour s'enleve meme si on a la souris dessus.
		this.remove(c);
		this.add(newCase, coord[0]*cases.length+coord[1]);
		this.revalidate();
		this.repaint();
	}
	
	public void replaceColonyByTown(ColonyView colony) {
		if(colony == null || colony.getPlayer() == null) // Il faut que le joueur associé à la colonie soit également non null.
			return;
		TownView town = new TownView(colony);
		replaceCaseBy(colony, town);
		town.repaint();
	}
	
	public Player getActualPlayer() {
		return actualPlayer;
	}
	
}
