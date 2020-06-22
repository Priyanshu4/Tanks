package tanks;

import java.awt.Color;
import java.util.ArrayList;

import core.Game;
import core.GameObject;
import graphics.DrawLayer;
import graphics.Explosion;
import graphics.Graphic;
import graphics.ImageLoader;
import graphics.Sprite;
import graphics.Text;
import maps.Sector;
import mathutil.BoundingBox;
import mathutil.Rotator;
import mathutil.Vector2;

public class Tank extends GameObject
{

	TankStats stats;
	private double barrelRotation = 0;

	private double currentVelocity;
	private boolean accelerating = false;

	private BoundingBox oldBounding;

	private Sprite tankSprite;
	private Sprite barrelSprite;
	private TankColor color;
	private boolean displayLivesAndAmmo = false;

	private double timeSinceLastLostLife = 100;
	private double timeSinceLastFiredBullet = 100;

	private int team = 0;

	// for different sprites
	public enum TankColor
	{
		BLUE, GREEN, ORANGE, GREY, BLACK;

		public Color getCorrespondingColor()
		{
			// TODO get correct colors
			switch (this)
			{
				case BLUE:
					return new Color(18, 131, 180);
				case GREEN:
					return new Color(81, 145, 81);
				case ORANGE:
					return Color.ORANGE;
				case GREY:
					return Color.GRAY;
				case BLACK:
					return Color.BLACK;
			}
			return Color.WHITE;
		}
	}

	public Tank(TankStats stats, TankColor color)
	{
		super(new BoundingBox(stats.width, stats.height));
		this.stats = stats;
		this.setColor(color);
	}

	@Override
	public void update(double deltaTime)
	{
		oldBounding = bounding.clone();
		timeSinceLastLostLife += deltaTime;
		timeSinceLastFiredBullet += deltaTime;
		Vector2 velocity = Vector2.createVector(currentVelocity, getRotation());
		bounding.position = bounding.position.add(velocity.multiply(deltaTime));
		if (!accelerating)
		{
			deaccelerate(deltaTime);
		} else
		{
			accelerating = false;
		}
	}

	/**
	 * Accelerates the tank linearly
	 * 
	 * @param direction true for forward, false for negative
	 */
	public void accelerate(double deltaTime, boolean direction)
	{
		if (direction)
		{
			currentVelocity += stats.acceleration * deltaTime;
		} else
		{
			currentVelocity -= stats.acceleration * deltaTime;
		}

		if (currentVelocity > stats.topSpeed)
		{
			currentVelocity = stats.topSpeed;
		} else if (currentVelocity < -stats.topSpeed)
		{
			currentVelocity = -stats.topSpeed;
		}
		accelerating = true;

	}

	public void deaccelerate(double deltaTime)
	{
		double originalVelocity = currentVelocity;
		if (currentVelocity > 0)
		{
			currentVelocity -= stats.acceleration * deltaTime;
		} else if (currentVelocity < 0)
		{
			currentVelocity += stats.acceleration * deltaTime;
		}

		// if sign of velocity switched set it to 0
		if (originalVelocity * currentVelocity <= 0)
		{
			currentVelocity = 0;
		}

	}

	/**
	 * Turns the tank
	 * 
	 * @param direction false for left, true for right
	 */
	public void turn(double deltaTime, boolean direction)
	{
		if (direction)
		{
			bounding.rotation -= stats.turnSpeed * deltaTime;
		} else
		{
			bounding.rotation += stats.turnSpeed * deltaTime;
		}
		bounding.rotation = Rotator.normalizeRadians(bounding.rotation);
	}

	/**
	 * Turns the tank
	 * 
	 * @param targetRotation the angle to turn the tank towards
	 */
	public void turn(double deltaTime, double targetRotation)
	{
		double radToRotate = targetRotation - getRotation();
		radToRotate = Rotator.normalizeRadians(radToRotate);
		double turnDist = stats.turnSpeed * deltaTime;
		if (radToRotate >= 2 * Math.PI - turnDist)
		{
			bounding.rotation = targetRotation;
		} else
		{
			turn(deltaTime, radToRotate >= Math.PI);
		}
	}

