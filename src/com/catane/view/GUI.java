package com.catane.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.catane.model.Game;
import com.catane.model.Resource;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public GUI(Game game, int w, int h) {
		super();
		this.setTitle("Catane");
		this.setSize(w, h);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setDefaultLookAndFeelDecorated(true);
		//this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		game.setupPlayers(4);
		for(int i=0;i<20;i++) {
			game.getActualPlayer().gainResource(Resource.WHEAT);
			game.getActualPlayer().gainResource(Resource.STONE);
			game.getActualPlayer().gainResource(Resource.WOOL);
		}
		
		this.setLayout(new BorderLayout());
		
		TextManager textManager = new TextManager("Welcome to Catane");
		BoardView board = new BoardView(game);
		ResourcePanel resourcePan = new ResourcePanel(game.getActualPlayer());
		JPanel boardContainer = new JPanel();
		boardContainer.setOpaque(false);
		boardContainer.setLayout(new GridBagLayout());
		board.setPreferredSize(new Dimension(boardContainer.getWidth(), boardContainer.getHeight()));
		boardContainer.add(board);
		boardContainer.addComponentListener(new ComponentAdapter(){

            @Override
            public void componentResized(ComponentEvent e) {
            	int width = boardContainer.getWidth();
            	int height = boardContainer.getHeight();

            	if(width > height) {
            		board.setPreferredSize(new Dimension(height, height));
            	}
            	else {
            		board.setPreferredSize(new Dimension(width, width));
            	}

            	board.revalidate();
            	board.repaint();
            	
            	game.getActualPlayer().pay(Resource.WHEAT);
            	resourcePan.refresh();
            }
        });
		//board.setPreferredSize(new Dimension(w, h));
		
		((JComponent) this.getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().setBackground(new Color(66, 212, 245)); // Couleur de la mer
		this.getContentPane().add(textManager, BorderLayout.NORTH);
		this.getContentPane().add(boardContainer, BorderLayout.CENTER);
		this.getContentPane().add(resourcePan, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
}
