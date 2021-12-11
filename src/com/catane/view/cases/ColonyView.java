package com.catane.view.cases;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.catane.model.cases.Colony;
import com.catane.view.BoardView;

public class ColonyView extends MovableCaseView {
	private static final long serialVersionUID = 1L;

	private Colony colony;
	
	public ColonyView(BoardView board, Colony colony) {
		super(board, colony); // Utilisé uniquement pour les cases vides.
		this.colony = colony;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) { // Pas besoin de redefinir cette methode dans Town.
		super.mouseReleased(e);
		// if(isSelectable)
		if(isEmpty()) {
			setPlayer(board.getActualPlayer());
		}
		else if(!isEmpty()) { // && player.canCreateTown....
			
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
		if(isEmpty()) // Si la case est vide, rien a afficher.
			return;
		g.setColor(getPlayer().getColor()); // player forcement non null ici.
		double coeff = 0.1;
		int space = (int)(coeff*getWidth()); // C'est un carré normalement, donc getWidth == getHeigt.
		int[] valX = {space, getWidth()/2, getWidth() - space};
		int[] valY = {getHeight() - space, space, getHeight() - space};
		g.fillPolygon(valX, valY, 3);
	}
	
}
