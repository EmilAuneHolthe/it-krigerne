package inf112.skeleton.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import inf112.skeleton.model.entity.Enemy;
import inf112.skeleton.model.entity.Player;

public class debug {
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private final float ATTACK_RANGE = 2.0f; // Same as in PlayerInteractions
    
    public debug(SpriteBatch spriteBatch, Player player, OrthographicCamera camera) {
        this.spriteBatch = spriteBatch;
        this.camera = camera;
        this.shapeRenderer = new ShapeRenderer();
    }
    
    public void playerDebug(Player player) {
        if (player == null || player.getBody() == null) return;
        
        // Draw a rectangle around the player's body
        float width = 0.8f; // Player body width
        float height = 0.8f; // Player body height
        float x = player.getBody().getPosition().x - width/2;
        float y = player.getBody().getPosition().y - height/2;
        
        // End any active sprite batch
        if (spriteBatch.isDrawing()) {
            spriteBatch.end();
        }
        
        // Draw the outline
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        
        // Draw player hitbox
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        shapeRenderer.rect(x, y, width, height);
        
        // Draw attack range circle
        shapeRenderer.setColor(0, 1, 0, 0.5f); // Semi-transparent green
        shapeRenderer.circle(
            player.getBody().getPosition().x,
            player.getBody().getPosition().y,
            ATTACK_RANGE,
            32 // Number of segments for smooth circle
        );
        
        shapeRenderer.end();
        
        // Restart sprite batch if it was active
    }
    
    public void enemyDebug(Enemy enemy) { 
        if (enemy == null || enemy.getBody() == null) return;
        
        // Draw a rectangle around the enemy's body
        float width = 0.8f; // Enemy body width
        float height = 0.8f; // Enemy body height
        float x = enemy.getBody().getPosition().x - width/2;
        float y = enemy.getBody().getPosition().y - height/2;
        
        // End any active sprite batch
        if (spriteBatch.isDrawing()) {
            spriteBatch.end();
        }
        
        // Draw the outline
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }
    
    public void dispose() {
        shapeRenderer.dispose();
    }
}
