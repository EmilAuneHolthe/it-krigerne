package inf112.skeleton.view;

import com.badlogic.gdx.Gdx;
import static inf112.skeleton.model.GamePanel.assetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.GameEntity;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.taskBoard.TaskBoard;
import inf112.skeleton.model.entity.door.Door;

/**
 * Handles the rendering of various game entities including players, enemies, items, doors, and task boards.
 * Provides methods for rendering entities with animations and health bars where applicable.
 */
public class EntityRenderer {
    
    private final GamePanel context;

    /**
     * Creates a new EntityRenderer instance.
     * 
     * @param context The GamePanel context that provides access to game resources and state
     */
    public EntityRenderer(GamePanel context) {
        this.context = context;
    }

    /**
     * Renders a game entity with its animation or static texture.
     * If the entity is an enemy, also renders its health bar.
     * 
     * @param batch The SpriteBatch to use for rendering
     * @param e The game entity to render
     * @param delta The time elapsed since the last frame
     */
    public void renderAll(SpriteBatch batch, GameEntity e, float delta) {
        if (Player.isDead) {
            return;
        }
        if(e.getAnimation() != null) e.getAnimation().update(Gdx.graphics.getDeltaTime());
        if(e.getAnimation() != null) e.getAnimation().render(batch, e.getBody());
        if(e.getAnimation() == null) render(e,batch);

        if(e instanceof Enemy enemy){
            drawHealthBar(enemy, batch);
        }
    }

    /**
     * Renders a door entity with its sprite.
     * 
     * @param batch The SpriteBatch to use for rendering
     * @param door The door entity to render
     */
    public void renderDoor(SpriteBatch batch, Door door) {
        Sprite sprite = new Sprite(assetManager.get("map/Door1.png", Texture.class));
        sprite.setSize(door.getSize().x, door.getSize().y);
        sprite.setPosition(door.getX(), door.getY());
        sprite.draw(batch);
    }

    /**
     * Renders the task board if it is active.
     * 
     * @param batch The SpriteBatch to use for rendering
     * @param taskBoard The task board to render
     */
    public void renderTaskBoard(SpriteBatch batch, TaskBoard taskBoard) {
        if(!taskBoard.isActive()) return;
        taskBoard.getSprite().draw(batch);
    }

    /**
     * Renders a game entity with its static texture.
     * Used when the entity has no animation.
     * 
     * @param e The game entity to render
     * @param batch The SpriteBatch to use for rendering
     */
    private void render(GameEntity e, SpriteBatch batch) {
        ItemType itemType = e.getItemType();
        float spriteWidth = ItemType.getItemSize(itemType);
        float spriteHeight = ItemType.getItemSize(itemType);
        batch.draw(ItemType.getItemTexture(itemType), e.getX() - spriteWidth / 2, e.getY() - spriteHeight / 2, spriteWidth, spriteHeight);
    }

    /**
     * Draws a health bar above an enemy entity.
     * Does not draw health bar for boss enemies.
     * 
     * @param enemy The enemy entity to draw the health bar for
     * @param batch The SpriteBatch to use for rendering
     */
    private void drawHealthBar(Enemy enemy, SpriteBatch batch) {
        if(enemy.getCharacterType() == CharacterType.BOSS) return;
        Texture healthTexture = context.getAssetManager().get("Ui/redtexture.png");
        Texture backgroundTexture = context.getAssetManager().get("Ui/graytexture.png");
        Vector2 pos = enemy.getBody().getPosition();
        float barWidth = (float) enemy.getMaxHealth() /100;
        
        batch.draw(backgroundTexture, pos.x - barWidth/2.3f, pos.y + 0.5f, barWidth, 0.2f);
        batch.draw(healthTexture, pos.x - barWidth/2.3f, pos.y + 0.5f, 1 * (enemy.getHealth() / 100f), 0.2f);
    }
}