package graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Graphic
{
	/**
	 * Order in which objects are drawn. Lower value draw layer means it will be
	 * drawn earlier, and may be covered by things with higher value draw layer.
	 */
	protected int drawLayer = 0;

	public Graphic(int drawLayer)
	{
		this.drawLayer = drawLayer;
	}

	public abstract void render(Graphics2D g, Camera camera);

	/**
	 * Uses selection sort to sort a list of graphics by their drawLayer.
	 * 
	 * @param drawables the list of graphics to sort
	 */
	public static void sortByDrawLayer(ArrayList<Graphic> graphics)
	{

		for (int i = 0; i < graphics.size(); i++)
		{
			// find position of smallest num between (i + 1)th element and last element
			int pos = i;
			for (int j = i; j < graphics.size(); j++)
			{
				if (graphics.get(j).drawLayer < graphics.get(pos).drawLayer)
					pos = j;
			}
			// Swap min (smallest num) to current position on array
			Graphic min = graphics.get(pos);
			graphics.set(pos, graphics.get(i));
			graphics.set(i, min);
		}
	}

}
