package com.catane.view.cases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public abstract class Case extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected String name = getClass().getSimpleName();
	protected Color color;
	protected int number;
	
	public Case(int number, Color color) {
		this.number = number;
		this.color = color;
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		setPreferredSize(new Dimension(80, 80));
		setBackground(color);
		
		setLayout(new GridBagLayout());
		
		JLabel label = new JLabel(name);
		label.setForeground(Color.BLACK);
		Circle circle = new Circle(number, 22);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
	    gbc.gridx = 0;
	    gbc.gridy = 0; 
		add(label, gbc); // On place le label en haut(gridy=0)
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(circle, gbc); // Puis le cercle en desous (gridy=1)
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
	}
	
	public abstract void gainRessources();
	
	public class Circle extends JPanel {
		private static final long serialVersionUID = 1L;

		private JLabel number;
		
		public Circle(int number, int size) {
			setPreferredSize(new Dimension(size, size)); // On change la dimension du panel
			this.number = new JLabel(number+"");
			if(number == 6 || number == 8)
				this.number.setForeground(Color.RED); // Couleur rouge pour le 6 et le 8
			else
				this.number.setForeground(Color.BLACK);
			setOpaque(false); // Comme on va dessiner un rond, il ne faut pas qu'on voit les coins carr�s du panel
			setLayout(new GridBagLayout()); // Pour centrer les �lements � l'int�rieur
			add(this.number); // On ajoute le JLabel au panel
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, getWidth(), getHeight()); // On dessine un cercle au centre qui fait la taille du panel
			g.setColor(Color.BLACK);
			g.drawOval(0, 0, getWidth(), getHeight());
		}
		
	}
	
}
