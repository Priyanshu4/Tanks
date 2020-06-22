package mathutil;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Rotator
{

	public static Vector2 rotatePoint(Vector2 point, Vector2 center, double radians)
	{
		Vector2 rotatedPoint = new Vector2(point.x, point.y);
		rotatedPoint = rotatedPoint.subtract(center);
		rotatedPoint.rotateRadians(radians);
		rotatedPoint = rotatedPoint.add(center);
		return rotatedPoint;
	}

	public static BufferedImage rotateImage(BufferedImage image, double radians)
	{
		radians = -radians;
		final double sin = Math.abs(Math.sin(radians));
		final double cos = Math.abs(Math.cos(radians));
		final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
		final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
		final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		final AffineTransform at = new AffineTransform();
		at.translate(w / 2, h / 2);
		at.rotate(radians, 0, 0);
		at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(image, rotatedImage);
		return rotatedImage;
	}

	/**
	 * 
	 * Returns a value equivalent to the input but between 0 and 2 pi.
	 */
	public static double normalizeRadians(double radians)
	{
		while (radians >= 2 * Math.PI)
		{
			radians -= 2 * Math.PI;
		}
		while (radians < 0)
		{
			radians += 2 * Math.PI;
		}
		return radians;
	}

}
