package inf112.skeleton.model.entity.taskBoard;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class TaskBoard {
    private float x, y, width, height;
    private Sprite sprite;
    private boolean active = false;


    public TaskBoard(float x, float y, float width, float height, AssetManager assets) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.sprite = new Sprite(assets.get("map/miniMap.png", Texture.class));
        sprite.setPosition(x, y);
        sprite.setSize(width * UNIT_SCALE + 2, height * UNIT_SCALE + 2);
    }

    public void render(SpriteBatch batch) {
      if(!active) return;
      sprite.draw(batch);

}
 public void dispose() {
        sprite.getTexture().dispose();
}
public void setActive(Boolean active) {
    this.active = active;
}
}