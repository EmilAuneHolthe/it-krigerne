package inf112.skeleton.model.entity;

public class Player implements entity{
  private int health;
  private int damage;
  private int x;
  private int y;
  @Override
  public void attack() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'attack'");
  }

  @Override
  public void die() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'die'");
  }


  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public boolean takeDamage(int damage) {
    health -= damage;
    return health <= 0;
  }

  @Override
  public void setHealth(int health) {
    this.health = health;
  }

  @Override
  public void setSpawn(int x, int y) {
    this.x = x;
    this.y = y;
  }
  @Override
   public int getX() {
    return x;
   }
   @Override
   public int getY() {
    return y;
   }
  
}
