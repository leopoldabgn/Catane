package com.catane.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JPanel;

import com.catane.model.Player;
import com.catane.view.cases.Case;
import com.catane.view.cases.Colony;
import com.catane.view.cases.Desert;
import com.catane.view.cases.Field;
import com.catane.view.cases.Forest;
import com.catane.view.cases.Hill;
import com.catane.view.cases.Mountain;
import com.catane.view.cases.Pre;
import com.catane.view.cases.Road;
import com.catane.view.cases.Town;

public class Board extends JPanel {
	private static final long serialVersionUID = 1L;
		
	private int size;
	private Case[][] cases;
	private Player actualPlayer;
	
	public Board(int size) {
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
		
	private Case[][] generateAndAddCases() {
		Case[][] cases = new Case[size][size];
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
		
	private Case createCase(int x) {
		switch (x) {
		case 0: return new Forest(this, 6);
		case 1: return new Pre(this, 10);
		case 2: return new Field(this, 11);
		case 3: return new Pre(this, 8);
		case 4: return new Field(this, 4);
		case 5: return new Hill(this, 9);
		case 6: return new Forest(this, 5);
		case 7: return new Mountain(this, 12);
		case 8: return new Mountain(this, 3);
		case 9: return new Desert(this);
		case 10: return new Field(this, 10);
		case 11: return new Hill(this, 6);
		case 12: return new Hill(this, 9);
		case 13: return new Mountain(this, 8);
		case 14: return new Pre(this, 5);
		case 15: return new Forest(this, 2);
		case 16: return new Forest(this, 5);
		case 17: return new Pre(this, 5);
		case 18: return new Mountain(this, 5);
		case 19: return new Field(this, 6);
		case 20: return new Forest(this, 5);
		case 21: return new Hill(this, 5);
		case 22: return new Field(this, 5);
		case 23: return new Mountain(this, 5);
		case 24: return new Hill(this, 5);
		case 25: return new Pre(this, 5);
		case 26: return new Field(this, 5);
		case 27: return new Mountain(this, 5);
		case 28: return new Forest(this, 5);
		case 29: return new Pre(this, 5);
		case 30: return new Hill(this, 5);
		case 31: return new Mountain(this, 5);
		case 32: return new Field(this, 5);
		case 33: return new Hill(this, 5);
		case 34: return new Forest(this, 5);
		case 35: return new Pre(this, 5);
		}
		return null;
	}
	
	private Case createRoad(boolean up) {
		return new Road(this, null, up);
	}
		
	private Case createColony() {
		return new Colony(this, null);
	}
	
	private Case[][] mixCases(Case[][] cases){
		Random r = new Random();
		int i1 = 0;
		int i2 = 0;
		int j1 = 0;
		int j2 = 0;
		Case tmp = null;
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
	
	public int[] getIndexesOf(Case c) {
		if(c == null)
			return null;
		
		for(int j=0;j<cases.length;j++)
			for(int i=0;i<cases[0].length;i++)
				if(cases[j][i] == c)
					return new int[] {j, i};
		
		return null;
	}
	
	public void replaceCaseBy(Case c, Case newCase) { // newCase peut etre null, c'est autorisé.
		int[] coord = getIndexesOf(c);
		if(coord == null)
			return;
		cases[coord[0]][coord[1]] = newCase;
		newCase.mouseEntered(null); // Sinon le contour s'enleve meme si on a la souris dessus.
		this.remove(c);
		this.add(newCase, coord[0]*cases.length+coord[1]);
		this.revalidate();
		this.repaint();
	}
	
	public void replaceColonyByTown(Colony colony) {
		if(colony == null || colony.getPlayer() == null) // Il faut que le joueur associé à la colonie soit également non null.
			return;
		Town town = new Town(colony);
		replaceCaseBy(colony, town);
		town.repaint();
	}
	
	public Player getActualPlayer() {
		return actualPlayer;
	}
	
}

