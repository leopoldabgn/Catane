package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.Resource;

public class GameView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private PlayersDataView playersDataView;
	private ResourcePanel resourcePan;
	private IconPanel dices;
	private JButton nextTurnButton;
	
	public GameView(Game game) {
		this.game = game;
		int count = 0;
		for(Player p : game.getPlayers()) {
			if(++count == 3)
				continue;
			for(int i=0;i<20;i++) {
				p.gainResource(Resource.CLAY);
				p.gainResource(Resource.WHEAT);
				p.gainResource(Resource.STONE);
				p.gainResource(Resource.WOOL);
				p.gainResource(Resource.WOOD);
			}
		}
		
		//TextManager textManager = new TextManager("Welcome to Catane");
		JPanel northPan = new JPanel();
		playersDataView = new PlayersDataView(game);
		northPan.add(playersDataView);
		
		BoardView board = new BoardView(this);
		resourcePan = new ResourcePanel(game);
		
		nextTurnButton = new JButton("Next Turn");
		
		nextTurnButton.addActionListener(e -> {
			game.nextRound();
			playersDataView.refresh();
			resourcePan.refresh();
		});
		
		dices = new IconPanel("dices_64", 32);

		JPanel buttonsPan = new JPanel();
		buttonsPan.setOpaque(false);
		buttonsPan.add(dices);
		buttonsPan.add(nextTurnButton);
		
		JPanel southPan = new JPanel();
		southPan.setOpaque(false);
		southPan.add(resourcePan);
		southPan.add(buttonsPan);
		
		JPanel boardContainer = new JPanel();
		boardContainer.setLayout(new GridBagLayout());
		boardContainer.add(board);
		
		//board.setPreferredSize(new Dimension(w, h));
		
		boardContainer.setBackground(new Color(0, 180, 216)); // Couleur de la mer
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(northPan, BorderLayout.NORTH);
		this.add(board, BorderLayout.CENTER);
		this.add(southPan, BorderLayout.SOUTH);
	}
	
	private class ActionPanel extends JPanel {
		
		private JButton colony, town, road;
		
		public ActionPanel() {
			colony = new JButton("Colony");
			town = new JButton("Town");
			road = new JButton("Road");
		}
		
	}
	
	public void refreshInfos() {
		playersDataView.refresh();
		resourcePan.refresh();
	}
	
	public PlayersDataView getPlayersDataView() {
		return playersDataView;
	}

	public JButton getNextTurnButton() {
		return nextTurnButton;
	}

	public ResourcePanel getResourcePan() {
		return resourcePan;
	}
	
	public Game getGame() {
		return game;
	}
	
}
