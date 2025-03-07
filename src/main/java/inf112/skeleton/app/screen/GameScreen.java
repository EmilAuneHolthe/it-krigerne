package inf112.skeleton.app.screen;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import inf112.skeleton.app.GamePanel;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private final World world;
    private final AssetManager assetManager;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private static final short BIT_CIRCLE = GamePanel.BIT_Circle;
    private static final short BIT_BOX = GamePanel.BIT_Box;
    private static final short BIT_GROUND = GamePanel.BIT_Ground;

    public GameScreen(GamePanel context) {
        super(context);
        this.assetManager = context.getAssetManager();
        this.camera = context.getCamera();
        mapRenderer = new OrthogonalTiledMapRenderer(null, GamePanel.UNIT_SCALE, context.getSpriteBatch());
        this.world = context.getWorld();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        // creates a circle
        bodyDef.position.set(4.5f, 15);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.75f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_BOX;
        CircleShape cShape = new CircleShape();
        cShape.setRadius(0.5f);
        fixtureDef.shape = cShape;
        body.createFixture(fixtureDef);
        cShape.dispose();

        // creates a Box
        bodyDef.position.set(5.3f, 6);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.75f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_BOX;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_CIRCLE;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();

        // creates a platform
        bodyDef.position.set(4.5f, 2);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.75f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(4f, 0.5f);
        fixtureDef.shape = pShape; 
        body.createFixture(fixtureDef);
        pShape.dispose();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            context.setScreen(ScreenType.LOADING);
        }

        viewport.apply(true);
        mapRenderer.setView((OrthographicCamera) viewport.getCamera());
        mapRenderer.render();
        box2DDebugRenderer.render(world, viewport.getCamera().combined);
        
    }

    @Override
    public void show() {
        mapRenderer.setMap(assetManager.get("map/map.tmx"));
    }

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