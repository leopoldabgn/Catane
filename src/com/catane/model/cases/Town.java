package com.catane.model.cases;

import com.catane.model.Player;
import com.catane.model.Board;

public class Town extends Colony {

	public Town(Board board, Player player) {
		super(board, player);
		this.resourceGain = 2;
	}

	public Town(Colony colony) {
		this(colony.board, colony.player);
	}
	
}
