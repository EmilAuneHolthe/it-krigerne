package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;
public record CollisionArea(float x, float y, float[] vertices) {
  /**
   * Constructs a new CollisionArea with the specified position and vertices.
   * All coordinates are automatically scaled by UNIT_SCALE.
   * The vertices array is modified in-place to apply the scaling.
   *
   * @param x The x-coordinate of the collision area's position
   * @param y The y-coordinate of the collision area's position
   * @param vertices An array of vertices defining the collision area's shape.
   *                 The array should contain pairs of x,y coordinates.
   *                 The array is modified in-place to apply UNIT_SCALE.
   */
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
