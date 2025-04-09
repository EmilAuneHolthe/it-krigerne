package inf112.skeleton.model.entity;

import inf112.skeleton.view.AnimationTypes;

public enum CharacterType {
    SOLDIER(AnimationTypes.SOLDIER_DOWN),
    OLD(AnimationTypes.OLD_DOWN),
    HERO(AnimationTypes.HERO_DOWN),
    ZOMBIE(AnimationTypes.ZOMBIE_DOWN);
    
    private final AnimationTypes defaultAnimation;
    
    CharacterType(AnimationTypes defaultAnimation) {
        this.defaultAnimation = defaultAnimation;
    }
    
    public AnimationTypes getDefaultAnimation() {
        return defaultAnimation;
    }
} 