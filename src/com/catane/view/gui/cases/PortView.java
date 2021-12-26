package com.catane.view.gui.cases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.catane.model.Player;
import com.catane.model.Resource;
import com.catane.model.cases.Colony;
import com.catane.model.cases.Port;
import com.catane.view.gui.BoardView;
import com.catane.view.gui.CardView;
import com.catane.view.gui.IconPanel;

public class PortView extends CaseView {
	private static final long serialVersionUID = 1L;

	// Si on oublie de la fermer, on la ferme si l'utilisateur en ouvre une autre.
	private static TradeFrame lastFrame;
	
	private Port port;
	
	public PortView(BoardView board, Port port) {
		super(board);
		this.port = port;
		isSelectable = false;
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(new IconPanel("ship32", 32));
		JLabel lbl = new JLabel(port.getResourcesToGive()+":1");
		lbl.setFont(new Font("Verdana", Font.BOLD, 14));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		JPanel pan = new JPanel();
		pan.add(lbl);
		pan.setOpaque(false);
		Resource resource = port.getResourceType();
		if(resource != null)
			pan.add(new IconPanel(resource.getEnglishName().toLowerCase(), 16));
		this.add(pan);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		boolean canTrade = false;
		Player actualPlayer = board.getGame().getActualPlayer();

		List<Colony> colonies = board.getBoardModel().getColonies(port);
		for(Colony c : colonies) {
			if(c.getPlayer() == actualPlayer) {
				canTrade = true;
				break;
			}
		}
		
		if(canTrade) {
			if(lastFrame != null && lastFrame.isVisible())
				lastFrame.dispose();
			lastFrame = new TradeFrame();
			lastFrame.setVisible(true);
		}
		else {
			// VOus ne poouvez pas echanger avec ce port !
		}
	}
	
	private class TradeFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		
		public TradeFrame() {
			setTitle("Trade : ("+port+")");
			setSize(450, 370);
			setLocationRelativeTo(null);
			
			JButton cancel = new JButton("Annuler"),
					trade =  new JButton("Echanger");
			
			cancel.addActionListener(e -> {
				this.dispose();
			});
			
			trade.addActionListener(e -> {
				this.dispose();
				// SOMETHING ELSE...
			});
			
			String sentence1 = "Vous allez echanger "+port.getResourcesToGive();
			Resource type = port.getResourceType();

			JPanel container = new JPanel();
			setContentPane(container);
			container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
			
			if(type == null) {
				sentence1 += " fois la ressource suivante :";
				container.add(new JLabel(sentence1));
				container.add(new TradePanel());
				String sentence2 = "Veuillez choisir la ressource que vous voulez en échange :";
				container.add(new JLabel(sentence2));
				container.add(new TradePanel());
			}
			else {
				if(board.getActualPlayer().getResource(port.getResourceType()) < port.getResourcesToGive()) {
					setSize(270, 150);
					setLayout(new GridLayout(2, 1));
					sentence1 = "Vous n'avez pas assez de "+port.getResourceType()+" !";
					container.add(new JLabel(sentence1));
					trade.setVisible(false);
					cancel.setText("Ok");
				}
				else {
					setSize(450, 200);
					sentence1 += " "+port.getResourceType()
					.toString().toLowerCase()+" en echange de :";
					container.add(new JLabel(sentence1));
					container.add(new TradePanel());
				}
			}
			
			JPanel buttons = new JPanel();
			buttons.add(cancel);
			buttons.add(trade);
			
			container.add(buttons);
		}
		
		private class TradePanel extends JPanel {
			private static final long serialVersionUID = 1L;
			
			private CardView[] cards;
			private CardView selectedCard;
			
			public TradePanel() {
				Resource[] resources = Resource.values();
				this.cards = new CardView[resources.length];
				for(int i=0;i<cards.length;i++) {
					cards[i] = new CardView.ResourceCardView(resources[i]);
					cards[i].addMouseListener(getCardListener(cards[i]));
					add(cards[i]);
				}
			}
			
			public MouseListener getCardListener(CardView card) {
				return new MouseAdapter() {
					
					@Override
					public void mousePressed(MouseEvent e) {
						if(selectedCard == card)
							return;
						card.setBorder(BorderFactory.createCompoundBorder(
								BorderFactory.createMatteBorder(2,  2,  2,  2, Color.BLACK),
								BorderFactory.createMatteBorder(3,  3,  3,  3, Color.WHITE)));
						card.setSelected(true);
						if(selectedCard != null) {
							selectedCard.setSelected(false);
							selectedCard.setBorder(BorderFactory.createLoweredBevelBorder());
						}
						selectedCard = card;
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						if(!card.isSelected())
							card.setBorder(BorderFactory.createRaisedBevelBorder());
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						if(!card.isSelected())
							card.setBorder(BorderFactory.createLoweredBevelBorder());
					}
					
				};
			}
			
		}
		
	}
	
}
