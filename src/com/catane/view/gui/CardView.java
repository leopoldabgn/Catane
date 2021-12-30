package com.catane.view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.catane.model.Resource;
import com.catane.model.cards.DevelopmentCard;
import com.catane.model.cards.Knight;
import com.catane.model.cards.Progress;

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
		
		public DevCardView(DevelopmentCard devCard) {
			super(devCard.getEnglishName());
			this.devCard = devCard;
			setBorder(null);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);
					int ans = JOptionPane.showOptionDialog(null,
								 "Voulez-vous utiliser votre carte "+devCard.toString().toLowerCase()+" ?",
					             devCard.toString(),
					             JOptionPane.YES_NO_OPTION,
					             JOptionPane.QUESTION_MESSAGE,
					             null,
					             new String[] {"Oui", "Non"}, "Oui");
					if(devCard instanceof Knight) {

					}
					else if(devCard instanceof Progress) {
						if(devCard == Progress.ROAD_CONSTRUCTION) {
							
						}
						else if(devCard == Progress.MONOPOLY) {
							
						}
						else if(devCard == Progress.INVENTION) {
							
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
		
		public DevelopmentCard getDevCard() {
			return devCard;
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
