package core;

import java.util.ArrayList;

import graphics.Graphic;
import mathutil.BoundingBox;
import mathutil.Vector2;

public abstract class GameObject
{
	protected BoundingBox bounding;
	private boolean displayBoundingBox = false;

	private boolean destroyed = false;

	public GameObject(BoundingBox bounding)
	{
		this.bounding = bounding;
	}

	public abstract void update(double deltaTime);

	/**
	 * Called when this is added to the scene.
	 */
	public void onCreation()
	{

	}

	/**
	 * Called before this is removed from the scene.
	 */
	public void onDestruction()
	{
	}

	public void destroy()
	{
		destroyed = true;
	}

	public boolean isDestroyed()
	{
		return destroyed;
	}

	public BoundingBox getBoundingBox()
	{
		return bounding;
	}

	public Vector2 getPosition()
	{
		return bounding.position;
	}

	public double getRotation()
	{
		return bounding.rotation;
	}

	public void setPosition(Vector2 position)
	{
		bounding.position = position;
	}

	public void setRotation(double rotation)
	{
		bounding.rotation = rotation;
	}

	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = new ArrayList<Graphic>();
		if (displayBoundingBox)
		{
			graphics.add(bounding.getOutlineGraphic());
		}
		return graphics;
	}

	public boolean isCollidingWith(GameObject object)
	{
		return bounding.intersects(object.bounding);
	}

}
