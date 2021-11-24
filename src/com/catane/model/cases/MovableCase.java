package com.catane.model.cases;

import com.catane.model.Player;
import com.catane.model.Board;

public class MovableCase extends Case {
	
	protected Player player;
	
	public MovableCase(Board board) {
		this(board, null);
	}
	
	public MovableCase(Board board, Player player) {
		super(board);
		this.player = player;
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
