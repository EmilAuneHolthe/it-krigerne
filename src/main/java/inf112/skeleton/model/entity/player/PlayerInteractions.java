package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.utils.Array;

import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;

/**
 * Handles player interactions with enemies, particularly combat interactions.
 * Manages attack mechanics and enemy targeting within a specified range.
 */
public class PlayerInteractions {
    private final float attackRange;
    private final GamePanel context;
  
    /**
     * Creates a new PlayerInteractions instance.
     * 
     * @param context The game panel context
     * @param player The player whose interactions will be managed
     */
    public PlayerInteractions(GamePanel context, Player player) {
        this.attackRange = 2.0f;
        this.context = context;
    }

    /**
     * Attempts to attack enemies within range of the player.
     * Plays attack sound and deals damage to all enemies within the attack range.
     * 
     * @param player The player performing the attack
     * @param enemies Array of enemies that could potentially be hit
     */
    public void attackEnemy(Player player, Array<Enemy> enemies) {
        if (player.canAttack) {
            Array<String> enemyNames = distanceToPlayer(player, enemies);
            context.getAudioHandler().playAudio(AudioTypes.ATTACK);
            if (enemyNames != null && enemyNames.size > 0) {
                for (Enemy enemy : enemies) {
                    if (enemyNames.contains(enemy.getName(), false)) {
                        enemy.takeDamage(player.attack());
                    }
                }
            }
        }
    }

    /**
     * Calculates which enemies are within attack range of the player.
     * 
     * @param player The player whose position is used for distance calculation
     * @param enemies Array of enemies to check distance against
     * @return Array of enemy names within range, or null if no enemies are in range
     */
    private Array<String> distanceToPlayer(Player player, Array<Enemy> enemies) {
        Array<String> enemyNames = new Array<>();
        for (Enemy enemy : enemies) {
            float distance = enemy.getPosition().dst(player.getPosition());
            if(distance < attackRange) {
                enemyNames.add(enemy.getName());
            }
        }
        if (enemyNames.size > 0) {
            return enemyNames;
        }
        return null;
    }
}