package inf112.skeleton;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import inf112.skeleton.Main;
import inf112.skeleton.model.GamePanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MainTest {
    
    private Lwjgl3ApplicationConfiguration spyConfig;
    private Lwjgl3Application mockApplication;
    private GamePanel mockGamePanel;
    
    @BeforeEach
    void setUp() {
        spyConfig = spy(new Lwjgl3ApplicationConfiguration());
        mockApplication = mock(Lwjgl3Application.class);
        mockGamePanel = mock(GamePanel.class);
    }
    
    @Test
    void testGameWindowConfiguration() {
        spyConfig.setTitle("The Invisible Stairs");
        spyConfig.setWindowedMode(960, 540);
        
        verify(spyConfig).setWindowedMode(960, 540);
    }
    
    @Test
    void testGameInitialization() {
        try (var mockedMain = mockStatic(Main.class)) {
            mockedMain.when(() -> Main.main(any())).thenAnswer(invocation -> null);
            Main.main(new String[]{});
            mockedMain.verify(() -> Main.main(any()), times(1));
        }
    }
}