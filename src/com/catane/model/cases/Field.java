package com.catane.model.cases;

import java.awt.Color;

import com.catane.model.Board;

public class Field extends ResourceCase {

	public Field(Board board, int number) {
		super(board, number, Color.YELLOW);
	}

	@Override
	public void giveResources() {
		
	}

}
