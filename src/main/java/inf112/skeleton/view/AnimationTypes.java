package inf112.skeleton.view;

public enum AnimationTypes {

    // Soilder
    SOLDIER_DOWN("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 0),
    SOLDIER_UP("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 3),
    SOLDIER_LEFT("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 1),
    SOLDIER_RIGHT("Entities/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 2),

    SOLDIER_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // king
    KING_DOWN("Entities/Atlas/king.atlas", "King", 0.5f, 0),
    KING_UP("Entities/Atlas/king.atlas", "King", 0.5f, 3),
    KING_LEFT("Entities/Atlas/king.atlas", "King", 0.5f, 1),
    KING_RIGHT("Entities/Atlas/king.atlas", "King", 0.5f, 2),

    KING_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // Old
    OLD_DOWN("Entities/Atlas/OldWalking.atlas", "Old", 0.5f, 0),
    OLD_UP("Entities/Atlas/OldWalking.atlas", "Old", 0.5f, 3),
    OLD_LEFT("Entities/Atlas/OldWalking.atlas", "Old", 0.5f, 1),
    OLD_RIGHT("Entities/Atlas/OldWalking.atlas", "Old", 0.5f, 2),
    
    OLD_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),
    // Zombie
    ZOMBIE_DOWN("Entities/Atlas/zombie.atlas", "Zombie", 0.5f, 0),
    ZOMBIE_UP("Entities/Atlas/zombie.atlas", "Zombie", 0.5f, 3),
    ZOMBIE_LEFT("Entities/Atlas/zombie.atlas", "Zombie", 0.5f, 1),
    ZOMBIE_RIGHT("Entities/Atlas/zombie.atlas", "Zombie", 0.5f, 2),
    
    ZOMBIE_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // Pumkin
    PUMKIN_DOWN("Entities/Atlas/Pumkin.atlas", "Pumkin", 0.5f, 0),
    PUMKIN_UP("Entities/Atlas/Pumkin.atlas", "Pumkin", 0.5f, 3),
    PUMKIN_LEFT("Entities/Atlas/Pumkin.atlas", "Pumkin", 0.5f, 1),
    PUMKIN_RIGHT("Entities/Atlas/Pumkin.atlas", "Pumkin", 0.5f, 2),
    
    PUMKIN_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // Skeleton
    SKELETON_DOWN("Entities/Atlas/skeleton.atlas", "Skeleton", 0.5f, 0),
    SKELETON_UP("Entities/Atlas/skeleton.atlas", "Skeleton", 0.5f, 3),
    SKELETON_LEFT("Entities/Atlas/skeleton.atlas", "Skeleton", 0.5f, 1),
    SKELETON_RIGHT("Entities/Atlas/skeleton.atlas", "Skeleton", 0.5f, 2),
    SKELETON_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // Dark
    DARK_DOWN("Entities/Atlas/Dark.atlas", "Dark", 0.5f, 0),
    DARK_UP("Entities/Atlas/Dark.atlas", "Dark", 0.5f, 3),
    DARK_LEFT("Entities/Atlas/Dark.atlas", "Dark", 0.5f, 1),
    DARK_RIGHT("Entities/Atlas/Dark.atlas", "Dark", 0.5f, 2),

    DARK_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // Ghost
    GHOST_DOWN("Entities/Atlas/Ghost.atlas", "Ghost", 0.5f, 0),
    GHOST_UP("Entities/Atlas/Ghost.atlas", "Ghost", 0.5f, 3),
    GHOST_LEFT("Entities/Atlas/Ghost.atlas", "Ghost", 0.5f, 1),
    GHOST_RIGHT("Entities/Atlas/Ghost.atlas", "Ghost", 0.5f, 2),

    GHOST_ATTACK("Entities/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),
    
    // Boss
    BOSS_DOWN("Entities/Atlas/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_UP("Entities/Atlas/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_LEFT("Entities/Atlas/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_RIGHT("Entities/Atlas/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_ATTACK("Entities/Atlas/Boss.atlas", "Boss", 0.5f, 3);


    private final String atlasPath;
    private final String atlasKey;
    private final float frameTime;
    private final int rowIndex;

    AnimationTypes(final String atlasPath, final String atlasKey, final float frameTime, final int rowIndex) {
        this.atlasPath = atlasPath;
        this.atlasKey = atlasKey;
        this.frameTime = frameTime;
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
