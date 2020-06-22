package graphics;

import mathutil.Vector2;

/**
 * A graphic with a position and rotation.
 *
 */
public abstract class PositionedGraphic extends Graphic
{

	/**
	 * Position of the graphic in physical space (m)
	 */
	protected Vector2 position = Vector2.Zero();

	/**
	 * Rotation in radians.
	 */
	protected double rotation = 0;

	public PositionedGraphic(int drawLayer)
	{
		super(drawLayer);
	}

	public PositionedGraphic(int drawLayer, Vector2 position, double rotation)
	{
		super(drawLayer);
		this.position = position;
		this.rotation = rotation;
	}

	public void updatePosition(Vector2 position, double rotation)
	{
		this.position = position;
		this.rotation = rotation;
	}

}
