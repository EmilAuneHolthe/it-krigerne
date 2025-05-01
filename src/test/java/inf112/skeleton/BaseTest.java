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

    @org.junit.jupiter.api.Test
    void sampleTest() {
        // A simple test to ensure the setup works
        org.junit.jupiter.api.Assertions.assertTrue(true, "Sample test passed.");
    }
    @BeforeAll
    static void init() {
        // Mock GL20 before creating the application
        GL20 gl20 = Mockito.mock(GL20.class);
        
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
            public void create() {
                // Empty implementation for testing
            }
            @Override
            public void resize(int width, int height) {
                // Empty implementation for testing
            }
            @Override
            public void render() {
                // Empty implementation for testing
            }
            @Override
            public void pause() {
                // Empty implementation for testing
            }
            
            @Override
            public void resume() {
                // Empty implementation for testing
            }
            
            @Override
            public void dispose() {
                // Empty implementation for testing
            }
        }, new HeadlessApplicationConfiguration());
    }
} 