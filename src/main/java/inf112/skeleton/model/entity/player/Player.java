package inf112.skeleton.model.entity.player;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import inf112.skeleton.audio.AudioTypes;
import inf112.skeleton.controller.KeyHandler;
import inf112.skeleton.controller.Keys;
import inf112.skeleton.model.GamePanel;
import inf112.skeleton.model.entity.GameEntity;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.view.screen.ScreenType;
import inf112.skeleton.view.ui.DeathOverlay;

public class Player extends GameEntity {
    private boolean isDead;
    private DeathOverlay deathOverlay;
    protected boolean alive;
    private int mana;
    private int maxMana;
    private float manaRegenRate = 10f; // Mana per second
    private float manaRegenAccumulator = 0.0f;
    public boolean canAttack = true; 
    private int maxHealth;
    private boolean hasKey = false;
    private PlayerInteractions playerInteractions;
    private final Item[] items;
    private int selectedItemIndex;

    public Player(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType, KeyHandler keyHandler) {
        super(context, world, body, health, damage, characterType);
        isDead = false;
        deathOverlay = new DeathOverlay(context);
        this.movement = new PlayerMovement(world, body, keyHandler);
        this.mana = 100;
        this.maxMana = 100;
        this.playerInteractions = new PlayerInteractions(context);
        maxHealth = health;
        items = new Item[4]; // 4 slots for items
        selectedItemIndex = 0;
    }
    
    @Override
    public void render(SpriteBatch batch) {
        if (isDead) {
            return;
        }
        super.render(batch);
        
    }
    public void renderDeathOverlay(SpriteBatch batch) {
        if (isDead && deathOverlay != null) {
            deathOverlay.render(batch);
        }
    }
    @Override
    protected boolean isActive() {
        return !isDead;
    }
    
    public void playerInput(KeyHandler keyHandler, Keys key) {
        if (isDead) {
            handleDeadPlayerInput(key);
            return;
        }
        
        // Handle item selection
        if (key == Keys.NUM_1) {
            selectItem(0);
        } else if (key == Keys.NUM_2) {
            selectItem(1);
        } else if (key == Keys.NUM_3) {
            selectItem(2);
        } else if (key == Keys.NUM_4) {
            selectItem(3);
        }
        // Handle item usage
        else if (key == Keys.USE_ITEM) {
            useSelectedItem();
        }
        // Handle other inputs
        else if (key == Keys.ATTACK && canAttack) {
            if (mana >= 30) {
                animation.startAttack();
                mana -= 30;
            }
        } else {
            movement.handleInput(key);
            animation.setMoving(movement.isMoving());
            animation.setDirection(movement.getDirection());
        }
        if (key == Keys.INTERACT) {
            playerInteractions.pickUpItem(this, context.getItems());
        }
    }
    
    private void handleDeadPlayerInput(Keys key) {
        if (key == Keys.INTERACT) {
            context.getAudioHandler().playAudio(AudioTypes.SELECT);
            context.resetPlayer();
            context.setScreen(ScreenType.MAIN_MENU);
        }
    }
    
    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    
    public void playerTakeDamage(Enemy enemy) {
        if (alive) {
            context.getAudioHandler().playAudio(AudioTypes.HURT);
          }
          alive = takeDamage(enemy.getDamage());
          if (!alive) {
            killPlayer();
          }
        }


    public void killPlayer() {
            Gdx.app.log("DAMAGE", "Player has died!");
            isDead = true;
            die();
        }
    

    @Override
    public int attack() {
        return damage;
    }
    
    @Override
    public void die() {
        if (deathOverlay != null) {
            deathOverlay.show();
        }
    }
    
    @Override
    public int getHealth() {
        if (health < 0) {
            return 0;
        }
        return health;
    }
   
    @Override
    public void setHealth(int health) {
        this.health = health;
    }
    
    @Override
    public void setSpawn(float x, float y) {
        this.x = x;
        this.y = y;
        if (body != null) {
            // Reset velocity to prevent sliding
            body.setLinearVelocity(0, 0);
            // Set the new position
            body.setTransform(x, y, 0);
        }
    }
    
    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public float getY() {
        return y;
    }
    
    @Override
    public void create(int health, int damage, float x, float y) {
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }
    
    public Body getBody() {
        return body;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (deathOverlay != null) {
            deathOverlay.dispose();
        }
    }
    
    public DeathOverlay getDeathOverlay() {
        return deathOverlay;
    }
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.min(Math.max(0, mana), maxMana);
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }


    public void regenerateMana(float deltaTime) {
        // Update mana regeneration
        manaRegenAccumulator += manaRegenRate * deltaTime;
        if (manaRegenAccumulator >= 1.0f) {
            int manaToAdd = (int) manaRegenAccumulator;
            setMana(getMana() + manaToAdd);
            manaRegenAccumulator -= manaToAdd;
            
            if(mana >= 20) {
                canAttack = true;
            } else {
                canAttack = false;
            }
        }
    }

    public float getManaRegenRate() {
        return manaRegenRate;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setManaRegenRate(float manaRegenRate) {
        this.manaRegenRate = manaRegenRate;
    }
    public void pickUpItem(ItemType itemType) {
        if (itemType == null) {
            Gdx.app.log("Player", "Cannot pick up null item type");
            return;
        }
        
        // Create a new item instance
        Item newItem = new Item(context, world, itemType, 0, 0);
        
        // Add the item to inventory
        addItem(newItem);
    }

    private void addItem(Item item) {
        if (item == null) {
            Gdx.app.log("Player", "Cannot add null item");
            return;
        }

        // Try to find an empty slot
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = item;
                Gdx.app.log("Player", "Added item to slot " + i);
                return;
            }
        }

        // If no empty slot, replace the selected slot
        if (selectedItemIndex >= 0 && selectedItemIndex < items.length) {
            items[selectedItemIndex] = item;
            Gdx.app.log("Player", "Replaced item in slot " + selectedItemIndex);
        } else {
            Gdx.app.log("Player", "No available slots for item");
        }
    }

    public void useSelectedItem() {
        if (selectedItemIndex < 0 || selectedItemIndex >= items.length) {
            Gdx.app.log("Player", "Invalid selected item index");
            return;
        }

        Item item = items[selectedItemIndex];
        if (item == null) {
            Gdx.app.log("Player", "No item in selected slot");
            return;
        }

        // Apply item effects
        switch (item.getItemType()) {
            case HEALTH:
                setHealth(Math.min(health + 20, maxHealth));
                break;
            case MANA:
                mana = Math.min(mana + 20, maxMana);
                manaRegenRate += 5f;
                break;
            case ATTACK:
                damage += 5;
                break;
            case KEY:
                hasKey = true;
                break;
        }

        // Remove the used item
        items[selectedItemIndex] = null;
        Gdx.app.log("Player", "Used and removed item from slot " + selectedItemIndex);
    }

    public boolean hasKey() {
        return hasKey;
    }

    public Item[] getItems() {
        return items;
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.length) {
            items[index] = null;
        }
    }

    public void selectItem(int index) {
        if (index >= 0 && index < items.length) {
            selectedItemIndex = index;
        }
    }

    public Item getSelectedItem() {
        return items[selectedItemIndex];
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }
}