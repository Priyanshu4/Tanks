package tanks;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import core.Game;
import graphics.ImageLoader;
import input.KeyInput;
import input.MouseInput;
import input.TankControls;

public class PlayerTank extends Tank
{
	private TankControls controls;
	double shotsInQueue = 0;

	public PlayerTank(TankStats stats, TankColor color, TankControls controls)
	{
		super(stats, color);
		this.controls = controls;
		enableLivesAndAmmoDisplay();
	}

	@Override
	public void onCreation()
	{
		if (controls.cursorShooting)
		{
			var img = ImageLoader.TargetCursor.getLoadedImage();
			var clickPt = new Point(0, 0);
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, clickPt, "Target Cursor");
			Game.get().getWindow().setCursor(cursor);
		}
	}

	@Override
	public void update(double deltaTime)
	{
		super.update(deltaTime);
		KeyInput keyInput = Game.get().getKeyInput();
		MouseInput mouseInput = Game.get().getMouseInput();
		if (keyInput.isKeyHeld(controls.forward))
		{
			accelerate(deltaTime, true);
		} else if (keyInput.isKeyHeld(controls.reverse))
		{
			accelerate(deltaTime, false);
		}
		if (keyInput.isKeyHeld(controls.left))
		{
			turn(deltaTime, false);
		}
		if (keyInput.isKeyHeld(controls.right))
		{
			turn(deltaTime, true);
		}
		if (controls.cursorShooting)
		{
			turnBarrel(deltaTime, mouseInput.getMouseLocation(), true);
			if (mouseInput.getLeftClicked())
			{
				shotsInQueue++;
			}
		} else
		{
			if (keyInput.isKeyPressed(controls.fire))
			{
				shotsInQueue++;
			}
			if (keyInput.isKeyHeld(controls.barrelCounterClockwise))
			{
				turnBarrel(deltaTime, false);
			}
			if (keyInput.isKeyHeld(controls.barrelClockwise))
			{
				turnBarrel(deltaTime, true);
			}
		}
		if (stats.ammo == 0)
		{
			shotsInQueue = 0;
		} else if (shotsInQueue > 0)
		{
			if (fireBullet())
			{
				shotsInQueue--;
			}
		}
	}

	public TankControls getControls()
	{
		return controls;
	}

}