	/**
	 * Turns the tank barrel
	 * 
	 * @param direction false for counter clockwise, true for clockwise
	 * 
	 */
	public void turnBarrel(double deltaTime, boolean direction)
	{
		if (direction)
		{
			barrelRotation -= deltaTime * stats.barrelTurnSpeed;
		} else
		{
			barrelRotation += deltaTime * stats.barrelTurnSpeed;
		}
		barrelRotation = Rotator.normalizeRadians(barrelRotation);
	}

	/**
	 * Turns the tank barrel
	 * 
	 * @param target the position to turn the turret towards
	 */
	public void turnBarrel(double deltaTime, Vector2 target, boolean instant)
	{
		double targetRotation = target.subtract(getPosition()).angleRadians();
		double relativeTargetRotation = targetRotation - getRotation();
		double radToRotate = relativeTargetRotation - barrelRotation;
		radToRotate = Rotator.normalizeRadians(radToRotate);
		double turnDist = stats.barrelTurnSpeed * deltaTime;
		if (instant || radToRotate <= turnDist || radToRotate >= 2 * Math.PI - turnDist)
		{
			barrelRotation = relativeTargetRotation;
		} else
		{
			turnBarrel(deltaTime, radToRotate >= Math.PI);
		}
	}

	/**
	 * Fires a bullet.
	 * 
	 * @return true if bullet is fired. False if not enough ammo or if bullets are
	 *         fired too quick.
	 */
	public boolean fireBullet()
	{
		if (timeSinceLastFiredBullet > stats.minFireTime && stats.ammo > 0)
		{
			double barrelAbsoluteRotation = getRotation() + barrelRotation;
			Bullet bullet = stats.getBullet();
			bullet.setColor(color);
			bullet.setRotation(barrelAbsoluteRotation);
			double offsetDistance = (bounding.getDiagnol() + bullet.getBoundingBox().getDiagnol()) / 2;
			Vector2 offset = Vector2.createVector(offsetDistance, barrelAbsoluteRotation);
			Vector2 bulletPosition = getPosition().add(offset);
			bullet.setPosition(bulletPosition);
			timeSinceLastFiredBullet = 0;
			stats.ammo--;
			Game.get().getScene().addBullet(bullet);
			return true;
		}
		return false;
	}

	public void onCollisionWithTank(Tank tank)
	{
		if (Math.abs(currentVelocity) > Math.abs(tank.currentVelocity))
		{
			currentVelocity = stats.collisionBounce * currentVelocity;
		}
		undoLastMove();
	}

	@Override
	public void onDestruction()
	{
		Explosion explosion = new Explosion(getPosition(), bounding.getDiagnol() * 2);
		Game.get().getScene().addOtherObject(explosion);
	}

	public void undoLastMove()
	{
		bounding = oldBounding;
	}

	public void loseLife()
	{
		stats.lives--;
		if (stats.lives <= 0)
		{
			this.destroy();
		}
		timeSinceLastLostLife = 0;
	}

	public void gainLife()
	{
		stats.lives++;
	}

