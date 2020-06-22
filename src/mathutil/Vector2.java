package mathutil;

/**
 * Represents a 2D Cartesian coordinate vector.
 *
 */
public class Vector2
{
	public double x = 0;
	public double y = 0;

	/**
	 * Constructor for a vector that takes in an x and y coordinate.
	 */
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a vector with 0 for its x and y components.
	 * 
	 * @return a vector with 0 for its x and y components
	 */
	public static Vector2 Zero()
	{
		return new Vector2(0, 0);
	}

	/**
	 * Creates a vector using its magnitude and direction.
	 * 
	 * @param magnitude magnitude of the vector
	 * @param angle     direction of the vector in radians
	 * @return a vector with the specified magnitude and direction
	 */
	public static Vector2 createVector(double magnitude, double angleRadians)
	{
		double x = magnitude * Math.cos(angleRadians);
		double y = magnitude * Math.sin(angleRadians);
		return new Vector2(x, y);
	}

	/**
	 * Finds the angle of the vector in radians.
	 * 
	 * @return the angle of the vector
	 */
	public double angleRadians()
	{
		double rad = Math.atan2(y, x);
		if (rad < 0)
		{
			rad += Math.PI * 2;
		}
		return rad;
	}

	/**
	 * Finds the angle of the vector in degrees.
	 * 
	 * @return the angle of the vector
	 */
	public double angleDegrees()
	{
		return Math.toDegrees(angleRadians());
	}

	/**
	 * Finds the magnitude of the vector.
	 * 
	 * @return the magnitude of the vector
	 */
	public double magnitude()
	{
		return Math.sqrt((x * x) + (y * y));
	}

	/**
	 * Rotates a vector counterclockwise.
	 * 
	 * @param angle radians to rotate counterclockwise
	 */
	public void rotateRadians(double angle)
	{
		double ang = angleRadians() + angle;
		double mag = magnitude();
		x = mag * Math.cos(ang);
		y = mag * Math.sin(ang);
	}

	/**
	 * Rotates a vector counterclockwise.
	 * 
	 * @param angle degrees to rotate counterclockwise
	 */
	public void rotateDegrees(double angle)
	{
		rotateRadians(Math.toRadians(angle));
	}

	/**
	 * Creates a vector with opposite direction to this one.
	 * 
	 * @return a vector with opposite direction to this one
	 */
	public Vector2 oppositeVector()
	{
		return new Vector2(-x, -y);
	}

	/**
	 * Returns the sum of this vector and the parameter vector.
	 * 
	 * 
	 * @param vector the vector to add
	 * @return the sum of this vector and the vector to add
	 */
	public Vector2 add(Vector2 vector)
	{
		return new Vector2(this.x + vector.x, this.y + vector.y);
	}

	/**
	 * Returns this vector minus the parameter vector.
	 * 
	 * 
	 * @param vector the vector to subtract
	 * @return the difference
	 */
	public Vector2 subtract(Vector2 vector)
	{
		return new Vector2(this.x - vector.x, this.y - vector.y);
	}

	/**
	 * Multiplies the vector by a scalar.
	 * 
	 * @param scalar the scalar to multiply by
	 * @return the product vector
	 */
	public Vector2 multiply(double scalar)
	{
		return new Vector2(x * scalar, y * scalar);
	}

	/**
	 * Multiplies 2 vectors using the dot product. The product is a scalar.
	 * 
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return the resulting scalar
	 */
	public static double dotProduct(Vector2 v1, Vector2 v2)
	{
		return (v1.x * v2.x) + (v1.y * v2.y);
	}

	@Override
	public String toString()
	{
		return "Vector " + "(" + x + "," + y + ") " + "magnitude=" + magnitude() + ", angle=" + angleDegrees();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Vector2)
		{
			Vector2 vector = (Vector2) (o);
			return vector.x == x && vector.y == y;
		} else
		{
			return false;
		}
	}

}
