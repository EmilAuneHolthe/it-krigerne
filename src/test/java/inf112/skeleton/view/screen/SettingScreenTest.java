// package inf112.skeleton.app.screenTest;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;
// import static org.mockito.Mockito.mockStatic;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Input;
// import com.badlogic.gdx.Files;
// import com.badlogic.gdx.Graphics;
// import com.badlogic.gdx.graphics.GL20;
// import com.badlogic.gdx.assets.AssetManager;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.files.FileHandle;
// import com.badlogic.gdx.scenes.scene2d.Stage;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.MockedStatic;

// import inf112.skeleton.app.GamePanel;
// import inf112.skeleton.app.screen.SettingScreen;

// class SettingScreenTest {
    
//     private SettingScreen settingScreen;
//     private GamePanel mockGamePanel;
//     private Stage mockStage;
//     private Texture mockTexture;
    
//     @BeforeEach
//     void setUp() {
//         mockGamePanel = mock(GamePanel.class);
//         mockStage = mock(Stage.class);
//         mockTexture = mock(Texture.class);
        
//         Gdx.input = mock(Input.class);
//         Gdx.graphics = mock(Graphics.class);
//         Gdx.files = mock(Files.class);
//         Gdx.gl = mock(GL20.class); // Mock OpenGL to prevent crashes
        
//         // Mock file loading to prevent texture errors
//         FileHandle mockFileHandle = mock(FileHandle.class);
//         when(Gdx.files.internal("settings.png")).thenReturn(mockFileHandle);
//         when(mockFileHandle.path()).thenReturn("mocked_path/settings.png");
//         when(mockFileHandle.name()).thenReturn("settings.png");
        
//         // Mock texture creation to prevent OpenGL dependency
//         try (MockedStatic<Texture> mockedTexture = mockStatic(Texture.class)) {
//             mockedTexture.when(() -> new Texture(mockFileHandle)).thenReturn(mockTexture);
//             settingScreen = new SettingScreen(mockGamePanel);
//         }
//     }
    
//     @Test
//     void testInitialization() {
//         assertNotNull(settingScreen, "SettingScreen should be initialized");
//     }
    
//     @Test
//     void testInputProcessorSet() {
//         verify(Gdx.input, times(1)).setInputProcessor(any(Stage.class));
//     }
    
//     @Test
//     void testBackgroundTextureLoads() {
//         assertNotNull(mockTexture, "Background texture should be loaded");
//     }
// }
