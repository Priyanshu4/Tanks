package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mathutil.Vector2;

public class Sprite extends PositionedGraphic
{

	private BufferedImage image;

	/**
	 * Width and height in physical units (m).
	 */
	private double width, height;

	public Sprite(
			BufferedImage image,
			int drawLayer,
			Vector2 position,
			double currentRotation,
			double width,
			double height)
	{
		super(drawLayer, position, currentRotation);
		this.image = image;
		this.width = width;
		this.height = height;
	}

	public Sprite(BufferedImage image, int drawLayer, Vector2 position, double width, double height)
	{
		super(drawLayer, position, 0);
		this.image = image;
		this.width = width;
		this.height = height;
	}

	public Sprite(BufferedImage image, int drawLayer)
	{
		super(drawLayer);
		this.image = image;
		this.width = 0;
		this.height = 0;
	}

	public void setDimensions(double width, double height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Graphics2D g, Camera camera)
	{
		camera.drawImage(g, image, position, rotation, width, height);
	}

	public double getWidth()
	{
		return width;
	}

	public double getHeight()
	{
		return height;
	}

}
