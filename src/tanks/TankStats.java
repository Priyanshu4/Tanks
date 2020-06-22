package tanks;

public class TankStats
{

	public double collisionBounce = -0.7;
	public double tankBarrelWidthRatio = 1.4;
	public double tankBarrelHeightRatio = 4;
	public double tankBulletWidthRatio = 2.5;

	public double width = 1;
	public double height = 1;
	public int lives = 5;
	public int ammo = 5;
	public double topSpeed = 3;
	public double acceleration = 3 / 1.5;
	public double turnSpeed = Math.PI / 3;
	public double barrelTurnSpeed = Math.PI;
	public double minFireTime = 0.2;
	public double bulletSpeed = 5;

	public Bullet getBullet()
	{
		return new Bullet(width / tankBulletWidthRatio, bulletSpeed);
	}

	public static TankStats getDefaultTankStats()
	{
		TankStats stats = new TankStats();
		stats.width = 1;
		stats.height = 1;
		stats.lives = 5;
		stats.ammo = 5;
		stats.topSpeed = 3;
		stats.acceleration = stats.topSpeed / 1.5;
		stats.turnSpeed = Math.PI / 3;
		stats.barrelTurnSpeed = Math.PI;
		stats.minFireTime = 0.2;
		stats.bulletSpeed = 5;
		return stats;
	}

}
