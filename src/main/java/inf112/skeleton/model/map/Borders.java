package inf112.skeleton.model.map;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

public class Borders {
  public float x1, x2, y1, y2, width, height;
  String name;
  public Borders(float x1, float y1, float x2, float y2, String name) {
    this.x1 = x1*UNIT_SCALE;
    this.y1 = y1*UNIT_SCALE;
    this.x2 = (x1 + x2)*UNIT_SCALE;
    this.y2 = (y1 + y2)*UNIT_SCALE;
    this.width = x2;
    this.height = y2;
    this.name = name;
  }
  public String isInside(float x, float y) {
    float xy = x/UNIT_SCALE;
    float yx = y/UNIT_SCALE;
    System.out.println("player Position" + "x: " + xy + " y: " + yx);
    System.out.println("x1 " + x1 + " y1 " + y1);
    System.out.println("x2 " + x2 + " y2 " + y2);
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
    return width;
  }
  public float getHeight() {
    return height;
  }
}
