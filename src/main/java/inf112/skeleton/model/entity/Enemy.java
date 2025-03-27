package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;

public class Enemy implements entity {
    private int health;
    private int damage;
    private float x;
    private float y;
    @Override
    public int attack() {
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
      if (health < 0) {
        return 0;
      }
      return health;
    }
    @Override
    public boolean takeDamage(int damage) {
      health -= damage;
      return health > 0;
    }
    @Override
    public void setHealth(int health) {
      this.health = health;
    }
    @Override
    public void setSpawn(float x, float y) {
      this.x = x;
      this.y = y;
    }
    @Override
    public float getX() {
     return x;
    }
    @Override
    public float getY() {
     return y;
    }
    public void create(int health, int damage, float x, float y) {
      this.health = health;
      this.damage = damage;
      this.x = x;
      this.y = y;
    }
    @Override
    public Body getBody() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getBody'");
    }
}
