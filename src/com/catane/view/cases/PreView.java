package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.BoardView;

public class PreView extends ResourceCaseView {
	private static final long serialVersionUID = 1L;

	public PreView(BoardView board, int number) {
		super(board, number, new Color(166, 255, 163)); // Light green
	}

	@Override
	public void giveResources() {
		
	}
	
}


