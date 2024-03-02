package com.mygdx.meow.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.meow.MeowGame;
import com.mygdx.meow.camera.OrthographicCameraWithLeftRightState;
import com.mygdx.meow.ui.MenuUserInterface;

public class MenuScreen implements Screen {
    private final MeowGame meowGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private MenuUserInterface menuUserInterface;

    public MenuScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("menu_back.png"));

        menuUserInterface = new MenuUserInterface(
                camera,
                () -> meowGame.changeScreen(MeowGame.GAME)
        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);
        /*meowGame.getFont().draw(
                batch,
                "TAP TO START",
                MeowGame.SCREEN_WIDTH / 2f - 100,
                MeowGame.SCREEN_HEIGHT / 2f
        );*/

        batch.end();

        menuUserInterface.drawUI();
    }

    @Override
    public void dispose() {
        background.dispose();
        menuUserInterface.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
