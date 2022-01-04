package com.catane.model;

import java.util.ArrayList;
import java.util.List;

public class History extends ArrayList<String> {
	private static final long serialVersionUID = 1L;

	public History() {}
	
	public History(List<String> list) {
		if(list == null)
			return;
		for(String s : list)
			add(s);
	}

	public History cutHistory(int beginIndex) {
		if(beginIndex < 0 || beginIndex >= size())
			return new History();
		return new History(this.subList(beginIndex, size()));
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
