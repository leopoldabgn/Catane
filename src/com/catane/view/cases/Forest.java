package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.Board;

public class Forest extends ResourceCase {
	private static final long serialVersionUID = 1L;

	public Forest(Board board, int number) {
		super(board, number, new Color(0, 125, 33)); // Dark green*
	}

	@Override
	public void giveResources() {
		
	}

	
}
