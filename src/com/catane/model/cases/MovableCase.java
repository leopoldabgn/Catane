package com.catane.model.cases;

import com.catane.model.Player;

public class MovableCase extends Case {
	
	protected Player player;
	
	public MovableCase() {
		this(null);
	}
	
	public MovableCase(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public boolean isEmpty() {
		return player == null;
	}
	
	public String toString() {
		if(player == null)
			return "  ";
		else
			return getClass().getSimpleName().charAt(0)+""+player.getNumber()+"";
	}
	
}
