package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.Board;

public class Desert extends ResourceCase {
	private static final long serialVersionUID = 1L;

	public Desert(Board board) {
		super(board, -1, Color.WHITE);
		setThief(true);
	}
	
	@Override
	public void giveResources() {
		
	}

}
