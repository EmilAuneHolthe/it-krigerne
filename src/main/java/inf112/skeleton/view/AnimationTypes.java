package inf112.skeleton.view;

public enum AnimationTypes {

    // Soilder
    SOLDIER_DOWN("Player/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 0),
    SOLDIER_UP("Player/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 3),
    SOLDIER_LEFT("Player/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 1),
    SOLDIER_RIGHT("Player/Atlas/SoldierWalking3.atlas", "Soldier", 0.5f, 2),

    SOLDIER_ATTACK("Player/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // Hero
    HERO_DOWN("Player/Atlas/HeroWalking.atlas", "Hero", 0.5f, 0),
    HERO_UP("Player/Atlas/HeroWalking.atlas", "Hero", 0.5f, 3),
    HERO_LEFT("Player/Atlas/HeroWalking.atlas", "Hero", 0.5f, 1),
    HERO_RIGHT("Player/Atlas/HeroWalking.atlas", "Hero", 0.5f, 2),

    HERO_ATTACK("Player/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    // Old
    OLD_DOWN("Player/Atlas/OldWalking.atlas", "Old", 0.5f, 0),
    OLD_UP("Player/Atlas/OldWalking.atlas", "Old", 0.5f, 3),
    OLD_LEFT("Player/Atlas/OldWalking.atlas", "Old", 0.5f, 1),
    OLD_RIGHT("Player/Atlas/OldWalking.atlas", "Old", 0.5f, 2),
    
    OLD_ATTACK("Player/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    ZOMBIE_DOWN("Player/Atlas/ZombieWalking.atlas", "Zombie", 0.5f, 0),
    ZOMBIE_UP("Player/Atlas/ZombieWalking.atlas", "Zombie", 0.5f, 3),
    ZOMBIE_LEFT("Player/Atlas/ZombieWalking.atlas", "Zombie", 0.5f, 1),
    ZOMBIE_RIGHT("Player/Atlas/ZombieWalking.atlas", "Zombie", 0.5f, 2),
    
    ZOMBIE_ATTACK("Player/Atlas/SoldierAttack.atlas", "PlayerAttacking", 0.5f, 0),

    BOSS_DOWN("Player/Atlas/zombie-1.1/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_UP("Player/Atlas/zombie-1.1/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_LEFT("Player/Atlas/zombie-1.1/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_RIGHT("Player/Atlas/zombie-1.1/Boss.atlas", "Boss", 0.5f, 3),
    BOSS_ATTACK("Player/Atlas/zombie-1.1/Boss.atlas", "Boss", 0.5f, 3);

    

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
