package inf112.skeleton.view;

public enum AnimationTypes {

    // Soldier
    SOLDIER_DOWN("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 0),
    SOLDIER_UP("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 3),
    SOLDIER_LEFT("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 1),
    SOLDIER_RIGHT("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 2),

    SOLDIER_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),

    // king
    KING_DOWN("Entities/Atlas/king.atlas", "King", 0),
    KING_UP("Entities/Atlas/king.atlas", "King", 3),
    KING_LEFT("Entities/Atlas/king.atlas", "King", 1),
    KING_RIGHT("Entities/Atlas/king.atlas", "King", 2),

    KING_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),

    // Old
    OLD_DOWN("Entities/Atlas/OldWalking.atlas", "Old", 0),
    OLD_UP("Entities/Atlas/OldWalking.atlas", "Old", 3),
    OLD_LEFT("Entities/Atlas/OldWalking.atlas", "Old", 1),
    OLD_RIGHT("Entities/Atlas/OldWalking.atlas", "Old", 2),
    
    OLD_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),
    // Zombie
    ZOMBIE_DOWN("Entities/Atlas/zombie.atlas", "Zombie", 0),
    ZOMBIE_UP("Entities/Atlas/zombie.atlas", "Zombie", 3),
    ZOMBIE_LEFT("Entities/Atlas/zombie.atlas", "Zombie", 1),
    ZOMBIE_RIGHT("Entities/Atlas/zombie.atlas", "Zombie", 2),
    
    ZOMBIE_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),

    PUMPKIN_DOWN("Entities/Atlas/Pumpkin.atlas", "Pumpkin", 0),
    PUMPKIN_UP("Entities/Atlas/Pumpkin.atlas", "Pumpkin", 3),
    PUMPKIN_LEFT("Entities/Atlas/Pumpkin.atlas", "Pumpkin", 1),
    PUMPKIN_RIGHT("Entities/Atlas/Pumpkin.atlas", "Pumpkin", 2),
    
    PUMPKIN_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),

    // Skeleton
    SKELETON_DOWN("Entities/Atlas/skeleton.atlas", "Skeleton", 0),
    SKELETON_UP("Entities/Atlas/skeleton.atlas", "Skeleton", 3),
    SKELETON_LEFT("Entities/Atlas/skeleton.atlas", "Skeleton", 1),
    SKELETON_RIGHT("Entities/Atlas/skeleton.atlas", "Skeleton", 2),
    SKELETON_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),

    // Dark
    DARK_DOWN("Entities/Atlas/Dark.atlas", "Dark", 0),
    DARK_UP("Entities/Atlas/Dark.atlas", "Dark", 3),
    DARK_LEFT("Entities/Atlas/Dark.atlas", "Dark", 1),
    DARK_RIGHT("Entities/Atlas/Dark.atlas", "Dark", 2),

    DARK_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),

    // Ghost
    GHOST_DOWN("Entities/Atlas/Ghost.atlas", "Ghost", 0),
    GHOST_UP("Entities/Atlas/Ghost.atlas", "Ghost",3),
    GHOST_LEFT("Entities/Atlas/Ghost.atlas", "Ghost", 1),
    GHOST_RIGHT("Entities/Atlas/Ghost.atlas", "Ghost", 2),

    GHOST_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0),
    
    // Boss
    BOSS_DOWN("Entities/Atlas/Boss.atlas", "Boss",  3),
    BOSS_UP("Entities/Atlas/Boss.atlas", "Boss", 3),
    BOSS_LEFT("Entities/Atlas/Boss.atlas", "Boss", 3),
    BOSS_RIGHT("Entities/Atlas/Boss.atlas", "Boss", 3),
    BOSS_ATTACK("Entities/Atlas/Boss.atlas", "Boss", 3);


    private final String atlasPath;
    private final String atlasKey;
    private final float frameTime;
    private final int rowIndex;

    AnimationTypes(final String atlasPath, final String atlasKey, final int rowIndex) {
        this.atlasPath = atlasPath;
        this.atlasKey = atlasKey;
        this.frameTime = (float) 0.5;
        this.rowIndex = rowIndex;
    }

    public String getAtlasPath() {
        return atlasPath;
    }

    public String getAtlasKey() {
        return atlasKey;
    }
    
    public float getFrameTime() {
        return frameTime;
    }

    public int getRowIndex() {
        return rowIndex;
    }  
    
}
