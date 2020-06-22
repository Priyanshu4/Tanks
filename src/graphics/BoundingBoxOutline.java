package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import mathutil.BoundingBox;

public class BoundingBoxOutline extends Graphic
{

	private BoundingBox bounding;
	private Color color;

	public BoundingBoxOutline(BoundingBox bounding, Color color)
	{
		super(DrawLayer.BOXOUTLINE.getValue());
		this.bounding = bounding;
		this.color = color;
	}

	@Override
	public void render(Graphics2D g, Camera camera)
	{
		var corners = bounding.getCorners();
		var nPoints = corners.length;
		var xPoints = new int[nPoints];
		var yPoints = new int[nPoints];

		for (int i = 0; i < nPoints; i++)
		{
			Point point = camera.toUserSpace(corners[i]);
			xPoints[i] = point.x;
			yPoints[i] = point.y;
		}

		g.setColor(color);
		g.setStroke(new BasicStroke(2));
		g.drawPolygon(xPoints, yPoints, nPoints);
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

}
