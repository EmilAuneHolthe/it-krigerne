package inf112.skeleton.view.ui;

import static inf112.skeleton.model.GamePanel.assetManager;

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

/**
 * Heads-Up Display (HUD) for the player in the game.
 * Manages the display of player health, mana, equipped items, and key status using LibGDX's scene2d UI framework.
 */
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
    private final Image keyIcon;
    private final Label keyLabel;

    /**
     * Creates a new PlayerHUD instance and initializes all UI elements.
     *
     * @param stage The stage to add UI elements to
     * @param player The player to display information for
     * @param healthTexture Texture for the health bar
     * @param backgroundTexture Texture for the health bar background
     * @param manaTexture Texture for the mana bar
     * @param manaBackgroundTexture Texture for the mana bar background
     */
    public PlayerHUD(Stage stage, Player player, Texture healthTexture, Texture backgroundTexture, Texture manaTexture,
            Texture manaBackgroundTexture) {
        this.player = player;
        
        healthBar = new Image(healthTexture);
        healthBar.setSize(maxHealthBarWidth, healthBarHeight);
        healthBar.setOrigin(Align.bottomLeft);

        Image healthBg = new Image(backgroundTexture);
        healthBg.setSize(maxHealthBarWidth, healthBarHeight);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.6f);
        LabelStyle style = new LabelStyle(font, Color.WHITE);
        healthLabel = new Label("100 / 100", style);
        healthLabel.setAlignment(Align.center);

        Stack healthStack = new Stack();
        healthStack.add(healthBg);
        healthStack.add(healthBar);
        healthStack.add(healthLabel);
        healthStack.setSize(maxHealthBarWidth, healthBarHeight);

        manaBar = new Image(manaTexture);
        manaBar.setSize(maxHealthBarWidth, healthBarHeight);
        manaBar.setOrigin(Align.bottomLeft);

        Image manaBg = new Image(manaBackgroundTexture);
        manaBg.setSize(maxHealthBarWidth, healthBarHeight);

        manaLabel = new Label("100 / 100", style);
        manaLabel.setAlignment(Align.center);

        Stack manaStack = new Stack();
        manaStack.add(manaBg);
        manaStack.add(manaBar);
        manaStack.add(manaLabel);
        manaStack.setSize(maxHealthBarWidth, healthBarHeight);

        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.bottom | Align.center);
        table.padBottom(50);

        table.add(healthStack).width(maxHealthBarWidth).height(healthBarHeight).padBottom(5).row();
        table.add(manaStack).width(maxHealthBarWidth).height(healthBarHeight);
        stage.addActor(table);

        Texture circleTexture = assetManager.get("Ui/sword_circle.png");
        Texture swordTexture = assetManager.get("Entities/Sword/Common.png");
        Texture keyTexture = assetManager.get("Entities/Items/Key.png");

        swordSlotCircle = new Image(circleTexture);
        equippedSwordIcon = new Image(swordTexture);

        swordSlotCircle.setSize(128, 128);
        equippedSwordIcon.setSize(48, 48);
        equippedSwordIcon.setOrigin(Align.center);
        equippedSwordIcon.setAlign(Align.center);

        swordStack = new Stack();
        swordStack.add(swordSlotCircle);
        Container<Image> swordIconContainer = new Container<>(equippedSwordIcon);
        swordIconContainer.size(48, 48);
        swordIconContainer.align(Align.center);
        swordStack.add(swordIconContainer);
        Table swordTable = new Table();

        // Add the table to the stage
        stage.addActor(swordTable);

        keyIcon = new Image(keyTexture);
        keyIcon.setRotation(270f);
        keyIcon.setSize(32, 32);
        keyIcon.setOrigin(Align.center);

        BitmapFont keyFont = new BitmapFont();
        keyFont.getData().setScale(0.8f);
        LabelStyle keyStyle = new LabelStyle(keyFont, Color.WHITE);

        keyLabel = new Label("0x", keyStyle);
        keyLabel.setAlignment(Align.left);

        Table keyRow = new Table();
        keyRow.add(keyIcon).size(32, 32).padRight(5);
        keyRow.add(keyLabel).align(Align.left).padLeft(5);

        Table leftHUDTable = new Table();
        leftHUDTable.bottom().left().pad(20);
        leftHUDTable.add(keyRow).left().padLeft(40).padBottom(-20).row(); // more right, less vertical gap
        leftHUDTable.add(swordStack).size(128, 128).top();
        stage.addActor(leftHUDTable);

        player.getContext().setPlayerHUD(this);
    }

    /**
     * Updates the HUD elements with current player stats.
     * Updates health bar, mana bar, and key status.
     */
    public void update() {
        float health = player.getHealth();
        float maxHealth = 100f;
        float healthPercent = Math.max(0f, health / maxHealth);
        healthBar.setSize(maxHealthBarWidth * healthPercent, healthBarHeight);
        healthLabel.setText((int) health + " / " + (int) maxHealth);

        float mana = player.getCurrentMana();
        float maxMana = player.getMaxMana();
        float manaPercent = Math.max(0f, mana / maxMana);
        manaBar.setSize(maxHealthBarWidth * manaPercent, healthBarHeight);
        manaLabel.setText((int) mana + " / " + (int) maxMana);

        keyLabel.setText(player.hasKey() ? "1x" : "0x");
    }

    /**
     * Updates the equipped sword icon with a new texture.
     *
     * @param texturePath Path to the new sword texture
     */
    public void updateEquippedSword(String texturePath) {
        Texture newSwordTexture = new Texture(Gdx.files.internal(texturePath));
        equippedSwordIcon.setDrawable(new Image(newSwordTexture).getDrawable());
    }
}
