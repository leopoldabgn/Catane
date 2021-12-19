package com.catane.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.catane.model.Player;
import com.catane.model.Resource;

public class ResourcePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Player player;
	
	public ResourcePanel(Player player) {
		this.setOpaque(false);
		refreshPlayer(player);
	}
	
	public void refreshPlayer(Player player) {
		if(player == null)
			return;
		this.player = player;
		this.removeAll();
		Resource[] resources = Resource.values();
		for(Resource res : resources) {
			IconPanel iconPanel = new IconPanel(res);
			this.add(iconPanel);
		}
	}
	
	public void refresh() {
		for(Component c : this.getComponents()) {
			IconPanel pan = (IconPanel)c;
			pan.refreshPan();
		}
	}
	
	private class IconPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private int ICON_SIZE = 32;
		
		private JLabel number;
		private Resource res;
		
		public IconPanel(Resource res) {
			this.res = res;
			this.number = new JLabel(player.getResource(res)+"");
			this.setOpaque(false);
			String path = res.getEnglishName().toLowerCase();
			path = "res/icons/"+path+".png";
			Image icon = null;
			try {
				icon = ImageIO.read(new File(path));
			}
			catch(IOException e) {
				e.printStackTrace();
				System.out.println(path);
			}
			
			this.add(number);
			ImagePanel panel = new ImagePanel(icon, ICON_SIZE);
			this.add(panel);
		}
		
		private void refreshPan() {
			this.number.setText(player.getResource(res)+"");
		}
		
		private class ImagePanel extends JPanel {
			private static final long serialVersionUID = 1L;
			
			private Image image;
			
			public ImagePanel(Image image, int size) {
				this.image = image;
				this.setOpaque(false);
				this.setPreferredSize(new Dimension(size, size));
			}
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(image != null)
					g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
			
		}
		
	}
	
}
