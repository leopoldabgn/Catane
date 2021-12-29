package com.catane.view.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class IconPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage icon, defaultIcon, grayIcon;
	
	public IconPanel(String path, int size) {
		this.setPreferredSize(new Dimension(size, size));
		setOpaque(false);
		this.setLayout(new GridBagLayout());
		String name = path;
		path = convertPath(path);
		
		try {
			icon = ImageIO.read(new File(path));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		defaultIcon = icon;
		try {
			path = convertPath("gray_"+name);
			grayIcon = ImageIO.read(new File(path));
		}
		catch(IOException e) {
			// On rentre dans le catch pour blé, argile, etc..
			// le grayIcon sera lu uniquement pour le
			// dices_32 et dices_64
		}
	}
	
	public String convertPath(String path) {
		path = path.charAt(0)+path.substring(1).toLowerCase();
		path = "res/icons/"+path+".png";
		return path;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if(enabled)
			icon = defaultIcon;
		else if(grayIcon != null)
			icon = grayIcon;
		
		revalidate();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = Math.min(getWidth(), getHeight());
		if(icon != null) {
			g.drawImage(icon, 0, 0, size, size, null);
		}
	}
}