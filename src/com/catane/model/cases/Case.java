package com.catane.model.cases;
import com.catane.model.Board;

public abstract class Case {
	
	protected Board board;
	
	public Case(Board board) {
		this.board = board;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
