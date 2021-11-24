package com.catane.model.cases;

import java.awt.Color;

import com.catane.model.Board;

public class Hill extends ResourceCase {

	public Hill(Board board, int number) {
		super(board, number, new Color(8, 255, 0));
	}

	@Override
	public void giveResources() {
		
	}

}
