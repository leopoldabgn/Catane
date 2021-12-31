package com.catane.view.gui.cases;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;

import com.catane.model.Board;
import com.catane.model.Player;
import com.catane.model.cases.Colony;
import com.catane.view.gui.BoardView;
import com.catane.view.gui.IconPanel;

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
		if (boardView.getGameView().isColonyActive()) {
			Board board = boardView.getBoardModel();
			Player actualPlayer = boardView.getActualPlayer();
			if(isEmpty()) {
				if(actualPlayer.canBuildColonyOn(board, board.getIndexesOf(colony), boardView.getGameView().isEarly()) != 0) {
					// On affiche un message d'erreur. Impossible de poser la colony.
					return;
				}
				boardView.putColony(actualPlayer, this, boardView.getGameView().isEarly());
				boardView.getGame().refreshLongestRoadOwner(); // On verifie si la personne qui detient la carte a change.
				boardView.getGameView().getActionPanel().refreshOptions();
				
				this.isSelectable = false; // Cette case n'est plus selectionable
				this.setOpaque(true);
				this.setLayout(new BorderLayout());
				this.setBackground(actualPlayer.getColor());
				add(new IconPanel("colony_64", 32), BorderLayout.CENTER);
			}
			else {
				if (boardView.getGameView().isTownActive()) {
					if(actualPlayer.canBuildTownOn(board, board.getIndexesOf(colony)) != 0) {
						// On affiche un message d'erreur. Impossible de poser la ville.
						return;
					}
					boardView.putTown(actualPlayer, this);
					boardView.getGameView().getActionPanel().refreshOptions();
				}
			}

			if (boardView.getGameView().isEarly() && boardView.getGame().getActualPlayer().getNbColonies() == 2)
				boardView.getGameView().setSelectedRoad(true);
			
			boardView.getGameView().refreshInfos();
			revalidate();
			repaint();
			
			if(boardView.getActualPlayer().hasWon()) // On verifie si il vient de gagne la partie
				boardView.getGameView().displayVictoryFrame();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);

	}

	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);

	}
	
	/* Ancienne methode : On dessinait un triangle.
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
	*/
}
