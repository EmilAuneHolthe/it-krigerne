package inf112.skeleton.view.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import inf112.skeleton.model.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoadingScreenTest {
    private LoadingScreen loadingScreen;
    private GamePanel mockGamePanel;
    private AssetManager mockAssetManager;

    @BeforeEach
    void setUp() {
        mockGamePanel = mock(GamePanel.class);
        mockAssetManager = mock(AssetManager.class);
        when(mockGamePanel.getAssetManager()).thenReturn(mockAssetManager);
        loadingScreen = new LoadingScreen(mockGamePanel);
    }

    @Test
    void testConstructor() {
        assertNotNull(loadingScreen, "LoadingScreen should be initialized");
        verify(mockAssetManager).setLoader(eq(TiledMap.class), any());
        verify(mockAssetManager).load(eq("map/testMap/testMap.tmx"), eq(TiledMap.class));
        verify(mockAssetManager).load(eq("map/SampleMap/samplemap.tmx"), eq(TiledMap.class));
    }

    @Test
    void testRenderWhenAssetsLoaded() {
        when(mockAssetManager.update()).thenReturn(true);
        loadingScreen.render(0.1f);
        verify(mockGamePanel).setScreen(eq(ScreenType.GAME));
    }

    @Test
    void testRenderWhenAssetsNotLoaded() {
        when(mockAssetManager.update()).thenReturn(false);
        loadingScreen.render(0.1f);
        verify(mockGamePanel, never()).setScreen(any(ScreenType.class));
    }
}
