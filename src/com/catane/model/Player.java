package com.catane.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.catane.model.resources.Resource;
import com.catane.view.cases.Road;

public class Player {
	private List<Resource> resources;
	private List<Road> roads;
	private Color color;
	
	public Player(Color color) {
		this.resources = new ArrayList<Resource>();
		this.roads = new ArrayList<Road>();
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
}
