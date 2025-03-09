package inf112.skeleton.app.screen;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;

import inf112.skeleton.app.GamePanel;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private final Body player;
    private final World world;
    private final AssetManager assetManager;
    private SpriteBatch batch;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private static final short BIT_PLAYER = GamePanel.BIT_Player;
    private static final short BIT_GROUND = GamePanel.BIT_Ground;

    public GameScreen(GamePanel context) {
        super(context); 
        this.assetManager = context.getAssetManager();
        this.camera = context.getCamera();
        this.batch = context.getSpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(null, GamePanel.UNIT_SCALE, batch);
        this.world = context.getWorld();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        // creates a Player
        bodyDef.position.set(8, 5.5f);
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
        // bodyDef.position.set(0, 0);
        // bodyDef.gravityScale = 0;
        // bodyDef.type = BodyDef.BodyType.StaticBody;
        // final Body body = world.createBody(bodyDef);
        // body.setUserData("GROUND");

        // fixtureDef.isSensor = false;
        // fixtureDef.restitution = 0.75f;
        // fixtureDef.friction = 0.2f;
        // fixtureDef.filter.categoryBits = BIT_GROUND;
        // fixtureDef.filter.maskBits = -1;
        // //pShape = new PolygonShape();
        // pShape.setAsBox(4f, 0.5f);
        // fixtureDef.shape = pShape; 
        // //body.createFixture(fixtureDef);
        // pShape.dispose();

        camera.setToOrtho(false, 25 * 32 * GamePanel.UNIT_SCALE, 14 * 32 * GamePanel.UNIT_SCALE);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(true);
        mapRenderer.setView(camera);
        mapRenderer.render();
        box2DDebugRenderer.render(world, camera.combined);

        final float speedx;
        final float speedy;

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {     
            speedx = -8;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            speedx = 8;
        } else {
            speedx = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            speedy = -8;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            speedy = 8;
        } else {
            speedy = 0;
        }
        player.applyLinearImpulse(
            (speedx - player.getLinearVelocity().x),
             (speedy - player.getLinearVelocity().y),
              player.getWorldCenter().x,player.getWorldCenter().y,
                true);
        
    }

    @Override
    public void show() {
        if (assetManager.isLoaded("map/map.tmx")) {
            mapRenderer.setMap(assetManager.get("map/map.tmx"));
        } else {
            throw new GdxRuntimeException("Tiled map not loaded!");
        };
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
