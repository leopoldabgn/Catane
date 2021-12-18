package com.catane.model.cases;

import com.catane.model.Resource;

public class Port extends Case {

	private Resource resource;
	private int resourcesToGive;
	
	public Port(int resourcesToGive) {
		this(resourcesToGive, null);
	}
	
	public Port(int resourcesToGive, Resource resource) {
		this.resourcesToGive = resourcesToGive;
		this.resource = resource;
	}

	public int getResourcesToGive() {
		return resourcesToGive;
	}
	
	public Resource getResourceType() {
		return resource;
	}
	
	public String toString() {
		String str = resourcesToGive+":1";
		if(resource != null)
			str += " "+resource;
		return str;
	}
	
}
