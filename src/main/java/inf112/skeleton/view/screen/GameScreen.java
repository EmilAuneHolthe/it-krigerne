package inf112.skeleton.view.screen;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

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

import inf112.skeleton.controller.gameKeys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.KeyListener;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.map.CollisionArea;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;

public class GameScreen extends AbstractScreen implements MapListener{
  

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
    private final GLProfiler profiler;

    private Sprite playerSprite;
    private Texture playerIdleFrontTexture, playerIdleUpTexture, playerIdleRightTexture, playerIdleLeftTexture;
    private Texture playerTexture;
    private Map map;
    private String direction;
    private final MapManager mapManager; 


    public GameScreen(GamePanel context) {
        super(context); 

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
        assetManager = context.getAssetManager();
        this.camera = context.getCamera();
        spriteBatch = context.getSpriteBatch();
        mapRenderer = new OrthogonalTiledMapRenderer(null, GamePanel.UNIT_SCALE, context.getSpriteBatch());
        this.world = context.getWorld();
    
        mapManager = context.getMapManager();
        mapManager.addListener(this);
        mapManager.setMap(MapType.MAP_1);

        spawnplayer();
    }


    private void spawnplayer(){
        GamePanel.resetBodyAndFixtureDefinition();

        GamePanel.BODY_DEF.position.set(map.getPlayerSpawn().x * UNIT_SCALE, map.getPlayerSpawn().y * UNIT_SCALE);
        GamePanel.BODY_DEF.fixedRotation = true;
        GamePanel.BODY_DEF.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(GamePanel.BODY_DEF);
        player.setUserData("PLAYER");

        GamePanel.FIXTURE_DEF.filter.categoryBits = BIT_PLAYER;   
        GamePanel.FIXTURE_DEF.filter.maskBits = GamePanel.BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.4f, 0.4f);
        GamePanel.FIXTURE_DEF.shape = pShape;
        player.createFixture(GamePanel.FIXTURE_DEF);
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            mapManager.setMap(MapType.MAP_1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            mapManager.setMap(MapType.MAP_2);
        }
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
    public void keyPressed(KeyHandler keyHandler, gameKeys key) {
        playerInput(keyHandler, key);

    }

    @Override
    public void keyReleased(KeyHandler keyHandler, gameKeys key) {
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

    private void playerInput(KeyHandler keyHandler, gameKeys key) {
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

    private void movePlayerReleased (KeyHandler keyHandler, gameKeys key) {

        switch (key) {
            case LEFT:
            case RIGHT:
                xFactor = 0;
                if (keyHandler.isKeyPressed(gameKeys.LEFT)) {
                    xFactor = -3;
                } else if (keyHandler.isKeyPressed(gameKeys.RIGHT)) {
                    xFactor = 3;
                }
                break;
            case UP:
            case DOWN:
                yFactor = 0;
                if (keyHandler.isKeyPressed(gameKeys.UP)) {
                    yFactor = 3;
                } else if (keyHandler.isKeyPressed(gameKeys.DOWN)) {
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


    @Override
    public void mapChanged(Map map) {
        this.map = map;
        this.mapRenderer.setMap(map.getTiledMap());
    }
}

