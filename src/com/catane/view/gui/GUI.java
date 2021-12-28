package com.catane.view.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.catane.model.Game;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private int width;
	private int height;
	
	// Pour l'instant la GUI prend un game en parametre.
	public GUI(Game game, int w, int h) {
		super();
		this.setTitle("Catane");
		this.width = w;
		this.height = h;
		this.setMinimumSize(new Dimension(1000, 650));
		
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setDefaultLookAndFeelDecorated(true);
		
		// Pour l'instant on appelle ça ici.
		//setGameViewPage(game);

		setHomePage(game);
		
		this.setVisible(true);
	}
	
	// Theoriquement, on appelle ça depuis la page où on creer les
	// joueurs puis on lance
	public void setGameViewPage(Game game) {
		this.getContentPane().removeAll();
		this.setMinimumSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		this.setResizable(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.getContentPane().add(new GameView(game));
		revalidate();
	}

	public void setHomePage(Game game) {
		this.getContentPane().removeAll();
		this.getContentPane().add(new Home(this, game));
		revalidate();
		setResizable(false);
	}

	public void setSettingsPage(Game game) {
		this.getContentPane().removeAll();
		this.getContentPane().add(new Settings(this, game));
		revalidate();
	}
	
}
