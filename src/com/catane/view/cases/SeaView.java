package com.catane.view.cases;

import com.catane.model.cases.Sea;
import com.catane.view.BoardView;

public class SeaView extends CaseView {
	private static final long serialVersionUID = 1L;
	
	private Sea sea;
	
	public SeaView(BoardView board, Sea sea) {
		super(board);
		this.sea = sea;
		setOpaque(false);
		isSelectable = false;
	}
	
}
