package com.catane.view.cases;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.catane.model.Player;
import com.catane.view.BoardView;

public class TownView extends ColonyView {
	private static final long serialVersionUID = 1L;

	public TownView(BoardView board, Player player) {
		super(board, player);
		this.resourceGain = 2;
	}

	public TownView(ColonyView colony) {
		this(colony.board, colony.player);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		// On dessine un cercle de la couleur du joueur actuel (recuperer via board.getActualPlayer() Par exemple)
	}

	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		// On efface le cercle de la joueur du joueur actuel (recuperer via board.getActualPlayer() Par exemple)
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g); // On n'appelle pas cette methode pour eviter que ça dessine le cercle de Colony sous le triangle.
		if(player == null) // Si la case est vide, rien a afficher.
			return;

		Color color = player.getColor(); // On applique la couleur du joueur.
		g.setColor(color);
		double coeff = 0.1;
		int space = (int)(coeff*getWidth()); // Le panel est un carré normalement, donc getWidth == getHeigt.
		g.fillOval(space, space, getWidth() - 2*space, getHeight() - 2*space);
	}
	
}
