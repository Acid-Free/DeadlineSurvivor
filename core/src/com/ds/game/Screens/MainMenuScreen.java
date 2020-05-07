package com.ds.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ds.game.DeadlineSurvivor;

public class MainMenuScreen implements Screen {
    final DeadlineSurvivor game;

    private GameScreen gameScreen;

    OrthographicCamera camera;

    public MainMenuScreen(final DeadlineSurvivor game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
        gameScreen = new GameScreen(game);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.29f, 0.29f, 0.29f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(Color.GOLD);
        game.font.draw(game.batch, "Deadline Survivor", 100, 370);
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Click anywhere to start", 120, 200);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(gameScreen);
            dispose();
        }
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

    @Override
    public void dispose() {

    }
}
