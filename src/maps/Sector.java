package maps;

import java.awt.Color;

import core.Game;
import graphics.Graphic;
import mathutil.BoundingBox;
import mathutil.Vector2;
import obstacles.Obstacle;
import tanks.Tank;
import tanks.powerups.Powerup;

public class Sector
{

	BoundingBox bounding;
	boolean fullyInbounds = true;

	public Sector(Vector2 position, double width, double height)
	{
		bounding = new BoundingBox(position, 0, width, height);
		bounding.getOutlineGraphic().setColor(Color.BLUE);
	}

	public boolean containsTank()
	{
		var tanks = Game.get().getScene().getTanks();
		for (Tank tank : tanks)
		{
			if (tank.getBoundingBox().intersects(bounding))
			{
				return true;
			}
		}
		return false;
	}

	public boolean containsObstacle()
	{
		var obstacles = Game.get().getScene().getObstacles();
		for (Obstacle obstacle : obstacles)
		{
			if (obstacle.getBoundingBox().intersects(bounding))
			{
				return true;
			}
		}
		return false;
	}

	public boolean containsNonArenaWallObstacle()
	{
		var obstacles = Game.get().getScene().getObstacles();
		for (Obstacle obstacle : obstacles)
		{
			if (!obstacle.isArenaWall && obstacle.getBoundingBox().intersects(bounding))
			{
				return true;
			}
		}
		return false;
	}

	public boolean containsPowerup()
	{
		var powerups = Game.get().getScene().getPowerups();
		for (Powerup powerup : powerups)
		{
			if (powerup.getBoundingBox().intersects(bounding))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty()
	{
		return !containsObstacle() && !containsTank() && !containsPowerup();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Sector)
		{
			Sector sector = (Sector) (o);
			return getPosition().equals(sector.getPosition());
		} else
		{
			return false;
		}
	}

	public BoundingBox getBoundingBox()
	{
		return bounding;
	}

	public Graphic getOutlineGraphic()
	{
		return bounding.getOutlineGraphic();
	}

	public Vector2 getPosition()
	{
		return bounding.position;
	}

	public boolean isFullyInbounds()
	{
		return fullyInbounds;
	}

}
