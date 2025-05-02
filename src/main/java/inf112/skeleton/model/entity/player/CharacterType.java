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
    
    /**
     * Gets the default animation for the character type.
     *
     * @return The default animation.
     */
    public AnimationTypes getDefaultAnimation() {
        return defaultAnimation;
    }

    /**
     * Gets the health value for a given enemy type.
     *
     * @param type The character type of the enemy.
     * @return The health value of the enemy.
     * @throws IllegalArgumentException If the character type is null or unknown.
     */
    public static int getEnemyHealth(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        return switch (type) {
            case SKELETON -> 100;
            case PUMPKIN, ZOMBIE -> 50;
            case DARK -> 200;
            case KING -> 300;
            case BOSS -> 500;
            case OLD -> 10;
            case GHOST -> 1;
            default -> throw new IllegalArgumentException("Unknown enemy type: " + type);
        };
    }

    /**
     * Gets the damage value for a given enemy type.
     *
     * @param type The character type of the enemy.
     * @return The damage value of the enemy.
     * @throws IllegalArgumentException If the character type is null or unknown.
     */
    public static int getEnemyDamage(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        return switch (type) {
            case DARK -> 34;
            case SKELETON -> 25;
            case OLD -> 5;
            case ZOMBIE, PUMPKIN -> 10;
            case KING -> 45;
            case BOSS -> 50;
            case GHOST -> 100;
            default -> throw new IllegalArgumentException("Unknown enemy type: " + type);
        };
    }

    /**
     * Gets the sight range for a given enemy type.
     *
     * @param type The character type of the enemy.
     * @return The sight range of the enemy.
     * @throws IllegalArgumentException If the character type is null or unknown.
     */
    public static int getEnemySight(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        if (type == CharacterType.BOSS) {
            return 1000;
        } else {
            return 8;
        }
    }
} 