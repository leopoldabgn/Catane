package com.catane.view.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.catane.model.Player;

public class PlayerFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private PlayerPanel selectedPanel;

    public PlayerFrame(List<Player> players, GameView gameView) {
        setTitle("Voler un joueur");
        switch (players.size()) {
            case 1:
                setSize(450, 140);
                break;
            case 2:
                setSize(450, 210);
                break;
            case 3:
                setSize(450, 280);
                break;
        }
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel mainPanel = new JPanel();
        getContentPane().add(mainPanel);

        JButton save = new JButton("Choisir");
        save.addActionListener(event -> {
            if (selectedPanel == null)
                return;

            gameView.getGame().getActualPlayer().stealResource(selectedPanel.getPlayer());
            gameView.endThief();
            dispose();
        });

        for (Player p : players) {
            PlayerPanel panel = new PlayerPanel(p);
            panel.addMouseListener(mouseListener(panel));
            mainPanel.add(panel);
        }

        mainPanel.add(save);
    }

    private MouseListener mouseListener(PlayerPanel panel) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (selectedPanel != null)
                    selectedPanel.deselect();
                panel.select();
                selectedPanel = panel;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(!panel.isSelected())
                    panel.setBorder(BorderFactory.createRaisedBevelBorder());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if(!panel.isSelected())
                    panel.setBorder(BorderFactory.createLoweredBevelBorder());
            }
        };
    }

    private class PlayerPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private Player player;
        private boolean isSelected = false;

        public PlayerPanel(Player p) {
            player = p;
            add(new JLabel(player + " -> "));
            add(new ResourcePanel(player));
            setBorder(BorderFactory.createLoweredBevelBorder());
        }

        public Player getPlayer() {
            return player;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void select() {
            isSelected = true;
            setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(2,  2,  2,  2, Color.BLACK),
                        BorderFactory.createMatteBorder(3,  3,  3,  3, Color.WHITE)));
        }
        
        public void deselect() {
            isSelected = false;
            setBorder(BorderFactory.createLoweredBevelBorder());
        }

    }
    
}
