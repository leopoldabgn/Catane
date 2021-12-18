package com.catane.view.cases;

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
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(new IconPanel("ship32", 32));
		JLabel lbl = new JLabel(port.getResourcesToGive()+":1");
		JPanel pan = new JPanel();
		pan.setOpaque(false);
		pan.add(lbl);
		Resource resource = port.getResourceType();
		if(resource != null)
			pan.add(new IconPanel(resource.getEnglishName().toLowerCase(), 16));
		this.add(pan);
	}

}
