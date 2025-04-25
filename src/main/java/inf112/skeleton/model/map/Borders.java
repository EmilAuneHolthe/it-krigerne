package inf112.skeleton.model.map;

public class Borders {
  public float x1, x2, y1, y2;
  String name;
  public Borders(float x1, float y1, float x2, float y2, String name) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.name = name;
  }
  public boolean isInside(float x, float y) {
    return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
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
}
