package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.BoardView;

public class ForestView extends ResourceCaseView {
	private static final long serialVersionUID = 1L;

	public ForestView(BoardView board, int number) {
		super(board, number, new Color(0, 125, 33)); // Dark green*
	}

	@Override
	public void giveResources() {
		
	}

	
}
