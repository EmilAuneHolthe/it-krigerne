// package inf112.skeleton.app.mapTest;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import com.badlogic.gdx.maps.objects.RectangleMapObject;
// import com.badlogic.gdx.maps.objects.MapObjects;
// import com.badlogic.gdx.math.Rectangle;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;

// import inf112.skeleton.app.map.CollisionArea;

// class CollisionAreaTest {

//     // <dependency>
// 	// 		<groupId>com.badlogicgames.gdx</groupId>
// 	// 		<artifactId>gdx-tiled</artifactId>
// 	// 		<version>${libgdx.version}</version>
// 	// 	</dependency>
//     // 
//     // må legges til for at MapObjects skal fungere
    
    
//     private CollisionArea collisionArea;
//     private Iterator<RectangleMapObject> mockIterator;
//     private List<RectangleMapObject> mockObjectsList;
    
//     @BeforeEach
//     void setUp() {
//         mockMapObjects = mock(MapObjects.class);
//         mockObjectsList = new ArrayList<>();
        
//         RectangleMapObject mockObject = mock(RectangleMapObject.class);
//         Rectangle mockRectangle = mock(Rectangle.class);
//         when(mockObject.getRectangle()).thenReturn(mockRectangle);
//         mockObjectsList.add(mockObject);
        
//         mockIterator = mockObjectsList.iterator();
//         when(mockMapObjects.iterator()).thenReturn(mockIterator);
        
//         collisionArea = new CollisionArea(mockMapObjects);
//     }
    
//     @Test
//     void testCollisionAreaInitialization() {
//         assertNotNull(collisionArea, "CollisionArea should be initialized");
//     }
    
//     @Test
//     void testCollisionObjectsNotEmpty() {
//         assertFalse(collisionArea.getCollisionObjects().isEmpty(), "Collision objects list should not be empty");
//     }
// }
