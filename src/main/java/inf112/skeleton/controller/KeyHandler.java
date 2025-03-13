package inf112.skeleton.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class KeyHandler implements InputProcessor {

    private final GameKeys[] keyMapping;
    private final boolean[] keyState;
    private final Array<KeyListener> listeners;

    public KeyHandler() {
        this.keyMapping = new GameKeys[256]; // 256 is the maximum number of keys in the Input.Keys class
        for(final GameKeys key : GameKeys.values()) {
            for(final int code : key.keyCode) {
                keyMapping[code] = key;
            }
        }
        this.keyState = new boolean[GameKeys.values().length];
        listeners = new Array<KeyListener>();
    }

    public void addListener(final KeyListener listener) {
        listeners.add(listener);
    }

    public void removeListener(final KeyListener listener) {
        listeners.removeValue(listener, true);
    }


    @Override
    public boolean keyDown(int keycode) {
        final GameKeys key = keyMapping[keycode];
        if(key == null) {
            return false;
        }

        notifyKeyDown(key);

        return true;
    }

    public void notifyKeyDown(final GameKeys key) {
        keyState[key.ordinal()] = true;
        for(final KeyListener listener : listeners) {
            listener.keyPressed(this, key);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        final GameKeys key = keyMapping[keycode];
        if(key == null) {
            return false;
        }

        notifyKeyUp(key);

        return true;
    }

    public void notifyKeyUp(final GameKeys key) {
        keyState[key.ordinal()] = false;
        for(final KeyListener listener : listeners) {
            listener.keyReleased(this, key);
    }
    }

    public boolean isKeyPressed(final GameKeys key) {
        return keyState[key.ordinal()];
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    
}
