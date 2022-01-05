package com.catane.view.gui.cases;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;

import com.catane.model.cases.Town;
import com.catane.view.gui.BoardView;
import com.catane.view.gui.IconPanel;

public class TownView extends ColonyView {
	private static final long serialVersionUID = 1L;

	private Town town;
	
	public TownView(BoardView boardView, Town town) {
		super(boardView, town);
		this.town = town;
		// On le precise meme si c'est deja le cas par default...
		this.isSelectable = false; // Cette case n'est plus selectionable
		/////
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setBackground(town.getPlayer().getColor());
		add(new IconPanel("town_64", 32), BorderLayout.CENTER);
	}
	
	@Override
	public Town getModelCase() {
		return town;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);

	}

	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);

	}
	
	/* Ancienne methode : on dessinait un cercle.
	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g); // On n'appelle pas cette methode pour eviter que ça dessine le cercle de Colony sous le triangle.
		if(isEmpty()) // Si la case est vide, rien a afficher.
			return;

		g.setColor(getPlayer().getColor()); // player forcement non null ici.
		double coeff = 0.1;
		int space = (int)(coeff*getWidth()); // Le panel est un carré normalement, donc getWidth == getHeigt.
		g.fillOval(space, space, getWidth() - 2*space, getHeight() - 2*space);
	}
	*/
}
