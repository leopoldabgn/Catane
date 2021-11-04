package com.catane.view;

import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JPanel;

import com.catane.view.cases.Case;
import com.catane.view.cases.Champs;
import com.catane.view.cases.Colline;
import com.catane.view.cases.Desert;
import com.catane.view.cases.Foret;
import com.catane.view.cases.Montagne;
import com.catane.view.cases.Pre;

public class Plateau extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int size;
	private Case[][] cases;
	
	public Plateau(int size) {
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
		System.out.println(rand);
		switch(rand) {
		case 0:
			return new Desert(i);
		case 1:
			return new Champs(i);
		case 2:
			return new Colline(i);
		case 3:
			return new Foret(i);
		case 4:
			return new Montagne(i);
		case 5:
			return new Pre(i);
		}
		return null;
	}
	
}
