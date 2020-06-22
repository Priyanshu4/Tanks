package obstacles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.DrawLayer;
import graphics.Graphic;
import graphics.ImageLoader;
import graphics.Sprite;
import mathutil.BoundingBox;
import mathutil.Vector2;

public class Sandbag extends Obstacle
{

	Sprite sprite;
	private static final double heightWidthRatio = 2d / 3;

	public Sandbag(Vector2 position, double rotation, double width, boolean brown)
	{
		super(new BoundingBox(position, rotation, width, width * heightWidthRatio));
		BufferedImage img;
		if (brown)
		{
			img = ImageLoader.BrownSandbag.getLoadedImage();
		} else
		{
			img = ImageLoader.BeigeSandbag.getLoadedImage();
		}
		sprite = new Sprite(img, DrawLayer.OBSTACLE.getValue(), position, rotation, width, width * heightWidthRatio);
	}

	public Sandbag(Vector2 position, double rotation, boolean brown)
	{
		super(new BoundingBox(position, rotation, 1, 1 * heightWidthRatio));
		BufferedImage img;
		if (brown)
		{
			img = ImageLoader.BrownSandbag.getLoadedImage();
		} else
		{
			img = ImageLoader.BeigeSandbag.getLoadedImage();
		}
		sprite = new Sprite(img, DrawLayer.OBSTACLE.getValue(), position, rotation, 1, 1 * heightWidthRatio);
	}

	@Override
	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = super.getGraphics();
		graphics.add(sprite);
		return graphics;
	}
}
