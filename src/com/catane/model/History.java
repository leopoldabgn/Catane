package com.catane.model;

import java.util.ArrayList;

public class History extends ArrayList<String> {
	private static final long serialVersionUID = 1L;

	public History() {
		
	}

	@Override
	public String toString() {
		String str = "";
		for(String s : this) {
			str += s+"\n";
		}
		return str;
	}
	
}
