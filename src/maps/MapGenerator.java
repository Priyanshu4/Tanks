package maps;

import core.Game;
import graphics.BackgroundTile;
import graphics.BackgroundTile.TileType;
import mathutil.Vector2;
import obstacles.InvisibleArena;
import obstacles.ReflectiveBox;
import obstacles.Sandbag;
import obstacles.Tree;

public abstract class MapGenerator
{

	protected double width;
	protected double height;

	protected double sectorWidth = 1.36;
	protected double sectorHeight = 1.36;
	protected SectorMap sectorMap;

	private double metalBoxSectorWidth = 2 * sectorWidth;
	private double metalBoxSectorHeight = 2 * sectorHeight;
	protected double stdMetalBoxSize = 2 * 1.1;
	protected SectorMap metalBoxSectorMap;

	public MapGenerator()
	{

	}

	public MapGenerator(double width, double height)
	{
		this.width = width;
		this.height = height;
		setupSectorMaps();
	}

	public void setupSectorMaps()
	{
		sectorMap = new SectorMap(Vector2.Zero(), width, height, sectorWidth, sectorHeight);
		metalBoxSectorMap = new SectorMap(Vector2.Zero(), width, height, metalBoxSectorWidth, metalBoxSectorHeight);
		Game.get().getScene().setSectorMap(sectorMap);
	}

	public abstract void generateMap();

	public void addArenaWalls()
	{
		var arena = new InvisibleArena(Vector2.Zero(), width, height, 3);
		arena.addWallsToScene();
	}

	public void addBackgroundTiles(TileType type)
	{
		Vector2 topLeft = new Vector2(-width / 2, height / 2);
		Vector2 bottomRight = new Vector2(width / 2, -height / 2);
		var tiles = BackgroundTile.generateBackgroundTiles(type, topLeft, bottomRight);
		Game.get().getScene().addBackgroundTiles(tiles);
	}

	public void addRandomTree()
	{
		Sector sector = sectorMap.getRandomEmptySector();
		if (sector != null)
		{
			Tree tree = new Tree(sector.getPosition(), 1);
			Game.get().getScene().addObstacle(tree);
			sectorMap.markSectorAsNotEmpty(sector);
		}
	}

	public void addRandomTrees(int num)
	{
		for (int i = 0; i < num; i++)
		{
			addRandomTree();
		}
	}

	public void addRandomSandbag(boolean color)
	{
		Sector sector = sectorMap.getRandomEmptySector();
		if (sector != null)
		{
			Sandbag sandbag = new Sandbag(sector.getPosition(), Math.random() * Math.PI * 2, color);
			Game.get().getScene().addObstacle(sandbag);
			sectorMap.markSectorAsNotEmpty(sector);
		}
	}

	public void addRandomSandbags(boolean color, int num)
	{
		for (int i = 0; i < num; i++)
		{
			addRandomSandbag(color);
		}
	}

	public void addRandomMetalBox()
	{
		Sector sector = metalBoxSectorMap.getRandomEmptySector();
		if (sector != null)
		{
			ReflectiveBox metalBox = new ReflectiveBox(sector.getPosition(), stdMetalBoxSize);
			Game.get().getScene().addObstacle(metalBox);
			metalBoxSectorMap.markSectorAsNotEmpty(sector);
		}
	}

	public void addRandomMetalBoxes(int num)
	{
		for (int i = 0; i < num; i++)
		{
			addRandomMetalBox();
		}
	}

	public int randomInt(int min, int max)
	{
		return (int) (Math.random() * (max - min) + min);
	}
}
