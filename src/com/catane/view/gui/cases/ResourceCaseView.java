package com.catane.view.gui.cases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.catane.model.cases.ResourceCase;
import com.catane.model.cases.ResourceCase.Desert;
import com.catane.model.cases.ResourceCase.Field;
import com.catane.model.cases.ResourceCase.Forest;
import com.catane.model.cases.ResourceCase.Hill;
import com.catane.model.cases.ResourceCase.Mountain;
import com.catane.model.cases.ResourceCase.Pre;
import com.catane.view.gui.BoardView;

public abstract class ResourceCaseView extends CaseView {
	private static final long serialVersionUID = 1L;

	private ResourceCase modelCase;
	
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public ResourceCaseView(BoardView board, ResourceCase rC) {
		super(board);
		this.modelCase = rC;
		setBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED));
		setPreferredSize(new Dimension(80, 80));
		setBackground(getColor());
		
		setLayout(new GridBagLayout());
		
		JLabel label = new JLabel(rC.toString());
		label.setFont(new Font("Verdana", Font.BOLD, 14));
		label.setForeground(Color.BLACK);
		
	    gbc.gridx = 0;
	    gbc.gridy = 0; 
		add(label, gbc); // On place le label en haut(gridy=0)
		
		gbc.gridx = 0;
		gbc.gridy = 1; // On place un cercle ou le voleur en dessous.(gridy=1)
		if(hasThief()) {
			add(new Thief(10, 20), gbc);
		}
		else if(!(this instanceof DesertView)) { // Le desert n'a pas de chiffre.
			add(new Circle(getNumber(), (int)getPreferredSize().getWidth()/5), gbc); // 80
		}
	}
	
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
			setOpaque(false); // Comme on va dessiner un rond, il ne faut pas qu'on voit les coins carres du panel
			setLayout(new GridBagLayout()); // Pour centrer les elements a l'interieur
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
			setOpaque(false); // Comme on va dessiner un rond, il ne faut pas qu'on voit les coins carres du panel
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
		return modelCase.getNumber();
	}
	
	public Color getColor() {
		return modelCase.getColor();
	}
	
	public void setThief(boolean thief) {
		if((thief && hasThief()) || (!thief && !hasThief()))
			return;
		modelCase.setThief(thief);
		
		// TODO : ATTENTION : cas ou le voleur revient sur le desert peut causer une erreur
		remove(getComponentCount()-1); // On retire le dernier element
		if(thief) {
			add(new Thief(10, 20), gbc);
		}
		else if(!(this instanceof DesertView)) { // Le desert n'a pas de chiffre.
			add(new Circle(getNumber(), 80), gbc);
		}
		repaint();
	}
	
	public boolean hasThief() {
		return modelCase.hasThief();
	}
	
	public static class DesertView extends ResourceCaseView {
		private static final long serialVersionUID = 1L;

		public DesertView(BoardView board, Desert d) {
			super(board, d);
		}

	}
	
	public static class FieldView extends ResourceCaseView {
		private static final long serialVersionUID = 1L;

		public FieldView(BoardView board, Field field) {
			super(board, field);
		}

	}
	
	public static class ForestView extends ResourceCaseView {
		private static final long serialVersionUID = 1L;

		public ForestView(BoardView board, Forest forest) {
			super(board, forest); // Dark green*
		}

		
	}
	
	public static class HillView extends ResourceCaseView {
		private static final long serialVersionUID = 1L;

		public HillView(BoardView board, Hill hill) {
			super(board, hill);
		}

	}
	
	public static class MountainView extends ResourceCaseView {
		private static final long serialVersionUID = 1L;

		public MountainView(BoardView board, Mountain mountain) {
			super(board, mountain);
		}

	}
	
	public static class PreView extends ResourceCaseView {
		private static final long serialVersionUID = 1L;

		public PreView(BoardView board, Pre pre) {
			super(board, pre); // Light green
		}
	}
	
}
