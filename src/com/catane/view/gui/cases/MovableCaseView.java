package com.catane.view.gui.cases;

import com.catane.model.Player;
import com.catane.model.cases.MovableCase;
import com.catane.view.gui.BoardView;

public class MovableCaseView extends CaseView {
	private static final long serialVersionUID = 1L;
	
	private MovableCase modelCase;
	
	public MovableCaseView(BoardView boardView, MovableCase modelCase) {
		super(boardView);
		this.modelCase = modelCase;
		setOpaque(false);
	}
	
	public MovableCase getModelCase() {
		return modelCase;
	}
	
	public Player getPlayer() {
		return modelCase.getPlayer();
	}
	
	public boolean isEmpty() {
		return getPlayer() == null;
	}

	public void reset() {}
	
}
