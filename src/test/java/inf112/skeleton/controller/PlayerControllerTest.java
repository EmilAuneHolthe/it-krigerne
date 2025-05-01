package inf112.skeleton.controller;

import static org.mockito.Mockito.*;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.item.Inventory;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerAnimation;
import inf112.skeleton.model.entity.player.PlayerInteractions;
import inf112.skeleton.model.entity.player.PlayerMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlayerControllerTest {

    @Mock
    private Player mockPlayer;
    @Mock
    private PlayerInteractions mockPlayerInteractions;
    @Mock
    private GamePanel mockContext;
    @Mock
    private Inventory mockInventory;
    @Mock
    private PlayerMovement mockMovement;
    @Mock
    private PlayerAnimation mockAnimation;

    private PlayerController playerController;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock player methods
        when(mockPlayer.getInventory()).thenReturn(mockInventory);
        when(mockPlayer.getMovement()).thenReturn(mockMovement);
        when(mockPlayer.getAnimation()).thenReturn(mockAnimation);

        // Initialize PlayerController
        playerController = new PlayerController(mockPlayer, mockPlayerInteractions, mockContext);
    }

    @Test
    void testPlayerInput_Attack() {
        playerController.playerInput(null, Keys.ATTACK);
    }

    @Test
    void testPlayerInput_Interact() {
        // Simulate pressing the INTERACT key
        playerController.playerInput(null, Keys.INTERACT);

        // Verify pickUpItems is called
        verify(mockInventory).pickUpItems(any());
    }

    @Test
    void testPlayerInput_Move() {
        // Simulate pressing the UP key
        playerController.playerInput(null, Keys.UP);

        // Verify movement and animation updates
        verify(mockMovement).handleInput(Keys.UP);
        verify(mockAnimation).setMoving(anyBoolean());
        verify(mockAnimation).setDirection(anyString());
    }

    @Test
    void testPlayerInput_QuitWhenDead() {
        // Simulate pressing the QUIT key
        playerController.playerInput(null, Keys.QUIT);

        // Verify quit logic (if implemented)
        // Add assertions or verifications for quit behavior
    }
}