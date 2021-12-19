package com.catane.view.cases;

import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.catane.model.Resource;
import com.catane.model.cases.Port;
import com.catane.view.BoardView;
import com.catane.view.IconPanel;

public class PortView extends CaseView {
	private static final long serialVersionUID = 1L;

	private Port port;
	
	public PortView(BoardView board, Port port) {
		super(board);
		this.port = port;
		isSelectable = false;
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(new IconPanel("ship32", 32));
		JLabel lbl = new JLabel(port.getResourcesToGive()+":1");
		lbl.setFont(new Font("Verdana", Font.BOLD, 14));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		JPanel pan = new JPanel();
		pan.add(lbl);
		pan.setOpaque(false);
		Resource resource = port.getResourceType();
		if(resource != null)
			pan.add(new IconPanel(resource.getEnglishName().toLowerCase(), 16));
		this.add(pan);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
	}
	
}
