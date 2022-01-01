package com.catane.view.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.catane.model.Resource;

public class ResourceChoicePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private CardView[] cards;
    private CardView selectedCard;
    
    public ResourceChoicePanel(List<Resource> resources) {
        this.cards = new CardView[resources.size()];
        for(int i=0;i<cards.length;i++) {
            cards[i] = new CardView.ResourceCardView(resources.get(i));
            cards[i].addMouseListener(getCardListener(cards[i]));
            add(cards[i]);
        }
    }
    
    public ResourceChoicePanel() {
        Resource[] resources = Resource.values();
        this.cards = new CardView[resources.length];
        for(int i=0;i<cards.length;i++) {
            cards[i] = new CardView.ResourceCardView(resources[i]);
            cards[i].addMouseListener(getCardListener(cards[i]));
            add(cards[i]);
        }
    }

    public CardView getSelected() {
        return selectedCard;
    }
    
    public MouseListener getCardListener(CardView card) {
        return new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                if(selectedCard == card)
                    return;
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(2,  2,  2,  2, Color.BLACK),
                        BorderFactory.createMatteBorder(3,  3,  3,  3, Color.WHITE)));
                card.setSelected(true);
                if(selectedCard != null) {
                    selectedCard.setSelected(false);
                    selectedCard.setBorder(BorderFactory.createLoweredBevelBorder());
                }
                selectedCard = card;
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!card.isSelected())
                    card.setBorder(BorderFactory.createRaisedBevelBorder());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if(!card.isSelected())
                    card.setBorder(BorderFactory.createLoweredBevelBorder());
            }
            
        };
    }
    
}