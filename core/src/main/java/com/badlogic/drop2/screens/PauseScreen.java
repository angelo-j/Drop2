package com.badlogic.drop2.screens;

import com.badlogic.drop2.Main;
import com.badlogic.drop2.managers.ScreenManager;
import com.badlogic.drop2.managers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private int currentScore;
    private Texture backgroundTexture;
    private ShapeRenderer shapeRenderer;

    public PauseScreen(Main game, int currentScore) {
        this.game = game;
        this.batch = game.batch;
        this.font = game.font;
        this.currentScore = currentScore;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        shapeRenderer = new ShapeRenderer();

        backgroundTexture = new Texture("visual/background.png");

        createPauseMenu();
    }

    private void createPauseMenu() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = null;  // Transparent background

        // Proper labels instead of placeholders
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label titleLabel = new Label("Paused", labelStyle);
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel).padBottom(50).align(Align.center).row();

        Label scoreLabel = new Label("Score: " + currentScore, labelStyle);
        scoreLabel.setAlignment(Align.center);
        table.add(scoreLabel).padBottom(30).align(Align.center).row();

        // Resume Button
        TextButton resumeButton = new TextButton("Resume", buttonStyle);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.playMusic();
                ScreenManager.setScreen(ScreenManager.ScreenType.GAME);
            }
        });

        // Quit Button
        TextButton quitButton = new TextButton("Quit", buttonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.playMusic();
                ScreenManager.setScreen(ScreenManager.ScreenType.MAIN_MENU);
            }
        });

        // Add buttons to table with padding
        table.add(resumeButton).padBottom(20).align(Align.center).row();
        table.add(quitButton).align(Align.center).row();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        backgroundTexture.dispose();
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 0.5f);
        shapeRenderer.rect(200, 100, Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 200);
        shapeRenderer.end();

        stage.act(delta);
        stage.draw();
    }
}
