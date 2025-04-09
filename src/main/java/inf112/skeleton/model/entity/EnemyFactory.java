package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.map.Map;
import com.badlogic.gdx.utils.Array;

public class EnemyFactory {
    private final GamePanel context;
    private final World world;
    private final KeyHandler keyHandler;
    private static final float UNIT_SCALE = GamePanel.UNIT_SCALE;
    private static final short BIT_PLAYER = 0x0001;
    private static final short BIT_GROUND = 0x0002;
    private static final BodyDef BODY_DEF = new BodyDef();
    private static final FixtureDef FIXTURE_DEF = new FixtureDef();
    
    public EnemyFactory(GamePanel context, World world, KeyHandler keyHandler) {
        this.context = context;
        this.world = world;
        this.keyHandler = keyHandler;
    }
    
    private void resetBodyAndFixtureDefinition() {
        BODY_DEF.position.set(0, 0);
        BODY_DEF.angle = 0;
        BODY_DEF.linearVelocity.set(0, 0);
        BODY_DEF.angularVelocity = 0;
        BODY_DEF.linearDamping = 0;
        BODY_DEF.angularDamping = 0;
        BODY_DEF.fixedRotation = false;
        BODY_DEF.bullet = false;
        BODY_DEF.active = true;
        BODY_DEF.awake = true;
        BODY_DEF.gravityScale = 1;
        BODY_DEF.type = BodyDef.BodyType.StaticBody;
        
        FIXTURE_DEF.friction = 0.2f;
        FIXTURE_DEF.restitution = 0;
        FIXTURE_DEF.density = 1;
        FIXTURE_DEF.isSensor = false;
        FIXTURE_DEF.filter.categoryBits = 0x0001;
        FIXTURE_DEF.filter.maskBits = -1;
        FIXTURE_DEF.filter.groupIndex = 0;
    }
    
    public Array<Enemy> createEnemiesFromMap(Map map, CharacterType characterType) {
        Array<Enemy> enemies = new Array<>();
        for (EnemySpawn spawn : map.getEnemySpawn()) {
            resetBodyAndFixtureDefinition();
            
            BODY_DEF.position.set(spawn.getPosition().x * UNIT_SCALE, spawn.getPosition().y * UNIT_SCALE);
            BODY_DEF.fixedRotation = true;
            BODY_DEF.type = BodyDef.BodyType.DynamicBody;
            
            Body body = world.createBody(BODY_DEF);
            body.setUserData(spawn.getName());
            
            FIXTURE_DEF.filter.categoryBits = BIT_PLAYER;
            FIXTURE_DEF.filter.maskBits = BIT_GROUND;
            
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.4f, 0.4f);
            FIXTURE_DEF.shape = shape;
            body.createFixture(FIXTURE_DEF);
            shape.dispose();
            
            Enemy enemy = new Enemy(context, world, body, 100, 10,
                    spawn.getPosition().x * UNIT_SCALE,
                    spawn.getPosition().y * UNIT_SCALE,
                    characterType,
                    spawn.getName(),
                    keyHandler);
            enemies.add(enemy);
        }
        return enemies;
    }
}
