package inf112.skeleton.model.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.model.entity.player.CharacterType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnemySpawnTest {
    @Test
    void testConstructor() {
        // Given
        Vector2 position = new Vector2(10.5f, 20.5f);
        String name = "TestEnemy";
        CharacterType characterType = CharacterType.SOLDIER;

        // When
        EnemySpawn enemySpawn = new EnemySpawn(position, name, characterType);

        // Then
        assertNotNull(enemySpawn, "EnemySpawn should be initialized");
        assertEquals(position, enemySpawn.position());
        assertEquals(name, enemySpawn.name());
        assertEquals(characterType, enemySpawn.characterType());
    }

    @Test
    void testGetPosition() {
        // Given
        Vector2 position = new Vector2(15.0f, 25.0f);
        EnemySpawn enemySpawn = new EnemySpawn(position, "TestEnemy", CharacterType.SOLDIER);

        // When
        Vector2 retrievedPosition = enemySpawn.position();

        // Then
        assertEquals(position, retrievedPosition);
        assertEquals(15.0f, retrievedPosition.x);
        assertEquals(25.0f, retrievedPosition.y);
    }

    @Test
    void testGetName() {
        // Given
        String name = "TestEnemy";
        EnemySpawn enemySpawn = new EnemySpawn(new Vector2(0, 0), name, CharacterType.SOLDIER);

        // When
        String retrievedName = enemySpawn.name();

        // Then
        assertEquals(name, retrievedName);
    }

    @Test
    void testGetCharacterType() {
        // Given
        CharacterType characterType = CharacterType.SOLDIER;
        EnemySpawn enemySpawn = new EnemySpawn(new Vector2(0, 0), "TestEnemy", characterType);

        // When
        CharacterType retrievedType = enemySpawn.characterType();

        // Then
        assertEquals(characterType, retrievedType);
    }
} 