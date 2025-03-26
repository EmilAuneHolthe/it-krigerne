package inf112.skeleton.controller;

import com.badlogic.gdx.Input;

public enum GameKeys {
    //Menu keys
    PAUSE (Input.Keys.ESCAPE),
    BACK (Input.Keys.BACKSPACE),

    //Movement keys
    UP (Input.Keys.W, Input.Keys.UP),
    DOWN (Input.Keys.S, Input.Keys.DOWN),
    LEFT (Input.Keys.A, Input.Keys.LEFT), 
    RIGHT (Input.Keys.D, Input.Keys.RIGHT), 

    //Player interaction keys
    INTERACT (Input.Keys.E, Input.Keys.ENTER);

    final int[] keyCode;
    
    private GameKeys(int... keyCode) {
        this.keyCode = keyCode;
    }

    public int[] getKeyCode() {
        return keyCode;
    }
}
