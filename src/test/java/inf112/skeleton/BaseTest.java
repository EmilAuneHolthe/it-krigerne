package inf112.skeleton;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.backends.headless.HeadlessNet;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

public class BaseTest {
    @BeforeAll
    static void init() {
        // Mock GL20 before creating the application
        GL20 gl20 = Mockito.mock(GL20.class);
        
        // Create headless application
        HeadlessApplication app = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });
        
        // Set the mocked GL20
        Gdx.gl = gl20;
        Gdx.gl20 = gl20;
        
        // Mock graphics
        Gdx.graphics = new MockGraphics();
        
        // Mock files
        Gdx.files = new HeadlessFiles();
        
        // Mock net
        Gdx.net = new HeadlessNet(new HeadlessApplicationConfiguration());
        
        // Mock preferences
        Gdx.app = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {}
            @Override
            public void resize(int width, int height) {}
            @Override
            public void render() {}
            @Override
            public void pause() {}
            @Override
            public void resume() {}
            @Override
            public void dispose() {}
        }, new HeadlessApplicationConfiguration());
    }
} 