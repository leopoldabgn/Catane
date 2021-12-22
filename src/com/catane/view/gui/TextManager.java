package com.catane.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.catane.model.Player;

public class TextManager extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel mainText, scoreText;
	
	public TextManager() {
		this("");
	}

	public TextManager(String text) {
		int height = 45;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), height));
		this.setLayout(new BorderLayout());
		
		this.mainText = new JLabel(text);
		this.scoreText = new JLabel();
		setScoreText(0);
		
		Font font = new Font(this.mainText.getFont().getName(), Font.BOLD, 18);
		this.mainText.setFont(font);
		this.scoreText.setFont(font);
		
		mainText.setHorizontalAlignment(JLabel.CENTER);
		mainText.setVerticalAlignment(JLabel.CENTER);

		this.add(mainText, BorderLayout.CENTER);
		this.add(scoreText, BorderLayout.EAST);
	}
	
	public void setScoreText(Player player) {
		if(player != null)
			setScoreText(player.getScore());
	}
	
	private void setScoreText(int score) {
		scoreText.setText("Score : "+score);
	}
	
}
