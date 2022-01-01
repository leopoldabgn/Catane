package com.catane.view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.catane.model.Resource;
import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;
import com.catane.model.cards.VictoryPoints;

public class CardView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 66, HEIGHT = 100;
	
	private boolean isSelected = false;
	private Image image;
	
	public CardView(String name) {
		try {
			this.image = ImageIO.read(new File("res/cards/"+name+".png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(name);
		}
		setSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
	
	public void setSelected(boolean selected) {
		this.isSelected = selected;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public static class DevCardView extends CardView {
		private static final long serialVersionUID = 1L;
		
		private DevelopmentCard devCard;
		
		public DevCardView(DevelopmentCard devCard, GameView gameView) {
			super(devCard.getEnglishName());
			this.devCard = devCard;
			setBorder(null);
			if(!(devCard instanceof VictoryPoints)) {
				addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						super.mousePressed(e);
						if (!gameView.isDev() && devCard.isUsable()) {
							int ans = JOptionPane.showOptionDialog(null,
										"Voulez-vous utiliser votre carte "+devCard.toString().toLowerCase()+" ?",
										devCard.toString(),
										JOptionPane.YES_NO_OPTION, // même en appuyant sur non ca ouvre la fenêtre
										JOptionPane.QUESTION_MESSAGE,
										null,
										new String[] {"Oui", "Non"}, "Oui");
							if(devCard instanceof Knight) {
								gameView.getGame().refreshMostPowerfulArmyOwner();
								// thief
								gameView.refreshInfos();
							}
							else if(devCard instanceof Progress) {
								if(devCard == Progress.ROAD_CONSTRUCTION) {
									gameView.isBeforeDices(gameView.getDicesEnabled());
									gameView.constructRoad();
								}
								else if(devCard == Progress.MONOPOLY) {
									new MonopolyFrame(gameView);
								}
								else if(devCard == Progress.INVENTION) {
									new InventionFrame(gameView);
								}
							}
						}
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						super.mouseEntered(e);
						repaint();
					}
					
				});
			}
		}
		
		public DevelopmentCard getDevCard() {
			return devCard;
		}

		public class InventionFrame extends JFrame {
			private static final long serialVersionUID = 1L;

			private ResourceChoicePanel invention1;
			private ResourceChoicePanel invention2;
			private JLabel error;

			public InventionFrame(GameView gameView) {
				setTitle("Invention");
				setSize(450, 370);
				setLocationRelativeTo(null);

				JButton save = new JButton("Confirmer");
				JButton cancel = new JButton("Annuler");

				cancel.addActionListener(event -> {
					this.dispose();
				});

				save.addActionListener(event -> {
					if (invention1.getSelected() == null || invention2.getSelected() == null) {
						if(error == null) {
							error = new JLabel("Merci de sélectionner les ressources !");
							error.setForeground(Color.RED);
							getContentPane().add(error, 0);
							revalidate();
							repaint();
						}
					return;
					}
					Resource r1 = ((ResourceCardView) invention1.getSelected()).getResource();
					Resource r2 = ((ResourceCardView) invention2.getSelected()).getResource();
					gameView.getGame().invention(r1, r2);
					gameView.refreshInfos();

					this.dispose();
				});

				JLabel label1 = new JLabel("Choisissez la première ressource");
				JLabel label2 = new JLabel("Choisissez la deuxième ressource");
				JPanel panel = new JPanel();
				setContentPane(panel);
				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
				panel.add(label1);
				invention1 = new ResourceChoicePanel();
				panel.add(invention1);
				panel.add(label2);
				invention2 = new ResourceChoicePanel();
				panel.add(invention2);

				JPanel buttons = new JPanel();
				buttons.add(cancel);
				buttons.add(save);

				panel.add(buttons);

				setVisible(true);
			}
		}

		public class MonopolyFrame extends JFrame {
			private static final long serialVersionUID = 1L;

			private ResourceChoicePanel monopoly;
			private JLabel error;

			public MonopolyFrame(GameView gameView) {
				setTitle("Monopole");
				setSize(450, 270);
				setLocationRelativeTo(null);

				JButton save = new JButton("Confirmer");
				JButton cancel = new JButton("Annuler");

				cancel.addActionListener(event -> {
					this.dispose();
				});

				save.addActionListener(event -> {
					if (monopoly.getSelected() == null) {
						if(error == null) {
							error = new JLabel("Merci de selectionner une ressource !");
							error.setForeground(Color.RED);
							getContentPane().add(error, 0);
							revalidate();
							repaint();
						}
					return;
					}
					Resource r = ((ResourceCardView) monopoly.getSelected()).getResource();
					gameView.getGame().monopoly(r);
					gameView.refreshInfos();

					this.dispose();
				});

				JLabel label = new JLabel("Choisissez une ressource");
				JPanel panel = new JPanel();
				setContentPane(panel);
				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
				panel.add(label);
				monopoly = new ResourceChoicePanel();
				panel.add(monopoly);

				JPanel buttons = new JPanel();
				buttons.add(cancel);
				buttons.add(save);

				panel.add(buttons);

				setVisible(true);
			}

		}
		
	}
	
	public static class ResourceCardView extends CardView {
		private static final long serialVersionUID = 1L;
		
		private Resource res;
		
		public ResourceCardView(Resource res) {
			super(res.getEnglishName().toLowerCase());
			this.res = res;
		}
		
		public Resource getResource() {
			return res;
		}
		
	}
	
}
