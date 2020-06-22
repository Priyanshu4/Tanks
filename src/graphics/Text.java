package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import mathutil.Vector2;

public class Text extends Graphic
{

	String text;
	String font;
	int style;
	Color color;
	double height;
	Vector2 position;

	public Text(int drawLayer, String text, String font, Color color, double height, Vector2 position)
	{
		super(drawLayer);
		this.text = text;
		this.font = font;
		this.style = Font.PLAIN;
		this.color = color;
		this.height = height;
		this.position = position;
	}

	public Text(int drawLayer, String text, String font, int style, Color color, double height, Vector2 position)
	{
		super(drawLayer);
		this.text = text;
		this.font = font;
		this.style = style;
		this.color = color;
		this.height = height;
		this.position = position;
	}

	@Override
	public void render(Graphics2D g, Camera camera)
	{
		// find the right size of the text to get the right height
		int size = 0;
		int heightPixels = camera.yDistanceToUserSpace(height);
		do
		{
			size++;
			g.setFont(new Font(font, style, size));
		} while (g.getFontMetrics().getHeight() < heightPixels);

		Font textFont = new Font(font, style, size);
		camera.drawText(g, position, text, textFont, color);
	}

	public String getTextString()
	{
		return text;
	}

}
