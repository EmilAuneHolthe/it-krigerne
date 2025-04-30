package inf112.skeleton.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.WorldFunctions;

import static inf112.skeleton.model.GamePanel.UNIT_SCALE;

import java.security.Key;
import java.util.ArrayList;

import javax.swing.border.Border;

import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.enemy.EnemyFactory;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemFactory;
import inf112.skeleton.model.entity.player.CharacterType;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerFactory;
import inf112.skeleton.model.entity.player.PlayerInteractions;
import inf112.skeleton.model.map.Borders;
import inf112.skeleton.model.map.Map;
import inf112.skeleton.model.map.MapListener;
import inf112.skeleton.model.map.MapManager;
import inf112.skeleton.model.map.MapType;
import inf112.skeleton.view.GameRenderer;
import inf112.skeleton.model.map.MapChanger;

public class GameScreen extends AbstractScreen {

    private final OrthographicCamera camera;
    private final GameRenderer gameRenderer;
    private final PlayerInteractions playerInteractions;
    private WorldFunctions  worldFunctions;


    public GameScreen(GamePanel context) {
        super(context);
        this.camera = context.getCamera();
        this.gameRenderer = context.getGameRenderer();
        playerInteractions = new PlayerInteractions(context);
        context.setPlayerInteractions(playerInteractions);
        worldFunctions = new WorldFunctions(context);
  
        gameRenderer.updateDoors();
    }

    @Override   
    public void render(float delta) {
        gameRenderer.render(delta);
        worldFunctions.update(delta);
        System.out.println(Player.isDead);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.setToOrtho(false, viewport.getWorldWidth(), viewport.getWorldHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        gameRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }

    @Override
    public void show() {
        keyHandler.addListener(this);

    }
    @Override
    public void keyPressed(KeyHandler keyHandler, Keys key) {
        context.getPlayer().playerInput(keyHandler, key);
        
    
        if ((Player.isDead == true) && (key == Keys.QUIT)) {
            System.out.println("kuk");
            Gdx.app.exit();
        }
    }    

    @Override
    public void keyReleased(KeyHandler keyHandler, Keys key) {
        context.getPlayer().movePlayerReleased(keyHandler, key);
    }

}




