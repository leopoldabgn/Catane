package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.BoardView;

public class HillView extends ResourceCaseView {
	private static final long serialVersionUID = 1L;

	public HillView(BoardView board, int number) {
		super(board, number, new Color(8, 255, 0));
	}

	@Override
	public void giveResources() {
		
	}

}
