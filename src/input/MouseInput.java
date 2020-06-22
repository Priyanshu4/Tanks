package input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import core.Game;
import graphics.Camera;
import mathutil.Vector2;

public class MouseInput implements MouseListener
{

	boolean leftClicked = false;

	public Vector2 getMouseLocation()
	{
		Point userSpacePixel = MouseInfo.getPointerInfo().getLocation();
		Camera camera = Game.get().getCamera();
		return camera.toPhysicalSpace(userSpacePixel);
	}

	public void update()
	{
		leftClicked = false;
	}

	public boolean getLeftClicked()
	{
		return leftClicked;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			leftClicked = true;
		}
	}

	// @formatter:off
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
