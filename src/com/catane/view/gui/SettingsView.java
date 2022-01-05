package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.catane.model.Game;
import com.catane.model.Player;

public class SettingsView extends JPanel {
	private static final long serialVersionUID = 1L;

    private JTextField j1 = new JTextField();
    private JTextField j2 = new JTextField();
    private JTextField j3 = new JTextField();
    private JTextField j4 = new JTextField();
    private NamePanel names = new NamePanel();

	public SettingsView(GUI frame, Game game) {
        setBackground(new Color(241, 117, 63));
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

        j1.setColumns(15);
        j2.setColumns(15);
        j3.setColumns(15);
        j4.setColumns(15);
        names.refresh(3, 0);

        // Taille du plateau
        JLabel sizeLabel = new JLabel("Taille du plateau :");
        JRadioButton size4 = new JRadioButton("4x4");
        size4.setOpaque(false);
        size4.setSelected(true);
        size4.setActionCommand("4");
        JRadioButton size6 = new JRadioButton("6x6");
        size6.setOpaque(false);
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
        nbJ.setOpaque(false);
        nbJ.setMajorTickSpacing(1);
        nbJ.setPaintLabels(true);
        JPanel joueurs = new JPanel();
        joueurs.setLayout(new BorderLayout());
        joueurs.add(labelJ, BorderLayout.NORTH);
        joueurs.add(nbJ, BorderLayout.SOUTH);
        joueurs.setOpaque(false);
        
        // Sélection du nombre d'IA
        JLabel labelIA = new JLabel("Nombre d'IA");
        JSlider nbIA = new JSlider(0, 4, 0);
        nbIA.setOpaque(false);
        nbIA.setMajorTickSpacing(1);
        nbIA.setPaintLabels(true);
        nbIA.addChangeListener(event -> {
            names.refresh(nbJ.getValue(), nbIA.getValue());
            names.revalidate();
            names.repaint();
        });
        nbJ.addChangeListener(event -> {
            names.refresh(nbJ.getValue(), nbIA.getValue());
            names.revalidate();
            names.repaint();
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

    private class NamePanel extends JPanel {

        public NamePanel() {
            this.setOpaque(false);
        }

        public void refresh(int p, int ai) {
            this.removeAll();
            if (ai > p)
                ai = p;
            this.setLayout(new GridLayout(2, p));
            for (int i = 0; i < p - ai; i++) {
                int n = i + 1;
                this.add(new JLabel("Joueur " + n));
            }
            for (int i = p - ai; i < p; i++) {
                int n = i + 1;
                this.add(new JLabel("Ordi" + n));
            }
            j1.setEditable(true);
            j2.setEditable(true);
            j3.setEditable(true);
            j4.setEditable(true);
            this.add(j1);
            if (ai == p) {
                j1.setText("");
                j1.setEditable(false);
            }
            this.add(j2);
            if (ai >= p - 1) {
                j2.setText("");
                j2.setEditable(false);
            }
            this.add(j3);
            if (ai >= p - 2) {
                j3.setText("");
                j3.setEditable(false);
            }
            if (p == 4) {
                this.add(j4);
                if (ai != 0) {
                    j4.setText("");
                    j4.setEditable(false);
                }
            }
        }

    }
    
}
