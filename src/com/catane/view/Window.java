package com.catane.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import com.catane.model.Game;
import com.catane.model.Resource;

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
		
		this.setLayout(new BorderLayout());
		
		TextManager textManager = new TextManager("Welcome to Catane");
		BoardView board = new BoardView(game);
		ResourcePanel resourcePan = new ResourcePanel(game.getActualPlayer());
		board.addComponentListener(new ComponentAdapter(){

            @Override
            public void componentResized(ComponentEvent e) {
            	int width = board.getWidth();
            	int height = board.getHeight();
            	if(width == height)
            		return;
            	System.out.println(board.getPreferredSize().getWidth()+" "+board.getPreferredSize().getHeight());
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
		this.getContentPane().add(board, BorderLayout.CENTER);
		this.getContentPane().add(resourcePan, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
}
