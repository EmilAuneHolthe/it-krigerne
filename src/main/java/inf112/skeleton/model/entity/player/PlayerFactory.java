package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.model.GamePanel;

/**
 * Factory class for creating Player instances with proper physics setup.
 * This class handles the creation of player bodies and fixtures in the physics world.
 */
public class PlayerFactory {
    private final GamePanel context;
    private final World world;

    /**
     * Creates a new PlayerFactory instance.
     * 
     * @param context The game panel context
     * @param world The physics world where the player will be created
     */
    public PlayerFactory(GamePanel context, World world) {
        this.context = context;
        this.world = world;
    }
    
    /**
     * Creates a new Player instance with the specified position and character type.
     * Sets up the physics body and fixture with appropriate properties for player movement.
     * 
     * @param x The x-coordinate where the player should be created
     * @param y The y-coordinate where the player should be created
     * @param characterType The type of character to create
     * @return A new Player instance with physics body and fixture set up
     */
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
        return new Player(context, world, body, 100, 25, x, y, characterType);
    }
}
