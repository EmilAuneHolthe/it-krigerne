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
import inf112.skeleton.model.WorldFunctions;
import inf112.skeleton.model.entity.GameEntity;
import inf112.skeleton.model.entity.enemy.Enemy;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;
import inf112.skeleton.view.screen.ScreenType;
import inf112.skeleton.view.ui.DeathOverlay;

public class Player extends GameEntity {
    public static boolean isDead;
    private DeathOverlay deathOverlay;
    public boolean alive;
    private int currentMana;
    private int maxMana;
    private float manaRegenRate = 10f; // Mana per second
    private float manaRegenAccumulator = 0.0f;
    public boolean canAttack = true; 
    private int maxHealth;
    private boolean hasKey = false;
    private PlayerInteractions playerInteractions;
    private final Item[] items;
    private int selectedItemIndex;
    private int swordUpgradeType = 0;
    private KeyHandler keyHandler;

    public Player(GamePanel context, World world, Body body, int health, int damage, float x, float y, CharacterType characterType) {
        super(context, world, body, health, damage, characterType);
        isDead = false;
        deathOverlay = new DeathOverlay(context);
        this.movement = new PlayerMovement(context, world, body);
        this.currentMana = 100;
        this.maxMana = 100;
        this.playerInteractions = new PlayerInteractions(context);
        this.keyHandler = context.getKeyHandler();
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

        switch (key) {
            case QUIT: 
                quitWhenDead(); break;
            case ATTACK: 
                playerInteractions.attackEnemy(this, WorldFunctions.getEnemies());
                if (canAttack) {
                    animation.startAttack();
                    currentMana -= 30;
                } break;
            case INTERACT: 
                playerInteractions.pickUpItem(this, context.getItems()); break;
            default:
                break;
        }

        playerUIinput(key);
        movement.handleInput(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }

    private void playerUIinput(Keys key) {

        switch (key) {
            case NUM_1: selectItem(0); break;
            case NUM_2: selectItem(1); break;
            case NUM_3: selectItem(2); break;
            case NUM_4: selectItem(3); break;

            case USE_ITEM:useSelectedItem();
                context.getAudioHandler().playAudio(AudioTypes.USE_ITEM);
                break;
            default:
                break;
        }

    }

    private void quitWhenDead() {
        if ((isDead)) {
            Gdx.app.exit();
        }   
    }

    public void movePlayerReleased(KeyHandler keyHandler, Keys key) {
        movement.handleInputRelease(key);
        animation.setMoving(movement.isMoving());
        animation.setDirection(movement.getDirection());
    }
    
    public void playerTakeDamage(Enemy enemy) {
        if (alive) {
            context.getAudioHandler().playAudio(AudioTypes.HURT2);
            context.getAudioHandler().playAudio(AudioTypes.HIT);
          }
          alive = takeDamage(enemy.getDamage());
          if (!alive) {
            isDead = true;
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
        return body.getPosition().x * context.UNIT_SCALE;
    }
    
    @Override
    public float getY() {
        return body.getPosition().y * context.UNIT_SCALE;
    }
    
    @Override
    public void create(int health, int damage, float x, float y) {
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (deathOverlay != null) {
            deathOverlay.dispose();
        }
    }
    
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    public void setCurrentMana(int mana) {
        this.currentMana = Math.min(Math.max(0, mana), maxMana);
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }


    public void regenerateMana(float deltaTime) {
        // Update mana regeneration
        manaRegenAccumulator += manaRegenRate * deltaTime;
        if (manaRegenAccumulator >= 1.0f) {
            int manaToAdd = (int) manaRegenAccumulator;
            setCurrentMana(getCurrentMana() + manaToAdd);
            manaRegenAccumulator -= manaToAdd;
            
            if(currentMana >=30) {
                canAttack = true;
            } else {
                canAttack = false;
            }
        }
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
                currentMana = Math.min(currentMana + 20, maxMana);
                manaRegenRate += 5f;
                break;
            case SWORD_UPGRADE:
                swordUpgradeType++;
                if (swordUpgradeType == 1) {
                    damage += 5;
                    getSwordHUDTexturePath("UncommonSword");
                }
                else if (swordUpgradeType == 2) {
                    damage += 5;
                    getSwordHUDTexturePath("RareSword");
                }             
                break;
            case KEY:
                hasKey = true;
                context.getAudioHandler().playAudio(AudioTypes.BONUS);
                break;

            default:
                break;
        }

        // Remove the used item
        items[selectedItemIndex] = null;
        Gdx.app.log("Player", "Used and removed item from slot " + selectedItemIndex);
    }

    private void getSwordHUDTexturePath(String Sword) {
            
            String swordHUDTexture = ItemType.getSwordHUDTexturePath(Sword);
           
            if (swordHUDTexture != null) {
                context.updateEquippedSwordHUD(swordHUDTexture);
            }
        }

    public boolean hasKey() {
        return hasKey;
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



    public Item[] getItems() { return items;}
    public Item getSelectedItem() { return items[selectedItemIndex];}
    public int getSelectedItemIndex() { return selectedItemIndex;}
    public GamePanel getContext() { return context;}
    public float getManaRegenRate() { return manaRegenRate;}
    public int getMaxHealth() { return maxHealth;}
    public int getMaxMana() { return maxMana;}
    public int getCurrentMana() { return currentMana;}
    public Body getBody() { return body;}
    public DeathOverlay getDeathOverlay() { return deathOverlay;}


    public void setKey(Boolean hasKey) {
        this.hasKey = hasKey;
    }
    public void removeKey() {
        this.hasKey = false;
    }
}