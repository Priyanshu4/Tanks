package graphics;

import java.util.ArrayList;

import core.GameObject;
import mathutil.BoundingBox;
import mathutil.Vector2;

public class Explosion extends GameObject
{
	double duration;
	Sprite sprite;

	public Explosion(Vector2 position, double size, double duration)
	{
		super(new BoundingBox(position, Math.random() * 2 * Math.PI, size, size));
		this.duration = duration;
		chooseRandomExplosionSprite();
	}

	public Explosion(Vector2 position, double size)
	{
		super(new BoundingBox(position, Math.random() * 2 * Math.PI, size, size));
		this.duration = 0.05;
		chooseRandomExplosionSprite();
	}

	@Override
	public void update(double deltaTime)
	{
		duration -= deltaTime;
		if (duration <= 0)
		{
			this.destroy();
		}
	}

	public void chooseRandomExplosionSprite()
	{
		int random = (int) (Math.random() * 3) + 1;
		switch (random)
		{
			case 1:
				sprite = new Sprite(ImageLoader.Explosion1.getLoadedImage(), DrawLayer.EXPLOSION.getValue());
				break;
			case 2:
				sprite = new Sprite(ImageLoader.Explosion2.getLoadedImage(), DrawLayer.EXPLOSION.getValue());
				break;
			case 3:
				sprite = new Sprite(ImageLoader.Explosion3.getLoadedImage(), DrawLayer.EXPLOSION.getValue());
				break;
		}
		sprite.updatePosition(getPosition(), getRotation());
		sprite.setDimensions(bounding.width, bounding.height);
	}

	@Override
	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = super.getGraphics();
		graphics.add(sprite);
		return graphics;
	}

}
