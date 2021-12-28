package com.catane.view.gui.cases;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.catane.model.Board;
import com.catane.model.Player;
import com.catane.model.cases.Road;
import com.catane.view.gui.BoardView;

public class RoadView extends MovableCaseView {
	private static final long serialVersionUID = 1L;
	
	private Road road;
	
	public RoadView(BoardView boardView, Road road) {
		super(boardView, road);
		this.road = road;
	}
	
	@Override
	public Road getModelCase() {
		return road;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		/*
		if(isEmpty())
			road.setPlayer(board.getActualPlayer());
		*/
		Board board = boardView.getBoardModel();
		Player actualPlayer = boardView.getActualPlayer();
		if(actualPlayer.canBuildRoadOn(board, boardView.getIndexesOf(this)) != 0) {
			// On affiche un message d'erreur. Impossible de poser la route.
			System.out.println("Impossible de construire la route : "+actualPlayer.canBuildRoadOn(board, boardView.getIndexesOf(this)));
			return;
		}
		boardView.putRoad(actualPlayer, this);
		System.out.println("Longest Road : "+boardView.getBoardModel().getLongestRoad(actualPlayer));
		revalidate();
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		// On dessine un rectangle de la couleur du joueur actuel (recuperer via board.getActualPlayer() Par exemple) de la mm size que la route classique
	}

	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		// On efface le rectangle de la couleur du joueur actuel (recuperer via board.getActualPlayer() Par exemple) de la mm size que la route classique
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isEmpty())
			return;
		// Verfier le sens de la route puis la dessiner.
		g.setColor(getPlayer().getColor()); // player forcement non null ici.
		double coeff = 0.4; // Entre 0 et 1.
		double coeff2 = 1; // Entre 0 et 1. // Coeff2 inutile pour le moment. Mais on pourra changer la valeur si on veut decoller la route des bords.
		int width, height, spaceX, spaceY;
		if(road.isVertical()) {
			width = (int)(coeff * getWidth());
			height = (int)(coeff2 * getHeight());
		}
		else {
			width = (int)(coeff2 * getWidth());
			height = (int)(coeff * getHeight());
		}
		spaceX = (getWidth() - width)/2;
		spaceY = (getHeight() - height)/2;
		g.fillRect(spaceX, spaceY, width, height);
	}
	
}
