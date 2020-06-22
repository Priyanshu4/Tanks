package tanks.powerups;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.DrawLayer;
import graphics.Graphic;
import graphics.ImageLoader;
import graphics.Sprite;
import mathutil.Vector2;
import tanks.Tank;

public class ExtraAmmo extends Powerup
{
	Sprite sprite;
	int ammo = 3;
	double growTime = 2;
	double timeToFullSize = growTime;

	private static final double heightWidthRatio = 34 / (20 * 3d);

	public ExtraAmmo(Vector2 position, double size)
	{
		super(position, size, size * heightWidthRatio);
		BufferedImage image = ImageLoader.ExtraAmmo.getLoadedImage();
		sprite = new Sprite(image, DrawLayer.POWERUP.getValue(), position, 0, 0);
	}

	public ExtraAmmo(Vector2 position)
	{
		super(position, 0.6, 0.6 * heightWidthRatio);
		BufferedImage image = ImageLoader.ExtraAmmo.getLoadedImage();
		sprite = new Sprite(image, DrawLayer.POWERUP.getValue(), position, 0, 0);
	}

	@Override
	public void update(double deltaTime)
	{
		timeToFullSize -= deltaTime;
		double spriteWidth = bounding.width;
		double spriteHeight = bounding.height;
		if (timeToFullSize >= 0)
		{
			spriteWidth = bounding.width / growTime * (growTime - timeToFullSize);
			spriteHeight = bounding.height / growTime * (growTime - timeToFullSize);
		}
		sprite.setDimensions(spriteWidth, spriteHeight);

	}

	@Override
	public void applyAffectToTank(Tank tank)
	{
		tank.addAmmo(ammo);
	}

	@Override
	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = super.getGraphics();
		graphics.add(sprite);
		return graphics;
	}

}
