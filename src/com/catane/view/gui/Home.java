package com.catane.view.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.catane.model.Game;

public class Home extends JPanel {

    private BufferedImage bg;

    public Home(GUI frame, Game game) {
        setLayout(new BorderLayout());
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
            frame.setSettingsPage(game);
        });
        
        // Bouton quitter
        JButton quit = new JButton("Quitter");
        quit.setPreferredSize(dim);
        quit.addActionListener(event -> {
            System.exit(0);
        });

        // Placement des boutons
        JPanel panel = new JPanel();
        panel.add(quit);
        panel.add(start);
        panel.setPreferredSize(new Dimension(250, 50));
        panel.setOpaque(false);
        add(panel, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 1;
        //add(quit, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        //add(start, gbc);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = getWidth() / 2 - bg.getWidth() / 2;
        int y = getHeight() / 2 - bg.getHeight() / 2;
        g.drawImage(bg, x, y, null);
    }
    
}
