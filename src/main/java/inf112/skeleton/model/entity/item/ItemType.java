package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.graphics.Texture;
import static inf112.skeleton.model.GamePanel.assetManager;

/**
 * Enum representing different types of items in the game.
 * Each item type has specific properties like texture, size, and behavior.
 */
public enum ItemType {
    /** Health potion that restores player health */
    HEALTH("health"),
    /** Key that can unlock doors */
    KEY("key"),
    /** Mana potion that restores player mana */
    MANA("mana"),
    /** Sword upgrade that increases player damage */
    SWORD_UPGRADE("swordUpgrade");

    private final String type;

    /**
     * Constructs an ItemType enum value.
     *
     * @param string The string identifier for the item type
     */
    ItemType(String string) {
        this.type = string;
    }

    /**
     * Gets the texture for a specific item type.
     *
     * @param itemType The type of item
     * @return The Texture for the specified item type
     */
    public static Texture getItemTexture(ItemType itemType) {
        return switch (itemType) {
            case HEALTH -> assetManager.get("Entities/Items/Health.png");
            case KEY -> assetManager.get("Entities/Items/Key.png");
            case MANA -> assetManager.get("Entities/Items/Mana.png");
            case SWORD_UPGRADE -> assetManager.get("Entities/Items/OverworldSword.png");
        };
    }

    /**
     * Gets the string identifier for an item type.
     *
     * @param itemType The type of item
     * @return The string identifier for the item type
     */
    public static String getItemType(ItemType itemType) {
        return itemType.type;
    }

    /**
     * Gets the size of an item type for rendering.
     *
     * @param itemType The type of item
     * @return The size of the item in world units
     */
    public static float getItemSize(ItemType itemType) {
        return switch (itemType) {
            case KEY -> 0.75f;
            case MANA -> 0.75f;
            case SWORD_UPGRADE -> 1f;
            default -> 0.5f;
        };
    }

    /**
     * Gets the HUD texture path for a specific sword type.
     *
     * @param sword The name of the sword
     * @return The path to the sword's HUD texture, or null if not found
     */
    public static String getSwordHUDTexturePath(String sword) {
        return switch (sword) {
            case "UncommonSword" -> "Entities/Sword/Uncommon.png";
            case "RareSword" -> "Entities/Sword/Rare.png";
            default -> null;
        };
    }
}
