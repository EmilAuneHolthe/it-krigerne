package inf112.skeleton.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import inf112.skeleton.model.entity.player.Player;
import inf112.skeleton.model.entity.item.Item;
import inf112.skeleton.model.entity.item.ItemType;

public class ItemBar {
    private static final int SLOT_COUNT = 4;
    private static final float SLOT_SIZE = 48f;
    private static final float ITEM_SIZE = 28f;
    private static final float PADDING = 10f;
    
    private final Player player;
    private final Vector2 position;
    private final float slotSize;
    private final float padding;
    private final Texture slotTexture;
    private final Texture selectedSlotTexture;
    private final Image[] slotBackgrounds;
    private final Image[] itemIcons;
    private final Label[] itemLabels;
    private final Stack[] slots;
    private final Table table;
    private final Item[] items;
    private final Stage stage;

    public ItemBar(Stage stage, Player player, float x, float y, float slotSize, float padding) {
        this.stage = stage;
        this.player = player;
        this.position = new Vector2(x, y);
        this.slotSize = slotSize;
        this.padding = padding;
        this.slotTexture = new Texture(Gdx.files.internal("Ui/slot.png"));
        this.selectedSlotTexture = new Texture(Gdx.files.internal("Ui/slotselected.png"));
        this.items = player.getItems();
        
        // Initialize arrays
        slotBackgrounds = new Image[SLOT_COUNT];
        itemIcons = new Image[SLOT_COUNT];
        itemLabels = new Label[SLOT_COUNT];
        slots = new Stack[SLOT_COUNT];
        
        // Create font for labels
        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.5f);
        LabelStyle style = new LabelStyle(font, Color.WHITE);
        
        // Create table for layout
        table = new Table();
        table.setFillParent(true);
        table.align(Align.bottom | Align.center);
        table.padBottom(50);
        
        // Create slots
        for (int i = 0; i < SLOT_COUNT; i++) {
            // Create slot background
            slotBackgrounds[i] = new Image(slotTexture);
            slotBackgrounds[i].setSize(SLOT_SIZE, SLOT_SIZE);
            
            // Create item icon (initially empty)
            itemIcons[i] = new Image();
            itemIcons[i].setSize(ITEM_SIZE, ITEM_SIZE);
            
            // Create label
            itemLabels[i] = new Label("", style);
            itemLabels[i].setAlignment(Align.center);
            
            // Create stack for slot
            slots[i] = new Stack();
            slots[i].add(slotBackgrounds[i]);
            slotBackgrounds[i].setSize(SLOT_SIZE, SLOT_SIZE);
            slots[i].add(itemIcons[i]);
            itemIcons[i].setSize(ITEM_SIZE, ITEM_SIZE);
            // Center the item icon in the slot
            itemIcons[i].setPosition((SLOT_SIZE - ITEM_SIZE) / 2, (SLOT_SIZE - ITEM_SIZE) / 2);
            slots[i].add(itemLabels[i]);
            slots[i].setSize(SLOT_SIZE, SLOT_SIZE);
            
            // Add slot to table
            table.add(slots[i]).size(SLOT_SIZE, SLOT_SIZE).pad(PADDING);
        }
        
        stage.addActor(table);
    }
    
    public void update() {
        if (player == null) {
            return;
        }

        Item[] items = player.getItems();
        if (items == null) {
            return;
        }

        for (int i = 0; i < SLOT_COUNT; i++) {
            // Update slot background based on selection
            slotBackgrounds[i].setDrawable(new TextureRegionDrawable(
                i == player.getSelectedItemIndex() ? selectedSlotTexture : slotTexture
            ));
            slotBackgrounds[i].setSize(SLOT_SIZE, SLOT_SIZE);

            if (i < items.length && items[i] != null) {
                try {
                    // Set item icon using the existing item texture
                    Texture itemTexture = ItemType.getItemTexture(items[i].getItemType());
                    if (itemTexture != null) {
                        itemIcons[i].setDrawable(new TextureRegionDrawable(itemTexture));
                        itemIcons[i].setSize(ITEM_SIZE, ITEM_SIZE);
                        // Center the item icon in the slot
                        itemIcons[i].setPosition((SLOT_SIZE - ITEM_SIZE) / 2, (SLOT_SIZE - ITEM_SIZE) / 2);
                        itemIcons[i].setVisible(true);
                    } else {
                        Gdx.app.log("ItemBar", "Item texture is null for item type: " + items[i].getItemType());
                        itemIcons[i].setVisible(false);
                    }
                    
                    // Set item label based on item type
                    String labelText = getLabelTextForItem(items[i]);
                    itemLabels[i].setText(labelText);
                    itemLabels[i].setVisible(true);
                } catch (Exception e) {
                    Gdx.app.log("ItemBar", "Error updating item slot " + i + ": " + e.getMessage());
                    // If texture loading fails, just show the label
                    itemIcons[i].setVisible(false);
                    String labelText = getLabelTextForItem(items[i]);
                    itemLabels[i].setText(labelText);
                    itemLabels[i].setVisible(true);
                }
            } else {
                // Clear slot
                itemIcons[i].setVisible(false);
                itemLabels[i].setVisible(false);
            }
        }
    }
    
    private String getLabelTextForItem(Item item) {
        return "Q";
        }
    
    
    public void render(SpriteBatch batch) {
        if (player == null || items == null) {
            Gdx.app.error("ItemBar", "Player or items array is null");
            return;
        }

        for (int i = 0; i < items.length; i++) {
            float x = position.x + (i * (slotSize + padding));
            float y = position.y;

            // Draw the appropriate slot texture based on selection
            Texture currentTexture = (i == player.getSelectedItemIndex()) ? selectedSlotTexture : slotTexture;
            batch.draw(currentTexture, x, y, slotSize, slotSize);

            // Draw item if present
            if (items[i] != null) {
                Texture itemTexture = items[i].getTexture();
                if (itemTexture != null) {
                    batch.draw(itemTexture, x, y, slotSize, slotSize);
                } else {
                    Gdx.app.error("ItemBar", "Item texture is null for item at index " + i);
                }
            }
        }
    }
    
    public void dispose() {
        slotTexture.dispose();
        selectedSlotTexture.dispose();
    }
} 