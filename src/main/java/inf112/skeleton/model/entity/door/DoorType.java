package inf112.skeleton.model.entity.door;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;



public enum DoorType {
  DOOR1("map/Door1.png", 2f),
  CAVE ("map/Door1.png", 0.75f),
  CASTLE_DOOR("map/Door1.png", 0.75f),;

  private final String textureFile;
  private final float  size;

  DoorType(String textureFile, float size) {
      this.textureFile = textureFile;
      this.size        = size;
  }

  /*  hentes via AssetManager for deling og minne-kontroll  */
  public Texture getTexture(AssetManager assets) {
      return assets.get(textureFile, Texture.class);
  }

  public float getSize() {
      return size;
  }
}
