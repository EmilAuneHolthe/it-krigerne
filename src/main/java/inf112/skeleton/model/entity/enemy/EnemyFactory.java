package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.map.Map;
import com.badlogic.gdx.utils.Array;

public class EnemyFactory {
    private final GamePanel context;
    private final World world;
    
    public EnemyFactory(GamePanel context, World world) {
        this.context = context;
        this.world = world;
    }
    
    public Array<Enemy> createEnemiesFromMap(Map map) {
        Array<Enemy> enemies = new Array<>();
        for (EnemySpawn spawn : map.getEnemySpawn()) {
            Enemy enemy = createEnemy(
                spawn.position().x * GamePanel.UNIT_SCALE,
                spawn.position().y * GamePanel.UNIT_SCALE,
                spawn.characterType(),
                spawn.name()
            );
            enemies.add(enemy);
        }
        return enemies;
    }
    
    public Enemy createEnemy(float x, float y, CharacterType characterType, String name) {
        // Create enemy body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        
        Body body = world.createBody(bodyDef);
        
        // Create enemy fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.3f, 0.3f);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;
        
        body.createFixture(fixtureDef);
        shape.dispose();
        
        // Create and return enemy
        return new Enemy(context, world, body, characterType, name);
    }

    public Body createEnemyBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        return world.createBody(bodyDef);
    }
}
