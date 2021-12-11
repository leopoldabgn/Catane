package com.catane.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import com.catane.model.Board;
import com.catane.model.Game;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public Window(Game game, int w, int h) {
		super();
		this.setTitle("Catane");
		this.setSize(w, h);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setDefaultLookAndFeelDecorated(true);
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		this.setLayout(new GridBagLayout());
		
		BoardView p = new BoardView(game);
		p.setPreferredSize(new Dimension((int)(w*0.85), (int)(h*0.85)));
		
		this.getContentPane().add(p);
		
		this.setVisible(true);
	}
	
}
