package com.catane.view.gui;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.catane.model.Game;
import com.catane.model.Player;

public class SettingsView extends JPanel {
	private static final long serialVersionUID = 1L;

	public SettingsView(GUI frame, Game game) {
        setBackground(Color.ORANGE);
        FlowLayout layout = new FlowLayout();
        layout.setHgap(500);
        layout.setVgap(75);
        setLayout(layout);

        // Boutons
        JButton start = new JButton("Jouer");
        JButton back = new JButton("Retour");
        back.addActionListener(event -> {
            frame.setHomePage();
        });
        JPanel buttons = new JPanel();
        buttons.add(back);
        buttons.add(start);
        buttons.setOpaque(false);

        // Noms des joueurs
        JPanel names = new JPanel();
        names.setLayout(new GridLayout(2, 4));
        JTextField j1 = new JTextField();
        j1.setColumns(15);
        JLabel l1 = new JLabel("Joueur 1");
        JTextField j2 = new JTextField();
        j2.setColumns(15);
        JLabel l2 = new JLabel("Joueur 2");
        JTextField j3 = new JTextField();
        j3.setColumns(15);
        JLabel l3 = new JLabel("Joueur 3");
        JTextField j4 = new JTextField();
        j4.setColumns(15);
        j4.setVisible(false);
        JLabel l4 = new JLabel("Joueur 4");
        l4.setVisible(false);
        names.add(l1);
        names.add(l2);
        names.add(l3);
        names.add(l4);
        names.add(j1);
        names.add(j2);
        names.add(j3);
        names.add(j4);
        names.setOpaque(false);

        // Taille du plateau
        JLabel sizeLabel = new JLabel("Taille du plateau :");
        JRadioButton size4 = new JRadioButton("4x4");
        size4.setSelected(true);
        size4.setActionCommand("4");
        JRadioButton size6 = new JRadioButton("6x6");
        size6.setActionCommand("6");
        ButtonGroup selectSize = new ButtonGroup();
        selectSize.add(size4);
        selectSize.add(size6);
        JPanel sizePanel = new JPanel();
        sizePanel.add(sizeLabel);
        sizePanel.add(size4);
        sizePanel.add(size6);
        sizePanel.setOpaque(false);

        // Sélection du nombre de joueurs
        JLabel labelJ = new JLabel("Nombre de joueurs");
        JSlider nbJ = new JSlider(3, 4, 3);
        nbJ.setMajorTickSpacing(1);
        nbJ.setPaintLabels(true);
        nbJ.addChangeListener(event -> {
            if (nbJ.getValue() == 4) {
                j4.setVisible(true);
                l4.setVisible(true);
            }else {
                j4.setVisible(false);
                l4.setVisible(false);
            }
        });
        JPanel joueurs = new JPanel();
        joueurs.setLayout(new BorderLayout());
        joueurs.add(labelJ, BorderLayout.NORTH);
        joueurs.add(nbJ, BorderLayout.SOUTH);
        joueurs.setOpaque(false);
        
        // Sélection du nombre d'IA
        JLabel labelIA = new JLabel("Nombre d'IA");
        JSlider nbIA = new JSlider(0, 4, 0);
        nbIA.setMajorTickSpacing(1);
        nbIA.setPaintLabels(true);
        nbIA.addChangeListener(event -> {
            switch (nbIA.getValue()) {
                case 0: j1.setEditable(true);
                        l1.setText("Joueur 1");
                        j2.setEditable(true);
                        l2.setText("Joueur 2");
                        j3.setEditable(true);
                        l3.setText("Joueur 3");
                        j4.setEditable(true);
                        l4.setText("Joueur 4");
                        break;
                case 1: j1.setEditable(true);
                        l1.setText("Joueur 1");
                        j2.setEditable(true);
                        l2.setText("Joueur 2");
                        j3.setEditable(true);
                        l3.setText("Joueur 3");
                        j4.setEditable(false);
                        l4.setText("Ordi 4");
                        j4.setText("");
                        break;
                case 2: j1.setEditable(true);
                        l1.setText("Joueur 1");
                        j2.setEditable(true);
                        l2.setText("Joueur 2");
                        j3.setEditable(false);
                        l3.setText("Ordi 3");
                        j3.setText("");
                        j4.setEditable(false);
                        l4.setText("Ordi 4");
                        j4.setText("");
                        break;
                case 3: j1.setEditable(true);
                        l1.setText("Joueur 1");
                        j2.setEditable(false);
                        l2.setText("Ordi 2");
                        j2.setText("");
                        j3.setEditable(false);
                        l3.setText("Ordi 3");
                        j3.setText("");
                        j4.setEditable(false);
                        l4.setText("Ordi 4");
                        j4.setText("");
                        break;
                case 4: j1.setEditable(false);
                        l1.setText("Ordi 1");
                        j1.setText("");
                        j2.setEditable(false);
                        l2.setText("Ordi 2");
                        j2.setText("");
                        j3.setEditable(false);
                        l3.setText("Ordi 3");
                        j3.setText("");
                        j4.setEditable(false);
                        l4.setText("Ordi 4");
                        j4.setText("");
                    break;
            }
        });
        JPanel ia = new JPanel();
        ia.setLayout(new BorderLayout());
        ia.add(labelIA, BorderLayout.NORTH);
        ia.add(nbIA, BorderLayout.SOUTH);
        ia.setOpaque(false);
        
        add(sizePanel);
        add(joueurs);
        add(ia);
        add(names);
        add(buttons);

        start.addActionListener(event -> {
            game.setBoard(Integer.parseInt(selectSize.getSelection().getActionCommand()));
            game.setupPlayers(nbJ.getValue(), Math.min(nbIA.getValue(), nbJ.getValue()));
            for (Player p : game.getPlayers()) {
                switch (p.getNumber()) {
                    case 1: p.setName(j1.getText());
                            break;
                    case 2: p.setName(j2.getText());
                            break;
                    case 3: p.setName(j3.getText());
                            break;
                    case 4: p.setName(j4.getText());
                            break;
                }
            }
            frame.setGameViewPage(game);
        });
    }
    
}
