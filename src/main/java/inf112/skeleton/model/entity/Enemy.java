package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

public class Enemy implements entity {
    private int health;
    private int damage;
    private float x;
    private float y;
    private World world;
    private Body body;
    private String direction;
    private final GamePanel context;
    public Texture playerIdleFrontTexture, playerIdleUpTexture, playerIdleRightTexture, playerIdleLeftTexture;
    private Sprite playerSprite;
    public Enemy(GamePanel context, World world, Body body, int health, int damage, float x, float y) {
        this.context = context;
        this.world = world;
        this.body = body;
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
        direction = "Front";
    }
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
        public void loadTextures(){
        playerIdleFrontTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleFront.png"));
        playerIdleUpTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleUp.png"));
        playerIdleLeftTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleLeft.png"));
        playerIdleRightTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleRight.png"));
        playerSprite = new Sprite(playerIdleFrontTexture);
        playerSprite.setSize(1, 1);
        playerSprite.setTexture(playerIdleFrontTexture);
    }
        public void render(SpriteBatch batch) {
        playerSprite.setPosition(body.getPosition().x - playerSprite.getWidth() / 2, 
        body.getPosition().y - playerSprite.getHeight() / 2);
        batch.begin();
        playerSprite.draw(batch);
        batch.end();
    }
}
