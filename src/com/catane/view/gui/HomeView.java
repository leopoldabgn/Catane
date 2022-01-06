package com.catane.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HomeView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bg;

    public HomeView(GUI frame) {
        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(1000, 630));

        try {
            bg = ImageIO.read(new File("res/cataneHome.png"));
            repaint();
        }catch (IOException e) {
            setBackground(Color.ORANGE);
        }

        Dimension dim = new Dimension(100, 50);

        // Bouton start
        JButton start = new JButton("Jouer");
        start.setPreferredSize(dim);
        start.addActionListener(event -> {
            frame.setSettingsPage();
        });
        
        // Bouton quitter
        JButton quit = new JButton("Quitter");
        quit.setPreferredSize(dim);
        quit.addActionListener(event -> {
        	frame.close();
        });

        JPanel pan = new JPanel();
        pan.setOpaque(false);
        
        add(pan);
        
        pan = new JPanel();
        pan.setOpaque(false);
        JPanel pan2 = new JPanel();
        pan2.setOpaque(false);
        pan.add(pan2);
        pan2.setLayout(new BoxLayout(pan2, BoxLayout.PAGE_AXIS));
        
        JPanel pan3 = new JPanel();
        pan3.setOpaque(false);
        pan3.add(start);
        pan2.add(pan3);
        pan3 = new JPanel();
        pan3.setOpaque(false);
        pan3.add(quit);
        pan2.add(pan3);
        
        add(pan);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = getWidth() / 2 - bg.getWidth() / 2;
        int y = getHeight() / 2 - bg.getHeight() / 2;
        g.drawImage(bg, x, y, null);
    }
    
}
