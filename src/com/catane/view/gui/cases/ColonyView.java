package com.catane.view.gui.cases;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.catane.model.Board;
import com.catane.model.Player;
import com.catane.model.cases.Colony;
import com.catane.view.gui.BoardView;

public class ColonyView extends MovableCaseView {
	private static final long serialVersionUID = 1L;

	private Colony colony;
	
	public ColonyView(BoardView boardView, Colony colony) {
		super(boardView, colony); // Utilisé uniquement pour les cases vides.
		this.colony = colony;
	}
	
	@Override
	public Colony getModelCase() {
		return colony;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) { // Pas besoin de redefinir cette methode dans Town.
		super.mouseReleased(e);
		Board board = boardView.getBoardModel();
		Player actualPlayer = boardView.getActualPlayer();
		if(isEmpty()) {
			if(actualPlayer.canBuildColonyOn(board, board.getIndexesOf(colony)) != 0) {
				// On affiche un message d'erreur. Impossible de poser la colony.
				System.out.println("Impossible de construire la colonie : "+actualPlayer.canBuildColonyOn(board, board.getIndexesOf(colony)));
				return;
			}
			boardView.putColony(actualPlayer, this);
		}
		else {
			if(actualPlayer.canBuildTownOn(board, board.getIndexesOf(colony)) != 0) {
				// On affiche un message d'erreur. Impossible de poser la ville.
				System.out.println("Impossible de construire la ville : "+actualPlayer.canBuildColonyOn(board, boardView.getIndexesOf(this)));
				return;
			}
			boardView.putTown(actualPlayer, this);
		}
		BoardView.display(boardView.getBoardModel());
		revalidate();
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		// On dessine un triangle avec la couleur du joueur actuel (recuperer via boardView.getActualPlayer() Par exemple)
	}

	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		// On efface le triangle avec la couleur du joueur actuel (recuperer via boardView.getActualPlayer() Par exemple)
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isEmpty()) // Si la case est vide, rien a afficher.
			return;
		g.setColor(getPlayer().getColor()); // player forcement non null ici.
		double coeff = 0.1;
		int size = Math.min(getWidth(), getHeight());
		int space = (int)(coeff*size); // C'est un carré normalement, donc getWidth == getHeigt.
		int[] valX = {space, size/2, size - space};
		int[] valY = {size - space, space, size - space};
		g.fillPolygon(valX, valY, 3);
	}
	
}
