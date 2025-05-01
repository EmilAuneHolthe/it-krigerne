package inf112.skeleton.model.entity.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PlayerTest {

    @Mock GamePanel context;
    @Mock World world;
    @Mock Body body;
    Player player;

    @BeforeEach
    void setUp() {
        // Initialiser Mockito-mocker
        MockitoAnnotations.openMocks(this);
        Gdx.app = mock(Application.class);
        // Opprett en Player med initial helse=100, skade=20, posisjon (0,0)
        player = new Player(context, world, body, 100, 20, 0f, 0f, CharacterType.SOLDIER);
        // Sørg for at statisk flagg nullstilles før hver test
        Player.isDead = false;
    }

    @Test
    void healthReducesWhenTakingDamage() {
      // **Helse og skade:** Når spilleren tar skade, skal helsen reduseres tilsvarende.
        assertTrue(player.getHealth() == 100, "Spilleren skal ha 100 liv ved oppstart");
        player.takeDamage(30);
        // Forvent at helsen er redusert fra 100 til 70
        assertEquals(70, player.getHealth(), "Helsen skal reduseres med 30");
        // Spilleren skal fortsatt være i live (alive = true) siden helse > 0
        //assertTrue(player.alive, "Spilleren skal fortsatt være i live når helse > 0");
        //assertFalse(Player.isDead, "Spilleren skal ikke flagges som død enda");
    }

    @Test
    void healthDoesNotGoNegative() {
        // **Helse ikke negativ:** Hvis spilleren tar mer skade enn den har helse, skal helsen ikke bli negativ.
        player.takeDamage(150);  // Mer skade enn spillerens helse (100)
        // Forvent at getHealth() returnerer 0 (ikke negativ verdi)
        assertEquals(0, player.getHealth(), "Helsen skal ikke gå under 0");
        // Etter dødelig skade skal spilleren markeres som død
        assertFalse(player.alive, "Spilleren skal være 'alive = false' når helse har nådd 0");
        assertTrue(Player.isDead, "Statisk flagg isDead skal være true ved død");
    }

    @Test
    void manaRegeneratesUpToMax() {
        // **Mana og mana-regenerering:** Mana skal gradvis regenereres opp til maksverdien.
        // Sett opp en spiller med begrenset mana for test (f.eks. maks mana = 100)
        player.setCurrentMana(50);
        player.setMaxMana(100);
        // Simuler at det går tid nok til å regenerere 60 mana (f.eks. 6 sekunder hvis 10 mana/sek)
        for (int i = 0; i < 300; i++) {  // iterer som om 6 sekunder har passert (60 * 0.1s ticks)
            player.regenerateMana(i);  // Antar Player.update() håndterer mana-regenerering per frame
        }
        // Forvent at mana har økt, men ikke overstiger maxMana
        int regeneratedMana = player.getCurrentMana();
        assertTrue(regeneratedMana > 50, "Mana skal ha økt etter regenerering");
        assertEquals(100, regeneratedMana, "Mana skal ikke overstige maksverdien (100)");
    }

    @Test
    void useHealthItemIncreasesHealth() {
        // **Bruk av helse-item:** Å bruke en helsegjenstand skal øke spillerens helse (opp til en øvre grense).
        // Skad spilleren litt først
        player.takeDamage(60);  // reduser helse til 40
        assertEquals(40, player.getHealth());
        // Opprett et helse-item og simuler at spilleren plukker det opp
        Item healthPotion = new Item(context, world, ItemType.HEALTH, player.getX(), player.getY());
        player.getInventory().pickUpItems(new Array<Item>(new Item[]{healthPotion}));
        // Bruk helse-itemet (antar at det finnes en metode for å bruke valgt item)
        player.getInventory().useSelectedItem();
        // Forvent at spillerens helse har økt, men ikke over opprinnelig verdi 100
        assertTrue(player.getHealth() > 40, "Helse skal øke ved bruk av helse-potion");
        assertTrue(player.getHealth() <= 100, "Helse skal ikke overstige opprinnelig maks (100) ved bruk av potion");
    }

    @Test
    void playerDiesWhenHealthDepletes() {
        // **Død/overlevelse:** Når helse går til 0 eller lavere, skal spilleren behandles som død.
        player.takeDamage(100);  // nøyaktig dødelig skade
        // Forvent at spilleren er markert som død
        assertFalse(player.alive, "Spilleren skal ikke være i live etter å ha tatt dødelig skade");
        assertTrue(Player.isDead, "isDead-flagget skal settes når spilleren dør");
        // Forvent at dødshåndtering (killPlayer) er trigget, f.eks. DeathOverlay aktivert (indirekte sjekket via isDead flagg her)
    }
}
