package inf112.skeleton.model.entity.enemy;

import inf112.skeleton.model.entity.player.CharacterType;

/**
 * Enum representing different types of enemies in the game.
 * Provides utility methods for retrieving enemy attributes such as health, damage, sight range, and size.
 */
public enum EnemyTypes {
    SKELETON("Skeleton"),
    ZOMBIE("Zombie"),
    BOSS("Boss"),
    OLD("Old");

    private final String name;

    /**
     * Constructs an EnemyTypes enum value.
     *
     * @param name The name of the enemy type.
     */
    EnemyTypes(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the enemy type.
     *
     * @return The name of the enemy type.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves an EnemyTypes enum value based on its name.
     *
     * @param name The name of the enemy type.
     * @return The corresponding EnemyTypes enum value.
     * @throws IllegalArgumentException If no enemy type matches the given name.
     */
    public static EnemyTypes fromName(String name) {
        for (EnemyTypes type : EnemyTypes.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enemy type found with name: " + name);
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

    /**
     * Gets the size of a given enemy type.
     *
     * @param type The character type of the enemy.
     * @return The size of the enemy.
     * @throws IllegalArgumentException If the character type is null or unknown.
     */
    public static int getEnemySize(CharacterType type) {
        if (type == null) {
            throw new IllegalArgumentException("CharacterType cannot be null");
        }
        if (type == CharacterType.BOSS) {
            return 2;
        } else if (type.getClass() == CharacterType.class) {
            return 1;
        }
        throw new IllegalArgumentException("Unknown enemy type: " + type);
    }
}



