package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mathutil.Vector2;

public class BackgroundTile extends Sprite
{

	public static final double TILE_SIZE = 3;

	public enum TileType
	{
		DIRT(ImageLoader.DirtTile.getLoadedImage()),
		GRASS(ImageLoader.GrassTile.getLoadedImage()),
		SAND(ImageLoader.SandTile.getLoadedImage());

		BufferedImage image;

		private TileType(BufferedImage image)
		{
			this.image = image;
		}

		public BufferedImage getImage()
		{
			return image;
		}
	}

	public BackgroundTile(TileType type, Vector2 position)
	{
		super(type.getImage(), DrawLayer.BACKGROUND.getValue(), position, 0, TILE_SIZE, TILE_SIZE);
	}

	public static ArrayList<BackgroundTile> generateBackgroundTiles(TileType type, Vector2 topLeft, Vector2 bottomRight)
	{
		ArrayList<BackgroundTile> tiles = new ArrayList<BackgroundTile>();
		double tileY = topLeft.y - TILE_SIZE / 2;
		while (tileY > bottomRight.y - TILE_SIZE)
		{
			double tileX = topLeft.x + TILE_SIZE / 2;
			while (tileX < bottomRight.x + TILE_SIZE)
			{
				BackgroundTile tile = new BackgroundTile(type, new Vector2(tileX, tileY));
				tiles.add(tile);
				tileX += TILE_SIZE - 0.02;
			}
			tileY -= TILE_SIZE - 0.02;
		}
		return tiles;
	}

}
