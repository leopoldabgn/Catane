package com.catane.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
		
		public PlayerDataPanel(Player player) {
			this.player = player;
			name = new JLabel(player.toString());
			score = new JLabel("Score : "+player.getScore());
			
			JPanel pan = new JPanel();
			pan.setOpaque(false);
			
			pan.add(new Square(player.getColor(), 13, 13));
			pan.add(name);
			pan.add(score);
			
			this.add(pan);
			
			pan = new JPanel();
			pan.setOpaque(false);
			pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
			pan.add(longestRoad);
			pan.add(mostPowerfulArmy);
			
			this.add(pan);
			
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
			
			score.setText("Score : "+player.getScore());
			
			revalidate();
			repaint();
		}
		
	}
	
	private class Square extends JPanel {
		
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
