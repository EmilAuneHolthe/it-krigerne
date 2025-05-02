package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import inf112.skeleton.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PlayerAnimationTest extends BaseTest {
    @Mock private Application app;
    @Mock private TextureAtlas mockCharacterAtlas;
    @Mock private TextureAtlas mockAttackAtlas;
    @Mock private TextureAtlas.AtlasRegion mockAtlasRegion;

    private PlayerAnimation playerAnimation;
    private static final float DELTA_TIME = 0.016f; // Simulating 60 FPS
    private static final float ATTACK_DURATION = 1.0f;
    private static final float DIRECTION_CHANGE_DELAY = 0.1f;

    @BeforeAll
    static void initGdx() {
        // Initialize headless application for testing
        Gdx.app = mock(Application.class);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Set up texture atlas mocks
        when(mockCharacterAtlas.findRegion(anyString())).thenReturn(mockAtlasRegion);
        when(mockAttackAtlas.findRegion(anyString())).thenReturn(mockAtlasRegion);
        
        // Create PlayerAnimation instance with mocked dependencies
        playerAnimation = new PlayerAnimation(CharacterType.SOLDIER);
    }

    @Test
    void testMovementState() {
        // Test moving state changes
        playerAnimation.setMoving(true);
        playerAnimation.update(DELTA_TIME);
        
        // Test stopping
        playerAnimation.setMoving(false);
        playerAnimation.update(DELTA_TIME);
    }

    @Test
    void testDirectionChanges() {
        // Test all four directions
        String[] directions = {"Up", "Down", "Left", "Right"};
        for (String direction : directions) {
            playerAnimation.setDirection(direction);
            playerAnimation.update(DELTA_TIME);
        }
    }

    @Test
    void testDirectionChangeCooldown() {
        // Set initial direction
        playerAnimation.setDirection("Down");
        playerAnimation.setMoving(true);
        
        // Change direction
        playerAnimation.setDirection("Up");
        
        // Try to change direction again immediately
        playerAnimation.setDirection("Down");
        
        // Update just before cooldown ends
        float elapsedTime = 0;
        while (elapsedTime < DIRECTION_CHANGE_DELAY - DELTA_TIME) {
            playerAnimation.update(DELTA_TIME);
            elapsedTime += DELTA_TIME;
        }
        
        // Direction should be able to change after cooldown
        playerAnimation.update(DELTA_TIME);
        playerAnimation.setDirection("Left");
    }

    @Test
    void testAttackSequence() {
        // Start attack
        playerAnimation.startAttack();
        
        // Update through attack duration
        float elapsedTime = 0;
        while (elapsedTime < ATTACK_DURATION) {
            playerAnimation.update(DELTA_TIME);
            elapsedTime += DELTA_TIME;
            
            // Try to start another attack during the sequence
            if (elapsedTime > ATTACK_DURATION / 2) {
                playerAnimation.startAttack();
            }
        }
    }

    @Test
    void testMovementDuringAttack() {
        // Start attack
        playerAnimation.startAttack();
        
        // Try to move in different directions during attack
        String[] directions = {"Up", "Down", "Left", "Right"};
        for (String direction : directions) {
            playerAnimation.setDirection(direction);
            playerAnimation.setMoving(true);
            playerAnimation.update(DELTA_TIME);
        }
    }
} 