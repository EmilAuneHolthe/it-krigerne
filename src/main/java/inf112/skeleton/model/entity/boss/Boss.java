package inf112.skeleton.model.entity.boss;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

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

public class Boss extends Enemy {
    private static final float GRID_SIZE = 1.0f; // Size of each grid cell
    private static final float ATTACK_DURATION = 2.0f; // Time before damage is dealt
    private static final float ATTACK_COOLDOWN = 5.0f;
    
    private Array<Vector2> gridPositions;
    private float attackTimer;
    private float cooldownTimer;
    private boolean isAttacking;
    private ShapeRenderer shapeRenderer;
    private GamePanel context;
    private int damage;

    public Boss(GamePanel context, World world, Body body, float x, float y, String name, KeyHandler keyHandler) {
        super(context, world, body, x, y, CharacterType.OLD, name, keyHandler);
        this.context = context;
        this.damage = 50;
        this.gridPositions = new Array<>();
        this.attackTimer = 0;
        this.cooldownTimer = 0;
        this.isAttacking = false;
        this.shapeRenderer = new ShapeRenderer();
        System.out.println("Boss created at position: " + x*UNIT_SCALE + ", " + y*UNIT_SCALE);
        
        // Set boss stats
        setHealth(500);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        
        // Render grid attack if active
        if (isAttacking) {
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            
            for (Vector2 pos : gridPositions) {
                shapeRenderer.rect(pos.x, pos.y, GRID_SIZE, GRID_SIZE);
            }
            
            shapeRenderer.end();
            batch.begin();
        }
    }

    public void update(float delta) {
        // Update attack timers
        if (isAttacking) {
            attackTimer += delta;
            if (attackTimer >= ATTACK_DURATION) {
                endGridAttack();
            }
        } else {
            cooldownTimer += delta;
        }
        
        // Move towards player
        Vector2 playerPos = context.getPlayer().getPosition();
        moveEnemy(playerPos.x, playerPos.y);
    }

    public void startGridAttack() {
        if (!isAttacking && cooldownTimer >= ATTACK_COOLDOWN) {
            isAttacking = true;
            attackTimer = 0;
            cooldownTimer = 0;
            gridPositions.clear();
            
            // Create 3x3 grid around boss
            Vector2 bossPos = getPosition();
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue; // Skip center position
                    if (Math.random() < 0.7) { // 70% chance for each cell to be red
                        gridPositions.add(new Vector2(
                            bossPos.x + x * GRID_SIZE,
                            bossPos.y + y * GRID_SIZE
                        ));
                    }
                }
            }
        }
    }

    private void endGridAttack() {
        isAttacking = false;
        gridPositions.clear();
        
        // Check if player is in any red square
        Vector2 playerPos = context.getPlayer().getPosition();
        for (Vector2 pos : gridPositions) {
            if (isInGridCell(playerPos, pos)) {
                context.getPlayer().playerTakeDamage(this);
                break;
            }
        }
    }

    private boolean isInGridCell(Vector2 position, Vector2 cellPos) {
        return position.x >= cellPos.x && position.x < cellPos.x + GRID_SIZE &&
               position.y >= cellPos.y && position.y < cellPos.y + GRID_SIZE;
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
} 