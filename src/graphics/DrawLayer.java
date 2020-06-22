package graphics;

public enum DrawLayer
{
	//@formatter:off
	BACKGROUND(-10), 
	PATHFINDING(-5),
	OBSTACLE(-3), 
	POWERUP(-1), 
	TANK(0), 
	TREE(2), 
	BARREL(3), 
	BULLET(5), 
	EXPLOSION(7),
	BOXOUTLINE(10),
	DISPLAY(20);
	

	int drawLayer;

	private DrawLayer(int drawLayer)
	{
		this.drawLayer = drawLayer;
	}

	public int getValue()
	{
		return drawLayer;
	}
}
