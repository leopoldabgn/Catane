package com.catane.view.gui;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
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
		
		private JLabel name;
		private JLabel score;
		
		public PlayerDataPanel(Player player) {
			this.player = player;
			name = new JLabel(player.toString());
			score = new JLabel("Score : "+player.getScore());
			add(name);
			add(score);
			refresh();
		}
		
		public void refresh() {
			if(game.getActualPlayer() == player)
				setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.PINK));
			else
				setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.BLACK));
			score.setText("Score : "+player.getScore());
			revalidate();
			repaint();
		}
		
	}
	
}