	public ArrayList<Graphic> getGraphics()
	{
		ArrayList<Graphic> graphics = super.getGraphics();
		tankSprite.updatePosition(getPosition(), getRotation());
		double barrelRot = barrelRotation + getRotation();
		double barrelXOffset = Math.cos(barrelRot) * (barrelSprite.getWidth() / 2 - tankSprite.getWidth() / 8);
		double barrelYOffset = Math.sin(barrelRot) * (barrelSprite.getWidth() / 2 - tankSprite.getWidth() / 8);
		Vector2 barrelCenterPosition = getPosition().add(new Vector2(barrelXOffset, barrelYOffset));
		barrelSprite.updatePosition(barrelCenterPosition, barrelRot);
		graphics.add(tankSprite);
		graphics.add(barrelSprite);

		if (displayLivesAndAmmo)
		{
			int layer = DrawLayer.DISPLAY.getValue();
			double textHeight = 0.5;
			double size = 0.4;
			double y = bounding.position.y - bounding.getLargerDimension() / 2 - textHeight / 2;
			String font = "Comic Sans MS";

			Vector2 ammoTextPos = new Vector2(getPosition().x - 0.55, y + textHeight / 4);
			Vector2 ammoIconPos = new Vector2(getPosition().x - 0.2, y);
			Vector2 livesTextPos = new Vector2(getPosition().x + 0.2, y + textHeight / 4);
			Vector2 heartPos = new Vector2(getPosition().x + 0.55, y);

			Color ammoTextColor = stats.ammo > 0 ? Color.WHITE : Color.RED;
			Color livesTextColor = timeSinceLastLostLife > 0.2 ? Color.WHITE : Color.RED;

			Text ammoText = new Text(layer, String.valueOf(stats.ammo), font, ammoTextColor, textHeight, ammoTextPos);
			Sprite ammoIcon = new Sprite(ImageLoader.AmmoIcon.getLoadedImage(), layer, ammoIconPos, size, size);
			Text livesText = new Text(layer, String.valueOf(stats.lives), font, livesTextColor, textHeight,
					livesTextPos);
			Sprite heartSprite = new Sprite(ImageLoader.Heart.getLoadedImage(), layer, heartPos, size, size);

			graphics.add(ammoText);
			graphics.add(ammoIcon);
			graphics.add(livesText);
			graphics.add(heartSprite);
		}
		return graphics;
	}

	public void setColor(TankColor color)
	{
		this.color = color;
		switch (color)
		{
			case BLUE:
				tankSprite = new Sprite(ImageLoader.BlueTank.getLoadedImage(), DrawLayer.TANK.getValue());
				barrelSprite = new Sprite(ImageLoader.BlueBarrel.getLoadedImage(), DrawLayer.BARREL.getValue());
				break;
			case GREEN:
				tankSprite = new Sprite(ImageLoader.GreenTank.getLoadedImage(), DrawLayer.TANK.getValue());
				barrelSprite = new Sprite(ImageLoader.GreenBarrel.getLoadedImage(), DrawLayer.BARREL.getValue());
				break;
			case ORANGE:
				tankSprite = new Sprite(ImageLoader.OrangeTank.getLoadedImage(), DrawLayer.TANK.getValue());
				barrelSprite = new Sprite(ImageLoader.OrangeBarrel.getLoadedImage(), DrawLayer.BARREL.getValue());
				break;
			case GREY:
				tankSprite = new Sprite(ImageLoader.GreyTank.getLoadedImage(), DrawLayer.TANK.getValue());
				barrelSprite = new Sprite(ImageLoader.GreyBarrel.getLoadedImage(), DrawLayer.BARREL.getValue());
				break;
			case BLACK:
				tankSprite = new Sprite(ImageLoader.BlackTank.getLoadedImage(), DrawLayer.TANK.getValue());
				barrelSprite = new Sprite(ImageLoader.BlackBarrel.getLoadedImage(), DrawLayer.BARREL.getValue());
				break;
		}
		tankSprite.setDimensions(bounding.width, bounding.height);
		//@formatter:off
		barrelSprite.setDimensions
		(bounding.width / stats.tankBarrelWidthRatio, 
		bounding.height / stats.tankBarrelHeightRatio);
	}

	public TankColor getColor()
	{
		return color;
	}
	
	public Sector getCurrentSector()
	{
		return Game.get().getScene().getSectorMap().getSectorAtPosition(getPosition());
	}
	
	public void assignTeam(int team)
	{
		this.team = team;
	}

	public int getTeam()
	{
		return team;
	}

	public TankStats getStats()
	{
		return stats;
	}

	public void addAmmo(int ammo)
	{
		stats.ammo += ammo;
	}

	public double getCurrentVelocity()
	{
		return currentVelocity;
	}

	public void setCurrentVelocity(double currentVelocity)
	{
		this.currentVelocity = currentVelocity;
	}
	
	public Vector2 getVelocity()
	{
		return Vector2.createVector(currentVelocity, getRotation());
	}
	
	protected void enableLivesAndAmmoDisplay()
	{
		displayLivesAndAmmo = true;
	}
	



}
