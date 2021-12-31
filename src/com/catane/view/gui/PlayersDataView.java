package com.catane.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.catane.model.Game;
import com.catane.model.Player;

public class PlayersDataView extends JPanel {
	private static final long serialVersionUID = 1L;

	private Game game;
	private PlayerDataPanel[] dataPanels;
	
	public PlayersDataView(Game game) {
		this.game = game;
		List<Player> players = game.getPlayers();
		dataPanels = new PlayerDataPanel[players.size()];
		for(int i=0;i<players.size();i++) {
			dataPanels[i] = new PlayerDataPanel(players.get(i));
			add(dataPanels[i]);
		}
		refresh();
	}
	
	public void refresh() {
		for(PlayerDataPanel pan : dataPanels)
			pan.refresh();
	}
	
	private class PlayerDataPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private Player player;
		
		private JLabel name, score,
		longestRoad = new JLabel("Route la plus longue"),
		mostPowerfulArmy = new JLabel("Armée la plus puissante");
		
		private JPanel cardsPan;
		
		public PlayerDataPanel(Player player) {
			this.player = player;
			name = new JLabel(player.toString());
			score = new JLabel("Score : "+player.getScore());
			
			setBackground(Color.WHITE);
			
			JPanel pan = new JPanel();
			pan.setOpaque(false);
			
			pan.add(new Square(player.getColor(), 13, 13));
			pan.add(name);
			pan.add(score);
			
			this.add(pan);
			
			cardsPan = new JPanel();
			cardsPan.setVisible(false);
			cardsPan.setOpaque(false);
			cardsPan.setLayout(new BoxLayout(cardsPan, BoxLayout.PAGE_AXIS));
			cardsPan.add(longestRoad);
			cardsPan.add(mostPowerfulArmy);
			
			this.add(cardsPan);
			
			refresh();
		}
		
		public void refresh() {
			if(game.getActualPlayer() == player)
				setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.PINK));
			else
				setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.BLACK));
			
			if(game.longestRoadOwner() == player)
				longestRoad.setVisible(true);
			else
				longestRoad.setVisible(false);
			
			if(game.mostPowerfulArmyOwner() == player)
				mostPowerfulArmy.setVisible(true);
			else
				mostPowerfulArmy.setVisible(false);
			
			if(longestRoad.isVisible() || mostPowerfulArmy.isVisible()) {
				setLayout(new GridLayout(2, 1));
				cardsPan.setVisible(true);
			}
			else {
				setLayout(new FlowLayout());
				cardsPan.setVisible(false);
			}
			
			score.setText("Score : "+player.getScore());
			
			revalidate();
			repaint();
		}
		
	}
	
	private class Square extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private Color color;
		
		public Square(Color color, int w, int h) {
			this.color = color;
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setPreferredSize(new Dimension(w, h));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
	}
	
}
