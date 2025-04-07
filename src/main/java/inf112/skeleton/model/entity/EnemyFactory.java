package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.EnemySpawn;
import inf112.skeleton.model.map.Map;

import static inf112.skeleton.model.GamePanel.*;
public class EnemyFactory {
  private static Array<Enemy> enemies = new Array<>();
      public static Array<Enemy> createEnemy(GamePanel context, World world, Map map) {
        for (EnemySpawn spawn : map.getEnemySpawn()) {
        resetBodyAndFixtureDefinition();

        BODY_DEF.position.set(spawn.getPosition().x * UNIT_SCALE, spawn.getPosition().y * UNIT_SCALE);
        BODY_DEF.fixedRotation = true;
        BODY_DEF.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(BODY_DEF);
        body.setUserData(spawn.getProperties().get("name"));

        FIXTURE_DEF.filter.categoryBits = BIT_Player;
        FIXTURE_DEF.filter.maskBits = BIT_Ground;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.4f);
        FIXTURE_DEF.shape = shape;
        body.createFixture(FIXTURE_DEF);
        shape.dispose();

        Enemy enemy = new Enemy(context, world, body, 100, 10,
                spawn.getPosition().x * UNIT_SCALE,
                spawn.getPosition().y * UNIT_SCALE);
        enemy.loadTextures();
        enemies.add(enemy);
    }
    return enemies;
}
}
