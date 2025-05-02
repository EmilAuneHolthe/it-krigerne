package inf112.skeleton.model.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import inf112.skeleton.view.AnimationTypes;

public class PlayerAnimation {
    
    private final TextureAtlas characterAtlas;
    private final TextureAtlas attackAtlas;
    private Animation<TextureRegion> currentAnimation;
    private final TextureRegion[] attackFrames;
    private AnimationTypes currentAnimationType;
    private float stateTime;
    private float attackTimer;
    private boolean isAttacking;
    private boolean isMoving;
    private String direction;
    private String lastMovingDirection;
    private final CharacterType characterType;
    private float directionChangeCooldown;
    private static final float DIRECTION_CHANGE_DELAY = 0.1f;
    
    private static final float ATTACK_DURATION = 1.0f;
    private static final float FRAME_DURATION = 0.2f;
    
    /**
    * Creates a new PlayerAnimation instance for the specified character type.
    * Initializes the character and attack animations, loading the necessary texture atlases.
    * 
    * @param characterType The type of character to create animations for
    * @throws RuntimeException if the animation atlases cannot be loaded or initialized
    */
    public PlayerAnimation(CharacterType characterType) {
        try {
            this.characterType = characterType;
            characterAtlas = new TextureAtlas(Gdx.files.internal(characterType.getDefaultAnimation().getAtlasPath()));
            
            // Get the attack animation type based on character type
            String attackTypeName = characterType.name() + "_ATTACK";
            AnimationTypes attackAnimationType;
            try {
                attackAnimationType = AnimationTypes.valueOf(attackTypeName);
            } catch (IllegalArgumentException e) {
                Gdx.app.error("PlayerAnimation", "Could not find attack animation type: " + attackTypeName);
                throw new RuntimeException("Failed to initialize player animation: Missing attack animation", e);
            }
            
            attackAtlas = new TextureAtlas(Gdx.files.internal(attackAnimationType.getAtlasPath()));
            
            // Load attack frames 
            TextureRegion region = attackAtlas.findRegion(attackAnimationType.getAtlasKey());
            if (region == null) {
                throw new RuntimeException("Could not find region '" + attackAnimationType.getAtlasKey() + 
                "' in attack atlas");
            }
            TextureRegion[][] regions = region.split(
            region.getRegionWidth() / 5,
            region.getRegionHeight()
            
            );
            attackFrames = new TextureRegion[5];
            System.arraycopy(regions[0], 0, attackFrames, 0, 5);
            
            currentAnimationType = characterType.getDefaultAnimation();
            direction = "Down";
            lastMovingDirection = "Down";
            directionChangeCooldown = 0f;
            updateAnimation();
        } catch (Exception e) {
            Gdx.app.error("PlayerAnimation", "Error loading atlases: " + e.getMessage());
            throw new RuntimeException("Failed to initialize player animation", e);
        }
    }
    
    /**
    * Updates the animation state based on the elapsed time.
    * Handles attack animation timing and direction change cooldown.
    * 
    * @param deltaTime The time elapsed since the last update
    */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (isAttacking) {
            attackTimer += deltaTime;
            if (attackTimer >= ATTACK_DURATION) {
                isAttacking = false;
                attackTimer = 0f;
                updateAnimation();
            }
        }
        
        if (directionChangeCooldown > 0) {
            directionChangeCooldown -= deltaTime;
        }
    }
    
    /**
    * Renders the current animation frame at the player's position.
    * Handles different animation states (attacking, moving, idle) and scales the sprite appropriately.
    * 
    * @param batch The SpriteBatch used for rendering
    * @param body The player's physics body containing position information
    */
    public void render(SpriteBatch batch, Body body) {
        TextureRegion currentFrame;
        if (isAttacking) {
            int frameIndex = (int)(attackTimer / FRAME_DURATION) % 5;
            currentFrame = attackFrames[frameIndex];
        } else if (isMoving) {
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
        
        batch.draw(currentFrame, 
        body.getPosition().x - width/2, 
        body.getPosition().y - height/2,
        width,
        height);
    }
    
    /**
    * Initiates the attack animation sequence.
    * Sets the attacking state to true and resets the attack timer.
    * Updates the animation to show the attack animation.
    */
    public void startAttack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = 0f;
            updateAnimation();
        }
    }

    /**
     * Sets the moving state of the player.
     * @param moving The new moving state to set
     */
    public void setMoving(boolean moving) {
        this.isMoving = moving;
        if (!isAttacking) {
            updateAnimation();
        }
    }
    
    /**
     * Checks if the direction change cooldown is greater than 0.
     * If it is, it returns and does not update the direction.
     * If it is not, it updates the direction and updates the animation.
     * @param direction The new direction to set
     */
    public void setDirection(String direction) {
        if (directionChangeCooldown > 0) {
            return;
        }
        
        // Only update direction if it's different from the last moving direction
        if (!direction.equals(lastMovingDirection)) {
            this.direction = direction;
            if (isMoving) {
                lastMovingDirection = direction;
                directionChangeCooldown = DIRECTION_CHANGE_DELAY;
            }
            if (!isAttacking) {
                updateAnimation();
            }
        }
    }
    
    private void updateAnimation() {
        try {
            if (!isAttacking) {
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
            }
        } catch (Exception e) {
            Gdx.app.error("PlayerAnimation", "Error updating animation: " + e.getMessage());
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
            Gdx.app.error("PlayerAnimation", "Could not find animation type: " + animationTypeName);
            return characterType.getDefaultAnimation();
        }
    }
    
    /**
     * Disposes of the character and attack texture atlases.
     */
    public void dispose() {
        characterAtlas.dispose();
        attackAtlas.dispose();
    }
} 