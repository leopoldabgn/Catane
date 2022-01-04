package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.catane.model.AI;
import com.catane.model.Game;
import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cards.Progress;
import com.catane.model.cases.Colony;
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
	private JPanel actions, actionsEarly;
	private JPanel knight;
	private HistoryView historyView;
	
	private int nbReady;
	private boolean early = true;
	private int constructRoad = 0;
	private boolean isBeforeDices;
	private boolean isDev = false;
	private boolean isThiefActive = false;

	public void endThief() {
		isThiefActive = false;
		setEnabledActions(true);
		dices.setEnabled(false);
		refreshInfos();
		refreshActions();
	}
	public boolean isDev() {
		return isDev;
	}
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

		// Programmation AI
		for (Player p : game.getPlayers())
			if (p instanceof AI)
				((AI) p).setGameView(this);
		
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
				dices.setEnabled(false); // Le faire avant refreshOptions ! Attention.
				actionPanel.refreshOptions();
				nextTurnButton.setEnabled(true);
				
				// Le mettre apres le refreshOptions ! Important !
				if(value == 7) {
					setEnabledActions(false);
					// discard();
					thiefAction();
				}

				refreshInfos();
				
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
		progressDeck = new DeckView(false, this);
		victoryPointsDeck = new DeckView(true, this);
		buyDevCardButton = new JButton("Acheter carte dev.");
		historyView = new HistoryView(game.getHistory());
		nextTurnButton = new JButton("Passer au prochain tour");
		nextTurnButton.setEnabled(false);
		tradeButton = new JButton("Echange (4:1)");
		
		buyDevCardButton.addActionListener(e -> {
			Player actualPlayer = game.getActualPlayer();
			if(actualPlayer.canBuyDevCard(game) == 0) {
				actualPlayer.getDevCard(game);
				buyDevCardButton.setEnabled(false);
				refreshInfos();
			}
		});
		
		nextTurnButton.addActionListener(e -> {
			nextTurn();
		});
		
		tradeButton.addActionListener(e -> {
			Port port = new Port(4);
			PortView portView = new PortView(boardView, port);
			// Ouvre une fenetre d'echange 4:1
			PortView.TradeFrame tradeFrame = (portView.new TradeFrame(true));
			tradeFrame.setVisible(true);
			refreshInfos();
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
		knight = new JPanel();
		refreshKnight();
		southPan.setOpaque(false);
		southPan.add(knight);
		southPan.add(resourcePan);
		southPan.add(progressDeck);
		southPan.add(victoryPointsDeck);
		
		JPanel eastPan = new JPanel();
		eastPan.setOpaque(false);
		eastPan.setLayout(new BorderLayout());
		tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.add(buttonsPan);
		eastPan.add(tmp, BorderLayout.NORTH);
		eastPan.add(nextTurnButton, BorderLayout.SOUTH);

		// panel actions possibles
		actions = new JPanel();
		JPanel border = new JPanel();
		border.setLayout(new BorderLayout());
		actions.setBorder(new EmptyBorder(10, 10, 10, 10));
		actions.setMinimumSize(new Dimension(200, 400));
		actions.setLayout(new GridLayout(0, 1, 5, 5));
		border.setBorder(BorderFactory.createEtchedBorder());
		border.add(actions, BorderLayout.NORTH);
		
		tmp = new JPanel();
		tmp.setBorder(new EmptyBorder(10, 10, 10, 10));
		tmp.setOpaque(false);
		tmp.setLayout(new GridLayout(2, 1));

		tmp.add(border);
		
		JScrollPane scroll = new JScrollPane(historyView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tmp.add(scroll);
		
		eastPan.add(tmp);
		refreshActions();
		
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
			} else {
				if (game.getActualPlayer().isReady()) {
					game.nextRound();
					setSelectedColony(true);
					if (game.getActualPlayer().isAI()) {
						((AI) game.getActualPlayer()).earlyGame(game);
						nextTurnButtonEarly.doClick();
					}
				}
			}
			boardView.reset();
			refreshInfos();
			refreshActionsEarly();
			revalidate();
			repaint();
		});

		// Instruction début de partie
		actionsEarly = new JPanel();
		actionsEarly.setLayout(new GridLayout(0, 1));
		actionsEarly.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(northPan, BorderLayout.NORTH);
		this.add(boardView, BorderLayout.CENTER);
		this.add(actionsEarly, BorderLayout.EAST);
		
		// Tu mets ça en commentaire si tu veux continuer ton travail (à enlever)
		////////////////
		// this.add(eastPan, BorderLayout.EAST);
		// this.add(southPan, BorderLayout.SOUTH);
		////////////////
		
		// Lancer earlyGame (à décommenter)
		// this.add(nextTurnButtonEarly, BorderLayout.SOUTH);
		// setSelectedColony(true);
		// refreshActionsEarly();

		
		new Thread() {
			public void run() {
				if (game.getActualPlayer().isAI()) {
					((AI) game.getActualPlayer()).earlyGame(game);
					nextTurnButtonEarly.doClick();
				}
			}
		}.start();
		
		// pour ne pas avoir à placer toutes les colonies/routes a chaque fois (à enlever)
		//startGame(boardView, northPan, eastPan, southPan);
		//early = false;
		
	}

	public void nextTurn() {
		game.getActualPlayer().refreshDevCards();
		game.nextRound();
		dicesLbl.setText("");
		actionPanel.setButtonsEnabled(false);
		refreshTradeButton(true);
		dices.setEnabled(true);
		buyDevCardButton.setEnabled(game.getActualPlayer().canBuyDevCard(game) == 0);
		nextTurnButton.setEnabled(false);
		refreshInfos();
		System.out.println("aaa");
		if (game.getActualPlayer().isAI()) {
			((AI) game.getActualPlayer()).midGame(game);
			System.out.println(game.getHistory());
			sleep(500);
			nextTurn();
		}
	}
	
	public void sleep(long s) {
		try {
			Thread.sleep(s);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setEnabledActions(boolean enabled) { // appel de isBeforeDices() avant
		dices.setEnabled(enabled);
		tradeButton.setEnabled(enabled);
		nextTurnButton.setEnabled(enabled);
		actionPanel.setButtonsEnabled(enabled);
		buyDevCardButton.setEnabled(enabled ? !game.getActualPlayer().hasDrawDev() : false);
	}

	public void discard() {
		List<Player> players = new ArrayList<Player>();
		for (Player p : game.getPlayers())
			if (p.getResources() > 7)
				players.add(p);
		if (!players.isEmpty())
			new DiscardFrame(players, 0, players.get(0).getResources()/2);
	}

	public void thiefAction() {
		// Déplacer le voleur
		isThiefActive = true;
		boardView.changeSelectableCases(new ResourceCaseView(), null, false);
		refreshInfos();
	}

	public void stealResource() { // Voler une ressource au hasard, appelé quand le voleur est déplacé
		int[] coord = game.getBoard().getThiefCoord();
		List<Colony> col = game.getBoard().getColonies(coord[0], coord[1]);
		List<Player> players = new ArrayList<Player>();
		for (Colony c : col)
			if (game.getActualPlayer() != c.getPlayer() && !players.contains(c.getPlayer()))
				players.add(c.getPlayer());
		if (players.isEmpty()) {
			endThief();
			return;
		}
		new PlayerFrame(players, this);
	}

	public void refreshKnight() {
		knight.removeAll();
		knight.add(new CardView("knight"));
		knight.add(new JLabel("x"+game.getActualPlayer().getArmy()));
		knight.revalidate();
		knight.repaint();
	}

	public void refreshActions() {
		actions.removeAll();
		String p = "Au tour de " + game.getActualPlayer().toString();
		actions.add(new JLabel(p));
		if (isThiefActive) {
			actions.add(new JLabel("Déplacez le voleur"));
		}else {
			if (isDev) {
				actions.add(new JLabel("Vous devez :"));
				int nb = 3 - constructRoad;
				String s = "- Poser " + nb + " route(s)";
				actions.add(new JLabel(s));
			}else {
				actions.add(new JLabel("Vous pouvez :"));
				if (buyDevCardButton.isEnabled())
					actions.add(new JLabel("- Acheter une carte de développement"));
				if (tradeButton.isEnabled())
					actions.add(new JLabel("- Echanger des ressources"));
				if (progressDeck.isUsable())
					actions.add(new JLabel("- Utiliser une carte progrès"));
				if (dices.isEnabled()) {
					actions.add(new JLabel("- Lancer les dés"));
				}else {
					if (game.getActualPlayer().canAffordColony())
						actions.add(new JLabel("- Construire une colonie"));
					if (game.getActualPlayer().canAffordTown() && game.getActualPlayer().getNbColonies() > 0)
						actions.add(new JLabel("- Construire une ville"));
					if (game.getActualPlayer().canAffordRoad())
						actions.add(new JLabel("- Construire une route"));
				}
				if (nextTurnButton.isEnabled())
					actions.add(new JLabel("- Passer le tour"));
			}
		}
		actions.revalidate();
		actions.repaint();
	}

	public void refreshActionsEarly() {
		actionsEarly.removeAll();
		Player p = game.getActualPlayer();
		if (p.isReady()) {
			actionsEarly.add(new JLabel("Passez au joueur suivant"));
		}else {
			String s = p.toString() + " doit poser ses premières constructions :";
			actionsEarly.add(new JLabel(s));
			if (isColonyActive()) {
				int nb = 2 - p.getNbColonies();
				s = "- Posez " + nb + " colonie(s)";
			}
			if (isRoadActive()) {
				int nb = 2 - p.getNbRoads();
				s = "- Posez " + nb + " route(s)";
			}
			actionsEarly.add(new JLabel(s));
		}
		actionsEarly.revalidate();
		actionsEarly.repaint();
	}

	public void constructRoad() {
		if (constructRoad < 2) {
			isDev = true;
			early = true;
			constructRoad++;
			setEnabledActions(false);
			setSelectedRoad(true);
		}else {
			early = false;
			constructRoad = 0;
			isDev = false;
			clear();
			tradeButton.setEnabled(true);
			if (!isBeforeDices) {
				nextTurnButton.setEnabled(true);
				actionPanel.setButtonsEnabled(true);
			}else {
				dices.setEnabled(true);
				actionPanel.setButtonsEnabled(false);
			}
			buyDevCardButton.setEnabled(!game.getActualPlayer().hasDrawDev());
			game.getActualPlayer().devCardUsed(Progress.ROAD_CONSTRUCTION);
		}
		refreshInfos();
	}

	public int getConstrucRoad() {
		return constructRoad;
	}

	public void isBeforeDices(boolean isBeforeDices) {
		this.isBeforeDices = isBeforeDices;
	}

	public boolean getDicesEnabled() {
		return dices.isEnabled();
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
		if (game.getActualPlayer().isAI()) {
			((AI) game.getActualPlayer()).midGame(game);
			nextTurnButton.setEnabled(true);
			nextTurnButton.doClick();
		}
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
		refreshActions();
		refreshKnight();
		historyView.refresh();
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
