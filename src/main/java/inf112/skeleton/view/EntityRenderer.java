package inf112.skeleton.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.GameEntity;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.enemy.EnemyTypes;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;


public class EntityRenderer {
    
    private final GamePanel context;

    public EntityRenderer(GamePanel context) {
        this.context = context;
    }
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
    private void render(GameEntity e, SpriteBatch batch){
    ItemType itemType = e.getItemType();
    float spriteWidth = ItemType.getItemSize(itemType);
    float spriteHeight = ItemType.getItemSize(itemType);
    batch.draw(ItemType.getItemTexture(itemType), e.getX() - spriteWidth / 2, e.getY() - spriteHeight / 2, spriteWidth, spriteHeight);
    }
    private void drawHealthBar(Enemy enemy, SpriteBatch batch){
      if(enemy.getCharacterType() == CharacterType.BOSS) return;
      Texture healthTexture = context.getAssetManager().get("UI/redtexture.png");
      Texture backgroundTexture = context.getAssetManager().get("UI/graytexture.png");
      Vector2 pos = enemy.getBody().getPosition();
      float barWidth = enemy.getMaxHealth()/100;
      
      batch.draw(backgroundTexture, pos.x - barWidth/2.3f, pos.y + 0.5f, barWidth, 0.2f);
      batch.draw(healthTexture, pos.x - barWidth/2.3f, pos.y + 0.5f, 1 * (enemy.getHealth() / 100f), 0.2f);
    }
  }