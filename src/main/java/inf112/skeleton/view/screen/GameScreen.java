package inf112.skeleton.view.screen;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import java.security.Key;

import org.lwjgl.opengl.GL20;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import inf112.skeleton.controller.Keys;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.KeyListener;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.Player;
import inf112.skeleton.model.map.CollisionArea;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;
import inf112.skeleton.view.ui.PlayerHUD;;


public class GameScreen extends AbstractScreen implements MapListener{
  

    //MOVEMENT
    private boolean directionChange;
    private float xFactor;
    private float yFactor;
    private Player player;
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
    private Body playerBody;


    //UI
    private Stage uiStage;
    private Texture healthTexture;
    private Texture backgroundTexture;
    private PlayerHUD playerHUD;
    private Skin skin;


    


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
        direction = "Front";

        spawnplayer();
        playerBody =  player.getBody();
        player.getHealth();
        

        //UI
        healthTexture = new Texture(Gdx.files.internal("redtexture.png"));
        backgroundTexture = new Texture(Gdx.files.internal("graytexture.png"));
        uiStage = new Stage(new ScreenViewport(), spriteBatch);
        playerHUD = new PlayerHUD(uiStage, player, healthTexture, backgroundTexture);
        
    }


    private void spawnplayer(){

        GamePanel.resetBodyAndFixtureDefinition();

        GamePanel.BODY_DEF.position.set(map.getPlayerSpawn().x * UNIT_SCALE, map.getPlayerSpawn().y * UNIT_SCALE);
        GamePanel.BODY_DEF.fixedRotation = true;
        GamePanel.BODY_DEF.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(GamePanel.BODY_DEF);
        body.setUserData("Test");

        GamePanel.FIXTURE_DEF.filter.categoryBits = BIT_PLAYER;   
        GamePanel.FIXTURE_DEF.filter.maskBits = GamePanel.BIT_GROUND;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.4f, 0.4f);
        GamePanel.FIXTURE_DEF.shape = pShape;
        body.createFixture(GamePanel.FIXTURE_DEF);
        pShape.dispose();

        // Initialize Player with Body properties
        player = new Player(world, body, 100, 10, (int) map.getPlayerSpawn().x *  UNIT_SCALE, (int) map.getPlayerSpawn().y *  UNIT_SCALE);

        getPlayerImage();
        createSprite(playerIdleFrontTexture);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(true);

        camera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0); // Use player
        camera.update();
        
        mapRenderer.setView(camera);
        mapRenderer.render();;
        box2DDebugRenderer.render(world, camera.combined);

        

        /*Gdx.app.debug("renderinfo", "Bindings" + profiler.getTextureBindings());
        Gdx.app.debug("renderinfo", "Drawcells" + profiler.getDrawCalls());
        profiler.reset();*/
        //Gdx.app.debug(FPSLogger.class.getSimpleName(), "FPS: " + Gdx.graphics.getFramesPerSecond());
        movePlayer();
        setPlayeSprite();
        playerSprite.setPosition(playerBody.getPosition().x - playerSprite.getWidth() / 2, playerBody.getPosition().y - playerSprite.getHeight() / 2);
        spriteBatch.begin();
        playerSprite.draw(spriteBatch);
        spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            mapManager.setMap(MapType.MAP_1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            mapManager.setMap(MapType.MAP_2);
        }



        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            boolean alive = player.takeDamage(10); // deal 10 damage
            Gdx.app.log("DAMAGE", "Player took 10 damage. Current HP: " + player.getHealth());
        
            if (!alive) {
                Gdx.app.log("DAMAGE", "Player has died!");
                // Optional: trigger death state, animation, etc.
            }
        }
        

        uiStage.act(delta);
        uiStage.draw();
        playerHUD.update();

    }

    @Override
    public void show() {
        keyHandler.addListener(this);
        Gdx.input.setInputProcessor(keyHandler); // Fjernet uiStage for å unngå crash
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
    public void keyPressed(KeyHandler keyHandler, Keys key) {
        playerInput(keyHandler, key);

    }

    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
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

    private void playerInput(KeyHandler keyHandler, Keys key) {
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

    private void movePlayerReleased (KeyHandler keyHandler, Keys key) {

        switch (key) {
            case LEFT:
            case RIGHT:
                xFactor = 0;
                if (keyHandler.isKeyPressed(Keys.LEFT)) {
                    xFactor = -3;
                } else if (keyHandler.isKeyPressed(Keys.RIGHT)) {
                    xFactor = 3;
                }
                break;
            case UP:
            case DOWN:
                yFactor = 0;
                if (keyHandler.isKeyPressed(Keys.UP)) {
                    yFactor = 3;
                } else if (keyHandler.isKeyPressed(Keys.DOWN)) {
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
            playerBody.applyLinearImpulse(
            (xFactor * 3 - playerBody.getLinearVelocity().x * playerBody.getMass()),
            (yFactor * 3 - playerBody.getLinearVelocity().y * playerBody.getMass()),
            playerBody.getWorldCenter().x, playerBody.getWorldCenter().y, true
            );
        }
    }


    @Override
    public void mapChanged(Map map) {
        this.map = map;
        this.mapRenderer.setMap(map.getTiledMap());
    }
    
}

