package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cases.Port;
import com.catane.view.gui.cases.PortView;

public class GameView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private JLabel dicesLbl;
	private PlayersDataView playersDataView;
	private ResourcePanel resourcePan;
	private IconPanel dices;
	private JButton nextTurnButton, tradeButton;
	private ActionPanel actionPanel;
	
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
		northPan.setLayout(new BorderLayout());
		dicesLbl = new JLabel();
		playersDataView = new PlayersDataView(game);
		northPan.add(dicesLbl, BorderLayout.WEST);
		northPan.add(playersDataView, BorderLayout.CENTER);
		
		BoardView boardView = new BoardView(this);
		resourcePan = new ResourcePanel(game);
		actionPanel = new ActionPanel();
		nextTurnButton = new JButton("Prochain tour");
		tradeButton = new JButton("Echange (4:1)");
		
		nextTurnButton.addActionListener(e -> {
			game.nextRound();
			refreshInfos();
			refreshTradeButton(true);
			dices.setEnabled(true);
		});
		
		tradeButton.addActionListener(e -> {
			Port port = new Port(4);
			PortView portView = new PortView(boardView, port);
			// Ouvre une fenetre d'echange 4:1
			PortView.TradeFrame tradeFrame = (portView.new TradeFrame(true));
			tradeFrame.setVisible(true);
		});
		
		dices = new IconPanel("dices_64", 32);

		dices.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!dices.isEnabled())
					return;
				super.mousePressed(e);
				int[] vals = game.rollDices();
				int value = vals[0]+vals[1];
				dicesLbl.setText("Dés : "+value);
				actionPanel.setButtonsEnabled(false);
				dices.setEnabled(false);
				revalidate();
				repaint();
			}
		});
		
		JPanel buttonsPan = new JPanel();
		buttonsPan.setOpaque(false);
		buttonsPan.add(dices);
		buttonsPan.add(nextTurnButton);
		buttonsPan.add(tradeButton);
		
		JPanel southPan = new JPanel();
		southPan.setOpaque(false);
		southPan.add(resourcePan);
		southPan.add(actionPanel);
		southPan.add(buttonsPan);
		
		JPanel boardContainer = new JPanel();
		boardContainer.setLayout(new GridBagLayout());
		boardContainer.add(boardView);
		
		//board.setPreferredSize(new Dimension(w, h));
		
		boardContainer.setBackground(new Color(0, 180, 216)); // Couleur de la mer
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(northPan, BorderLayout.NORTH);
		this.add(boardView, BorderLayout.CENTER);
		this.add(southPan, BorderLayout.SOUTH);
	}
	
	private class ActionPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private ButtonGroup group;
		private JRadioButton colony, town, road;
		
		public ActionPanel() {
			setBorder(new EmptyBorder(10, 10, 10, 10));
			//setBackground(Color.CYAN);
			
			colony = new JRadioButton("Colonie");
			town = new JRadioButton("Ville");
			road = new JRadioButton("Route");
			
			group = new ButtonGroup();
			group.add(colony);
			group.add(town);
			group.add(road);
			
			add(colony);
			add(town);
			add(road);
			
			setButtonsEnabled(false);
		}
	
		public void setButtonsEnabled(boolean enable) {
			colony.setEnabled(enable);
			town.setEnabled(enable);
			road.setEnabled(enable);
		}
		
	}
	
	public void refreshInfos() {
		playersDataView.refresh();
		resourcePan.refresh();
	}
	
	public void refreshTradeButton(boolean enabled) {
		tradeButton.setEnabled(enabled);
		revalidate();
		repaint();
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
