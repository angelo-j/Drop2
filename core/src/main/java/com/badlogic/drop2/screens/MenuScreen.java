package com.badlogic.drop2.screens;

import com.badlogic.drop2.Main;
import com.badlogic.drop2.managers.ScreenManager;
import com.badlogic.drop2.managers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background;
    private Stage stage;
    private Skin skin;
    private int highScore = 0;

    public MenuScreen(Main game) {
        this.game = game;
        this.batch = game.batch;
        this.font = game.font;

        background = new Texture("visual/background.png");
        highScore = loadHighScore();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        createMenu();
    }

    private void createMenu() {

        // All on-screen elements share one table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Simple button setup, prepares yellow button highlight
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = null;
        buttonStyle.overFontColor = Color.YELLOW;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label titleLabel = new Label("Drop", labelStyle);
        titleLabel.setFontScale(3, 5);
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel).padBottom(30).align(Align.center).row();

        Label highScoreLabel = new Label("High Score: " + highScore, labelStyle);
        highScoreLabel.setAlignment(Align.center);
        table.add(highScoreLabel).padBottom(20).align(Align.center).row();

        TextButton startButton = new TextButton("Start Game", buttonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.setScreen(ScreenManager.ScreenType.GAME);         // Switches to GameScreen
            }
        });

        TextButton optionsButton = new TextButton("Options", buttonStyle);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.setScreen(ScreenManager.ScreenType.OPTIONS);      // Switches to OptionsScreen
            }
        });

        TextButton exitGameButton = new TextButton("Exit Game", buttonStyle);
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(startButton).padBottom(20).align(Align.center).row();
        table.add(optionsButton).padBottom(20).align(Align.center).row();
        table.add(exitGameButton).align(Align.center).row();
    }

    // Loads high score from highscore.txt
    private int loadHighScore() {
        FileHandle file = Gdx.files.local("highscore.txt");
        if (file.exists()) {
            try {
                return Integer.parseInt(file.readString());
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        SoundManager.playMusic();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        SoundManager.pauseMusic();
    }

    @Override
    public void resume() {
        SoundManager.playMusic();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        skin.dispose();
    }
}
