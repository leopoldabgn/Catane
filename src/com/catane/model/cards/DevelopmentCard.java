package com.catane.model.cards;

public interface DevelopmentCard {
    
    default String getEnglishName() {
    		return getClass().getSimpleName().toLowerCase();
    }
    
}
