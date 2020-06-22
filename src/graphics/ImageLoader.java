package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader
{

	String imagePath;
	BufferedImage image = null;

	public ImageLoader(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public void loadImage()
	{
		try
		{
			image = ImageIO.read(ImageLoader.class.getResource(imagePath));
		} catch (IOException e)
		{
			System.out.println("Failed to Load Image from " + imagePath);
			image = new BufferedImage(0, 0, 0);
			e.printStackTrace();
		}
	}

	public BufferedImage getLoadedImage()
	{
		if (image == null)
		{
			loadImage();
		}
		return image;
	}

	public static ImageLoader BlueTank = new ImageLoader("/sprites/Tanks/tankBlue.png");
	public static ImageLoader BlueBarrel = new ImageLoader("/sprites/Tanks/barrelBlue.png");
	public static ImageLoader GreenTank = new ImageLoader("/sprites/Tanks/tankGreen.png");
	public static ImageLoader GreenBarrel = new ImageLoader("/sprites/Tanks/barrelGreen.png");
	public static ImageLoader OrangeTank = new ImageLoader("/sprites/Tanks/tankOrange.png");
	public static ImageLoader OrangeBarrel = new ImageLoader("/sprites/Tanks/barrelOrange.png");
	public static ImageLoader GreyTank = new ImageLoader("/sprites/Tanks/tankGrey.png");
	public static ImageLoader GreyBarrel = new ImageLoader("/sprites/Tanks/barrelGrey.png");
	public static ImageLoader BlackTank = new ImageLoader("/sprites/Tanks/tankBlack.png");
	public static ImageLoader BlackBarrel = new ImageLoader("/sprites/Tanks/barrelBlack.png");

	public static ImageLoader BlueBullet = new ImageLoader("/sprites/Bullets/bulletBlue.png");
	public static ImageLoader GreenBullet = new ImageLoader("/sprites/Bullets/bulletGreen.png");
	public static ImageLoader OrangeBullet = new ImageLoader("/sprites/Bullets/bulletOrange.png");
	public static ImageLoader GreyBullet = new ImageLoader("/sprites/Bullets/bulletGrey.png");
	public static ImageLoader SilverBullet = new ImageLoader("/sprites/Bullets/bulletSilver.png");

	public static ImageLoader Heart = new ImageLoader("/sprites/Icons/heart.png");
	public static ImageLoader AmmoIcon = new ImageLoader("/sprites/Icons/ammoIcon.png");
	public static ImageLoader ExtraAmmo = new ImageLoader("/sprites/Icons/ammoPowerup.png");
	public static ImageLoader TargetCursor = new ImageLoader("/sprites/Icons/targetCursor.png");

	public static ImageLoader BeigeSandbag = new ImageLoader("/sprites/Obstacles/sandbagBeige.png");
	public static ImageLoader BrownSandbag = new ImageLoader("/sprites/Obstacles/sandbagBrown.png");
	public static ImageLoader MetalBox = new ImageLoader("/sprites/Obstacles/metalBox.png");
	public static ImageLoader Tree = new ImageLoader("/sprites/Obstacles/treeLarge.png");

	public static ImageLoader DirtTile = new ImageLoader("/sprites/Tiles/dirt.png");
	public static ImageLoader GrassTile = new ImageLoader("/sprites/Tiles/grass.png");
	public static ImageLoader SandTile = new ImageLoader("/sprites/Tiles/sand.png");

	public static ImageLoader Explosion1 = new ImageLoader("/sprites/Effects/Explosions/explosion1.png");
	public static ImageLoader Explosion2 = new ImageLoader("/sprites/Effects/Explosions/explosion2.png");
	public static ImageLoader Explosion3 = new ImageLoader("/sprites/Effects/Explosions/explosion3.png");

	//@formatter:off
	private static ImageLoader imagesToPreload[] = 
	{ 
			BlueTank, 
			BlueBarrel, 
			GreenTank, 
			GreenBarrel, 
			OrangeTank,
			OrangeBarrel, 
			GreyTank, 
			GreyBarrel, 
			BlackTank, 
			BlackBarrel, 
			
			BlueBullet,
			GreenBullet,
			OrangeBullet,
			GreyBullet,
			SilverBullet,
			
			Heart,
			AmmoIcon,
			ExtraAmmo,
			TargetCursor,
			
			BeigeSandbag,
			BrownSandbag,
			MetalBox,
			Tree,
			
			DirtTile, 
			GrassTile, 
			SandTile,
			
			Explosion1,
			Explosion2,
			Explosion3
	};

	public static void preloadImages()
	{
		for (ImageLoader loader : imagesToPreload)
		{
			loader.loadImage();
		}
	}
}