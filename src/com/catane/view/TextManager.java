package com.catane.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextManager extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel text;
	
	public TextManager() {
		this("");
	}

	public TextManager(String text) {
		int height = 45;
		this.text = new JLabel(text);
		this.text.setFont(new Font(this.text.getFont().getName(), Font.BOLD, 18));
		
		this.setOpaque(false);
		this.setPreferredSize(new Dimension((int)getPreferredSize().getWidth(), height));
		this.setLayout(new GridBagLayout());
		this.add(this.text);
	}
	
}
