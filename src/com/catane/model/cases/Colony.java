package com.catane.model.cases;

import com.catane.model.Player;
import com.catane.model.Board;

public class Colony extends MovableCase {

	protected int resourceGain = 1;
	
	public Colony(Board board) {
		super(board, null); // Utilisé uniquement pour les cases vides.
	}
	
	public Colony(Board board, Player player) {
		super(board, player);
		//setPreferredSize(new Dimension()); ?
	}
	
}
