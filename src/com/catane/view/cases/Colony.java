package com.catane.view.cases;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.catane.model.Player;
import com.catane.view.Board;

public class Colony extends MovableCase {
	private static final long serialVersionUID = 1L;

	protected int resourceGain = 1;
	
	public Colony(Board board) {
		super(board, null); // Utilisé uniquement pour les cases vides.
	}
	
	public Colony(Board board, Player player) {
		super(board, player);
		//setPreferredSize(new Dimension()); ?
	}
	
	@Override
	public void mouseReleased(MouseEvent e) { // Pas besoin de redefinir cette methode dans Town.
		super.mouseReleased(e);
		// if(isSelectable)
		if(player == null) {
			player = board.getActualPlayer();
		}
		else if(player != null) { // && player.canCreateTown....
			board.replaceColonyByTown(this);
		}
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		// On dessine un triangle avec la couleur du joueur actuel (recuperer via board.getActualPlayer() Par exemple)
	}

	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		// On efface le triangle avec la couleur du joueur actuel (recuperer via board.getActualPlayer() Par exemple)
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(player == null) // Si la case est vide, rien a afficher.
			return;
		Color color = player.getColor();
		g.setColor(color);
		double coeff = 0.1;
		int space = (int)(coeff*getWidth()); // C'est un carré normalement, donc getWidth == getHeigt.
		int[] valX = {space, getWidth()/2, getWidth() - space};
		int[] valY = {getHeight() - space, space, getHeight() - space};
		g.fillPolygon(valX, valY, 3);
	}
	
}
