package inf112.skeleton.model.entity.taskBoard;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TaskBoard {

    private Sprite sprite;
    private boolean active = false;


    public TaskBoard(float x, float y, float width, float height, AssetManager assets) {
        this.sprite = new Sprite(assets.get("map/miniMap.png", Texture.class));
        sprite.setPosition(x, y);
        sprite.setSize(width * UNIT_SCALE + 2, height * UNIT_SCALE + 2);
    }


 public void dispose() {
        sprite.getTexture().dispose();
}
public void setActive(Boolean active) {
    this.active = active;
}
public Sprite getSprite() {
    return sprite;
}
public Boolean isActive() {
    return active;
}
}