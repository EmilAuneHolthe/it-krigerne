package inf112.skeleton.model.entity;

public interface entity {
    void attack();
    void die();
    int getHealth();
    boolean takeDamage(int damage);
    void setHealth(int health);
    void setSpawn(int x, int y);
    int getX();
    int getY();
}
