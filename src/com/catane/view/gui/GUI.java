package com.catane.view.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.catane.model.Game;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Pour l'instant la GUI prend un game en parametre.
	public GUI(Game game, int w, int h) {
		super();
		this.setTitle("Catane");
		this.setMinimumSize(new Dimension(w, h));
		this.setSize(new Dimension(w, h));
		
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setDefaultLookAndFeelDecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		// Pour l'instant on appelle ça ici.
		setGameViewPage(game);
		
		this.setVisible(true);
	}
	
	// Theoriquement, on appelle ça depuis la page où on creer les
	// joueurs puis on lance
	public void setGameViewPage(Game game) {
		this.getContentPane().removeAll();
		this.getContentPane().add(new GameView(game));
	}
	
}
