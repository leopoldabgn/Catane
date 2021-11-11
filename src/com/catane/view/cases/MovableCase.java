package com.catane.view.cases;

import com.catane.model.Player;
import com.catane.view.Board;

public class MovableCase extends Case {
	private static final long serialVersionUID = 1L;
	
	protected Player player;
	
	public MovableCase(Board board) {
		this(board, null);
	}
	
	public MovableCase(Board board, Player player) {
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
