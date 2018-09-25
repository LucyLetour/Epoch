package com.epochgames.epoch;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epochgames.epoch.screens.InGame;
import com.epochgames.epoch.screens.MainMenu;
import com.epochgames.epoch.util.Assets;

public class Epoch extends Game {
    public static boolean debug = false;

	public Viewport viewport;
	public OrthographicCamera camera;

	public static GameManager gameManager;

	public BitmapFont font;
	public SpriteBatch batch;
	public SpriteBatch guiBatch;
	public boolean assetsLoaded = false;

	public Screen activeScreen;
	public InGame inGameScreen;
	public MainMenu mainMenuScreen;

	public Epoch(boolean debugMode) {
	    super();
	    debug = debugMode;
    }

	@Override
	public void create () {
		//Set the log level first thing so that we know what's going on
		Gdx.app.setLogLevel(debug ? Gdx.app.LOG_DEBUG : Gdx.app.LOG_INFO);

		//Loads Assets (Go figure)
		loadAssets();

		//Loading Assets
		while(!assetsLoaded) {
			//Use this to make a sick progress bar
			Gdx.app.log("Loading",  Assets.MANAGER.getProgress() * 100 + "% complete");

			//Updates assetsLoaded to true if all assets are loaded
			assetsLoaded = Assets.MANAGER.update();
		}

		//Create the GameManager
		gameManager = GameManager.getInstance();
		gameManager.setGame(this);

		//Setup the Camera, Viewport and Stage
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(1920, 1080, camera);
		camera.setToOrtho(false, viewport.getScreenWidth(), viewport.getScreenHeight());

		//Create the Batches
		batch = new SpriteBatch();
		guiBatch = new SpriteBatch();
		font = new BitmapFont();

		//Set the screen to our starting screen and add all of them
		//TODO
		initializeScreens();
		gameManager.setGameState(GameManager.GameState.IN_GAME);
	}

	@Override
	public void render () {
		//Render the appropriate context by switching to the correct screen if we're not there already
		if(getScreen() != activeScreen) {
			setScreen(activeScreen);
		}

		//Calls the render() method of the appropriate screen
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		guiBatch.dispose();
		font.dispose();
	}

	private void initializeScreens() {
		inGameScreen = new InGame(this);
		mainMenuScreen = new MainMenu(this);
	}

	private void loadAssets() {
		Assets.MANAGER.load(Assets.Spritesheets.SHIPS);
		Assets.MANAGER.load(Assets.TileMaps.OPEN_SPACE);
		Assets.MANAGER.load(Assets.TileMaps.OPEN_SPACE_TILE_SET);
		Assets.MANAGER.load(Assets.Textures.HEX_TILE);
	}

	public void setActiveScreen(Screen screen) {
		this.activeScreen = screen;
		setScreen(screen);
	}

	public void setActiveScreen(GameManager.GameState gameState) {
		Screen screen;
		switch (gameState) {
			case MAIN_MENU:
				screen = mainMenuScreen;
				break;
			case IN_GAME:
				screen = inGameScreen;
				break;
			default:
				Gdx.app.error("Invalid Screen", "An invalid game state was given");
				screen = null;
				break;
		}
		setActiveScreen(screen);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
