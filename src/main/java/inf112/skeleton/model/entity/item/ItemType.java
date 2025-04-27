package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;

public enum ItemType {
  HEALTH("health"),
  KEY("key"),
  ATTACK("attack"),
  EMERALD_SWORD("sword"),
  DIAMOND_SWORD("sword2"),
  MANA("mana");

  private static final String KNIFE_TEXTURE = "knife.png"; 

  private final String type;

  ItemType(String string) {
    this.type = string;
  }

  public static Texture getItemTexture(ItemType itemType) {
    switch (itemType) {
      case HEALTH:
        return new Texture("heart.png");
      case KEY:
        return new Texture("key.png");
      case ATTACK:
        return new Texture(KNIFE_TEXTURE); 
      case MANA:
        return new Texture("mana.png");
      case DIAMOND_SWORD:
        return new Texture(KNIFE_TEXTURE);
      case EMERALD_SWORD:
        return new Texture(KNIFE_TEXTURE); 
      default:
        break;
    }
    return new Texture("health.png");
  }

public static String getItemType(ItemType itemType) {
  return itemType.type;
}

public static String getItemAction(ItemType itemType) {
  switch (itemType) {
    case HEALTH:
      return "Heal";
    case KEY:
      return "key";
    case ATTACK:
      return "AttackDMG";
    case MANA:
      return "maxMana";
    case DIAMOND_SWORD:
      break;
    case EMERALD_SWORD:
      break;
    default:
      break;
  }
  return "Heal";
}

public static float getItemSize(ItemType itemType) {
  switch (itemType) {
    case HEALTH:
      return 0.5f;
    case KEY:
      return 0.75f;
    case ATTACK:
      return 1f;
    case MANA:
      return 0.75f;
    case DIAMOND_SWORD:
      break;
    case EMERALD_SWORD:
      break;
    default:
      break;
  }
  return 0.5f;
}

public static String getSwordHUDTexturePath(ItemType itemType) {
  switch (itemType) {
    case ATTACK:
      return "sword.png";
    case DIAMOND_SWORD:
      return "sword_diamond.png";
    case EMERALD_SWORD:
      return "sword_emerald.png";
    default:
      return null;
  }
}
}
