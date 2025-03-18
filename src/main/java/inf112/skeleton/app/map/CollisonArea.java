package inf112.skeleton.app.map;

import static inf112.skeleton.app.GamePanel.UNIT_SCALE;
public class CollisonArea {
  private final float[] vertices;
  private final float x;
  private final float y;

  public CollisonArea(final float x, final float y, final float[] vertices) {
    this.x = x * UNIT_SCALE;
    this.y = y * UNIT_SCALE;
    this.vertices = vertices;
    for (int i = 0; i < vertices.length; i+= 2) {
      vertices[i] = vertices[i] * UNIT_SCALE;
      vertices[i+1] = vertices[i+1] * UNIT_SCALE;
    }
  }

  public float getY() {
    return y;
  }

  public float getX() {
    return x;
  }
  public float[] getVertices() {
    return vertices;
  }
}
