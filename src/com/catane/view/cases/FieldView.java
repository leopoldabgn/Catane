package com.catane.view.cases;

import java.awt.Color;

import com.catane.view.BoardView;

public class FieldView extends ResourceCaseView {
	private static final long serialVersionUID = 1L;

	public FieldView(BoardView board, int number) {
		super(board, number, Color.YELLOW);
	}

	@Override
	public void giveResources() {
		
	}

}
