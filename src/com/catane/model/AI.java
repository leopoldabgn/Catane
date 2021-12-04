package com.catane.model;

import java.awt.Color;

public class AI extends Player {

	public AI(Color color) {
		super(color);
	}

	public String getName() {
		return "Ordi"+getNumber();
	}
	
}
