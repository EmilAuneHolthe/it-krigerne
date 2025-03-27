package inf112.skeleton.view.ui;

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

import inf112.skeleton.model.entity.Player;

public class PlayerHUD {
    private final Player player;
    private final Image healthBar;
    private final Label healthLabel;
    private final float maxHealthBarWidth = 200f;

    public PlayerHUD(Stage stage, Player player, Texture healthTexture, Texture backgroundTexture) {
        this.player = player;

        // Health bar (foreground)
        healthBar = new Image(healthTexture);
        healthBar.setSize(maxHealthBarWidth, 20);
        healthBar.setOrigin(Align.bottomLeft);

        // Health bar background
        Image healthBg = new Image(backgroundTexture);
        healthBg.setSize(maxHealthBarWidth, 20);

        // Label setup with inline style
        BitmapFont font = new BitmapFont(); // Default font
        LabelStyle style = new LabelStyle(font, Color.WHITE);
        healthLabel = new Label("100 / 100", style);
        healthLabel.setAlignment(Align.center);

        // Stack everything
        Stack healthStack = new Stack();
        healthStack.add(healthBg);
        healthStack.add(healthBar);
        healthStack.add(healthLabel);
        healthStack.setSize(maxHealthBarWidth, 20);

        // Place using Table
        Table table = new Table();
        table.setFillParent(true);
        table.top().left().padTop(10).padLeft(10);
        table.add(healthStack).width(maxHealthBarWidth).height(20);
        stage.addActor(table);
    }

    public void update() {
        float health = player.getHealth();
        float percent = Math.max(0f, health / 100f);
        healthBar.setSize(maxHealthBarWidth * percent, 20);
        healthLabel.setText((int) health + " / 100");
    }
}
