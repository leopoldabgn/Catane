package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.catane.model.History;

public class HistoryView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private History history;
	private JTextArea textArea;
	
	public HistoryView(History history) {
		this.history = history;
		this.textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setLineWrap(true); // Pour un retour à ligne automatique
        textArea.setWrapStyleWord(true); // Pour que les mots ne soient pas coupés
        
        JLabel title = new JLabel("Historique :");
        title.setHorizontalAlignment(JLabel.CENTER);
        
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);
		textArea.setBackground(Color.RED);
		refresh();
	}
	
	public void refresh() {
		textArea.setText(history.toString());
		revalidate();
		repaint();
	}
	
}
