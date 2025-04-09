package inf112.skeleton.model.entity;

public enum EnemyTypes {
    SOLDIER("Soldier"),
    OLD("Old"),
    HERO("Hero"),
    ZOMBIE("Zombie");

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
            case SOLDIER:
                return 100;
            case OLD:
                return 10;
            case HERO:
                return 200;
            case ZOMBIE:
                return 50  ;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
      }
      public static int getEnemyDamage(CharacterType type) {
        switch (type) {
            case SOLDIER:
                return 20;
            case OLD:
                return 15;
            case HERO:
                return 25;
            case ZOMBIE:
                return 10;
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }
  }

