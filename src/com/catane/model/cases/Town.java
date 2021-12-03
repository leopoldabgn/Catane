package com.catane.model.cases;

import com.catane.model.Player;
import com.catane.model.Board;

public class Town extends Colony {

	public Town(Player player) {
		super(player);
		this.resourceGain = 2;
	}

	public Town(Colony colony) {
		this(colony.player);
	}

	public boolean isBuildible(Player p){
		return true;
	}
	
}
