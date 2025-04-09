package inf112.skeleton.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import inf112.skeleton.view.AnimationTypes;

public class EnemyAnimation {
    private TextureAtlas characterAtlas;
    private Animation<TextureRegion> currentAnimation;
    private AnimationTypes currentAnimationType;
    private float stateTime;
    private boolean isMoving;
    private String direction;
    private CharacterType characterType;
    
    private static final float FRAME_DURATION = 0.2f;
    
    public EnemyAnimation(CharacterType characterType) {
        try {
            this.characterType = characterType;
            characterAtlas = new TextureAtlas(Gdx.files.internal(characterType.getDefaultAnimation().getAtlasPath()));
            
            currentAnimationType = characterType.getDefaultAnimation();
            direction = "Down";
            updateAnimation();
        } catch (Exception e) {
            Gdx.app.error("EnemyAnimation", "Error loading atlas: " + e.getMessage());
            throw new RuntimeException("Failed to initialize enemy animation", e);
        }
    }
    
    public void update(float deltaTime) {
        stateTime += deltaTime;
    }
    
    public void render(SpriteBatch batch, Body body) {
        TextureRegion currentFrame;
        if (isMoving) {
            currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        } else {
            TextureRegion[][] regions = characterAtlas.findRegion(currentAnimationType.getAtlasKey()).split(
                characterAtlas.findRegion(currentAnimationType.getAtlasKey()).getRegionWidth() / 3,
                characterAtlas.findRegion(currentAnimationType.getAtlasKey()).getRegionHeight() / 4
            );
            currentFrame = regions[currentAnimationType.getRowIndex()][1];
        }
        
        float width = currentFrame.getRegionWidth() / 32f;
        float height = currentFrame.getRegionHeight() / 32f;
        
        batch.begin();
        batch.draw(currentFrame, 
            body.getPosition().x - width/2, 
            body.getPosition().y - height/2,
            width,
            height);
        batch.end();
    }
    
    public void setMoving(boolean moving) {
        this.isMoving = moving;
        updateAnimation();
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
        updateAnimation();
    }
    
    private void updateAnimation() {
        try {
            switch (direction) {
                case "Down":
                    currentAnimationType = getAnimationTypeForDirection("Down");
                    break;
                case "Up":
                    currentAnimationType = getAnimationTypeForDirection("Up");
                    break;
                case "Left":
                    currentAnimationType = getAnimationTypeForDirection("Left");
                    break;
                case "Right":
                    currentAnimationType = getAnimationTypeForDirection("Right");
                    break;
            }
            
            TextureRegion region = characterAtlas.findRegion(currentAnimationType.getAtlasKey());
            if (region == null) {
                throw new RuntimeException("Could not find region '" + currentAnimationType.getAtlasKey() + 
                    "' in atlas");
            }
            
            TextureRegion[][] regions = region.split(
                region.getRegionWidth() / 3,
                region.getRegionHeight() / 4
            );
            TextureRegion[] walkingFrames = new TextureRegion[2];
            walkingFrames[0] = regions[currentAnimationType.getRowIndex()][0];
            walkingFrames[1] = regions[currentAnimationType.getRowIndex()][2];
            currentAnimation = new Animation<>(currentAnimationType.getFrameTime(), walkingFrames);
        } catch (Exception e) {
            Gdx.app.error("EnemyAnimation", "Error updating animation: " + e.getMessage());
            throw new RuntimeException("Failed to update animation", e);
        }
    }
    
    private AnimationTypes getAnimationTypeForDirection(String direction) {
        String prefix = characterType.name();
        String suffix = direction.toUpperCase();
        String animationTypeName = prefix + "_" + suffix;
        try {
            return AnimationTypes.valueOf(animationTypeName);
        } catch (IllegalArgumentException e) {
            Gdx.app.error("EnemyAnimation", "Could not find animation type: " + animationTypeName);
            return characterType.getDefaultAnimation();
        }
    }
    
    public void dispose() {
        characterAtlas.dispose();
    }
} 