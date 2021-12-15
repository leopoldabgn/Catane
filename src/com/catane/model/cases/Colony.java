package com.catane.model.cases;

import com.catane.model.Player;

public class Colony extends MovableCase {
	
	public Colony() {
		super(null); // Utilisé uniquement pour les cases vides.
	}
	
	public Colony(Player player) {
		super(player);
	}
	
	public int resourceGain() {
		return 1;
	}
}
