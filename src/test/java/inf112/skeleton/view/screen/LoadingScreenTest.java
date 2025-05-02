package inf112.skeleton.view.screen;

import com.badlogic.gdx.assets.AssetManager;
import inf112.skeleton.model.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void testRenderWhenAssetsNotLoaded() {
        when(mockAssetManager.update()).thenReturn(false);
        loadingScreen.render(0.1f);
        verify(mockGamePanel, never()).setScreen(any(ScreenType.class));
    }
}
