package inf112.skeleton.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorldContactListenerTest {
    
    private Contact mockContact;
    private Fixture mockFixtureA;
    private Fixture mockFixtureB;
    
    @BeforeEach
    void setUp() {
        mockContact = mock(Contact.class);
        mockFixtureA = mock(Fixture.class);
        mockFixtureB = mock(Fixture.class);
        
        when(mockContact.getFixtureA()).thenReturn(mockFixtureA);
        when(mockContact.getFixtureB()).thenReturn(mockFixtureB);
    }
    
    @Test
    void testContact() {
        // Test that contact methods can be called
        assertNotNull(mockContact.getFixtureA());
        assertNotNull(mockContact.getFixtureB());
    }
}
