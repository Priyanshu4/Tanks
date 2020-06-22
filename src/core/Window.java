package core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import graphics.Camera;

public class Window extends JFrame
{

	private static final long serialVersionUID = 1L;

	public Window(String title)
	{
		super(title);
		this.setResizable(false);
	}

	public Window(String title, boolean resizable, boolean exitOnClose)
	{
		super(title);
		this.setResizable(resizable);
		this.setExitOnClose(exitOnClose);
	}

	public void setup()
	{
		maximize();
		setVisible(true);
		toFront();
		requestFocus();
	}

	public void update()
	{
		this.repaint();
	}

	public void maximize()
	{
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}

	/**
	 * Removes all other panels and adds a camera to the window.
	 * 
	 * @param camera
	 */
	public void addFullWindowCamera(Camera camera)
	{
		this.getContentPane().removeAll();
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.add(camera, BorderLayout.CENTER);
		var insets = this.getInsets();
		int cameraWidth = getSize().width - insets.left - insets.right;
		int cameraHeight = getSize().height - insets.top - insets.bottom;
		camera.setSize(new Dimension(cameraWidth, cameraHeight));
		camera.requestFocusInWindow();
	}

	public void setExitOnClose(boolean exitOnClose)
	{
		if (exitOnClose)
		{
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else
		{
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}

}
