package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cases.Port;
import com.catane.view.gui.cases.ColonyView;
import com.catane.view.gui.cases.PortView;
import com.catane.view.gui.cases.ResourceCaseView;
import com.catane.view.gui.cases.RoadView;

public class GameView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GUI gui;
	private Game game;
	
	private BoardView boardView;
	
	private JLabel dicesLbl;
	private PlayersDataView playersDataView;
	private ResourcePanel resourcePan;
	private IconPanel dices;
	private JButton nextTurnButton, tradeButton, buyDevCardButton;
	private ActionPanel actionPanel;
	private DeckView progressDeck, victoryPointsDeck;

	private int nbReady;
	private boolean early = true;

	public boolean isEarly() {
		return early;
	}
	public boolean isColonyActive() {
		return actionPanel.colony.isSelected();
	}
	public void setSelectedColony(boolean selected) {
		actionPanel.colony.setSelected(selected);
	}
	public boolean isTownActive() {
		return actionPanel.town.isSelected();
	}
	public void setSelectedTown(boolean selected) {
		actionPanel.town.setSelected(selected);
	}
	public boolean isRoadActive() {
		return actionPanel.road.isSelected();
	}
	public void setSelectedRoad(boolean selected) {
		actionPanel.road.setSelected(selected);;
	}
	public void clear() {
		actionPanel.group.clearSelection();
	}
	
	public GameView(GUI gui, Game game) {
		this.gui = gui;
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
		
		dices = new IconPanel("dices_64", 32);
		dices.setVisible(false);
		
		dices.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!dices.isEnabled())
					return;
				super.mousePressed(e);
				int[] vals = game.rollDices();
				int value = vals[0]+vals[1];
				dicesLbl.setText("Dés : "+value);
				game.getBoard().gainResource(value);
				refreshInfos();
				dices.setEnabled(false); // Le faire avant refreshOptions ! Attention.
				actionPanel.refreshOptions();
				nextTurnButton.setEnabled(true);
				
				// Le mettre apres le refreshOptions ! Important !
				if(value == 7) {
					boardView.changeSelectableCases(new ResourceCaseView(), null, false);
				}
				
				revalidate();
				repaint();
			}
		});
		
		//TextManager textManager = new TextManager("Welcome to Catane");
		JPanel northPan = new JPanel();
		northPan.setLayout(new BorderLayout());
		dicesLbl = new JLabel();
		JPanel tmp = new JPanel();
		tmp.setOpaque(false);
		playersDataView = new PlayersDataView(game);
		tmp.add(playersDataView);
		tmp.add(dices);
		northPan.add(dicesLbl, BorderLayout.WEST);
		northPan.add(tmp, BorderLayout.CENTER);
		
		boardView = new BoardView(this);
		resourcePan = new ResourcePanel(game);
		actionPanel = new ActionPanel();
		progressDeck = new DeckView(game.getActualPlayer(), false);
		victoryPointsDeck = new DeckView(game.getActualPlayer(), true);
		buyDevCardButton = new JButton("Acheter carte dev.");
		nextTurnButton = new JButton("Passer au prochain tour");
		nextTurnButton.setEnabled(false);
		tradeButton = new JButton("Echange (4:1)");
		
		buyDevCardButton.addActionListener(e -> {
			Player actualPlayer = game.getActualPlayer();
			if(actualPlayer.canBuyDevCard(game) == 0) {
				actualPlayer.getDevCard(game);
				refreshDecks();
				buyDevCardButton.setEnabled(false);
			}
		});
		
		nextTurnButton.addActionListener(e -> {
			game.nextRound();
			dicesLbl.setText("");
			refreshInfos();
			actionPanel.setButtonsEnabled(false);
			refreshTradeButton(true);
			dices.setEnabled(true);
			buyDevCardButton.setEnabled(game.getActualPlayer().canBuyDevCard(game) == 0);
			nextTurnButton.setEnabled(false);
		});
		
		tradeButton.addActionListener(e -> {
			Port port = new Port(4);
			PortView portView = new PortView(boardView, port);
			// Ouvre une fenetre d'echange 4:1
			PortView.TradeFrame tradeFrame = (portView.new TradeFrame(true));
			tradeFrame.setVisible(true);
		});
		
		JPanel buttonsPan = new JPanel();
		buttonsPan.setOpaque(false);
		buttonsPan.setLayout(new BoxLayout(buttonsPan, BoxLayout.PAGE_AXIS));
		buttonsPan.add(actionPanel);
		tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.add(tradeButton);
		tmp.add(buyDevCardButton);
		buttonsPan.add(tmp);
		
		JPanel southPan = new JPanel();
		southPan.setOpaque(false);
		southPan.add(resourcePan);
		southPan.add(progressDeck);
		southPan.add(victoryPointsDeck);
		
		JPanel eastPan = new JPanel();
		eastPan.setOpaque(false);
		eastPan.setLayout(new BorderLayout());
		tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.add(buttonsPan);
		eastPan.add(tmp, BorderLayout.CENTER);
		eastPan.add(nextTurnButton, BorderLayout.SOUTH);
		
		JPanel boardContainer = new JPanel();
		boardContainer.setLayout(new GridBagLayout());
		boardContainer.add(boardView);
		
		//board.setPreferredSize(new Dimension(w, h));
		
		boardContainer.setBackground(new Color(0, 180, 216)); // Couleur de la mer

		JButton nextTurnButtonEarly = new JButton("Prochain joueur");
		nextTurnButtonEarly.addActionListener(event -> {
			nbReady = refreshReady();
			if (game.getPlayers().size() == nbReady) {
				game.nextRound();
				actionPanel.refreshOptions();
				early = false;
				startGame(boardView, northPan, eastPan, southPan);
			}else {
				if (game.getActualPlayer().isReady()) {
					game.nextRound();
					setSelectedColony(true);
				}
			}
			refreshInfos();
			revalidate();
			repaint();
		});
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(northPan, BorderLayout.NORTH);
		this.add(boardView, BorderLayout.CENTER);
		
		// Tu mets ça en commentaire si tu veux continuer ton travail
		////////////////
		//this.add(eastPan, BorderLayout.EAST);
		//this.add(southPan, BorderLayout.SOUTH);
		////////////////
		
		this.add(nextTurnButtonEarly, BorderLayout.SOUTH);

		// Lancer earlyGame
		setSelectedColony(true);

	}

	public int refreshReady() {
		int r = 0;
		for (Player p : game.getPlayers())
			if (p.isReady())
				r++;
		return r;
	}
	
	public void startGame(JPanel boardView, JPanel northPan, JPanel eastPan, JPanel southPan) {
		this.removeAll();
		this.add(boardView, BorderLayout.CENTER);
		this.add(northPan, BorderLayout.NORTH);
		this.add(eastPan, BorderLayout.EAST);
		this.add(southPan, BorderLayout.SOUTH);
		dices.setVisible(true);
	}
	
	public class ActionPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private ButtonGroup group;
		private JRadioButton colony, town, road;
		
		public ActionPanel() {
			setOpaque(false);
			setBorder(new EmptyBorder(10, 10, 10, 10));
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			colony = new JRadioButton("Colonie");
			colony.setOpaque(false);
			town = new JRadioButton("Ville");
			town.setOpaque(false);
			road = new JRadioButton("Route");
			road.setOpaque(false);
			
			colony.addChangeListener(e -> {
				if(colony.isSelected())
					boardView.changeSelectableCases(new ColonyView(null, null), game.getActualPlayer(), true, early);
				else
					boardView.changeSelectableCases(null, null, true);
			});
			
			town.addChangeListener(e -> {
				if(town.isSelected())
					boardView.changeSelectableCases(new ColonyView(null, null), game.getActualPlayer(), false, early);
				else
					boardView.changeSelectableCases(null, null, true);
			});
			
			road.addChangeListener(e -> {
				if(road.isSelected())
					boardView.changeSelectableCases(new RoadView(null, null), game.getActualPlayer(), true, early);
				else
					boardView.changeSelectableCases(null, null, true);
			});
			
			group = new ButtonGroup();
			group.add(colony);
			group.add(town);
			group.add(road);
			
			JPanel buttons = new JPanel();
			buttons.setOpaque(false);
			
			buttons.add(colony);
			buttons.add(town);
			buttons.add(road);
			
			JLabel title = new JLabel("Faire une action :");
			
			JPanel titlePan = new JPanel();
			titlePan.setLayout(new BorderLayout());
			titlePan.add(title, BorderLayout.WEST);
			
			add(titlePan);
			add(buttons);
			
			setButtonsEnabled(false);
		}
	
		public void refreshOptions() {
			if(dices.isEnabled())
				return;
			Player player = game.getActualPlayer();
			if(player.canAffordColony())
				colony.setEnabled(true);
			if(player.canAffordTown())
				town.setEnabled(true);
			if(player.canAffordRoad())
				road.setEnabled(true);
		}
		
		public void setButtonsEnabled(boolean enable) {
			group.clearSelection();
			colony.setEnabled(enable);
			town.setEnabled(enable);
			road.setEnabled(enable);
			boardView.changeSelectableCases(null, null, false);
		}
		
	}
	
	public void refreshInfos() {
		playersDataView.refresh();
		resourcePan.refresh();
		refreshDecks();
	}
	
	public void refreshDecks() {
		progressDeck.changePlayer(game.getActualPlayer());
		progressDeck.refresh();
		victoryPointsDeck.changePlayer(game.getActualPlayer());
		victoryPointsDeck.refresh();
	}
	
	public void refreshTradeButton(boolean enabled) {
		tradeButton.setEnabled(enabled);
	}
	
	public void displayVictoryFrame() {
		int ans = JOptionPane.showOptionDialog(null,
				 game.getActualPlayer()+" a gagné !",
	             "Victoire de "+game.getActualPlayer(),
	             JOptionPane.YES_NO_OPTION,
	             JOptionPane.QUESTION_MESSAGE,
	             null,
	             new String[] {"Menu", "Quitter"}, "Menu");
		if(ans == 0)
			gui.setHomePage();
		else
			gui.close();
	}
	
	public ActionPanel getActionPanel() {
		return actionPanel;
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
