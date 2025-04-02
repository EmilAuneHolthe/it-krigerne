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
import inf112.skeleton.audio.AudioTypes;
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
    private Player player;
    private final World world;
    private final AssetManager assetManager;
    private SpriteBatch spriteBatch;

    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;

    private static final short BIT_PLAYER = GamePanel.BIT_Player;
    private final GLProfiler profiler;

    private Texture playerIdleFrontTexture;
    private Map map;
    private final MapManager mapManager; 
    private Body playerBody;


    //UI
    private Stage uiStage;
    private Texture healthTexture;
    private Texture backgroundTexture;
    private PlayerHUD playerHUD;


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

        player = new Player(context, world, body, 100, 10, map.getPlayerSpawn().x * UNIT_SCALE, map.getPlayerSpawn().y * UNIT_SCALE);
        player.loadTextures(); // Legg til denne linjen

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

        player.render(spriteBatch);


        Gdx.app.debug("renderinfo", "Bindings" + profiler.getTextureBindings());
        Gdx.app.debug("renderinfo", "Drawcells" + profiler.getDrawCalls());
        profiler.reset();
        //Gdx.app.debug(FPSLogger.class.getSimpleName(), "FPS: " + Gdx.graphics.getFramesPerSecond());
        
        
        
        
        


        /* Dette er bare test, skal ikke stå sånt, ikke legg til andre funksjoner som 
        bruker Gdx.input.isKeyJustPressed, bruk keyhandleren!*/ 
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            mapManager.setMap(MapType.MAP_1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            mapManager.setMap(MapType.MAP_2);
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
        player.playerInput(keyHandler, key);
        player.playerTakeDamage(keyHandler, key);
    }

    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
        player.movePlayerReleased(keyHandler, key);
    }


    @Override
    public void mapChanged(Map map) {
        this.map = map;
        this.mapRenderer.setMap(map.getTiledMap());
    }
    
}

