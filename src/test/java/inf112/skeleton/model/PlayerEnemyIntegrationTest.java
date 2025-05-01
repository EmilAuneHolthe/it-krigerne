package inf112.skeleton.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.enemy.EnemyTypes;
import inf112.skeleton.model.entity.item.Inventory;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.PlayerInteractions;
import inf112.skeleton.model.entity.player.CharacterType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlayerEnemyIntegrationTest {

    @Mock GamePanel context;
    @Mock World world;
    @Mock Body playerBody;
    @Mock Body enemyBody;
    Player player;
    Enemy enemy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Stub AudioHandler i context for å unngå feil ved lydeffekter (f.eks. død)
        when(context.getAudioHandler()).thenReturn(mock(inf112.skeleton.audio.AudioHandler.class));
        // Opprett en Player og en Enemy for testene
        player = new Player(context, world, playerBody, 100, 15, 0f, 0f, CharacterType.SOLDIER);
        enemy = new Enemy(context, world, enemyBody, CharacterType.SKELETON, "Enemy");
        // Reset statiske/spill-tilstander
        Player.isDead = false;
        player.alive = true;
    }


@BeforeAll
static void initGdx() {
    // HeadlessApplication for Gdx.app etc.
    HeadlessApplicationConfiguration cfg = new HeadlessApplicationConfiguration();
    new HeadlessApplication(new ApplicationAdapter() {}, cfg);
    // --- stub Gdx.files ---------------------------------
    Gdx.files = new HeadlessFiles();     // fra gdx-headless: gir tomme FileHandle
}


    @Test
    void playerAttackKillsEnemy() {
        // **Player angriper Enemy -> Enemy dør**
        // Sett opp fienden med lav helse slik at ett angrep dreper den
        enemy.setHealth(20);
        // Sørg for at spilleren kan angripe
        player.canAttack = true;
        // Legg fienden i en liste og konfigurer GamePanel for PlayerInteractions
        Array<Enemy> enemies = new Array<>();
        enemies.add(enemy);
        when(context.getEnemy()).thenReturn(enemies);
        // Spilleren angriper fienden
        PlayerInteractions interactions = new PlayerInteractions(context, player);
        interactions.attackEnemy(player, enemies);
        // Forvent at fienden ble fjernet fra listen (død)
        assertFalse(enemies.contains(enemy, true), "Fienden skal fjernes fra listen etter å ha blitt drept av spilleren");
        // Fiendens helse skal være 0 og body skal være destruert
        assertEquals(0, enemy.getHealth(), "Fiendens helse skal være 0 etter å ha blitt drept");
        verify(world).destroyBody(enemyBody);
    }

    @Test
    void enemyDamagesPlayerOnContact() {
        // **Enemy skader Player ved nærkontakt**
        // Sett spillerens posisjon nær fienden
        when(playerBody.getPosition()).thenReturn(new Vector2(0f, 0f));
        when(enemyBody.getPosition()).thenReturn(new Vector2(0.5f, 0f));  // innenfor ~0.5 distanse
        // Startverdier for helse
        int playerStartHealth = player.getHealth();
        // Oppdater fienden (som burde angripe spilleren pga nærhet)
        enemy.update(player);
        // Forvent at spilleren har tatt skade tilsvarende fiendens skadeverdi
        int expectedHealth = playerStartHealth - enemy.getDamage();
        assertEquals(expectedHealth, player.getHealth(), "Spillerens helse skal reduseres når fienden er i angrepsrekkevidde");
        // Etter angrepet skal spilleren være markert død hvis helsen gikk <= 0
        if (playerStartHealth <= enemy.getDamage()) {
            assertTrue(Player.isDead, "Spilleren skal være død om den gikk tom for helse etter fiendeangrep");
        } else {
            assertFalse(Player.isDead, "Spilleren skal overleve hvis den har helse igjen etter fiendeangrep");
        }
    }

    @Test
    void itemCanBePickedUpAndUsed() {
        // **Items kan plukkes opp og brukes**
        // Opprett inventory for spilleren
        Inventory inventory = new Inventory(4, player, context, world);
        // Plasser et helse-item på samme posisjon som spilleren og et mana-item like ved
        Item healthItem = new Item(context, world, ItemType.HEALTH, player.getX(), player.getY());
        Item manaItem = new Item(context, world, ItemType.MANA, player.getX(), player.getY());
        Array<Item> itemsOnGround = new Array<>();
        itemsOnGround.add(healthItem);
        itemsOnGround.add(manaItem);
        // Simuler at spilleren plukker opp begge items
        inventory.pickUpItems(itemsOnGround);
        // Forvent at inventory nå inneholder items (f.eks. itemCount økt til 2)
        assertEquals(2, inventory.getItemCount(), "Spilleren skal ha plukket opp 2 items");
        // Skad spilleren og tøm mana for å teste effekten av items
        player.setHealth(50);
        player.setCurrentMana(0);
        // Bruk helse-potion (forvent at helse øker)
        inventory.setSelectedItemIndex(0);  // velg første item (health)
        inventory.useSelectedItem();
        assertTrue(player.getHealth() > 50, "Helse-potion skal øke spillerens helse");
        // Bruk mana-potion (forvent at mana øker)
        inventory.setSelectedItemIndex(1);  // velg andre item (mana)
        inventory.useSelectedItem();
        assertTrue(player.getCurrentMana() > 0, "Mana-potion skal gi spilleren mana");
        // Forvent at items er brukt opp (fjernet fra inventory)
        assertEquals(0, inventory.getItemCount(), "Items skal være fjernet fra inventory etter bruk");
    }
}
