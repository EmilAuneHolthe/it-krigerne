package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

public record CollisionArea(float x, float y, float[] vertices) {
  public CollisionArea(final float x, final float y, final float[] vertices) {
    this.x = x * UNIT_SCALE;
    this.y = y * UNIT_SCALE;
    this.vertices = vertices;
    for (int i = 0; i < vertices.length; i += 2) {
      vertices[i] = vertices[i] * UNIT_SCALE;
      vertices[i + 1] = vertices[i + 1] * UNIT_SCALE;
    }
  }
}
