package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.Board;

public class Hill extends ResourceCase {
	private static final long serialVersionUID = 1L;

	public Hill(Board board, int number) {
		super(board, number, new Color(8, 255, 0));
	}

	@Override
	public void giveResources() {
		
	}

}
