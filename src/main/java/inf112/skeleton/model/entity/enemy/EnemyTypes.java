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
    public static int getEnemySight(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        if (type == CharacterType.BOSS) {
            return 1000;
        }
        else {
            return 8;
        }
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
        
    

