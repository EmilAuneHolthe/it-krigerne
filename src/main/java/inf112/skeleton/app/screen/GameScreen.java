package inf112.skeleton.app.screen;

import static inf112.skeleton.app.GamePanel.UNIT_SCALE;

import java.security.Key;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
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
import inf112.skeleton.app.map.CollisionArea;
import inf112.skeleton.app.map.Map;
import inf112.skeleton.controller.GameKeys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.KeyListener;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    //MOVEMENT
    private boolean directionChange;
    private float xFactor;
    private float yFactor;

    private Body player;
    private final World world;
    private final AssetManager assetManager;
    private SpriteBatch spriteBatch;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private static final short BIT_PLAYER = GamePanel.BIT_Player;
    private static final short BIT_GROUND = GamePanel.BIT_Ground;
    private final GLProfiler profiler;

    private Sprite playerSprite;
    private Texture playerIdleFrontTexture, playerIdleUpTexture, playerIdleRightTexture, playerIdleLeftTexture;
    private Texture playerTexture;
    private Map map;
    private String direction;


    public GameScreen(GamePanel context) {
        super(context); 

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();



        assetManager = context.getAssetManager();
        this.camera = context.getCamera();
        spriteBatch = context.getSpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(null, GamePanel.UNIT_SCALE, context.getSpriteBatch());
        this.world = context.getWorld();
     
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        final TiledMap tiledMap = assetManager.get("map/SampleMap/samplemap.tmx", TiledMap.class);
        //final TiledMap tiledMap = assetManager.get("map/testMap/testMap.tmx", TiledMap.class);
        mapRenderer.setMap(tiledMap);
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
        for (final CollisionArea collisionArea : map.getColissionAreas()) {
            resetBodyAndFixtureDefinition();
            // creates room
            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.fixedRotation = true;
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
        pShape.setAsBox(0.4f, 0.4f);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();

        getPlayerImage();
        createSprite(playerIdleFrontTexture);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(true);

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();
        
        mapRenderer.setView(camera);
        mapRenderer.render();;
        box2DDebugRenderer.render(world, camera.combined);

        /*Gdx.app.debug("renderinfo", "Bindings" + profiler.getTextureBindings());
        Gdx.app.debug("renderinfo", "Drawcells" + profiler.getDrawCalls());
        profiler.reset();*/
        //Gdx.app.debug(FPSLogger.class.getSimpleName(), "FPS: " + Gdx.graphics.getFramesPerSecond());
        movePlayer();
        playerSprite.setPosition(player.getPosition().x - playerSprite.getWidth() / 2, player.getPosition().y - playerSprite.getHeight() / 2);
        
        spriteBatch.begin();
        playerSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void show() {
        keyHandler.addListener(this);
        Gdx.input.setInputProcessor(keyHandler);

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
    public void hide() {
        keyHandler.removeListener(this);
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        playerIdleFrontTexture.dispose();
    }

    @Override
    public void keyPressed(KeyHandler keyHandler, GameKeys key) {
        playerInput(keyHandler, key);

    }

    @Override
    public void keyReleased(KeyHandler keyHandler, GameKeys key) {
        movePlayerReleased(keyHandler, key);
    }

    private void getPlayerImage(){
        playerIdleFrontTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleFront.png"));
        playerIdleUpTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleUp.png"));
        playerIdleLeftTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleLeft.png"));
        playerIdleRightTexture = new Texture(Gdx.files.internal("Player/PlayerIdle/PlayerIdleRight.png"));
        
      }
    
    private void setPlayeSprite() {
        
        switch (direction) {
            case "Front":
                playerTexture = playerIdleFrontTexture;
                createSprite(playerTexture);
                break;
            case "Up":
                playerTexture = playerIdleUpTexture;
                createSprite(playerTexture);
                break;

            case "Left":
                playerTexture = playerIdleLeftTexture;
                createSprite(playerTexture);
                break;

            case "Right":
                playerTexture = playerIdleRightTexture;
                createSprite(playerTexture);
                break;
        
            default:
                break;
            
        }
    }

    private void createSprite(Texture playerTexture) {
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1, 1);
    }

    private void playerInput(KeyHandler keyHandler, GameKeys key) {
        System.err.println("Key pressed: " + key);
        
        switch (key) {
            case LEFT:
                direction = "Left";
                xFactor = -3;
                break;
            case RIGHT:
                direction = "Right";
                xFactor = 3;
                break;
            case UP:
                direction = "Up";
                yFactor = 3;
                break;
            case DOWN:
                direction = "Front";
                yFactor = -3;
                break;
            default:
                break;
        }

        updateDirection();
        dontAccelerate();
    }

    private void movePlayerReleased (KeyHandler keyHandler, GameKeys key) {

        switch (key) {
            case LEFT:
            case RIGHT:
                xFactor = 0;
                if (keyHandler.isKeyPressed(GameKeys.LEFT)) {
                    xFactor = -3;
                } else if (keyHandler.isKeyPressed(GameKeys.RIGHT)) {
                    xFactor = 3;
                }
                break;
            case UP:
            case DOWN:
                yFactor = 0;
                if (keyHandler.isKeyPressed(GameKeys.UP)) {
                    yFactor = 3;
                } else if (keyHandler.isKeyPressed(GameKeys.DOWN)) {
                    yFactor = -3;
                }
                break;
            default:
                break;
        }
        updateDirection();
        dontAccelerate();
    }

    private void updateDirection() {
        directionChange = true;
    }

    private void dontAccelerate() {
        //Player speed is not multiplied by pressing multiple keys
        float speed = 3.0f;
        float magnitude = (float) Math.sqrt(xFactor * xFactor + yFactor * yFactor);
        if (magnitude > 0) {
            xFactor = (xFactor / magnitude) * speed;
            yFactor = (yFactor / magnitude) * speed;
        }
    }
    private void movePlayer() {
        if(directionChange) {
        player.applyLinearImpulse(
            (xFactor * 3 - player.getLinearVelocity().x * player.getMass()),
            (yFactor * 3 - player.getLinearVelocity().y * player.getMass()),
            player.getWorldCenter().x, player.getWorldCenter().y, true
            );

        }
    }
}

