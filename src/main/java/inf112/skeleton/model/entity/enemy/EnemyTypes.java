package inf112.skeleton.model.entity.enemy;

import inf112.skeleton.model.entity.player.CharacterType;

public enum EnemyTypes {
    SKELETON("Skeleton"),
    ZOMBIE("Zombie"),
    BOSS("Boss"),
    OLD("Old"),;

    private final String name;

    EnemyTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static EnemyTypes fromName(String name) {
        for (EnemyTypes type : EnemyTypes.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enemy type found with name: " + name);
    }
    public static int getEnemyHealth(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        switch (type) {
            case SKELETON:
                return 100;
            case PUMPKIN:
                return 50;
            case DARK:
                return 200;
            case ZOMBIE:
                return 50;
            case KING:
                return 300;
            case BOSS:
                return 500;
            case OLD:
                return 10;
            case GHOST:
                return 1;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }
    public static int getEnemyDamage(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        switch (type) {
            case DARK:
                return 34;
            case SKELETON:
                return 25;
            case OLD:
                return 5;
            case ZOMBIE:
                return 10;
            case KING:
                return 45;
            case BOSS:
                return 50;
            case PUMPKIN:
                return 10;
            case GHOST:
                return 100;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }
    public static int getEnemySight(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        if (type == CharacterType.BOSS) {
            return 1000;
        }
        else if (type.getClass() == CharacterType.class) {
            return 8;
        }
        throw new IllegalArgumentException("Unknown enemy type: " + type);
    }
       
    public static int getEnemySize(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        if (type == CharacterType.BOSS) {
            return 2;
        }
        else if (type.getClass() == CharacterType.class) {
            return 1;
        }
        throw new IllegalArgumentException("Unknown enemy type: " + type);
    }
}
        
    

