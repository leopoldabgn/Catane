package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.Board;

public class Field extends ResourceCase {
	private static final long serialVersionUID = 1L;

	public Field(Board board, int number) {
		super(board, number, Color.YELLOW);
	}

	@Override
	public void giveResources() {
		
	}

}
