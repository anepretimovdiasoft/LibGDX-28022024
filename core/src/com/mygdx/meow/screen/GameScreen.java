package com.mygdx.meow.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.meow.MeowGame;
import com.mygdx.meow.camera.OrthographicCameraWithLeftRightState;
import com.mygdx.meow.ui.GameUserInterface;
import com.mygdx.meow.unit.Worker;

import java.util.Random;

public class GameScreen implements Screen {
    public static final int WORLD_WIDTH = MeowGame.SCREEN_WIDTH * 2;
    private final MeowGame meowGame;
    private OrthographicCameraWithLeftRightState camera;
    private SpriteBatch batch;
    private Texture background;
    private GameUserInterface gameUserInterface;

    private Rectangle coreTower;
    private Rectangle resource;
    private Texture tmpTexture;
    private Vector3 touchPoint;
    private Worker worker;

    public GameScreen(MeowGame meowGame) {
        this.meowGame = meowGame;
    }

    @Override
    public void show() {
        batch = meowGame.getSpriteBatch();

        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, MeowGame.SCREEN_WIDTH, MeowGame.SCREEN_HEIGHT);

        background = new Texture(Gdx.files.internal("game_back.png"));

        gameUserInterface = new GameUserInterface(camera);

        tmpTexture = new Texture(Gdx.files.internal("tmp.png"));
        coreTower = new Rectangle(
                MeowGame.SCREEN_WIDTH - 150 - 50,
                MeowGame.SCREEN_HEIGHT / 2f - 75,
                150,
                150
        );
        resource = new Rectangle(
                0 + 50,
                MeowGame.SCREEN_HEIGHT / 2f - 50,
                100,
                100
        );

        worker = new Worker(coreTower);
        touchPoint = new Vector3();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (worker.contains(touchPoint.x, touchPoint.y)) {
                worker.clicked();
            } else {
                if (worker.getCurrentState() == Worker.StateWorker.CLICKED) {
                    boolean startWorking = false;

                    if (resource.contains(touchPoint.x, touchPoint.y)) {
                        worker.setWorkingPlace(resource);
                        worker.setCurrentState(Worker.StateWorker.GO_TO);
                        worker.setDestination(resource);
                        startWorking = true;
                    }

                    if (!startWorking) worker.setCurrentState(Worker.StateWorker.SLEEP);
                }
            }



        }

        batch.begin();

        batch.draw(background, 0, 0, MeowGame.SCREEN_WIDTH * 2, MeowGame.SCREEN_HEIGHT);

        if (camera.isLeftState()) {
            batch.draw(tmpTexture, coreTower.x, coreTower.y, coreTower.width, coreTower.height);
            batch.draw(tmpTexture, resource.x, resource.y, resource.width, resource.height);
        }

        if (worker.isAlive()) {
            worker.sleepSametime();
            worker.nextXY();
            if (camera.isLeftState()) {
                worker.draw(batch);
            }
            worker.setTimeInState(deltaTime);
        }

        batch.end();

        gameUserInterface.drawUI();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameUserInterface.dispose();
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
