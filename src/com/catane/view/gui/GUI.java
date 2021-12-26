package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.catane.model.Game;
import com.catane.model.Resource;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private PlayersDataView playersDataView;
	
	private JButton nextTurnButton;
	
	public GUI(Game game, int w, int h) {
		super();
		this.setTitle("Catane");
		this.setMinimumSize(new Dimension(w, h));
		this.setSize(new Dimension(w, h));
		
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		game.setupPlayers(4);
		for(int i=0;i<20;i++) {
			game.getActualPlayer().gainResource(Resource.WHEAT);
			game.getActualPlayer().gainResource(Resource.STONE);
			game.getActualPlayer().gainResource(Resource.WOOL);
		}
		
		this.setLayout(new BorderLayout());
		
		//TextManager textManager = new TextManager("Welcome to Catane");
		JPanel northPan = new JPanel();
		playersDataView = new PlayersDataView(game);
		northPan.add(playersDataView);
		
		BoardView board = new BoardView(game);
		ResourcePanel resourcePan = new ResourcePanel(game.getActualPlayer());
		
		nextTurnButton = new JButton("Next Turn");
		
		nextTurnButton.addActionListener(e -> {
			game.nextRound();
			playersDataView.refresh();
		});
		
		IconPanel dices;
		
		JPanel buttonsPan = new JPanel();
		buttonsPan.setOpaque(false);
		buttonsPan.add(nextTurnButton);
		
		JPanel southPan = new JPanel();
		southPan.setOpaque(false);
		southPan.add(resourcePan);
		southPan.add(buttonsPan);
		
		JPanel boardContainer = new JPanel();
		boardContainer.setLayout(new GridBagLayout());
		boardContainer.add(board);
		
		//board.setPreferredSize(new Dimension(w, h));
		
		((JComponent) this.getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		boardContainer.setBackground(new Color(0, 180, 216)); // Couleur de la mer
		this.getContentPane().add(northPan, BorderLayout.NORTH);
		this.getContentPane().add(board, BorderLayout.CENTER);
		this.getContentPane().add(southPan, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
}
