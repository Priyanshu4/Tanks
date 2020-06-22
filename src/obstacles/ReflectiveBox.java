package obstacles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import core.Game;
import graphics.DrawLayer;
import graphics.Graphic;
import graphics.ImageLoader;
import graphics.Sprite;
import mathutil.BoundingBox;
import mathutil.Vector2;
import obstacles.BoxSide.Side;
import tanks.Bullet;

public class ReflectiveBox extends Obstacle
{

	private static final double BOX_REFLECTOR_THICKNESS_RATIO = 1d / 6;

	double size;
	Sprite sprite;

	ArrayList<Obstacle> reflectorsAndCorners = new ArrayList<Obstacle>();
	ArrayList<Obstacle> sideReflectors = new ArrayList<Obstacle>();

	public ReflectiveBox(Vector2 position, double size)
	{
		super(new BoundingBox(position, 0, size, size));
		double correctedSize = size * (1 - 2 * BOX_REFLECTOR_THICKNESS_RATIO);
		this.size = size;
		bounding.width = correctedSize;
		bounding.height = correctedSize;
		BufferedImage img = ImageLoader.MetalBox.getLoadedImage();
		int drawLayer = DrawLayer.OBSTACLE.getValue();
		sprite = new Sprite(img, drawLayer, position, size, size);
	}

	@Override
	public void onCreation()
	{
		initSideReflectors();
		for (Obstacle obs : sideReflectors)
		{
			Game.get().getScene().addObstacle(obs);
		}
	}

	private void initSideReflectors()
	{
		double reflectorThickness = size * BOX_REFLECTOR_THICKNESS_RATIO;
		double reflectorDistance = size / 2 - reflectorThickness / 2;
		double reflectorLength = size * 0.95;

		var topPosition = new Vector2(getPosition().x, getPosition().y + reflectorDistance);
		var bottomPosition = new Vector2(getPosition().x, getPosition().y - reflectorDistance);
		var leftPosition = new Vector2(getPosition().x - reflectorDistance, getPosition().y);
		var rightPosition = new Vector2(getPosition().x + reflectorDistance, getPosition().y);

		var topReflector = new BoxSide(topPosition, Side.TOP, reflectorLength, reflectorThickness);
		var bottomReflector = new BoxSide(bottomPosition, Side.BOTTOM, reflectorLength, reflectorThickness);
		var leftReflector = new BoxSide(leftPosition, Side.LEFT, reflectorLength, reflectorThickness);
		var rightReflector = new BoxSide(rightPosition, Side.RIGHT, reflectorLength, reflectorThickness);

		sideReflectors.add(topReflector);
		sideReflectors.add(bottomReflector);
		sideReflectors.add(leftReflector);
		sideReflectors.add(rightReflector);
	}

	@Override
	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = super.getGraphics();
		graphics.add(sprite);
		return graphics;
	}

	@Override
	public void onCollisionWithBullet(Bullet bullet)
	{
		bullet.destroy();
	}

	public void onDestruction()
	{
		for (Obstacle obs : reflectorsAndCorners)
		{
			obs.destroy();
		}
	}

}
