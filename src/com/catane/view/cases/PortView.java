package com.catane.view.cases;

import java.awt.Graphics;

import com.catane.model.cases.Port;
import com.catane.view.BoardView;

public class PortView extends CaseView {
	private static final long serialVersionUID = 1L;

	private Port port;
	
	public PortView(BoardView board, Port port) {
		super(board);
		this.port = port;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	
}
