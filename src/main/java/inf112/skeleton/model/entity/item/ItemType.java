package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;

public enum ItemType {
  HEALTH("health"),
  KEY("key"),
  ATTACK("attack"),
  MANA("mana");

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
      return new Texture("knife.png");
    case MANA:
      return new Texture("mana.png");
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
  }
  return 0.5f;
}
}
