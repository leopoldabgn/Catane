package com.catane.view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class IconPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Image icon;
	
	public IconPanel(String path, int size) {
		this.setPreferredSize(new Dimension(size, size));
		setOpaque(false);
		this.setLayout(new GridBagLayout());
		icon = (new ImageIcon(path)).getImage();
		path = path.charAt(0)+path.substring(1).toLowerCase();
		path = "res/icons/"+path+".png";
		
		try {
			icon = ImageIO.read(new File(path));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = Math.min(getWidth(), getHeight());
		if(icon != null)
			g.drawImage(icon, 0, 0, size, size, null);
	}
}