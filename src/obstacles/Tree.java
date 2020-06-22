package obstacles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.DrawLayer;
import graphics.Graphic;
import graphics.ImageLoader;
import graphics.Sprite;
import mathutil.BoundingBox;
import mathutil.Vector2;
import tanks.Bullet;

public class Tree extends Obstacle
{

	Sprite sprite;

	public Tree(Vector2 position, double size)
	{
		super(new BoundingBox(position, 0, size, size));
		BufferedImage img = ImageLoader.Tree.getLoadedImage();
		int drawLayer = DrawLayer.TREE.getValue();
		sprite = new Sprite(img, drawLayer, position, Math.random() * 2 * Math.PI, size, size);
	}

	@Override
	public void onCollisionWithBullet(Bullet bullet)
	{
		bullet.destroy();
	}

	@Override
	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = super.getGraphics();
		graphics.add(sprite);
		return graphics;
	}

}
