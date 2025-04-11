package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;

public enum ItemType {
  HEALTH("health"),
  SPEED("speed"),
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
    case SPEED:
      return new Texture("speed.png");
    case ATTACK:
      return new Texture("attack.png");
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
    case SPEED:
      return "Speed";
    case ATTACK:
      return "AttackDMG";
    case MANA:
      return "maxMana";
  }
  return "Heal";
}
}
