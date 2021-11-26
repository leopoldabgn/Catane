package com.catane.model.cases;

import com.catane.model.resources.Resource;

public class Port extends Case {

	private Resource resource;
	private int resourcesToGive;
	
	public Port(int resourcesToGive, Resource resource) {
		this.resourcesToGive = resourcesToGive;
		this.resource = resource;
	}
	
	public String toString() {
		String str = resourcesToGive+":1";
		if(resource != null)
			str += " ("+resource.getClass().getSimpleName()+")";
		return str;
	}
	
}
