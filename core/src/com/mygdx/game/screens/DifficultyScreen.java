package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.components.TextView;

public class DifficultyScreen extends ScreenAdapter {

    private final MyGdxGame myGdxGame;

    private final ImageView backgroundView;
    private final TextView titleText1;
    private final TextView titleText2;
    private final ButtonView easyButton;
    private final TextView easyText;
    private final ButtonView hardButton;
    private final TextView hardText;
    private final ButtonView backButton;

    public DifficultyScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new ImageView(0, 0, GameResources.BACKGROUND_IMG_PATH);


        titleText1 = new TextView(myGdxGame.largeWhiteFont, 300, 1130, "CHOOSE");
        titleText2 = new TextView(myGdxGame.largeWhiteFont, 260, 1070, "DIFFICULTY");


        easyButton = new ButtonView(
                210, 800, 300, 80,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "EASY"
        );

        easyText = new TextView(myGdxGame.commonWhiteFont, 240, 740, "Different trash skins");


        hardButton = new ButtonView(
                210, 550, 300, 80,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_LONG_BG_IMG_PATH,
                "HARD"
        );

        hardText = new TextView(myGdxGame.commonWhiteFont, 230, 490, "Some trash need 2 hits!");


        backButton = new ButtonView(
                260, 300, 200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Back"
        );
    }

    @Override
    public void render(float delta) {
        handleInput();
        draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            float touchX = myGdxGame.touch.x;
            float touchY = myGdxGame.touch.y;

            if (easyButton.isHit(touchX, touchY)) {
                GameSettings.isHardMode = false;
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            else if (hardButton.isHit(touchX, touchY)) {
                GameSettings.isHardMode = true;
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            else if (backButton.isHit(touchX, touchY)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
        }
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        titleText1.draw(myGdxGame.batch);
        titleText2.draw(myGdxGame.batch);
        easyButton.draw(myGdxGame.batch);
        easyText.draw(myGdxGame.batch);
        hardButton.draw(myGdxGame.batch);
        hardText.draw(myGdxGame.batch);
        backButton.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {}
}