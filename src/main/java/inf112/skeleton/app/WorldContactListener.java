package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(final Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        //Gdx.app.debug("CONTACT", "BEGIN: " + fixtureA.getBody().getUserData() + " " + fixtureA.isSensor());
        //Gdx.app.debug("CONTACT", "BEGIN: " + fixtureB.getBody().getUserData() + " " + fixtureB.isSensor());
    }

    @Override
    public void endContact(final Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        //Gdx.app.debug("CONTACT", "END: " + fixtureA.getBody().getUserData() + " " + fixtureA.isSensor());
        //Gdx.app.debug("CONTACT", "END: " + fixtureB.getBody().getUserData() + " " + fixtureB.isSensor());
    }

    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {
       
    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {
       
    }
    
}
