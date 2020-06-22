package obstacles;

import mathutil.Vector2;
import tanks.Bullet;

public class BoxSide extends BulletReflector
{

	enum Side
	{
		TOP, RIGHT, BOTTOM, LEFT;
	}

	Side side;

	public BoxSide(Vector2 position, Side side, double length, double thickness)
	{
		super(position, side == Side.LEFT || side == Side.RIGHT, length, thickness);
		this.side = side;
	}

	@Override
	public void onCollisionWithBullet(Bullet bullet)
	{
		Vector2 velocity = bullet.getVelocity();
		if (side == Side.TOP && velocity.y >= 0)
		{
			return;
		} else if (side == Side.BOTTOM && velocity.y <= 0)
		{
			return;
		} else if (side == Side.LEFT && velocity.x <= 0)
		{
			return;
		} else if (side == Side.RIGHT && velocity.x >= 0)
		{
			return;
		} else
		{
			reflectBullet(bullet);
		}
	}

}
