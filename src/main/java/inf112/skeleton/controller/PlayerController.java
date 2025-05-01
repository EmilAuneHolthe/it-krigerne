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

public class PlayerController {
  private final PlayerInteractions playerInteractions;
  private final Player player;
  private final GamePanel context;
  private final Inventory inventory;
  private final PlayerMovement movement;
  private final PlayerAnimation animation;
  
  public PlayerController(Player player, PlayerInteractions playerInteractions, GamePanel context) {
    this.player = player;
    this.playerInteractions = playerInteractions;
    this.context = context;
    this.inventory = player.getInventory();
    this.movement = player.getMovement();
    this.animation = player.getAnimation();
    
  }
      public void playerInput(KeyHandler keyHandler, Keys key) {

        switch (key) {
            case QUIT: 
                quitWhenDead(); break;
            case ATTACK: 
                playerInteractions.attackEnemy(player, WorldFunctions.getEnemies());
                if (player.canAttack) {
                    animation.startAttack();
                    player.setMana(player.getMana() - 30);
                } break;
            case INTERACT: 
                inventory.pickUpItems(context.getItems()); break;
            default:
                break;
        }

        playerUIinput(key);
        movement.handleInput(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }

  public void playerUIinput(Keys key) {
    switch (key) {
      case NUM_1: inventory.selectItem(0); break;
      case NUM_2: inventory.selectItem(1); break;
      case NUM_3: inventory.selectItem(2); break;
      case NUM_4: inventory.selectItem(3); break;

      case USE_ITEM:inventory.useSelectedItem();
          context.getAudioHandler().playAudio(AudioTypes.USE_ITEM);
          break;
      default:
          break;
  }

}
public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
  movement.handleInputRelease(key);
  animation.setMoving(movement.isMoving());
  animation.setDirection(movement.getDirection());
}


private void quitWhenDead() {
  if ((Player.isDead)) {
      Gdx.app.exit();
  } 

}
}
