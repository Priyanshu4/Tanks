package input;

import java.awt.event.KeyEvent;

public class TankControls
{

	public int forward;
	public int reverse;
	public int left;
	public int right;

	public boolean cursorShooting;
	public int barrelCounterClockwise;
	public int barrelClockwise;
	public int fire;

	public static TankControls player1Controls = getDefaultPlayer1Controls();
	public static TankControls player2Controls = getDefaultPlayer2Controls();


	public static TankControls getDefaultPlayer1Controls()
	{
		TankControls controls = new TankControls();
		controls.forward = KeyEvent.VK_W;
		controls.reverse = KeyEvent.VK_S;
		controls.left = KeyEvent.VK_A;
		controls.right = KeyEvent.VK_D;
		controls.cursorShooting = false;
		controls.barrelCounterClockwise = KeyEvent.VK_Q;
		controls.barrelClockwise = KeyEvent.VK_E;
		controls.fire = KeyEvent.VK_SPACE;
		return controls;
	}

	public static TankControls getDefaultPlayer2Controls()
	{
		TankControls controls = new TankControls();
		controls.forward = KeyEvent.VK_UP;
		controls.reverse = KeyEvent.VK_DOWN;
		controls.left = KeyEvent.VK_LEFT;
		controls.right = KeyEvent.VK_RIGHT;
		controls.cursorShooting = false;
		controls.barrelCounterClockwise = KeyEvent.VK_COMMA;
		controls.barrelClockwise = KeyEvent.VK_PERIOD;
		controls.fire = KeyEvent.VK_SLASH;
		return controls;
	}

}
