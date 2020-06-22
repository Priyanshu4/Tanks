package obstacles;

import core.GameObject;
import mathutil.BoundingBox;
import tanks.Bullet;
import tanks.Tank;

public class Obstacle extends GameObject
{

	// only true for obstacles that create the outer walls of the arena
	public boolean isArenaWall = false;

	public Obstacle(BoundingBox bounding)
	{
		super(bounding);
	}

	@Override
	public void update(double deltaTime)
	{
		// TODO Auto-generated method stub
	}

	public void onCollisionWithTank(Tank tank)
	{
		tank.setCurrentVelocity(tank.getStats().collisionBounce * tank.getCurrentVelocity());
		tank.undoLastMove();
	}

	public void onCollisionWithBullet(Bullet bullet)
	{
		bullet.destroy();
	}

}
