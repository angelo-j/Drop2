package com.badlogic.drop2.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    private static Music music;
    private static Sound dropSound;
    private static float musicVolume = 0.5f;
    private static float soundVolume = 1.0f;
    private static boolean musicMuted = false;
    private static boolean soundMuted = false;

    public static void load() {
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.MP3"));
        music.setLooping(true);
        music.setVolume(musicMuted ? 0 : musicVolume);

        dropSound = Gdx.audio.newSound(Gdx.files.internal("audio/drop.MP3"));
    }

    public static void playMusic() {
        if (!musicMuted && !music.isPlaying()) {
            music.setVolume(musicVolume);
            music.play();
        }
    }

    public static boolean isMusicPlaying() {
        return music.isPlaying();
    }

    public static void setMusicVolume(float volume) {
        musicVolume = volume;
        if (!musicMuted) {
            music.setVolume(volume);
        }
    }

    public static void setSoundVolume(float volume) {
        soundVolume = volume;
    }

    public static void muteMusic(boolean mute) {
        musicMuted = mute;
        music.setVolume(mute ? 0 : musicVolume);
    }

    public static void muteSound(boolean mute) {
        soundMuted = mute;
    }

    public static void pauseMusic() {
        music.pause();
    }

    public static void stopMusic() {
        music.stop();
    }

    public static void playDropSound() {
        if (!soundMuted) {
            dropSound.play(soundVolume);
        }
    }

    public static void dispose() {
        music.dispose();
        dropSound.dispose();
    }
}
