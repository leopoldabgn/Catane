package com.catane.view.gui;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.catane.model.Player;
import com.catane.model.Resource;

public class DiscardFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ResourceChoicePanel choice;
    private JLabel error1;
    private JLabel error2;
    
    public DiscardFrame(List<Player> players, int i, int nb) {
        setTitle("Défausser carte");
		setSize(450, 270);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        Player p = players.get(i);
        boolean visible = true;

        ResourcePanel res = new ResourcePanel(p);
        
		JButton save = new JButton("Se défausser");

        save.addActionListener(event -> {
            if (choice.getSelected() == null) {
                if(error1 == null) {
                    error1 = new JLabel("Merci de sélectionner une ressource !");
                    error1.setForeground(Color.RED);
                    getContentPane().add(error1, 0);
                    revalidate();
                    repaint();
                }
                return;
            }
            Resource r = ((CardView.ResourceCardView) choice.getSelected()).getResource();

            if (p.getResource(r) == 0) {
                if (error2 == null) {
                    error2 = new JLabel("Vous n'avez pas cette ressource !");
                    error2.setForeground(Color.RED);
                    getContentPane().add(error2, 0);
                }
                if (error1 != null)
                    error1.setVisible(false);
                revalidate();
                repaint();
                return;
            }

            p.pay(r);

            this.dispose();
            if (nb > 1) {
                new DiscardFrame(players, i, nb - 1);
            }else {
                if (players.size() > i + 1)
                    new DiscardFrame(players, i + 1, players.get(i+1).getResources()/2);
            }
        });

        String s = p.toString() + " doit se défausser de " + nb + " ressource(s)";
        JLabel label = new JLabel(s);
        JPanel panel = new JPanel();
        setContentPane(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(res);
		panel.add(label);
		choice = new ResourceChoicePanel();
		panel.add(choice);

		JPanel buttons = new JPanel();
		buttons.add(save);

		panel.add(buttons);

		setVisible(visible);
    }
    
}
