package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;

public class PlayerFactory {
    private final GamePanel context;
    private final World world;
    private final KeyHandler keyHandler;
    
    public PlayerFactory(GamePanel context, World world, KeyHandler keyHandler) {
        this.context = context;
        this.world = world;
        this.keyHandler = keyHandler;
    }
    
    public Player createPlayer(float x, float y, CharacterType characterType) {
        // Create player body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        
        Body body = world.createBody(bodyDef);
        
        // Create player fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.4f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);
        shape.dispose();
        
        // Create and return player
        return new Player(context, world, body, 100, 34, x, y, characterType, keyHandler);
    }
}
