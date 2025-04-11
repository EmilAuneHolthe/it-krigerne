package inf112.skeleton.model.map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MapListenerTest {
    private TestMapListener mapListener;
    
    @Mock
    private Map mockMap;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapListener = new TestMapListener();
    }
    
    @Test
    void testMapChanged() {
        // When
        mapListener.mapChanged(mockMap);
        
        // Then
        assertTrue(mapListener.wasMapChangedCalled());
        assertSame(mockMap, mapListener.getLastMap());
    }
    
    @Test
    void testMapChangedWithNull() {
        // When
        mapListener.mapChanged(null);
        
        // Then
        assertTrue(mapListener.wasMapChangedCalled());
        assertNull(mapListener.getLastMap());
    }
    
    @Test
    void testMultipleMapChanges() {
        // Given
        Map anotherMockMap = mock(Map.class);
        
        // When
        mapListener.mapChanged(mockMap);
        mapListener.mapChanged(anotherMockMap);
        
        // Then
        assertEquals(2, mapListener.getChangeCount());
        assertSame(anotherMockMap, mapListener.getLastMap());
    }
    
    /**
     * Test implementation of MapListener that tracks calls to mapChanged
     */
    private static class TestMapListener implements MapListener {
        private boolean mapChangedCalled = false;
        private Map lastMap = null;
        private int changeCount = 0;
        
        @Override
        public void mapChanged(Map map) {
            mapChangedCalled = true;
            lastMap = map;
            changeCount++;
        }
        
        public boolean wasMapChangedCalled() {
            return mapChangedCalled;
        }
        
        public Map getLastMap() {
            return lastMap;
        }
        
        public int getChangeCount() {
            return changeCount;
        }
    }
} 