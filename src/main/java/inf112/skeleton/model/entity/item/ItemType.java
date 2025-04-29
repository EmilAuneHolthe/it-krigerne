package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;

public enum ItemType {
  HEALTH("health"),
  KEY("key"),
  MANA("mana"),
  SWORD_UPGRADE ("swordUpgrade");

  private static final String OVERWORLD_SWORD = "Entities/Items/OverworldSword.png"; 


  private final String type;

  ItemType(String string) {
    this.type = string;
  }

  public static Texture getItemTexture(ItemType itemType) {
    switch (itemType) {
      case HEALTH:
        return new Texture("Entities/Items/Health.png");
      case KEY:
        return new Texture("Entities/Items/Key.png");
      case MANA:
        return new Texture("Entities/Items/Health.png");
      case SWORD_UPGRADE:
        return new Texture(OVERWORLD_SWORD ); 
      default:
        break;
    }
    return new Texture("health.png");
  }

public static String getItemType(ItemType itemType) {
  return itemType.type;
}


public static float getItemSize(ItemType itemType) {
  switch (itemType) {
    case HEALTH:
      return 0.5f;
    case KEY:
      return 0.75f;
    case MANA:
      return 0.75f;
    case SWORD_UPGRADE:
      return 1f;
    default:
      break;
  }
  return 0.5f;
}

public static String getSwordHUDTexturePath(String sword) {
  switch (sword) {
    case "UncommonSword":
      return "Entities/Sword/Uncommon.png";
    case "RareSword":
      return "Entities/Sword/Rare.png";
    default:
      return null;
  }
}

}
