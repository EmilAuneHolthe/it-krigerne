package inf112.skeleton.model.entity.boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.CharacterType;
import inf112.skeleton.model.entity.Enemy;
import inf112.skeleton.model.entity.Player;

public class Boss extends Enemy {
    private static final float GRID_SIZE = 1.0f; // Size of each grid cell
    private static final float ATTACK_DURATION = 2.0f; // Time before damage is dealt
    private Array<Vector2> attackGridPositions;
    private float attackTimer;
    private boolean isAttacking;
    private ShapeRenderer shapeRenderer;
    private GamePanel context;
    private int damage;

    public Boss(GamePanel context, World world, Body body, float x, float y, String name, KeyHandler keyHandler) {
        super(context, world, body, x, y, CharacterType.OLD, name, keyHandler);
        Gdx.app.log("Boss", "Boss created");
        this.context = context;
        this.damage = 50; // Boss deals more damage
        this.attackGridPositions = new Array<>();
        this.attackTimer = 0f;
        this.isAttacking = false;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        
        // Draw attack grid if attacking
        if (isAttacking) {
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            
            for (Vector2 pos : attackGridPositions) {
                shapeRenderer.rect(pos.x, pos.y, GRID_SIZE, GRID_SIZE);
            }
            
            shapeRenderer.end();
            batch.begin();
        }
    }

    public void update(float deltaTime) {
        if (isAttacking) {
            attackTimer += deltaTime;
            if (attackTimer >= ATTACK_DURATION) {
                // Check if player is in any of the attack positions
                Player player = context.getPlayer();
                if (player != null) {
                    Vector2 playerPos = player.getPosition();
                    for (Vector2 attackPos : attackGridPositions) {
                        if (isInGridCell(playerPos, attackPos)) {
                            player.takeDamage(damage);
                            break;
                        }
                    }
                }
                // Reset attack
                isAttacking = false;
                attackTimer = 0f;
                attackGridPositions.clear();
            }
        }
    }

    public void startGridAttack() {
        Gdx.app.log("Boss", "Starting grid attack");
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = 0f;
            attackGridPositions.clear();
            
            // Create a 3x3 grid around the boss
            Vector2 bossPos = getPosition();
            for (float x = -1; x <= 1; x++) {
                for (float y = -1; y <= 1; y++) {
                    // Skip the center (boss position)
                    if (x == 0 && y == 0) continue;
                    
                    // Add random grid positions (you can modify this to create different patterns)
                    if (Math.random() > 0.5) {
                        attackGridPositions.add(new Vector2(
                            bossPos.x + x * GRID_SIZE,
                            bossPos.y + y * GRID_SIZE
                        ));
                    }
                }
            }
        }
    }

    private boolean isInGridCell(Vector2 position, Vector2 gridCell) {
        return position.x >= gridCell.x && position.x < gridCell.x + GRID_SIZE &&
               position.y >= gridCell.y && position.y < gridCell.y + GRID_SIZE;
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
} 