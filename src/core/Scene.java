package core;

import java.util.ArrayList;

import graphics.BackgroundTile;
import graphics.Graphic;
import maps.SectorMap;
import obstacles.Obstacle;
import tanks.Bullet;
import tanks.Tank;
import tanks.powerups.Powerup;

public class Scene
{
	private ArrayList<Tank> tanks = new ArrayList<Tank>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	private ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	private ArrayList<GameObject> otherObjects = new ArrayList<GameObject>();
	private ArrayList<BackgroundTile> bgTiles = new ArrayList<BackgroundTile>();
	private ArrayList<Graphic> otherGraphics = new ArrayList<Graphic>();
	private SectorMap sectorMap;

	public void update(double deltaTime)
	{
		for (int i = 0; i < tanks.size(); i++)
		{
			Tank tank = tanks.get(i);
			if (tank.isDestroyed())
			{
				tank.onDestruction();
				tanks.remove(i);
				i--;
			} else
			{
				tank.update(deltaTime);
			}
		}

		for (int i = 0; i < bullets.size(); i++)
		{
			GameObject bullet = bullets.get(i);
			if (bullet.isDestroyed())
			{
				bullet.onDestruction();
				bullets.remove(i);
				i--;
			} else
			{
				bullet.update(deltaTime);
			}
		}

		for (int i = 0; i < obstacles.size(); i++)
		{
			GameObject obstacle = obstacles.get(i);
			if (obstacle.isDestroyed())
			{
				obstacle.onDestruction();
				obstacles.remove(i);
				i--;
			} else
			{
				obstacle.update(deltaTime);
			}
		}

		for (int i = 0; i < powerups.size(); i++)
		{
			GameObject powerup = powerups.get(i);
			if (powerup.isDestroyed())
			{
				powerup.onDestruction();
				powerups.remove(i);
				i--;
			} else
			{
				powerup.update(deltaTime);
			}
		}

		for (int i = 0; i < otherObjects.size(); i++)
		{
			GameObject object = otherObjects.get(i);
			if (object.isDestroyed())
			{
				object.onDestruction();
				otherObjects.remove(i);
				i--;
			} else
			{
				object.update(deltaTime);
			}
		}

		// tank to tank collisions
		for (int i = 0; i < tanks.size(); i++)
		{
			for (int j = i + 1; j < tanks.size(); j++)
			{
				Tank tank1 = tanks.get(i);
				Tank tank2 = tanks.get(j);
				if (tank1.isCollidingWith(tank2))
				{
					tank1.onCollisionWithTank(tank2);
					tank2.onCollisionWithTank(tank1);
				}
			}
		}

		// bullet to bullet collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			for (int j = i + 1; j < bullets.size(); j++)
			{
				Bullet bullet1 = bullets.get(i);
				Bullet bullet2 = bullets.get(j);
				if (bullet1.isCollidingWith(bullet2))
				{
					bullet1.onCollisionWithBullet(bullet2);
					bullet2.onCollisionWithBullet(bullet1);
				}
			}
		}

		// tank to bullet collisions
		for (int i = 0; i < tanks.size(); i++)
		{
			for (int j = 0; j < bullets.size(); j++)
			{
				Bullet bullet = bullets.get(j);
				Tank tank = tanks.get(i);
				if (bullet.isCollidingWith(tank))
				{
					bullet.onCollisionWithTank(tanks.get(i));
				}
			}
		}

		// tank to obstacle collisions
		for (int i = 0; i < tanks.size(); i++)
		{
			for (int j = 0; j < obstacles.size(); j++)
			{
				Obstacle obstacle = obstacles.get(j);
				Tank tank = tanks.get(i);
				if (obstacle.isCollidingWith(tank))
				{
					obstacle.onCollisionWithTank(tanks.get(i));
				}
			}
		}

		// bullet to obstacle collisions
		for (int i = 0; i < bullets.size(); i++)
		{
			for (int j = 0; j < obstacles.size(); j++)
			{
				Obstacle obstacle = obstacles.get(j);
				Bullet bullet = bullets.get(i);
				if (obstacle.isCollidingWith(bullet))
				{
					obstacle.onCollisionWithBullet(bullet);
				}
			}
		}

		// tank to powerup collisions
		for (int i = 0; i < tanks.size(); i++)
		{
			for (int j = 0; j < powerups.size(); j++)
			{
				Powerup powerup = powerups.get(j);
				Tank tank = tanks.get(i);
				if (powerup.isCollidingWith(tank))
				{
					powerup.onCollisionWithTank(tanks.get(i));
				}
			}
		}

	}

	public ArrayList<GameObject> getAllGameObjects()
	{
		ArrayList<GameObject> allObjects = new ArrayList<GameObject>();
		allObjects.addAll(tanks);
		allObjects.addAll(bullets);
		allObjects.addAll(obstacles);
		allObjects.addAll(powerups);
		allObjects.addAll(otherObjects);
		return allObjects;
	}

	public ArrayList<Tank> getTanks()
	{
		return tanks;
	}

	public ArrayList<Bullet> getBullets()
	{
		return bullets;
	}

	public ArrayList<Obstacle> getObstacles()
	{
		return obstacles;
	}

	public ArrayList<Powerup> getPowerups()
	{
		return powerups;
	}

	public ArrayList<Graphic> getAllGraphics()
	{
		ArrayList<GameObject> allObjects = getAllGameObjects();
		ArrayList<Graphic> graphics = new ArrayList<Graphic>();
		for (int i = 0; i < allObjects.size(); i++)
		{
			ArrayList<Graphic> toAdd = allObjects.get(i).getGraphics();
			graphics.addAll(toAdd);
		}
		graphics.addAll(bgTiles);
		graphics.addAll(otherGraphics);
		if (sectorMap != null)
		{
			graphics.addAll(sectorMap.getGraphics());
		}
		return graphics;
	}

	public void addTank(Tank tank)
	{
		tanks.add(tank);
		tank.onCreation();
	}

	public void addBullet(Bullet bullet)
	{
		bullets.add(bullet);
		bullet.onCreation();
	}

	public void addObstacle(Obstacle obstacle)
	{
		obstacles.add(obstacle);
		obstacle.onCreation();
	}

	public void addPowerup(Powerup powerup)
	{
		powerups.add(powerup);
		powerup.onCreation();
	}

	public void addOtherObject(GameObject object)
	{
		otherObjects.add(object);
		object.onCreation();
	}

	public void addBackgroundTiles(ArrayList<BackgroundTile> bgTiles)
	{
		this.bgTiles.addAll(bgTiles);
	}

	public void addGraphic(Graphic graphic)
	{
		otherGraphics.add(graphic);
	}

	public SectorMap getSectorMap()
	{
		return sectorMap;
	}

	public void setSectorMap(SectorMap sectorMap)
	{
		this.sectorMap = sectorMap;
	}

}
