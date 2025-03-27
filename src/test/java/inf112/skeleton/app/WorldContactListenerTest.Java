package inf112.skeleton.app;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorldContactListenerTest {
    
    private WorldContactListener worldContactListener;
    private Contact mockContact;
    private Fixture mockFixtureA;
    private Fixture mockFixtureB;
    
    @BeforeEach
    void setUp() {
        worldContactListener = new WorldContactListener();
        mockContact = mock(Contact.class);
        mockFixtureA = mock(Fixture.class);
        mockFixtureB = mock(Fixture.class);
        
        when(mockContact.getFixtureA()).thenReturn(mockFixtureA);
        when(mockContact.getFixtureB()).thenReturn(mockFixtureB);
    }
    
    @Test
    void testBeginContact() {
        worldContactListener.beginContact(mockContact);
        verify(mockContact, times(1)).getFixtureA();
        verify(mockContact, times(1)).getFixtureB();
    }
    
    @Test
    void testEndContact() {
        worldContactListener.endContact(mockContact);
        verify(mockContact, times(1)).getFixtureA();
        verify(mockContact, times(1)).getFixtureB();
    }
    
    @Test
    void testPreSolveDoesNotCrash() {
        Manifold mockManifold = mock(Manifold.class);
        worldContactListener.preSolve(mockContact, mockManifold);
        // No assertions needed; we just ensure it does not throw exceptions
    }
    
    @Test
    void testPostSolveDoesNotCrash() {
        ContactImpulse mockImpulse = mock(ContactImpulse.class);
        worldContactListener.postSolve(mockContact, mockImpulse);
        // No assertions needed; we just ensure it does not throw exceptions
    }
}
