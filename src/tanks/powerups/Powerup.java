package tanks.powerups;

import core.GameObject;
import mathutil.BoundingBox;
import mathutil.Vector2;
import tanks.Tank;

public abstract class Powerup extends GameObject
{

	public Powerup(Vector2 position, double width, double height)
	{
		super(new BoundingBox(position, 0, width, height));

	}

	@Override
	public void update(double deltaTime)
	{
	}

	public void onCollisionWithTank(Tank tank)
	{
		applyAffectToTank(tank);
		this.destroy();
	}

	protected abstract void applyAffectToTank(Tank tank);

}
