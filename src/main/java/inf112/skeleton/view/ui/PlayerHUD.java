package inf112.skeleton.view.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import inf112.skeleton.model.entity.Player;

public class PlayerHUD {
    private final Player player;
    private final Image healthBar;
    private final float maxHealthBarWidth = 200f;

    public PlayerHUD(Stage stage, Player player, Texture healthTexture) {
        this.player = player;

        // Health bar setup
        healthBar = new Image(healthTexture);
        healthBar.setSize(maxHealthBarWidth, 20);
        healthBar.setOrigin(Align.bottomLeft);

        Table table = new Table();
        table.setFillParent(true);
        table.top().left().padTop(10).padLeft(10);
        table.add(healthBar).width(maxHealthBarWidth).height(20);
        stage.addActor(table);
    }

    public void update() {
        float percent = Math.max(0f, player.getHealth() / 100f); // assuming max = 100
        healthBar.setSize(maxHealthBarWidth * percent, 20);
    }
}

