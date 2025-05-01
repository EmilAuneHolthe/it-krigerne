package inf112.skeleton.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

/**
 * Handles keyboard input and notifies registered listeners about key events.
 * This class maps key codes to custom key definitions and tracks the state of keys.
 */
public class KeyHandler implements InputProcessor {

    private final Keys[] keyMapping;
    private final boolean[] keyState;
    private final Array<KeyListener> listeners;

    /**
     * Constructs a KeyHandler instance.
     * Initializes key mappings and key states.
     */
    public KeyHandler() {
        this.keyMapping = new Keys[256]; // 256 is the maximum number of keys in the Input.Keys class
        for (final Keys key : Keys.values()) {
            for (final int code : key.keyCode) {
                keyMapping[code] = key;
            }
        }
        this.keyState = new boolean[Keys.values().length];
        listeners = new Array<>();
    }

    /**
     * Adds a listener to receive key events.
     *
     * @param listener The KeyListener to add.
     */
    public void addListener(final KeyListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from receiving key events.
     *
     * @param listener The KeyListener to remove.
     */
    public void removeListener(final KeyListener listener) {
        listeners.removeValue(listener, true);
    }

    /**
     * Handles a key press event.
     * Notifies all registered listeners about the key press.
     *
     * @param keycode The key code of the pressed key.
     * @return True if the key press was handled, false otherwise.
     */
    @Override
    public boolean keyDown(int keycode) {
        final Keys key = keyMapping[keycode];
        if (key == null) {
            return false;
        }

        notifyKeyDown(key);

        return true;
    }

    /**
     * Notifies all registered listeners about a key press event.
     *
     * @param key The key that was pressed.
     */
    public void notifyKeyDown(final Keys key) {
        keyState[key.ordinal()] = true;
        for (final KeyListener listener : listeners) {
            listener.keyPressed(this, key);
        }
    }

    /**
     * Handles a key release event.
     * Notifies all registered listeners about the key release.
     *
     * @param keycode The key code of the released key.
     * @return True if the key release was handled, false otherwise.
     */
    @Override
    public boolean keyUp(int keycode) {
        final Keys key = keyMapping[keycode];
        if (key == null) {
            return false;
        }

        notifyKeyUp(key);

        return true;
    }

    /**
     * Notifies all registered listeners about a key release event.
     *
     * @param key The key that was released.
     */
    public void notifyKeyUp(final Keys key) {
        keyState[key.ordinal()] = false;
        for (final KeyListener listener : listeners) {
            listener.keyReleased(this, key);
        }
    }

    /**
     * Checks if a specific key is currently pressed.
     *
     * @param key The key to check.
     * @return True if the key is pressed, false otherwise.
     */
    public boolean isKeyPressed(final Keys key) {
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
