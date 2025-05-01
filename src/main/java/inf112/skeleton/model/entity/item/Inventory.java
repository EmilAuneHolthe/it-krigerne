package inf112.skeleton.model.entity.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.player.Player;

/**
 * Represents the player's inventory in the game.
 * Handles adding, removing, selecting, and using items, as well as managing inventory capacity.
 */
public class Inventory {
    private final Item[] items;
    private final int capacity;
    private int itemCount;
    private int selectedItemIndex = 0; 
    private final Player player;
    private int swordUpgradeType = 0;
    private final GamePanel context;
    private final World world;

    /**
     * Constructs an Inventory instance.
     *
     * @param capacity The maximum number of items the inventory can hold.
     * @param player   The player associated with this inventory.
     * @param context  The game context, providing access to shared resources.
     * @param world    The Box2D world for item interactions.
     */
    public Inventory(int capacity, Player player, GamePanel context, World world) {
        this.capacity = capacity;
        this.items = new Item[capacity];
        this.itemCount = 0;
        this.player = player;
        this.context = context;
        this.world = world;
    }

    /**
     * Picks up items from the game world and adds them to the inventory.
     *
     * @param items The list of items in the game world.
     */
    public void pickUpItems(Array<Item> items) {
        for (Item item : items) {
            if (player.getPosition().dst(item.getPosition()) < 1.0f) {
                pickUpItem(item.getItemType());
                item.remove();
                items.removeValue(item, true);
            }
        }
    }

    /**
     * Picks up a single item and adds it to the inventory.
     *
     * @param itemType The type of the item to pick up.
     */
    public void pickUpItem(ItemType itemType) {
        if (itemType == null) {
            Gdx.app.log("Player", "Cannot pick up null item type");
            return;
        }

        // Create a new item instance
        Item newItem = new Item(context, world, itemType, 0, 0);

        // Add the item to inventory
        addItem(newItem);
    }

    /**
     * Adds an item to the inventory.
     * If the inventory is full, replaces the currently selected item.
     *
     * @param item The item to add.
     */
    private void addItem(Item item) {
        if (item == null) {
            Gdx.app.log("Player", "Cannot add null item");
            return;
        }

        // Try to find an empty slot
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                Gdx.app.log("Player", "Added item to slot " + i);
                return;
            }
        }

        // If no empty slot, replace the selected slot
        if (selectedItemIndex >= 0 && selectedItemIndex < items.length) {
            items[selectedItemIndex] = item;
            Gdx.app.log("Player", "Replaced item in slot " + selectedItemIndex);
        } else {
            Gdx.app.log("Player", "No available slots for item");
        }
    }

    /**
     * Uses the currently selected item and applies its effects.
     * Removes the item from the inventory after use.
     */
    public void useSelectedItem() {
        if (selectedItemIndex < 0 || selectedItemIndex >= items.length) {
            Gdx.app.log("Player", "Invalid selected item index");
            return;
        }

        Item item = items[selectedItemIndex];
        if (item == null) {
            Gdx.app.log("Player", "No item in selected slot");
            return;
        }

        // Apply item effects
        switch (item.getItemType()) {
            case HEALTH:
                player.setHealth(Math.min(player.getHealth() + 20, player.getMaxHealth()));
                break;
            case MANA:
                player.setMana(Math.min(player.getMana() + 20, player.getMaxMana()));
                player.setManaRegenRate(player.getManaRegenRate() + 0.5f);
                break;
            case SWORD_UPGRADE:
                swordUpgradeType++;
                if (swordUpgradeType == 1) {
                    player.increaseDamage(5);
                    player.updateSwordHUDTexturePath("UncommonSword");
                } else if (swordUpgradeType == 2) {
                    player.increaseDamage(10);
                    player.updateSwordHUDTexturePath("RareSword");
                }
                break;
            case KEY:
                player.setKey(true);
                context.getAudioHandler().playAudio(AudioTypes.BONUS);
                break;
            default:
                break;
        }

        // Remove the used item
        items[selectedItemIndex] = null;
        Gdx.app.log("Player", "Used and removed item from slot " + selectedItemIndex);
    }

    /**
     * Removes an item from the inventory at the specified index.
     *
     * @param index The index of the item to remove.
     * @return The removed item, or null if the index is invalid.
     */
    public Item removeItem(int index) {
        if (index >= 0 && index < itemCount) {
            Item item = items[index];
            items[index] = null;
            itemCount--;
            return item;
        }
        return null;
    }

    /**
     * Gets the item at the specified index.
     *
     * @param index The index of the item to retrieve.
     * @return The item at the specified index, or null if the index is invalid.
     */
    public Item getItem(int index) {
        if (index >= 0 && index < itemCount) {
            return items[index];
        }
        return null;
    }

    /**
     * Gets all items in the inventory.
     *
     * @return An array of items in the inventory.
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * Gets the number of items currently in the inventory.
     *
     * @return The number of items in the inventory.
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Gets the maximum capacity of the inventory.
     *
     * @return The maximum number of items the inventory can hold.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Selects an item in the inventory by its index.
     *
     * @param index The index of the item to select.
     */
    public void selectItem(int index) {
      if (index >= 0 && index < items.length) {
          selectedItemIndex = index;
      }
  }
  public void setSelectedItemIndex(int index) {
      if (index >= 0 && index < items.length) {
          selectedItemIndex = index;
      }
  }
}
