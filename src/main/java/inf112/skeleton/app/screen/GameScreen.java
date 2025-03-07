package inf112.skeleton.app.screen;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.app.GamePanel;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private final Body player;
    private final World world;

    private static final short BIT_PLAYER = GamePanel.BIT_Player;
    private static final short BIT_GROUND = GamePanel.BIT_Ground;

    public GameScreen(GamePanel context) {
        super(context);
        this.world = context.getWorld();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        // creates a Player
        bodyDef.position.set(4.5f, 3);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.density = 1;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;   
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();


        // creates room
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 0;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        final Body body = world.createBody(bodyDef);
        body.setUserData("GROUND");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        final ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new float[] {1,1,1,15,8,15,8,1});
        fixtureDef.shape = chainShape;
        body.createFixture(fixtureDef);
        chainShape.dispose();
        


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        final float speedx;
        final float speedy;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedx = -8;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedx = 8;
        } else {
            speedx = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedy = -8;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedy = 8;
        } else {
            speedy = 0;
        }
        player.applyLinearImpulse(
            (speedx - player.getLinearVelocity().x),
             (speedy - player.getLinearVelocity().y),
              player.getWorldCenter().x,player.getWorldCenter().y,
                true);

        viewport.apply(true);
        box2DDebugRenderer.render(world, viewport.getCamera().combined);
        
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}