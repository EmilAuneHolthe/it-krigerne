// package inf112.skeleton.app.screenTest;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Input;
// import com.badlogic.gdx.Files;
// import com.badlogic.gdx.Graphics;
// import com.badlogic.gdx.graphics.GL20;
// import com.badlogic.gdx.scenes.scene2d.Stage;
// import com.badlogic.gdx.scenes.scene2d.ui.Table;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import inf112.skeleton.app.GamePanel;
// import inf112.skeleton.app.screen.MainMenuScreen;

// class MainMenuScreenTest {
    
//     private MainMenuScreen mainMenuScreen;
//     private GamePanel mockGamePanel;
//     private Stage mockStage;
//     private Table mockTable;
    
//     @BeforeEach
//     void setUp() {
//         mockGamePanel = mock(GamePanel.class);
//         mockStage = mock(Stage.class);
//         mockTable = mock(Table.class);
        
//         Gdx.input = mock(Input.class);
//         Gdx.graphics = mock(Graphics.class);
//         Gdx.files = mock(Files.class);
//         Gdx.gl = mock(GL20.class);
        
//         mainMenuScreen = new MainMenuScreen(mockGamePanel);
//     }
    
//     @Test
//     void testInitialization() {
//         assertNotNull(mainMenuScreen, "MainMenuScreen should be initialized");
//     }
    
//     @Test
//     void testInputProcessorSet() {
//         verify(Gdx.input, times(1)).setInputProcessor(any(Stage.class));
//     }
    
//     @Test
//     void testStageContainsTable() {
//         assertNotNull(mockTable, "MainMenuScreen should contain a UI Table");
//     }
// }