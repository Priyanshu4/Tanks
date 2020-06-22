package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyInput implements KeyListener
{

	ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	ArrayList<Integer> keysHeld = new ArrayList<Integer>();
	ArrayList<Integer> loopsHeld = new ArrayList<Integer>();

	public void update()
	{
		keysPressed = new ArrayList<Integer>();
		for (int i = 0; i < loopsHeld.size(); i++)
		{
			loopsHeld.set(i, loopsHeld.get(i) + 1);
		}
	}

	@Override
	public void keyPressed(KeyEvent key)
	{
		int keyCode = key.getKeyCode();
		keysPressed.add(keyCode);
		if (!keysHeld.contains(keyCode))
		{
			keysHeld.add(keyCode);
			loopsHeld.add(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent key)
	{
		int keyCode = key.getKeyCode();
		for (int i = 0; i < keysHeld.size(); i++)
		{
			if (keysHeld.get(i) == keyCode)
			{
				keysHeld.remove(i);
				loopsHeld.remove(i);
				i--;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}

	public boolean isKeyPressed(int keyCode)
	{
		int index = keysHeld.indexOf(keyCode);
		if (index == -1)
		{
			return false;
		} else
		{
			return keysPressed.contains(keyCode) && (loopsHeld.get(index) < 1);
		}
	}

	public boolean isKeyHeld(int keyCode)
	{
		return keysHeld.contains(keyCode);
	}

}
