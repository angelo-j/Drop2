package com.badlogic.drop2.screens;

import com.badlogic.drop2.Main;
import com.badlogic.drop2.managers.ScreenManager;
import com.badlogic.drop2.managers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.drop2.models.Raindrop;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private Texture bucketTexture, dropTexture, backgroundTexture;
    private Rectangle bucketRectangle;
    private static Array<Raindrop> rainDrops = new Array<>();
    private static int score = 0;
    private int highScore = 0;
    private long lastDropTime;
    private long startTime;
    private int remainingTime = 45;
    private BitmapFont font;
    private boolean isPaused = false;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.batch;
        this.font = game.font;

        bucketTexture = new Texture("visual/bucket.png");
        dropTexture = new Texture("visual/drop.png");
        backgroundTexture = new Texture("visual/background.png");

        // Prepares rudimentary collision for bucket
        bucketRectangle = new Rectangle(Gdx.graphics.getWidth() / 2f - 32, 20, 64, 64);

        // Replenishes Raindrop Array
        if (rainDrops.size == 0) {
            spawnRaindrop();
        }

        highScore = loadHighScore();  // Load high score at start
        startTime = TimeUtils.nanoTime();  // Start the timer
    }

    private void spawnRaindrop() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth() - 64);
        float speed = MathUtils.random(150, 250);  // Random speed for each drop
        rainDrops.add(new Raindrop(dropTexture, x, Gdx.graphics.getHeight(), speed));
        lastDropTime = TimeUtils.nanoTime();
    }

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

    private void saveHighScore() {
        FileHandle file = Gdx.files.local("highscore.txt");
        file.writeString(String.valueOf(highScore), false);
    }

    @Override
    public void render(float delta) {

        // Implements timer
        long elapsedTime = (TimeUtils.nanoTime() - startTime) / 1000000000;
        remainingTime = 30 - (int) elapsedTime;

        if (remainingTime <= 0) {
            if (score > highScore) {
                highScore = score;
                saveHighScore();  // Save new high score
            }
            score = 0;  // Reset current score
            ScreenManager.setScreen(ScreenManager.ScreenType.MAIN_MENU);  // Go back to MenuScreen
        }

        // Clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Prepare batch
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Score: " + score, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 20, 0, Align.center, false);
        font.draw(batch, "High Score: " + highScore, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 50, 0, Align.center, false);
        font.draw(batch, "Time Left: " + remainingTime, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 80, 0, Align.center, false);
        batch.draw(bucketTexture, bucketRectangle.x, bucketRectangle.y);
        for (Raindrop raindrop : rainDrops) {
            raindrop.draw(batch);
        }

        batch.end();

        handleInput(delta); // Important: handles user left-right input and screen bounds
        updateRaindrops(delta); // Maintains rainfall
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ScreenManager.setScreen(ScreenManager.ScreenType.PAUSE, score);  // Go to PauseScreen
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucketRectangle.x -= 300 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucketRectangle.x += 300 * delta;
        bucketRectangle.x = MathUtils.clamp(bucketRectangle.x, 0, Gdx.graphics.getWidth() - 64);
    }

    private void updateRaindrops(float delta) {
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        Iterator<Raindrop> iter = rainDrops.iterator();
        while (iter.hasNext()) {
            Raindrop raindrop = iter.next();
            raindrop.move(delta);
            if (raindrop.overlaps(bucketRectangle)) {
                SoundManager.playDropSound();
                score++;
                iter.remove();
            } else if (raindrop.isOffScreen()) {
                iter.remove();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "Resized to " + width + "x" + height);
    }

    @Override
    public void pause() {
        SoundManager.pauseMusic();
        isPaused = true;
        Gdx.app.log("GameScreen", "Game is paused.");
    }

    @Override
    public void resume() {
        SoundManager.playMusic();
        isPaused = false;
        Gdx.app.log("GameScreen", "Game is resumed.");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
        SoundManager.playMusic();
        Gdx.app.log("GameScreen", "Game started.");
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        Gdx.app.log("GameScreen", "GameScreen is now hidden.");
    }

    @Override
    public void dispose() {
        bucketTexture.dispose();
        dropTexture.dispose();
        backgroundTexture.dispose();
        Gdx.app.log("GameScreen", "Disposed GameScreen resources.");
    }
}
