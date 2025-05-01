package inf112.skeleton.model.entity.enemy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.player.CharacterType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EnemyTest {

    @Mock GamePanel context;
    @Mock World world;
    @Mock Body body;
    // Vi kan bruke en ekte Array fra LibGDX for enkelhets skyld i noen tester
    Array<Enemy> enemyList;
    Enemy enemy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Stub AudioHandler for å unngå NPE ved fiendedød (spiller av død-lyd)
        when(context.getAudioHandler()).thenReturn(mock(inf112.skeleton.audio.AudioHandler.class));
        // Stub GamePanel.getEnemies() til å returnere en liste som fienden kan fjerne seg fra
        enemyList = spy(new Array<>());
        when(context.getEnemy()).thenReturn(enemyList);
        // Opprett en Enemy (f.eks. SKELETON) med kjent startverdi
        enemy = new Enemy(context, world, body, CharacterType.SKELETON, "TestFiende");
        enemyList.add(enemy);
    }

    @Test
    void enemyHasDefaultHealthAndDamage() {
        // **Opprettelse og defaultverdier:** Kontroller at fienden har riktig standard helse og skade basert på type.
        // For CharacterType.SKELETON forventes f.eks. helse=100 og skade=25 (i henhold til EnemyTypes)
        assertEquals(100, enemy.getHealth(), "Fienden skal ha korrekt start-helse for sin type");
        assertEquals(25, enemy.getDamage(), "Fienden skal ha korrekt skadeverdi for sin type");
        assertEquals("TestFiende", enemy.getName(), "Fiendens navn skal settes riktig ved opprettelse");
    }

    @Test
    void enemyTakesDamageAndSurvivesIfHealthRemains() {
        // **Skade håndtering:** Fienden skal miste helse når den tar skade, men overleve hvis helse fortsatt er >0.
        boolean stillAlive = enemy.takeDamage(40);
        // Forvent at helse er redusert (100 -> 60) og at metoden returnerer true (fienden lever)
        assertEquals(60, enemy.getHealth(), "Fiendens helse skal reduseres med 40");
        assertTrue(stillAlive, "Fienden skal fortsatt være i live (returnerer true) når helse > 0");
        // Kontroller at fienden ikke er fjernet fra verden eller listen enda
        verify(world, never()).destroyBody(body);
        assertTrue(enemyList.contains(enemy, true), "Fienden skal fortsatt være i fiendelisten når den lever");
    }

    @Test
    void enemyDiesWhenHealthDepletes() {
        // **Fjerning ved død:** Hvis fiendens helse går til 0 eller lavere, skal fienden fjernes (body destrueres og fjernet fra liste).
        int lethalDamage = enemy.getHealth();  // skade lik nåværende helse for å drepe fienden
        boolean stillAlive = enemy.takeDamage(lethalDamage);
        // Forvent at metoden returnerer false (fienden død)
        assertFalse(stillAlive, "takeDamage skal returnere false når fienden dør");
        assertEquals(0, enemy.getHealth(), "Fiendens helse skal være 0 etter dødelig skade");
        // Body skal være destruert i Box2D-verden:
        verify(world).destroyBody(body);
        // Fienden skal fjerne seg selv fra GamePanel sin fiendeliste:
        verify(enemyList).removeValue(enemy, true);
    }

    @Test
    void enemyMovesTowardsPlayer() {
        // **Bevegelse mot spiller:** Fienden skal bevege seg mot spillerens posisjon når oppdatert.
        // Lag en mock Player med en gitt posisjon
        Player mockPlayer = mock(Player.class);
        // Sett fiendens nåværende posisjon og spillers posisjon slik at de er forskjellige
        Vector2 enemyPos = new Vector2(5f, 5f);
        when(body.getPosition()).thenReturn(enemyPos);       // fiendens posisjon (5,5)
        when(mockPlayer.getPosition()).thenReturn(new Vector2(0f, 5f)); // spillerens posisjon (0,5)
        // Kall oppdatering (som skal forsøke å flytte fienden mot spilleren)
        enemy.update(mockPlayer);
        // Verifiser at fiendens body fikk en ikke-null hastighet (fienden beveger seg)
        verify(body).setLinearVelocity(argThat(v -> v.len() > 0f));
        // Siden spilleren er til venstre for fienden (x=0 < 5), forvent at fienden beveger seg mot venstre (negativ x-retning)
        verify(body).setLinearVelocity(argThat(v -> v.x < 0f));
    }

    @Test
    void enemyAttacksPlayerOnlyWithinRange() {
        // **Angrepstimer/rekkevidde:** Fienden skal kun skade spilleren når den er innenfor angrepsrekkevidde.
        Player mockPlayer = mock(Player.class);
        // Konfigurer avstand utenfor rekkevidde (> 0.8)
        when(body.getPosition()).thenReturn(new Vector2(0f, 0f));
        when(mockPlayer.getPosition()).thenReturn(new Vector2(5f, 5f));  // langt unna
        enemy.update(mockPlayer);
        // Forvent at fienden IKKE skader spilleren når avstanden er for stor
        verify(mockPlayer, never()).playerTakeDamage(any(Enemy.class));

        // Konfigurer avstand innenfor rekkevidde (< 0.8)
        when(mockPlayer.getPosition()).thenReturn(new Vector2(0.3f, 0.4f));  // veldig nær (ca 0.5 unna)
        enemy.update(mockPlayer);
        // Nå skal fienden angripe spilleren
        verify(mockPlayer).playerTakeDamage(enemy);
    }
}
