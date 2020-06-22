package mathutil;

import java.awt.Color;

import graphics.BoundingBoxOutline;

public class BoundingBox
{
	public Vector2 position = Vector2.Zero();
	public double rotation = 0;
	public double width;
	public double height;
	private BoundingBoxOutline outlineGraphic;

	public BoundingBox(Vector2 position, double rotation, double width, double height)
	{
		this.position = position;
		this.rotation = rotation;
		this.width = width;
		this.height = height;
		outlineGraphic = new BoundingBoxOutline(this, Color.RED);
	}

	public BoundingBox(double width, double height)
	{
		this.width = width;
		this.height = height;
		outlineGraphic = new BoundingBoxOutline(this, Color.RED);
	}

	public Vector2[] getCorners()
	{
		Vector2[] unrotatedCorners = new Vector2[4];
		unrotatedCorners[0] = new Vector2(position.x - width / 2, position.y + height / 2);
		unrotatedCorners[1] = new Vector2(position.x + width / 2, position.y + height / 2);
		unrotatedCorners[2] = new Vector2(position.x + width / 2, position.y - height / 2);
		unrotatedCorners[3] = new Vector2(position.x - width / 2, position.y - height / 2);

		Vector2[] corners = new Vector2[4];
		for (int i = 0; i < unrotatedCorners.length; i++)
		{
			corners[i] = Rotator.rotatePoint(unrotatedCorners[i], position, rotation);
		}

		return corners;
	}

	public LineSegment[] getLineSegments()
	{
		var corners = getCorners();
		LineSegment segments[] = new LineSegment[4];
		segments[0] = new LineSegment(corners[3], corners[0]);
		segments[1] = new LineSegment(corners[0], corners[1]);
		segments[2] = new LineSegment(corners[1], corners[2]);
		segments[3] = new LineSegment(corners[2], corners[3]);
		return segments;
	}

	public boolean intersects(BoundingBox bounding)
	{
		// first check if the bounding boxes are even close enough for intersection to
		// be possible
		double maxPossibleIntersectingDistance = (bounding.getDiagnol() + this.getDiagnol()) / 2;
		double distance = bounding.position.subtract(this.position).magnitude();
		if (distance > maxPossibleIntersectingDistance)
		{
			return false;
		}

		// next check if any corner of either shape is inside the other shape
		var corners = getCorners();
		for (int i = 0; i < corners.length; i++)
		{
			if (bounding.contains(corners[i]))
			{
				return true;
			}
		}

		var otherCorners = bounding.getCorners();
		for (int i = 0; i < otherCorners.length; i++)
		{
			if (contains(otherCorners[i]))
			{
				return true;
			}
		}

		// check if center of either shape is inside the other shape
		if (contains(bounding.position))
		{
			return true;
		}

		if (bounding.contains(position))
		{
			return true;
		}

		return false;
	}

	public boolean intersects(LineSegment segment)
	{
		var segments = getLineSegments();
		for (int i = 0; i < segments.length; i++)
		{
			if (segment.intersectsLineSegment(segments[i]))
			{
				return true;
			}
		}
		return false;
	}

	public boolean contains(Vector2 point)
	{
		Vector2 rotatedPoint = Rotator.rotatePoint(point, position, -rotation);
		double left = position.x - width / 2;
		double right = position.x + width / 2;
		double top = position.y + height / 2;
		double bottom = position.y - height / 2;

		if (rotatedPoint.x >= left && rotatedPoint.x <= right)
		{
			if (rotatedPoint.y >= bottom && rotatedPoint.y <= top)
			{
				return true;
			}
		}

		return false;
	}

	public double getLargerDimension()
	{
		return Math.max(width, height);
	}

	public double getDiagnol()
	{
		return Math.sqrt(height * height + width * width);
	}

	public BoundingBox clone()
	{
		return new BoundingBox(new Vector2(position.x, position.y), rotation, width, height);
	}

	public BoundingBoxOutline getOutlineGraphic()
	{
		return outlineGraphic;
	}

}
