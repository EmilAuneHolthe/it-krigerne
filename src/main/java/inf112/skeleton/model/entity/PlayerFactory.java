package inf112.skeleton.model.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.Map;

import static inf112.skeleton.model.GamePanel.*;

public class PlayerFactory {

    public static Player createPlayer(GamePanel context, World world, Map map) {
        resetBodyAndFixtureDefinition();

        BODY_DEF.position.set(map.getPlayerSpawn().x * UNIT_SCALE, map.getPlayerSpawn().y * UNIT_SCALE);
        BODY_DEF.fixedRotation = true;
        BODY_DEF.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(BODY_DEF);
        body.setUserData("Player");

        FIXTURE_DEF.filter.categoryBits = BIT_Player;
        FIXTURE_DEF.filter.maskBits = BIT_Ground;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.4f);
        FIXTURE_DEF.shape = shape;
        body.createFixture(FIXTURE_DEF);
        shape.dispose();

        Player player = new Player(context, world, body, 100, 10,
                map.getPlayerSpawn().x * UNIT_SCALE,
                map.getPlayerSpawn().y * UNIT_SCALE);
        player.loadTextures();
        return player;
    }
}
