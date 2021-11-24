package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.BoardView;

public class DesertView extends ResourceCaseView {
	private static final long serialVersionUID = 1L;

	public DesertView(BoardView board) {
		super(board, -1, Color.WHITE);
		setThief(true);
	}
	
	@Override
	public void giveResources() {
		
	}

}
