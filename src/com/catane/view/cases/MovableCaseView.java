package com.catane.view.cases;

import com.catane.model.Player;
import com.catane.view.BoardView;

public class MovableCaseView extends CaseView {
	private static final long serialVersionUID = 1L;
	
	protected Player player;
	
	public MovableCaseView(BoardView board) {
		this(board, null);
	}
	
	public MovableCaseView(BoardView board, Player player) {
		super(board);
		this.player = player;
		setOpaque(false);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public boolean isEmpty() {
		return player == null;
	}
	
}
