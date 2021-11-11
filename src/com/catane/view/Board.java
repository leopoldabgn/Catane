package com.catane.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JPanel;

import com.catane.model.Player;
import com.catane.view.cases.Case;
import com.catane.view.cases.Colony;
import com.catane.view.cases.Desert;
import com.catane.view.cases.Mountain;
import com.catane.view.cases.Road;
import com.catane.view.cases.Town;

public class Board extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// Fonctions dés
	
	/*
	 * 
	 * Pour créer des routes vides au debut dans le terrain, utilise :
	 * new Road(this, sens). Et fait en sorte que le boolean sens change pour mettre
	 * les routes dans le bon sens.
	 * 
	 * Et pour créer des cases vides qui vont contenir des villes ou des colonies, utilise :
	 * new Colony(this).
	 * 
	 * 
	 */
	
	private int size;
	private Case[][] cases;
	
	public Board(int size) {
		this.size = size;
		setLayout(new GridLayout(size, size));
		cases = generateAndAddCases();
	}
	
	private Case[][] generateAndAddCases() {
		Case[][] cases = new Case[size][size];
		for(int j=0;j<size;j++)
		{
			for(int i=0;i<size;i++)
			{
				cases[j][i] = getRandomCase(j*size+i+1);
				this.add(cases[j][i]);
			}
		}
		
		return cases;
	}
	
	private Case getRandomCase(int i) {
		Random r = new Random();
		int rand = r.nextInt(6);
		switch(rand) {
		case 0:
			return new Desert(this);
		case 1:
			return new Road(this, new Player(Color.BLUE), true);
		case 2:
			return new Road(this, new Player(Color.RED), false);
		case 3:
			return new Colony(this, new Player(Color.RED));
		case 4:
			return new Mountain(this, i);
		case 5:
			return new Town(this, new Player(Color.GREEN));
		}
		return null;
	}
	
}
