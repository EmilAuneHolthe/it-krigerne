package inf112.skeleton.controller;

import com.badlogic.gdx.Gdx;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.WorldFunctions;
import inf112.skeleton.model.entity.item.Inventory;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerAnimation;
import inf112.skeleton.model.entity.player.PlayerInteractions;
import inf112.skeleton.model.entity.player.PlayerMovement;

/**
 * Controls the player's actions and interactions in the game.
 * Handles input, movement, animations, inventory management, and interactions with the game world.
 */
public class PlayerController {
    private final PlayerInteractions playerInteractions;
    private final Player player;
    private final GamePanel context;
    private final Inventory inventory;
    private final PlayerMovement movement;
    private final PlayerAnimation animation;

    /**
     * Constructs a PlayerController instance.
     *
     * @param player             The player entity being controlled.
     * @param playerInteractions The player's interaction handler.
     * @param context            The game context, providing access to shared resources.
     */
    public PlayerController(Player player, PlayerInteractions playerInteractions, GamePanel context) {
        this.player = player;
        this.playerInteractions = playerInteractions;
        this.context = context;
        this.inventory = player.getInventory();
        this.movement = player.getMovement();
        this.animation = player.getAnimation();
    }

    /**
     * Handles player input and performs actions based on the key pressed.
     *
     * @param keyHandler The KeyHandler managing input events.
     * @param key        The key that was pressed.
     */
    public void playerInput(KeyHandler keyHandler, Keys key) {
        switch (key) {
            case QUIT:
                quitWhenDead();
                break;
            case ATTACK:
                playerInteractions.attackEnemy(player, WorldFunctions.getEnemies());
                if (player.canAttack) {
                    animation.startAttack();
                    player.setMana(player.getMana() - 30);
                }
                break;
            case INTERACT:
                inventory.pickUpItems(context.getItems());
                break;
            default:
                break;
        }

        playerUIinput(key);
        movement.handleInput(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }

    /**
     * Handles player input related to the UI, such as selecting or using items.
     *
     * @param key The key that was pressed.
     */
    public void playerUIinput(Keys key) {
        switch (key) {
            case NUM_1:
                inventory.selectItem(0);
                break;
            case NUM_2:
                inventory.selectItem(1);
                break;
            case NUM_3:
                inventory.selectItem(2);
                break;
            case NUM_4:
                inventory.selectItem(3);
                break;
            case USE_ITEM:
                inventory.useSelectedItem();
                context.getAudioHandler().playAudio(AudioTypes.USE_ITEM);
                break;
            default:
                break;
        }
    }

    /**
     * Handles player movement when a key is released.
     *
     * @param keyHandler The KeyHandler managing input events.
     * @param key        The key that was released.
     */
    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }

    private void quitWhenDead() {
        if (Player.isDead) {
            Gdx.app.exit();
        }
    }
}
