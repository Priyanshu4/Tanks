package obstacles;

import core.Game;
import mathutil.BoundingBox;
import mathutil.Vector2;
import tanks.Bullet;

public class BulletReflector extends Obstacle
{
	boolean vertical;

	public BulletReflector(Vector2 position, boolean vertical, double length, double thickness)
	{
		super(new BoundingBox(position, vertical ? Math.PI / 2 : 0, length, thickness));
		this.vertical = vertical;
	}

	@Override
	public void onCollisionWithBullet(Bullet bullet)
	{
		reflectBullet(bullet);
	}

	public void reflectBullet(Bullet bullet)
	{
		Vector2 velocity = bullet.getVelocity();
		if (vertical)
		{
			velocity.x = -velocity.x;
		} else
		{
			velocity.y = -velocity.y;
		}
		bullet.setRotation(velocity.angleRadians());
		int moves = 0;
		int maxMoves = 10;
		while (bounding.intersects(bullet.getBoundingBox()) && moves < maxMoves)
		{
			bullet.move(Game.get().getDeltaTime() * 1);
			moves++;
		}
	}

}
