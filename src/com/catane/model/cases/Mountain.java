package com.catane.model.cases;

import java.awt.Color;

import com.catane.model.Board;

public class Mountain extends ResourceCase {

	public Mountain(Board board, int number) {
		super(board, number, Color.GRAY);
	}

	@Override
	public void giveResources() {
		
	}

}
