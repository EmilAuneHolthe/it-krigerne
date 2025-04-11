package inf112.skeleton.controller;

import com.badlogic.gdx.Input;

public enum Keys {
    //Menu keys
    PAUSE (Input.Keys.ESCAPE),
    BACK (Input.Keys.BACKSPACE),

    //Movement keys
    UP (Input.Keys.W, Input.Keys.UP),
    DOWN (Input.Keys.S, Input.Keys.DOWN),
    LEFT (Input.Keys.A, Input.Keys.LEFT), 
    RIGHT (Input.Keys.D, Input.Keys.RIGHT), 

    //Player interaction keys
    INTERACT (Input.Keys.E, Input.Keys.ENTER),
    ATTACK (Input.Keys.F),
    
    //Item keys
    NUM_1 (Input.Keys.NUM_1),
    NUM_2 (Input.Keys.NUM_2),
    NUM_3 (Input.Keys.NUM_3),
    NUM_4 (Input.Keys.NUM_4),
    USE_ITEM (Input.Keys.Q);

    final int[] keyCode;
    
    private Keys(int... keyCode) {
        this.keyCode = keyCode;
    }

    public int[] getKeyCode() {
        return keyCode;
    }
}
