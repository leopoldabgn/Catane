package com.catane.view.gui.cases;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.catane.model.cases.ResourceCase;
import com.catane.model.cases.ResourceCase.Desert;
import com.catane.model.cases.ResourceCase.Field;
import com.catane.model.cases.ResourceCase.Forest;
import com.catane.model.cases.ResourceCase.Hill;
import com.catane.model.cases.ResourceCase.Mountain;
import com.catane.model.cases.ResourceCase.Pre;
import com.catane.view.gui.BoardView;
import com.catane.view.gui.IconPanel;

public class ResourceCaseView extends CaseView {
	private static final long serialVersionUID = 1L;

	private ResourceCase modelCase;
	
	private JPanel mainPan;
	
	public ResourceCaseView() {
		super(null);
	}
	
	public ResourceCaseView(BoardView board, ResourceCase rC) {
		super(board);
		this.modelCase = rC;
		setupView();
	}
	
	public void setupView() {
		setBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED));
		//setBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED));
		setBackground(getColor());
		//setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		
		mainPan = new JPanel();
		mainPan.setOpaque(false);
		mainPan.setLayout(new GridLayout(1, 2));
		
		JPanel tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.setLayout(new GridLayout());
		tmp.add(new IconPanel(modelCase.getClass().getSimpleName().toLowerCase()+"_64", 32));
		mainPan.add(tmp);
	    
		tmp = new JPanel();
		tmp.setOpaque(false);
		tmp.setLayout(new BorderLayout());
		
		if(hasThief()) {
			tmp.add(new IconPanel("stealer_64", 64), BorderLayout.CENTER);
			mainPan.add(tmp);
		}
		else if(!(this instanceof DesertView)) { // Le desert n'a pas de chiffre.
			//add(new Circle(getNumber(), (int)getPreferredSize().getWidth()/5), gbc); // 80
			JLabel lbl = new JLabel(getNumber()+"", SwingConstants.CENTER);
			lbl.setFont(new Font("Verdana", Font.BOLD, 13));
			if(getNumber() == 6 || getNumber() == 8)
				lbl.setForeground(Color.RED);
			else
				lbl.setForeground(Color.BLACK);
			tmp.add(lbl, BorderLayout.CENTER);
			mainPan.add(tmp);
		}
		
		this.add(mainPan, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if(!isSelectable)
			return;
		boardView.switchThief(this);
		boardView.getGameView().endThief();
		boardView.getGameView().refreshInfos();
		revalidate();
		repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		if(isSelectable)
			setBorder(BorderFactory.createLineBorder(Color.RED));
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		if(getBorder() == null)
			setBorder(BorderFactory.createBevelBorder(EtchedBorder.RAISED));
	}
	
	public int getNumber() {
		return modelCase.getNumber();
	}
	
	public Color getColor() {
		return modelCase.getColor();
	}
	
	public void refreshThiefView() {
		this.removeAll();
		setupView();
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
