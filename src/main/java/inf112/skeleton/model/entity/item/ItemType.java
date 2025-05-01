package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;
import static inf112.skeleton.model.GamePanel.assetManager;

public enum ItemType {
  HEALTH("health"),
  KEY("key"),
  MANA("mana"),
  SWORD_UPGRADE ("swordUpgrade");


    private final String type;

  ItemType(String string) {
    this.type = string;
  }

  public static Texture getItemTexture(ItemType itemType) {
      return switch (itemType) {
          case HEALTH -> assetManager.get("Entities/Items/Health.png");
          case KEY -> assetManager.get("Entities/Items/Key.png");
          case MANA -> assetManager.get("Entities/Items/Mana.png");
          case SWORD_UPGRADE -> assetManager.get("Entities/Items/OverworldSword.png");
      };
  }

public static String getItemType(ItemType itemType) {
  return itemType.type;
}


public static float getItemSize(ItemType itemType) {
    return switch (itemType) {
        case KEY -> 0.75f;
        case MANA -> 0.75f;
        case SWORD_UPGRADE -> 1f;
        default -> 0.5f;
    };
}

public static String getSwordHUDTexturePath(String sword) {
    return switch (sword) {
        case "UncommonSword" -> "Entities/Sword/Uncommon.png";
        case "RareSword" -> "Entities/Sword/Rare.png";
        default -> null;
    };
}

}
