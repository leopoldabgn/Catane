package com.catane.model.cases;

import com.catane.model.Player;

public class Colony extends MovableCase {

	protected int resourceGain = 1;
	
	public Colony() {
		super(null); // Utilisé uniquement pour les cases vides.
	}
	
	public Colony(Player player) {
		super(player);
	}
	
	public boolean isBuildable(){
		
		return false;
	}
	
}
