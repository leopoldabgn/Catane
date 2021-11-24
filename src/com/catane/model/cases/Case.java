package com.catane.model.cases;
import com.catane.model.Board;

public abstract class Case {
	
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
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
