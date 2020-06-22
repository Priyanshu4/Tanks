package obstacles;

import java.util.ArrayList;

import core.Game;
import mathutil.Vector2;

/**
 * An invisible arena of bullet reflectors.
 */
public class InvisibleArena
{
	ArrayList<BulletReflector> walls = new ArrayList<BulletReflector>();

	public InvisibleArena(Vector2 center, double width, double height, double thickness)
	{
		var leftPosition = new Vector2(center.x - (width + thickness) / 2, center.y);
		var leftWall = new BulletReflector(leftPosition, true, height, thickness);
		var rightPosition = new Vector2(center.x + (width + thickness) / 2, center.y);
		var rightWall = new BulletReflector(rightPosition, true, height, thickness);
		var topPosition = new Vector2(center.x, center.y + (height + thickness) / 2);
		var topWall = new BulletReflector(topPosition, false, width, thickness);
		var bottomPosition = new Vector2(center.x, center.y - (height + thickness) / 2);
		var bottomWall = new BulletReflector(bottomPosition, false, width, thickness);
		leftWall.isArenaWall = true;
		rightWall.isArenaWall = true;
		topWall.isArenaWall = true;
		bottomWall.isArenaWall = true;
		walls.add(leftWall);
		walls.add(rightWall);
		walls.add(topWall);
		walls.add(bottomWall);
	}

	public void addWallsToScene()
	{
		for (int i = 0; i < walls.size(); i++)
		{
			Game.get().getScene().addObstacle(walls.get(i));
		}
	}

}
