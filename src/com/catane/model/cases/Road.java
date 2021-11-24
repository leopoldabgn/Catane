package com.catane.model.cases;

import com.catane.model.Player;
import com.catane.model.Board;

public class Road extends MovableCase {
	
	private boolean vertical; // false -> horizontal, true -> vertical.
	
	public Road(Board board) {
		this(board, null);
	}
	
	public Road(Board board, boolean vertical) {
		this(board, null, vertical);
	}
	
	public Road(Board board, Player player) {
		this(board, player, false);
	}
	
	public Road(Board board, Player player, boolean vertical) {
		super(board, player);
		// setPreferredSize(new Dimension(100, 100)); // On le fait uniquement dans ce constructeur. Il est appele a coup sur.
		this.vertical = vertical;
	}
	
}
