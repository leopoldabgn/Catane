package com.catane.model;

import java.awt.Color;
import java.util.Random;

import com.catane.model.cases.Case;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Desert;
import com.catane.model.cases.Field;
import com.catane.model.cases.Forest;
import com.catane.model.cases.Hill;
import com.catane.model.cases.Mountain;
import com.catane.model.cases.Pre;
import com.catane.model.cases.ResourceCase;
import com.catane.model.cases.Road;
import com.catane.model.cases.Town;

public class Board {
		
	private int size;
	private Case[][] cases;
	private int[] thief;
	private Player actualPlayer;
	
	public Board(int size) {
		this.size = size * 2 + 1;
		cases = generateAndAddCases();
		cases = mixCases(cases);
		this.actualPlayer = new Player(Color.BLUE);
	}	
	
	public void display() {
		int space = 4, maxSize = 10;
		char letter = 'A';
		System.out.print(" ".repeat(space));
		for(int i=0;i<size;i++)
			System.out.print((letter++)+" ".repeat(7));
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
				Case c = cases[j][i];
		 		if(c instanceof ResourceCase) {
		 			System.out.print(c);
		 		}
		 		else {
		 			space = maxSize-2;
		 			if(i%2 == 0)
		 				System.out.print(" "+c+" ");
		 			else {
		 				System.out.print(" ".repeat(space/2)+c+" ".repeat(space/2));
		 			}
		 		}
		 		System.out.print("|");
			 }
			
			System.out.println(" "+(j+1));
			System.out.print("  "+"-".repeat(line));
			System.out.println();
		 }
		
	}

	private Case[][] generateAndAddCases() {
		Case[][] cases = new Case[size][size];
		int x = 0;
			
		// Ajout des cases de ressources
		for (int i = 1; i < size; i += 2){
			for (int j = 1; j < size; j += 2) {
				cases[i][j] = createCase(x);
				if(cases[i][j] instanceof Desert) {
					((ResourceCase)cases[i][j]).setThief(true);
					thief = new int[] {i, j}; // on retient les coordonnées du voleur.
				}
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
		case 0: return new Forest(6);
		case 1: return new Pre(10);
		case 2: return new Field(11);
		case 3: return new Pre(8);
		case 4: return new Field(4);
		case 5: return new Hill(9);
		case 6: return new Forest(5);
		case 7: return new Mountain(12);
		case 8: return new Mountain(3);
		case 9: return new Desert();
		case 10: return new Field(10);
		case 11: return new Hill(6);
		case 12: return new Hill(9);
		case 13: return new Mountain(8);
		case 14: return new Pre(5);
		case 15: return new Forest(2);
		case 16: return new Forest(5);
		case 17: return new Pre(5);
		case 18: return new Mountain(5);
		case 19: return new Field(6);
		case 20: return new Forest(5);
		case 21: return new Hill(5);
		case 22: return new Field(5);
		case 23: return new Mountain(5);
		case 24: return new Hill(5);
		case 25: return new Pre(5);
		case 26: return new Field(5);
		case 27: return new Mountain(5);
		case 28: return new Forest(5);
		case 29: return new Pre(5);
		case 30: return new Hill(5);
		case 31: return new Mountain(5);
		case 32: return new Field(5);
		case 33: return new Hill(5);
		case 34: return new Forest(5);
		case 35: return new Pre(5);
		}
		return null;
	}
	
	private Case createRoad(boolean up) {
		return new Road(null, up);
	}
		
	private Case createColony() {
		return new Colony(null);
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
		//newCase.mouseEntered(null); // Sinon le contour s'enleve meme si on a la souris dessus.
	}
	
	public void replaceColonyByTown(Colony colony) {
		if(colony == null || colony.getPlayer() == null) // Il faut que le joueur associé à la colonie soit également non null.
			return;
		Town town = new Town(colony);
		replaceCaseBy(colony, town);
	}
	
	public Player getActualPlayer() {
		return actualPlayer;
	}
	
}

