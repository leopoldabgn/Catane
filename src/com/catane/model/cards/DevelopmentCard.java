package com.catane.model.cards;

public interface DevelopmentCard {

    public void canUse();
    public boolean isUsable();
    
    default String getEnglishName() {
    		return getClass().getSimpleName().toLowerCase();
    }
    
}
