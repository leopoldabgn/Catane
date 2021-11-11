package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.Board;

public class Mountain extends ResourceCase {
	private static final long serialVersionUID = 1L;

	public Mountain(Board board, int number) {
		super(board, number, Color.GRAY);
	}

	@Override
	public void giveResources() {
		
	}

}
