package com.catane.view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.catane.model.Resource;

public class CardView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private boolean isSelected = false;
	private Image image;
	
	public CardView(String name) {
		try {
			this.image = ImageIO.read(new File("res/cards/"+name+".png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(name);
		}
		setPreferredSize(new Dimension(66, 100));
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
	
	public static class ResourceCardView extends CardView {
		private static final long serialVersionUID = 1L;
		
		private Resource res;
		
		public ResourceCardView(Resource res) {
			super(res.getEnglishName().toLowerCase());
			this.res = res;
		}
		
	}
	
}
