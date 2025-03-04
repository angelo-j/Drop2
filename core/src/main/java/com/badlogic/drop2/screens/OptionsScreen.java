package com.badlogic.drop2.screens;

import com.badlogic.drop2.Main;
import com.badlogic.drop2.managers.ScreenManager;
import com.badlogic.drop2.managers.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class OptionsScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Texture backgroundTexture;
    private float soundVolume = 1.0f;
    private float musicVolume = 0.5f;
    private boolean soundMuted = false;
    private boolean musicMuted = false;

    public OptionsScreen(Main game) {
        this.game = game;
        this.batch = game.batch;
        this.font = game.font;

        // Load the same background texture as other screens
        backgroundTexture = new Texture("visual/background.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        loadSettings();
        createOptionsMenu();
    }

    private void createOptionsMenu() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label titleLabel = new Label("Options", labelStyle);
        titleLabel.setFontScale(2);
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel).padBottom(30).row();

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.up = null;

        TextButton resetScoreButton = new TextButton("Reset High Score", buttonStyle);
        resetScoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FileHandle file = Gdx.files.local("highscore.txt");
                file.writeString("0", false);
                System.out.println("High score reset to 0!");
            }
        });
        table.add(resetScoreButton).padTop(20).align(Align.center).row();

        // Sound Volume
        Label soundLabel = new Label("Sound Effects Volume", labelStyle);
        soundLabel.setAlignment(Align.center);
        table.add(soundLabel).padBottom(10).row();
        Slider soundSlider = new Slider(0, 1, 0.1f, false, skin);
        soundSlider.setValue(soundVolume);
        soundSlider.addListener(event -> {
            soundVolume = soundSlider.getValue();
            SoundManager.setSoundVolume(soundVolume);
            saveSettings();
            return false;
        });
        table.add(soundSlider).width(300).padBottom(20).row();

        // Sound Mute
        CheckBox soundMuteToggle = new CheckBox(" Mute Sound Effects", skin);
        soundMuteToggle.setChecked(soundMuted);
        soundMuteToggle.addListener(event -> {
            soundMuted = soundMuteToggle.isChecked();
            SoundManager.muteSound(soundMuted);
            saveSettings();
            return false;
        });
        table.add(soundMuteToggle).padBottom(20).row();

        // Music Volume
        Label musicLabel = new Label("Music Volume", labelStyle);
        musicLabel.setAlignment(Align.center);
        table.add(musicLabel).padBottom(10).row();
        Slider musicSlider = new Slider(0, 1, 0.1f, false, skin);
        musicSlider.setValue(musicVolume);
        musicSlider.addListener(event -> {
            musicVolume = musicSlider.getValue();
            SoundManager.setMusicVolume(musicVolume);
            saveSettings();
            return false;
        });
        table.add(musicSlider).width(300).padBottom(20).row();

        // Music Mute
        CheckBox musicMuteToggle = new CheckBox(" Mute Music", skin);
        musicMuteToggle.setChecked(musicMuted);
        musicMuteToggle.addListener(event -> {
            musicMuted = musicMuteToggle.isChecked();
            SoundManager.muteMusic(musicMuted);
            saveSettings();
            return false;
        });
        table.add(musicMuteToggle).padBottom(20).row();

        // Back Button
        TextButton backButton = new TextButton("Back", buttonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveSettings();
                ScreenManager.setScreen(ScreenManager.ScreenType.MAIN_MENU);
            }
        });
        table.add(backButton).padTop(30);
    }

    private void loadSettings() {
        FileHandle file = Gdx.files.local("settings.txt");
        if (file.exists()) {
            String[] settings = file.readString().split(",");
            try {
                soundVolume = Float.parseFloat(settings[0]);
                musicVolume = Float.parseFloat(settings[1]);
                soundMuted = Boolean.parseBoolean(settings[2]);
                musicMuted = Boolean.parseBoolean(settings[3]);

                SoundManager.setSoundVolume(soundVolume);
                SoundManager.setMusicVolume(musicVolume);
                SoundManager.muteSound(soundMuted);
                SoundManager.muteMusic(musicMuted);
            } catch (Exception e) {
                Gdx.app.log("OptionsScreen", "Failed to load settings.");
            }
        }
    }

    private void saveSettings() {
        FileHandle file = Gdx.files.local("settings.txt");
        file.writeString(soundVolume + "," + musicVolume + "," + soundMuted + "," + musicMuted, false);
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
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Options", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 50, 0, Align.center, false);
        batch.end();

        stage.act(delta);
        stage.draw();
    }
}
