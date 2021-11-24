package com.catane.model.cases;

import java.awt.Color;

import com.catane.model.Board;

public class Desert extends ResourceCase {

	public Desert(Board board) {
		super(board, -1, Color.WHITE);
		setThief(true);
	}
	
	@Override
	public void giveResources() {
		
	}

}
