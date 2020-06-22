package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import core.Game;
import mathutil.Rotator;
import mathutil.Vector2;

public class Camera extends JPanel
{
	private static final long serialVersionUID = -4763003290699437089L;

	/**
	 * How many units to include in the camera's view
	 */
	public double horizontalFOV, verticalFOV;

	public Vector2 position;

	public Camera(Vector2 position, double horizontalFOV, double verticalFOV)
	{
		this.position = position;
		this.horizontalFOV = horizontalFOV;
		this.verticalFOV = verticalFOV;
	}

	public Camera()
	{
		this.position = Vector2.Zero();
		this.horizontalFOV = 10;
		this.verticalFOV = 10;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// render all graphics

		if (Game.get().isFullySetup())
		{
			Graphics2D g2d = (Graphics2D) (g);
			ArrayList<Graphic> graphics = Game.get().getScene().getAllGraphics();
			Graphic.sortByDrawLayer(graphics);
			for (int i = 0; i < graphics.size(); i++)
			{
				graphics.get(i).render(g2d, this);
			}
		}
	}

	public Point toUserSpace(Vector2 physicalCoordinate)
	{
		int xPixels = xDistanceToUserSpace(physicalCoordinate.x - position.x + (horizontalFOV / 2));
		int yPixels = this.getHeight() - yDistanceToUserSpace(physicalCoordinate.y - position.y + (verticalFOV / 2));
		Point pixelCoordinate = new Point(xPixels, yPixels);
		return pixelCoordinate;
	}

	public int xDistanceToUserSpace(double physicalXDistance)
	{
		return (int) (physicalXDistance * getWidth() / horizontalFOV);
	}

	public int yDistanceToUserSpace(double physicalYDistance)
	{
		return (int) (physicalYDistance * getHeight() / verticalFOV);
	}

	public Vector2 toPhysicalSpace(Point userSpace)
	{
		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		int xDistance = (userSpace.x - centerX);
		int yDistance = centerY - userSpace.y;
		double xPos = xDistanceToPhysicalSpace(xDistance) + position.x;
		double yPos = yDistanceToPhysicalSpace(yDistance) + position.y;
		return new Vector2(xPos, yPos);
	}

	public double xDistanceToPhysicalSpace(int pixels)
	{
		return pixels * (horizontalFOV / getWidth());
	}

	public double yDistanceToPhysicalSpace(int pixels)
	{
		return pixels * (verticalFOV / getHeight());
	}

	/**
	 * Adjusts one dimension of the camera (horizontalFOV or horizontalFOV) to make
	 * sure ratios between meters and pixels are consistent for x and y dimensions.
	 * 
	 * @param adjustHeight which dimension to adjust, true for height, false for
	 *                     width
	 */
	public void normalizeRatios(boolean adjustHeight)
	{
		if (getWidth() == 0)
		{
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
			this.setSize(topFrame.getSize());
		}

		if (adjustHeight)
		{
			double meterPixelRatio = horizontalFOV / getWidth();
			verticalFOV = getHeight() * meterPixelRatio;
		} else
		{
			double meterPixelRatio = verticalFOV / getHeight();
			horizontalFOV = getWidth() * meterPixelRatio;
		}
	}

	public void drawImage(Graphics2D g, BufferedImage image, Vector2 centerPosition, double rotationRadians,
			double width, double height)
	{
		Point pixelCoord = toUserSpace(new Vector2(centerPosition.x - width / 2, centerPosition.y + height / 2));
		int pixelWidth = xDistanceToUserSpace(width);
		int pixelHeight = yDistanceToUserSpace(height);

		int leftMost = pixelCoord.x - 5;
		int rightMost = pixelCoord.x + pixelWidth + 5;
		int topMost = pixelCoord.y - 5;
		int bottomMost = pixelCoord.y + pixelHeight + 5;
		if (rightMost >= 0 && leftMost <= getWidth() && bottomMost >= 0 && topMost <= getHeight())
		{
			if (rotationRadians == 0)
			{
				g.drawImage(image, pixelCoord.x, pixelCoord.y, pixelWidth, pixelHeight, null);
			} else
			{

				double sin = Math.abs(Math.sin(rotationRadians));
				double cos = Math.abs(Math.cos(rotationRadians));
				image = Rotator.rotateImage(image, rotationRadians);

				int wPrime = (int) (pixelHeight * sin + pixelWidth * cos);
				int hPrime = (int) (pixelWidth * sin + pixelHeight * cos);
				int widthDiff = wPrime - pixelWidth;
				int heightDiff = hPrime - pixelHeight;
				g.drawImage(image, -widthDiff / 2 + pixelCoord.x, -heightDiff / 2 + pixelCoord.y, wPrime, hPrime, null);
			}
		}

	}

	public void drawText(Graphics g, Vector2 centerPosition, String text, Font font, Color color)
	{
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		int width = metrics.stringWidth(text);
		int height = metrics.getHeight();
		Point centerPixelCoord = toUserSpace(centerPosition);
		g.setColor(color);
		g.drawString(text, centerPixelCoord.x - width / 2, centerPixelCoord.y + height / 2);
	}

}
