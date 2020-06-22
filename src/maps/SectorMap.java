package maps;

import java.awt.Point;
import java.util.ArrayList;

import graphics.Graphic;
import mathutil.Vector2;

public class SectorMap
{
	Vector2 bottomLeft;
	double sectorWidth;
	double sectorHeight;

	private Sector[][] sectorGrid;
	private ArrayList<Sector> emptySectors = new ArrayList<Sector>();

	private boolean displaySectorOutlines = false;

	public SectorMap(Vector2 center, double width, double height, double sectorWidth, double sectorHeight)
	{
		this.sectorWidth = sectorWidth;
		this.sectorHeight = sectorHeight;
		int sectorsWide = (int) Math.ceil(width / sectorWidth);
		int sectorsTall = (int) Math.ceil(height / sectorHeight);
		sectorGrid = new Sector[sectorsWide][sectorsTall];
		double leftX = center.x - (width - sectorWidth) / 2;
		double bottomY = center.y - (height - sectorHeight) / 2;

		for (int x = 0; x < sectorsWide; x++)
		{
			for (int y = 0; y < sectorsTall; y++)
			{
				Vector2 position = new Vector2(x * sectorWidth + leftX, y * sectorHeight + bottomY);
				sectorGrid[x][y] = new Sector(position, sectorWidth, sectorHeight);
				if (x == sectorsWide - 1 && width / sectorWidth < sectorsWide)
				{
					sectorGrid[x][y].fullyInbounds = false;
				}
				if (y == sectorsTall - 1 && height / sectorHeight < sectorsTall)
				{
					sectorGrid[x][y].fullyInbounds = false;
				}
			}
		}

		this.bottomLeft = new Vector2(center.x - width / 2, center.y - height / 2);

		updateEmptySectors();
	}

	public void updateEmptySectors()
	{
		emptySectors = new ArrayList<Sector>();
		for (int x = 0; x < sectorGrid.length; x++)
		{
			for (int y = 0; y < sectorGrid[x].length; y++)
			{
				var sector = sectorGrid[x][y];
				if (sector.isEmpty())
				{
					emptySectors.add(sector);
				}
			}
		}
	}

	public Sector getRandomEmptySector()
	{
		if (emptySectors.size() == 0)
		{
			return null;
		}
		int index = (int) (Math.random() * emptySectors.size());
		return emptySectors.get(index);
	}

	public void markSectorAsNotEmpty(Sector sector)
	{
		emptySectors.remove(sector);
	}

	public boolean isSectorInBounds(int xIndex, int yIndex)
	{
		boolean xInbounds = xIndex >= 0 && xIndex < sectorGrid.length;
		if (xInbounds)
		{
			boolean yInbounds = yIndex >= 0 && yIndex < sectorGrid[0].length;
			return yInbounds;
		}
		return false;
	}

	public Sector getSector(Point index)
	{
		return sectorGrid[index.x][index.y];
	}

	public Point getSectorIndex(Sector sector)
	{
		return getIndexOfSectorAtPosition(sector.getPosition());
	}

	public Point getIndexOfSectorAtPosition(Vector2 position)
	{
		int x = (int) ((position.x - bottomLeft.x) / sectorWidth);
		int y = (int) ((position.y - bottomLeft.y) / sectorHeight);
		return new Point(x, y);
	}

	public Sector getSectorAtPosition(Vector2 position)
	{
		Point index = getIndexOfSectorAtPosition(position);
		return getSector(index);
	}

	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = new ArrayList<Graphic>();
		if (displaySectorOutlines)
		{
			for (int x = 0; x < sectorGrid.length; x++)
			{
				for (int y = 0; y < sectorGrid[x].length; y++)
				{
					graphics.add(sectorGrid[x][y].getOutlineGraphic());
				}
			}
		}

		return graphics;
	}

	public Sector[][] getSectorGrid()
	{
		return sectorGrid;
	}

}
