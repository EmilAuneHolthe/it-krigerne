package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

public class Borders {
  public final float x1;
  public final float x2;
  public final float y1;
  public final float y2;
  public final float width;
  public final float height;
  final String name;
  public Borders(float x1, float y1, float x2, float y2, String name) {
    this.x1 = x1*UNIT_SCALE;
    this.y1 = y1*UNIT_SCALE;
    this.x2 = x2*UNIT_SCALE;
    this.y2 = y2*UNIT_SCALE;
    this.width = x2;
    this.height = y2;
    this.name = name;
  }
  public String isInside(float x, float y) {
    float xy = x/UNIT_SCALE;
    float yx = y/UNIT_SCALE;
    if (xy >= x1 && xy <= x2 && yx >= y1 && yx <= y2) {
      return name;
    }
    return null;
  }
  public float getX1() {
    return x1;
  }
  public float getX2() {
    return x2;
  }
  public float getY1() {
    return y1;
  }
  public float getY2() {
    return y2;
  }
  public String getName() {
    return name;
  }
  public float getWidth() {
    return x2/UNIT_SCALE;
  }
  public float getHeight() {
    return y2/UNIT_SCALE;
  }
}
