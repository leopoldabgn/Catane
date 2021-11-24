package com.catane.model.cases;

public abstract class Case {
	
	@Override
	public String toString() {
		String str = getClass().getSimpleName();
		int max = 10;
		if(str.length() >= max)
			return str;
		int reste = max-str.length();
		str = (reste % 2 == 1 ? " " : "")+" ".repeat(reste/2)+str+" ".repeat(reste/2);

		return str;
	} 

}