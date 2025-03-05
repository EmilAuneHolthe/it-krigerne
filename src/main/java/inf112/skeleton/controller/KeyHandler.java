package inf112.skeleton.controller;

import java.awt.event.KeyListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {

    public boolean up, down, left, right;
    private float dx = 1, dy = 1;
    private Rectangle spriteRect;

    @Override
    public void keyTyped(KeyEvent e) {
     
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			dy = -10;
			spriteRect.y += dy;
			dy = 0;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			dy = 10;
			spriteRect.y += dy;
			dy = 0;
		
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			dx = -10;
			spriteRect.x += dx;
			dx = 0;
		
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			dx = 10;
			spriteRect.x += dx;
			dx = 0;
		}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_UP:
                up = false;  
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            default:
                break;
        }
    } 

    

}