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

import inf112.skeleton.model.entity.player.Player;

public class PlayerHUD {
    private final Player player;
    private final Image healthBar;
    private final Label healthLabel;
    private final float maxHealthBarWidth = 240f;
    private final float healthBarHeight = 24f;
    private final Image manaBar;
    private final Label manaLabel;

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
}
