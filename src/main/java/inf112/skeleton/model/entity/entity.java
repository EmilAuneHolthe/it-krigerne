package inf112.skeleton.model.entity;

public interface entity {
    //should return the amount of damage the entity does
    int attack();
    //should set the entity's health to 0 and remove it from the board
    void die();
    //should return the entity's health
    int getHealth();
    //should return true if the entity's health is above 0, false otherwise
    boolean takeDamage(int damage);
    //should set the entity's health to the given value
    void setHealth(int health);
    //should set the entity's spawn point to the given coordinates
    void setSpawn(int x, int y);
    //should return the x-coordinate of the entity
    int getX();
    //should return the y-coordinate of the entity
    int getY();
}
