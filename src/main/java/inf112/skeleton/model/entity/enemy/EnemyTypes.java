package inf112.skeleton.model.entity.enemy;

import inf112.skeleton.model.entity.player.CharacterType;

public enum EnemyTypes {
    SKELETON("Skeleton"),
    ZOMBIE("Zombie"),
    BOSS("Boss");

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
        switch (type) {
            case SKELETON:
                return 100;
            case ZOMBIE:
                return 50  ;
            case BOSS:
                return 500;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
      }
      public static int getEnemyDamage(CharacterType type) {
        switch (type) {
            case SKELETON:
                return 25;
            case ZOMBIE:
                return 10;
            case BOSS:
                return 50;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }
  }

