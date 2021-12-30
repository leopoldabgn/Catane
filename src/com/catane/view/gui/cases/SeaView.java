package com.catane.view.gui.cases;

import java.awt.Color;

import com.catane.model.cases.Sea;
import com.catane.view.gui.BoardView;

public class SeaView extends CaseView {
	private static final long serialVersionUID = 1L;
	
	private Sea sea;
	
	public SeaView(BoardView board, Sea sea) {
		super(board);
		this.sea = sea;
		isSelectable = false;
		setBackground(new Color(66, 188, 245));
	}
	
}
