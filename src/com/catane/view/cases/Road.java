package com.catane.view.cases;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.catane.model.Player;
import com.catane.view.Board;

public class Road extends MovableCase {
	private static final long serialVersionUID = 1L;
	
	private boolean sens = false; // false -> horizontal, true -> vertical.
	
	public Road(Board board) {
		this(board, null);
	}
	
	public Road(Board board, boolean sens) {
		this(board, null, sens);
	}
	
	public Road(Board board, Player player) {
		this(board, player, false);
	}
	
	public Road(Board board, Player player, boolean sens) {
		super(board, player);
		// setPreferredSize(new Dimension(100, 100)); // On le fait uniquement dans ce constructeur. Il est appele a coup sur.
		this.sens = sens;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if(player == null)
			this.player = board.getActualPlayer();
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
		if(player == null)
			return;
		// Verfier le sens de la route puis la dessiner.
		Color color = player.getColor(); // Plus tard on mettra la couleur du joueur ici.
		g.setColor(color);
		double coeff = 0.4; // Entre 0 et 1.
		double coeff2 = 1; // Entre 0 et 1. // Coeff2 inutile pour le moment. Mais on pourra changer la valeur si on veut decoller la route des bords.
		int width, height, spaceX, spaceY;
		if(sens) {
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
