package com.catane.model.cases;

import java.awt.Color;

import com.catane.model.Board;

public class Forest extends ResourceCase {

	public Forest(Board board, int number) {
		super(board, number, new Color(0, 125, 33)); // Dark green*
	}

	@Override
	public void giveResources() {
		
	}

	
}
