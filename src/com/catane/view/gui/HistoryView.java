package com.catane.view.gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.catane.model.History;

public class HistoryView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private History history;
	private JTextArea textArea;
	
	public HistoryView(History history) {
		this.history = history;
		this.textArea = new JTextArea();
		add(textArea);
		refresh();
	}
	
	public void refresh() {
		textArea.setText(history.toString());
		revalidate();
		repaint();
	}
	
}
