package tanks;

import java.util.ArrayList;

import core.Game;
import core.GameObject;
import graphics.DrawLayer;
import graphics.Explosion;
import graphics.Graphic;
import graphics.ImageLoader;
import graphics.Sprite;
import mathutil.BoundingBox;
import mathutil.Vector2;
import tanks.Tank.TankColor;

public class Bullet extends GameObject
{

	private double speed;
	private Sprite bulletSprite;
	private static final double heightWidthRatio = 20d / 34;

	public Bullet(double width, double speed)
	{
		super(new BoundingBox(Vector2.Zero(), 0, width, width * heightWidthRatio));
		this.speed = speed;
	}

	@Override
	public void update(double deltaTime)
	{
		move(deltaTime);
	}

	public void move(double deltaTime)
	{
		Vector2 velocity = getVelocity();
		bounding.position = bounding.position.add(velocity.multiply(deltaTime));
	}

	public void onCollisionWithBullet(Bullet bullet)
	{
		this.destroy();
	}

	public void onCollisionWithTank(Tank tank)
	{
		tank.loseLife();
		this.destroy();
	}

	@Override
	public void onCreation()
	{
	}

	@Override
	public void onDestruction()
	{
		Explosion explosion = new Explosion(getPosition(), bounding.getDiagnol() * 3);
		Game.get().getScene().addOtherObject(explosion);
	}

	@Override
	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = super.getGraphics();
		bulletSprite.updatePosition(getPosition(), getRotation());
		graphics.add(bulletSprite);
		return graphics;
	}

	public void setColor(TankColor color)
	{
		switch (color)
		{
			case BLUE:
				bulletSprite = new Sprite(ImageLoader.BlueBullet.getLoadedImage(), DrawLayer.BULLET.getValue());
				break;
			case GREEN:
				bulletSprite = new Sprite(ImageLoader.GreenBullet.getLoadedImage(), DrawLayer.BULLET.getValue());
				break;
			case ORANGE:
				bulletSprite = new Sprite(ImageLoader.OrangeBullet.getLoadedImage(), DrawLayer.BULLET.getValue());
				break;
			case GREY:
				bulletSprite = new Sprite(ImageLoader.GreyBullet.getLoadedImage(), DrawLayer.BULLET.getValue());
				break;
			case BLACK:
				bulletSprite = new Sprite(ImageLoader.SilverBullet.getLoadedImage(), DrawLayer.BULLET.getValue());
				break;
		}
		bulletSprite.setDimensions(bounding.width, bounding.height);
	}

	public Vector2 getVelocity()
	{
		return Vector2.createVector(speed, getRotation());
	}

}
