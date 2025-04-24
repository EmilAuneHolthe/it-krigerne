package inf112.skeleton.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Container;


import inf112.skeleton.model.entity.player.Player;

public class PlayerHUD {
    private final Player player;
    private final Image healthBar;
    private final Label healthLabel;
    private final float maxHealthBarWidth = 240f;
    private final float healthBarHeight = 24f;
    private final Image manaBar;
    private final Label manaLabel;

    private final Image swordSlotCircle;
    private final Image equippedSwordIcon;
    private final Stack swordStack;


    public PlayerHUD(Stage stage, Player player, Texture healthTexture, Texture backgroundTexture, Texture manaTexture, Texture manaBackgroundTexture) {
        this.player = player;
        // Health bar (foreground)
        healthBar = new Image(healthTexture);
        healthBar.setSize(maxHealthBarWidth, healthBarHeight);
        healthBar.setOrigin(Align.bottomLeft);

        // Health bar background
        Image healthBg = new Image(backgroundTexture);
        healthBg.setSize(maxHealthBarWidth, healthBarHeight);

        // Label setup with inline style
        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.6f);
        LabelStyle style = new LabelStyle(font, Color.WHITE);
        healthLabel = new Label("100 / 100", style);
        healthLabel.setAlignment(Align.center);

        // Stack everything
        Stack healthStack = new Stack();
        healthStack.add(healthBg);
        healthStack.add(healthBar);
        healthStack.add(healthLabel);
        healthStack.setSize(maxHealthBarWidth, healthBarHeight);

        // Mana bar (foreground)
        manaBar = new Image(manaTexture);
        manaBar.setSize(maxHealthBarWidth, healthBarHeight);
        manaBar.setOrigin(Align.bottomLeft);

        // Mana bar background
        Image manaBg = new Image(manaBackgroundTexture);
        manaBg.setSize(maxHealthBarWidth, healthBarHeight);

        // Mana label
        manaLabel = new Label("100 / 100", style);
        manaLabel.setAlignment(Align.center);

        // Stack everything for mana
        Stack manaStack = new Stack();
        manaStack.add(manaBg);
        manaStack.add(manaBar);
        manaStack.add(manaLabel);
        manaStack.setSize(maxHealthBarWidth, healthBarHeight);

        // Place using Table
        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.bottom | Align.center);
        table.padBottom(5);

        table.add(healthStack).width(maxHealthBarWidth).height(healthBarHeight).padBottom(5).row();
        table.add(manaStack).width(maxHealthBarWidth).height(healthBarHeight);
        stage.addActor(table);


        // Load the circular slot frame and the player's initial sword icon
        Texture circleTexture = new Texture(Gdx.files.internal("sword_circle.png"));
        Texture swordTexture = new Texture(Gdx.files.internal("knife.png")); // change path as needed

        swordSlotCircle = new Image(circleTexture);
        equippedSwordIcon = new Image(swordTexture);

        // Make sure they're sized appropriately
        swordSlotCircle.setSize(128, 128);
        equippedSwordIcon.setSize(48, 48); // slightly smaller to fit inside
        equippedSwordIcon.setOrigin(Align.center);
        equippedSwordIcon.setAlign(Align.center); // Center inside the stack
        equippedSwordIcon.setRotation(135f); // angle in degrees


        // Stack them: sword icon on top of circle
        swordStack = new Stack();
        swordStack.add(swordSlotCircle);
        Container<Image> swordIconContainer = new Container<>(equippedSwordIcon);
        swordIconContainer.size(48, 48);
        swordIconContainer.align(Align.center);
        swordStack.add(swordIconContainer);

        // Place the stack somewhere in your HUD (top-right example)
        Table swordTable = new Table();
        swordTable.bottom().left().pad(0);
        swordTable.add(swordStack).size(128, 128);

        // Add the table to the stage
        stage.addActor(swordTable);

        player.getContext().setPlayerHUD(this);


    }

    public void update() {
        // Update health bar - using percentage of max health
        float health = player.getHealth();
        float maxHealth = 100f; // Assuming max health is 100
        float healthPercent = Math.max(0f, health / maxHealth);
        healthBar.setSize(maxHealthBarWidth * healthPercent, healthBarHeight);
        healthLabel.setText((int) health + " / " + (int) maxHealth);
        
        // Update mana bar - using percentage of max mana
        float mana = player.getMana();
        float maxMana = player.getMaxMana();
        float manaPercent = Math.max(0f, mana / maxMana);
        manaBar.setSize(maxHealthBarWidth * manaPercent, healthBarHeight);
        manaLabel.setText((int) mana + " / " + (int) maxMana);
    }

    public void updateEquippedSword(String texturePath) {
        Texture newSwordTexture = new Texture(Gdx.files.internal(texturePath));
        equippedSwordIcon.setDrawable(new Image(newSwordTexture).getDrawable());
    }
    
}
