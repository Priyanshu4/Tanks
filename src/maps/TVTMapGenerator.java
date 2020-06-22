package maps;

import core.Game;
import graphics.BackgroundTile.TileType;
import graphics.Camera;
import mathutil.Vector2;
import obstacles.ReflectiveBox;
import tanks.Tank;

public class TVTMapGenerator extends MapGenerator
{
	Tank tank1;
	Tank tank2;

	public TVTMapGenerator(Tank tank1, Tank tank2)
	{
		// setup camera
		this.tank1 = tank1;
		this.tank2 = tank2;
		this.width = 20;
		Camera camera;
		camera = new Camera(Vector2.Zero(), width, 0);
		Game.get().setCamera(camera);
		camera.normalizeRatios(true);
		this.height = camera.verticalFOV;
		setupSectorMaps();
	}

	public enum MapStyle
	{
		ScatteredBoxes(0.75), CenterBox(0.25);

		double probability = 0;

		private MapStyle(double probability)
		{
			this.probability = probability;
		}

		public static MapStyle getRandom()
		{
			var styles = values();
			double totalProb = 0;
			for (int i = 0; i < styles.length; i++)
			{
				totalProb += styles[i].probability;
			}
			double random = Math.random() * totalProb;
			double runningTotal = 0;
			for (int i = 0; i < styles.length; i++)
			{
				runningTotal += styles[i].probability;
				if (random <= runningTotal)
				{
					return styles[i];
				}
			}
			return styles[styles.length];
		}
	}

	@Override
	public void generateMap()
	{
		// add tanks
		tank1.setPosition(new Vector2(-8, 0));
		tank1.setRotation(0);
		tank1.assignTeam(1);
		tank2.setPosition(new Vector2(8, 0));
		tank2.setRotation(Math.PI);
		tank2.assignTeam(2);
		Game.get().getScene().addTank(tank1);
		Game.get().getScene().addTank(tank2);

		// make the arena
		addArenaWalls();

		// get style
		MapStyle style = MapStyle.getRandom();

		// update sector maps
		sectorMap.updateEmptySectors();
		metalBoxSectorMap.updateEmptySectors();

		switch (style)
		{
			case ScatteredBoxes:
			{
				addBackgroundTiles(TileType.DIRT);
				addRandomMetalBoxes(randomInt(4, 7));
				sectorMap.updateEmptySectors();
				addRandomTrees(randomInt(3, 7));
				break;
			}
			case CenterBox:
			{
				addBackgroundTiles(TileType.SAND);
				ReflectiveBox metalBox = new ReflectiveBox(new Vector2(0, 0), height / 2.1);
				Game.get().getScene().addObstacle(metalBox);
				sectorMap.updateEmptySectors();
				addRandomTrees(randomInt(5, 10));
				addRandomSandbags(true, randomInt(2, 6));
				break;
			}
		}

	}

}
