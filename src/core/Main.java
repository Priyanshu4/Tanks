package core;

import graphics.ImageLoader;

public class Main
{
	public static void main(String[] args)
	{
		ImageLoader.preloadImages();
		TankVersusTank tvt = new TankVersusTank();
		Window window = new Window("Tanks", false, true);
		Game game = new Game(window, tvt, 50, 50);
		game.startGame();
	}

}
