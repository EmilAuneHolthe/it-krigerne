package inf112.skeleton.app.screen;

import static inf112.skeleton.app.GamePanel.UNIT_SCALE;

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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.GamePanel;
import inf112.skeleton.app.map.CollisonArea;
import inf112.skeleton.app.map.Map;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private Body player;
    private final World world;
    private final AssetManager assetManager;
    private SpriteBatch spriteBatch;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private static final short BIT_PLAYER = GamePanel.BIT_Player;
    private static final short BIT_GROUND = GamePanel.BIT_Ground;

    private Sprite playerSprite;
    private Texture playerTexture;
    private Map map;

    public GameScreen(GamePanel context) {
        super(context); 

        assetManager = context.getAssetManager();
        this.camera = context.getCamera();
        spriteBatch = context.getSpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(null, GamePanel.UNIT_SCALE, context.getSpriteBatch());
        this.world = context.getWorld();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        final TiledMap tiledMap = assetManager.get("map/map.tmx", TiledMap.class);
        mapRenderer.setMap(assetManager.get("map/map.tmx", TiledMap.class));
        map = new Map(tiledMap);
        spawnplayer();
        spawnCollisionsAreas();
    }

    private void resetBodyAndFixtureDefinition(){
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 0;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.75f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
    }

    private void spawnCollisionsAreas() {
        for (final CollisonArea collisionArea : map.getColissionAreas()) {
            resetBodyAndFixtureDefinition();
            // creates room
            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.fixedRotation = true;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            final Body body = world.createBody(bodyDef);
            body.setUserData("GROUND");

            fixtureDef.filter.categoryBits = BIT_GROUND;
            fixtureDef.filter.maskBits = -1;
            final ChainShape cShape = new ChainShape();
            cShape.createChain(collisionArea.getVertices());
            fixtureDef.shape = cShape;
            body.createFixture(fixtureDef);
            cShape.dispose();
        }
    }

    private void spawnplayer(){
        resetBodyAndFixtureDefinition();

        bodyDef.position.set(map.getPlayerSpawn().x * UNIT_SCALE, map.getPlayerSpawn().y * UNIT_SCALE);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.filter.categoryBits = BIT_PLAYER;   
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();

        playerTexture = new Texture(Gdx.files.internal("mario.jpeg"));
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1, 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(true);

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();
        
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

        playerSprite.setPosition(player.getPosition().x - playerSprite.getWidth() / 2, player.getPosition().y - playerSprite.getHeight() / 2);

        spriteBatch.begin();
        playerSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        Gdx.app.debug(null,"resized");
        camera.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        mapRenderer.dispose();
        playerTexture.dispose();
    }
}
