package com.catane.view.gui.cases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.List;

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
import com.catane.view.gui.IconPanel;
import com.catane.view.gui.ResourceChoicePanel;
import com.catane.view.gui.CardView.ResourceCardView;;

public class PortView extends CaseView {
	private static final long serialVersionUID = 1L;

	// Si on oublie de la fermer, on la ferme si l'utilisateur en ouvre une autre.
	private static TradeFrame lastFrame;
	
	private Port port;
	
	public PortView(BoardView boardView, Port port) {
		super(boardView);
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
		Player actualPlayer = boardView.getGame().getActualPlayer();

		List<Colony> colonies = boardView.getBoardModel().getColonies(port);
		for(Colony c : colonies) {
			if(c.getPlayer() == actualPlayer) {
				canTrade = true;
				break;
			}
		}
		
		if(lastFrame != null && lastFrame.isVisible())
			lastFrame.dispose();
		lastFrame = new TradeFrame(canTrade);
		lastFrame.setVisible(true);
	}
	
	public class TradeFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		
		private ResourceChoicePanel trade1, trade2;
		private JLabel error;
		
		public TradeFrame(boolean canTrade) {
			setTitle("Trade : ("+port+")");
			setSize(450, 370);
			setLocationRelativeTo(null);
			
			JButton cancel = new JButton("Annuler"),
					trade =  new JButton("Echanger");
			
			cancel.addActionListener(e -> {
				this.dispose();
			});
			
			trade.addActionListener(e -> {
				if(trade1 != null && trade1.getSelected() == null ||
				   trade2 != null && trade2.getSelected() == null) {
					// On affiche le message d'erreur que une seule fois.
					// La deuxieme fois on fait rien.
					if(error == null) {
						error = new JLabel("Merci de sélectionner les ressources !");
						error.setForeground(Color.RED);
						getContentPane().add(error, 0);
						revalidate();
						repaint();
					}
				return;
				}
				Resource r1, r2;
				if(trade2 == null) {
					r1 = ((ResourceCardView)trade1.getSelected()).getResource();
					boardView.getActualPlayer().trade(port.getResourceType(),
							port.getResourcesToGive(), r1);
				}
				else {
					r1 = ((ResourceCardView)trade1.getSelected()).getResource();
					r2 = ((ResourceCardView)trade2.getSelected()).getResource();
					boardView.getActualPlayer().trade(r1, port.getResourcesToGive(), r2);
				}
				
				boardView.getGameView().refreshTradeButton(false);
				boardView.getGameView().refreshInfos();
				
				this.dispose();
			});
			
			String sentence1 = "Vous allez échanger "+port.getResourcesToGive();
			Resource type = port.getResourceType();

			JPanel container = new JPanel();
			setContentPane(container);
			container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
			if(canTrade) {
				if(type == null) {
					List<Resource> resources = boardView.getActualPlayer().getResourcesByNb(port.getResourcesToGive());
					if(resources.isEmpty()) {
						setSize(400, 150);
						setLayout(new GridLayout(2, 1));
						sentence1 = "Vous n'avez aucune ressource en "+port.getResourcesToGive()+" exemplaires !";
						container.add(new JLabel(sentence1));
						trade.setVisible(false);
						cancel.setText("Ok");
					}
					else {
						sentence1 += " fois la ressource suivante :";
						container.add(new JLabel(sentence1));
						trade1 = new ResourceChoicePanel(resources);
						container.add(trade1);
						String sentence2 = "Veuillez choisir la ressource que vous voulez en échange :";
						container.add(new JLabel(sentence2));
						trade2 = new ResourceChoicePanel();
						container.add(trade2);
					}
				}
				else {
					if(boardView.getActualPlayer().getResource(port.getResourceType()) < port.getResourcesToGive()) {
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
						trade1 = new ResourceChoicePanel();
						container.add(trade1);
					}
				}
			}
			else {
				setSize(270, 150);
				setLayout(new GridLayout(2, 1));
				sentence1 = "Vous ne pouvez pas échanger avec ce port !";
				container.add(new JLabel(sentence1));
				trade.setVisible(false);
				cancel.setText("Ok");
			}
			
			JPanel buttons = new JPanel();
			buttons.add(cancel);
			buttons.add(trade);
			
			container.add(buttons);
		}
		
	}
	
}
