package com.catane.view.cases;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.catane.view.Board;

public abstract class Case extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	
	// Par exemple si le joueur doit selectionner une route,
	// Alors, on met isSelectable=true sur toutes les cases route
	// On fait un repaint() de chaque case modifiée.
	// Et les cases routes ont leur bordure en rouge par exemple
	// Et si le joueur clique sur une case avec isSelectable = false;
	// Alors l'action est directement ignoree.
	protected boolean isSelectable = true;
	protected Board board;
	
	public Case(Board board) {
		this.board = board;
		this.addMouseListener(this);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(!isSelectable)
			return;
		setBorder(BorderFactory.createLineBorder(Color.RED)); // plus tard, mettre la couleur du joueur.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(getBorder() != null)
			setBorder(null);
	}
	
}
