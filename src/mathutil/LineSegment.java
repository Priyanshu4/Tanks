package mathutil;

public class LineSegment
{
	public Vector2 point1;
	public Vector2 point2;

	/**
	 * An arbitrary number that is returned when undefined slope causes an error.
	 */
	public static final int UNDEFINED_SLOPE_ERROR = Integer.MAX_VALUE - 10;

	public LineSegment(Vector2 point1, Vector2 point2)
	{
		this.point1 = point1;
		this.point2 = point2;
	}

	public double getSlope()
	{
		double run = point1.x - point2.x;
		if (run == 0)
		{
			return UNDEFINED_SLOPE_ERROR;
		} else
		{
			double rise = point1.y - point2.y;
			return rise / run;
		}
	}

	/**
	 * Finds the y-value of the line at a certain x-value. This may find a value
	 * that is not actually within the range of the line segment. Returns
	 * {@link LineSegment#UNDEFINED_SLOPE_ERROR} if the slope of this line segment
	 * is undefined.
	 * 
	 * @param x the x value
	 * @returns the y value
	 */
	public double valueAt(double x)
	{
		double slope = getSlope();
		if (slope == UNDEFINED_SLOPE_ERROR)
		{
			return UNDEFINED_SLOPE_ERROR;
		} else
		{
			return (slope * (x - point1.x)) + point1.y;
		}
	}

	public boolean intersectsLineSegment(LineSegment segment2)
	{
		Vector2 intersection = solveSystem(this, segment2);
		if (intersection == null) // if intersection is null, lines must be colinear or parallel
		{
			if (valueAt(0) != segment2.valueAt(0) && getSlope() != UNDEFINED_SLOPE_ERROR)
			{
				// lines must be parallel, cannot intersect
				return false;
			} else
			{
				return onSegment(segment2.point1) || onSegment(segment2.point2);
			}
		}
		return onSegment(intersection) && segment2.onSegment(intersection);
	}

	/**
	 * Given a point that falls somewhere on the equation of this segment's infinite
	 * line, this checks if it actually falls on the segment
	 */
	public boolean onSegment(Vector2 point)
	{
		boolean xBetween = (point.x <= Math.max(point1.x, point2.x) && point.x >= Math.min(point1.x, point2.x));
		boolean yBetween = (point.y <= Math.max(point1.y, point2.y) && point.y >= Math.min(point1.y, point2.y));
		return xBetween && yBetween;
	}

	/**
	 * Given 2 lines, this solves for the point of intersection of the lines.
	 * 
	 * <p>
	 * This does NOT check that the intersection falls on the domain and range of
	 * the line segments, and instead considers the segments as infinite lines. This
	 * method should function correctly even if one of the lines has undefined
	 * slope. In the situation that the lines are colinear or parallel then a null
	 * point is returned, as there would either be infinite or 0 points of
	 * intersection.
	 *
	 * @param line1 the first line
	 * @param line2 the second line
	 * @return the Point of intersection as a {@link Point.Double}
	 */
	public static Vector2 solveSystem(LineSegment line1, LineSegment line2)
	{
		double slope1 = line1.getSlope();
		double yint1 = line1.valueAt(0); // y-intercept

		double slope2 = line2.getSlope();
		double yint2 = line2.valueAt(0); // y-intercept

		if (slope1 == slope2)
		{
			return null;
		} else if (slope1 == UNDEFINED_SLOPE_ERROR)
		{
			return new Vector2(line1.point1.x, line2.valueAt(line1.point1.x));
		} else if (slope2 == UNDEFINED_SLOPE_ERROR)
		{
			return new Vector2(line2.point1.x, line1.valueAt(line2.point1.x));
		} else
		{
			double xValue = (yint2 - yint1) / (slope1 - slope2);
			double yValue = line1.valueAt(xValue);

			return new Vector2(xValue, yValue);
		}
	}

}
