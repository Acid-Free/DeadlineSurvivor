package com.ds.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.ds.game.Entities.Player;
import com.ds.game.Screens.MainMenuScreen;

public class DeadlineSurvivor extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 480;
	public static final String GAME_TITLE = "Deadline Survivor";

	public SpriteBatch batch;
	public BitmapFont font;

	public Player player;

	public long startTime;

	@Override
	public void create () {
	    startTime = TimeUtils.nanoTime();

		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
