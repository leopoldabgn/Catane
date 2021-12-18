package com.catane.view.cases;

import java.awt.Dimension;

import com.catane.model.cases.Port;
import com.catane.view.BoardView;
import com.catane.view.IconPanel;

public class PortView extends CaseView {
	private static final long serialVersionUID = 1L;

	private Port port;
	
	public PortView(BoardView board, Port port) {
		super(board);
		this.port = port;
		isSelectable = false;
		this.add(new IconPanel("ship32", 32));
	}

}
