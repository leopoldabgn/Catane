package com.catane.view.cases;

import com.catane.model.Player;
import com.catane.model.cases.MovableCase;
import com.catane.view.BoardView;

public class MovableCaseView extends CaseView {
	private static final long serialVersionUID = 1L;
	
	private MovableCase modelCase;
	
	public MovableCaseView(BoardView board, MovableCase modelCase) {
		super(board);
		this.modelCase = modelCase;
		setOpaque(false);
	}
	
	public MovableCase getModelCase() {
		return modelCase;
	}
	
	public Player getPlayer() {
		return modelCase.getPlayer();
	}
	
	public void setPlayer(Player player) {
		this.modelCase.setPlayer(player);
	}
	
	public boolean isEmpty() {
		return getPlayer() == null;
	}
	
}
