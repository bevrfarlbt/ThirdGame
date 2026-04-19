package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.components.MovingBackgroundView;
import com.mygdx.game.components.TextView;
import com.mygdx.game.managers.MemoryManager;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView blackoutImageView;
    ButtonView returnButton;

    TextView musicSettingView;
    TextView soundSettingView;
    TextView clearSettingView;

    TextView musicVolumeUp;
    TextView musicVolumeDown;
    TextView soundVolumeUp;
    TextView soundVolumeDown;
    TextView musicVolumePercent;
    TextView soundVolumePercent;

    private float musicVolume;
    private float soundVolume;

    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(myGdxGame.largeWhiteFont, 280, 956, "Settings");
        blackoutImageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);

        clearSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 550, "clear records");

        musicVolume = MemoryManager.loadMusicVolume();
        soundVolume = MemoryManager.loadSoundVolume();

        musicSettingView = new TextView(
                myGdxGame.commonWhiteFont,
                173, 750,
                "music: " + translateStateToText(MemoryManager.loadIsMusicOn())
        );

        soundSettingView = new TextView(
                myGdxGame.commonWhiteFont,
                173, 690,
                "sound: " + translateStateToText(MemoryManager.loadIsSoundOn())
        );


        musicVolumeDown = new TextView(myGdxGame.commonWhiteFont, 480, 750, "-");
        musicVolumeUp = new TextView(myGdxGame.commonWhiteFont, 580, 750, "+");
        musicVolumePercent = new TextView(myGdxGame.commonWhiteFont, 500, 750, (int)(musicVolume * 100) + "%"); // было 510, стало 500

        soundVolumeDown = new TextView(myGdxGame.commonWhiteFont, 480, 690, "-");
        soundVolumeUp = new TextView(myGdxGame.commonWhiteFont, 580, 690, "+");
        soundVolumePercent = new TextView(myGdxGame.commonWhiteFont, 500, 690, (int)(soundVolume * 100) + "%");
        returnButton = new ButtonView(
                280, 400,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "return"
        );
    }

    @Override
    public void render(float delta) {
        handleInput();
        draw();
    }

    void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            float touchX = myGdxGame.touch.x;
            float touchY = myGdxGame.touch.y;

            if (returnButton.isHit(touchX, touchY)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }

            if (clearSettingView.isHit(touchX, touchY)) {
                MemoryManager.saveTableOfRecords(new ArrayList<Integer>());
                clearSettingView.setText("clear records (cleared)");
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        clearSettingView.setText("clear records");
                    }
                }, 2);
            }

            if (musicSettingView.isHit(touchX, touchY)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
                musicSettingView.setText("music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                myGdxGame.audioManager.updateMusicFlag();
            }

            if (soundSettingView.isHit(touchX, touchY)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                soundSettingView.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                myGdxGame.audioManager.updateSoundFlag();
            }

            if (musicVolumeUp.isHit(touchX, touchY)) {
                musicVolume = Math.min(1f, musicVolume + 0.05f);
                MemoryManager.saveMusicVolume(musicVolume);
                myGdxGame.audioManager.setMusicVolume(musicVolume);
                musicVolumePercent.setText((int)(musicVolume * 100) + "%");
            }

            if (musicVolumeDown.isHit(touchX, touchY)) {
                musicVolume = Math.max(0f, musicVolume - 0.05f);
                MemoryManager.saveMusicVolume(musicVolume);
                myGdxGame.audioManager.setMusicVolume(musicVolume);
                musicVolumePercent.setText((int)(musicVolume * 100) + "%");
            }

            if (soundVolumeUp.isHit(touchX, touchY)) {
                soundVolume = Math.min(1f, soundVolume + 0.05f);
                MemoryManager.saveSoundVolume(soundVolume);
                myGdxGame.audioManager.setSoundVolume(soundVolume);
                soundVolumePercent.setText((int)(soundVolume * 100) + "%");
                myGdxGame.audioManager.playShootSound();
            }

            if (soundVolumeDown.isHit(touchX, touchY)) {
                soundVolume = Math.max(0f, soundVolume - 0.05f);
                MemoryManager.saveSoundVolume(soundVolume);
                myGdxGame.audioManager.setSoundVolume(soundVolume);
                soundVolumePercent.setText((int)(soundVolume * 100) + "%");
                myGdxGame.audioManager.playShootSound();
            }
        }
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleTextView.draw(myGdxGame.batch);
        blackoutImageView.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);

        musicSettingView.draw(myGdxGame.batch);
        soundSettingView.draw(myGdxGame.batch);
        clearSettingView.draw(myGdxGame.batch);

        musicVolumeDown.draw(myGdxGame.batch);
        musicVolumeUp.draw(myGdxGame.batch);
        musicVolumePercent.draw(myGdxGame.batch);

        soundVolumeDown.draw(myGdxGame.batch);
        soundVolumeUp.draw(myGdxGame.batch);
        soundVolumePercent.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
}