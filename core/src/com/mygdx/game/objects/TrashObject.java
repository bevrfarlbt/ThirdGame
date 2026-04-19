package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject {

    private static final int paddingHorizontal = 30;
    private static final Random random = new Random();

    private int livesLeft;
    private boolean isHardTrash;

    public TrashObject(int width, int height, String texturePath, World world) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + random.nextInt(GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.TRASH_BIT,
                world
        );
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = 1;
        isHardTrash = false;
    }

    public TrashObject(World world) {
        this(
                GameSettings.TRASH_WIDTH,
                GameSettings.TRASH_HEIGHT,
                getRandomTexture(),
                world
        );

        if (GameSettings.isHardMode) {
            isHardTrash = random.nextInt(100) < 30;
            livesLeft = isHardTrash ? 2 : 1;
        } else {
            isHardTrash = false;
            livesLeft = 1;
        }
    }

    private static String getRandomTexture() {
        int r = random.nextInt(3);
        switch (r) {
            case 0: return GameResources.TRASH_1_IMG_PATH;
            case 1: return GameResources.TRASH_2_IMG_PATH;
            default: return GameResources.TRASH_3_IMG_PATH;
        }
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }
}