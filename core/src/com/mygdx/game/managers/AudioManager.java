package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameResources;

public class AudioManager {

    public boolean isSoundOn;
    public boolean isMusicOn;

    public Music backgroundMusic;
    public Sound shootSound;
    public Sound explosionSound;


    private float musicVolume;
    private float soundVolume;

    public AudioManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));

        musicVolume = MemoryManager.loadMusicVolume();
        soundVolume = MemoryManager.loadSoundVolume();

        backgroundMusic.setVolume(musicVolume);
        backgroundMusic.setLooping(true);

        updateSoundFlag();
        updateMusicFlag();
    }

    public void updateSoundFlag() {
        isSoundOn = MemoryManager.loadIsSoundOn();
    }

    public void updateMusicFlag() {
        isMusicOn = MemoryManager.loadIsMusicOn();

        if (isMusicOn) {
            backgroundMusic.play();
            backgroundMusic.setVolume(musicVolume);
        } else {
            backgroundMusic.stop();
        }
    }


    public float getMusicVolume() {
        return musicVolume;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public void setMusicVolume(float volume) {
        musicVolume = Math.max(0f, Math.min(1f, volume));
        MemoryManager.saveMusicVolume(musicVolume);

        if (isMusicOn && backgroundMusic != null) {
            backgroundMusic.setVolume(musicVolume);
        }
    }

    public void setSoundVolume(float volume) {
        soundVolume = Math.max(0f, Math.min(1f, volume));
        MemoryManager.saveSoundVolume(soundVolume);
    }

    public void playShootSound() {
        if (isSoundOn && shootSound != null) {
            shootSound.play(soundVolume);
        }
    }

    public void playExplosionSound() {
        if (isSoundOn && explosionSound != null) {
            explosionSound.play(soundVolume);
        }
    }
}