package com.catane.view.cases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.catane.view.BoardView;

public abstract class ResourceCaseView extends CaseView {
	private static final long serialVersionUID = 1L;

	private Color color;
	private int number;
	private boolean thief = false;
	
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public ResourceCaseView(BoardView board, int number, Color color) {
		super(board);
		this.number = number;
		this.color = color;
		setBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED));
		setPreferredSize(new Dimension(80, 80));
		setBackground(color);
		
		setLayout(new GridBagLayout());
		
		JLabel label = new JLabel(toString());
		label.setForeground(Color.BLACK);
		Circle circle = new Circle(number, (int)getPreferredSize().getWidth()/5);
		
	    gbc.gridx = 0;
	    gbc.gridy = 0; 
		add(label, gbc); // On place le label en haut(gridy=0)
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(circle, gbc); // Puis le cercle en desous (gridy=1)
	}
	
	public abstract void giveResources();
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if(!isSelectable)
			return;
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		if(getBorder() == null)
			setBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED));
	}
	
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
			double width = getPreferredSize().getWidth();
			double height = getPreferredSize().getHeight();
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, (int)width, (int)height); // On dessine un cercle au centre qui fait la taille du panel
			g.setColor(Color.BLACK);
			g.drawOval(0, 0, (int)width, (int)height);
		}
		
	}
	
	public class Thief extends JPanel {
		private static final long serialVersionUID = 1L;
		
		public Thief(int width, int height) {
			setPreferredSize(new Dimension(width, height)); // On change la dimension du panel
			setOpaque(false); // Comme on va dessiner un rond, il ne faut pas qu'on voit les coins carr�s du panel
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillOval(0, 0, getWidth(), getWidth());
			int space = 0;
			int[] valX = {space, getWidth()/2, getWidth() - space};
			int[] valY = {getHeight() - space, space, getHeight() - space};
			g.fillPolygon(valX, valY, 3);
		}
		
	}
	
	public int getNumber() {
		return number;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setThief(boolean thief) {
		if((thief && hasThief()) || (!thief && !hasThief()))
			return;
		this.thief = thief;
		remove(getComponentCount()-1); // On retire le dernier element
		if(thief) {
			add(new Thief(10, 20), gbc);
		}
		else if(!(this instanceof DesertView)) { // Le desert n'a pas de chiffre.
			add(new Circle(number, 80), gbc);
		}
		repaint();
	}
	
	public boolean hasThief() {
		return thief;
	}
	
}
