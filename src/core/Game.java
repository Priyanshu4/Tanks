package core;

import graphics.Camera;
import input.KeyInput;
import input.MouseInput;

public class Game implements Runnable
{
	double fps = 20;
	double updatesPerSecond = 20;
	double deltaTime = 1d / updatesPerSecond;
	double accumulatedUpdateTime = 0;
	double timeSinceLastFrame = 0;
	double loopStart;

	private boolean fullySetup = false;
	private boolean finished = false;

	private GameLogic gameLogic;
	private Thread gameLoopThread;
	private Window window;
	private Scene scene = new Scene();
	private Camera camera = new Camera();
	private KeyInput keyInput = new KeyInput();
	private MouseInput mouseInput = new MouseInput();

	private static Game GAME_ENGINE;

	public Game(Window window, GameLogic gameLogic)
	{
		GAME_ENGINE = this;
		this.window = window;
		this.gameLogic = gameLogic;
		this.gameLoopThread = new Thread(this);
	}

	public Game(Window window, GameLogic gameLogic, double fps, double updatesPerSecond)
	{
		GAME_ENGINE = this;
		this.window = window;
		this.gameLogic = gameLogic;
		this.gameLoopThread = new Thread(this);
		this.fps = fps;
		this.setUpdatesPerSecond(updatesPerSecond);
	}

	private void setup()
	{
		window.setup();
		gameLogic.setup();
		fullySetup = true;
	}

	private void update(double deltaTime)
	{
		scene.update(deltaTime);
		gameLogic.update(deltaTime);
		keyInput.update();
		mouseInput.update();
	}

	private void render()
	{
		window.update();
	}

	public void startGame()
	{
		gameLoopThread.start();
	}

	@Override
	public void run()
	{
		setup();
		while (!finished)
		{
			loop();
		}
		window.dispose();
	}

	public void loop()
	{
		long currentTime = System.nanoTime();

		double elapsedTime = (currentTime - loopStart) / 1000000000d;
		accumulatedUpdateTime += elapsedTime;
		timeSinceLastFrame += elapsedTime;

		loopStart = currentTime;

		if (accumulatedUpdateTime > deltaTime * 5)
		{
			accumulatedUpdateTime = deltaTime * 5;
		}

		while (accumulatedUpdateTime >= deltaTime)
		{
			update(deltaTime);
			accumulatedUpdateTime -= deltaTime;
		}

		if (timeSinceLastFrame > (1d / fps))
		{
			render();
			timeSinceLastFrame = 0;
		}
	}

	public void setWindow(Window window)
	{
		this.window = window;
	}

	public static Game get()
	{
		return GAME_ENGINE;
	}

	public void setScene(Scene scene)
	{
		this.scene = scene;
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
		window.addFullWindowCamera(camera);
		camera.addKeyListener(keyInput);
		camera.addMouseListener(mouseInput);
	}

	public GameLogic getGameLogic()
	{
		return gameLogic;
	}

	public Window getWindow()
	{
		return window;
	}

	public Scene getScene()
	{
		return scene;
	}

	public Camera getCamera()
	{
		return camera;
	}

	public KeyInput getKeyInput()
	{
		return keyInput;
	}

	public MouseInput getMouseInput()
	{
		return mouseInput;
	}

	public double getDeltaTime()
	{
		return deltaTime;
	}

	public void setUpdatesPerSecond(double updatesPerSecond)
	{
		this.updatesPerSecond = updatesPerSecond;
		this.deltaTime = 1d / updatesPerSecond;
	}

	public void endGame()
	{
		this.finished = true;
	}

	public boolean isFullySetup()
	{
		return fullySetup;
	}

}
