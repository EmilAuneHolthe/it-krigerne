package inf112.skeleton.model.entity.player;

import inf112.skeleton.view.AnimationTypes;

public enum CharacterType {
    SOLDIER(AnimationTypes.SOLDIER_DOWN),
    OLD(AnimationTypes.OLD_DOWN),
    KING(AnimationTypes.KING_DOWN),
    ZOMBIE(AnimationTypes.ZOMBIE_DOWN),
    BOSS(AnimationTypes.BOSS_DOWN),
    SKELETON(AnimationTypes.SKELETON_DOWN),
    DARK(AnimationTypes.DARK_DOWN),
    PUMPKIN(AnimationTypes.PUMPKIN_DOWN),
    GHOST(AnimationTypes.GHOST_DOWN);
    
    private final AnimationTypes defaultAnimation;
    
    CharacterType(AnimationTypes defaultAnimation) {
        this.defaultAnimation = defaultAnimation;
    }
    
    public AnimationTypes getDefaultAnimation() {
        return defaultAnimation;
    }
} 