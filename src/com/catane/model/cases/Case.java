package com.catane.model.cases;

public abstract class Case {
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	} 

	public boolean isRoad() {
		return (this instanceof Road);
	}
	
	public boolean isEmptyRoad() {
		if(isRoad())
			return ((Road)this).isEmpty();
		return false;
	}
	
	public boolean isColony() {
		return (this instanceof Colony);
	}
	
	public boolean isEmptyColony() {
		if(isColony())
			return ((Colony)this).isEmpty();
		return false;
	}
	
	public boolean isTown() {
		return (this instanceof Town);
	}

	public boolean isResourceCase() {
		return (this instanceof ResourceCase);
	}

}