package com.catane.model.cases;

import com.catane.model.Player;

public class Town extends Colony {

	public Town(Player player) {
		super(player);
	}

	public Town(Colony colony) {
		this(colony.player);
	}
	
	@Override
	public int resourceGain() {
		return 2;
	}
}
