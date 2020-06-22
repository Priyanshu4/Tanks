package core;

import java.awt.Color;
import java.awt.Font;

import graphics.Camera;
import graphics.DrawLayer;
import graphics.Text;
import input.TankControls;
import maps.Sector;
import maps.SectorMap;
import maps.TVTMapGenerator;
import mathutil.Vector2;
import tanks.PlayerTank;
import tanks.Tank;
import tanks.Tank.TankColor;
import tanks.TankStats;
import tanks.powerups.ExtraAmmo;

public class TankVersusTank extends GameLogic
{

	Tank tank1;
	Tank tank2;

	double timeBetweenAmmoSpawns = 8;
	double timeTillNextAmmoSpawn = 12;
	double timeToDeclareWinner = 1;
	double winScreenTime = 5;
	boolean gameOver = false;

	public TankVersusTank()
	{
		TankStats p1stats = TankStats.getDefaultTankStats();
		TankStats p2stats = TankStats.getDefaultTankStats();
		this.tank1 = new PlayerTank(p1stats, TankColor.GREEN, TankControls.player1Controls);
		this.tank2 = new PlayerTank(p2stats, TankColor.BLUE, TankControls.player2Controls);
	}

	public TankVersusTank(Tank tank1, Tank tank2)
	{
		this.tank1 = tank1;
		this.tank2 = tank2;
	}

	@Override
	public void setup()
	{
		// generate map
		TVTMapGenerator generator = new TVTMapGenerator(tank1, tank2);
		generator.generateMap();
	}

	@Override
	public void update(double deltaTime)
	{
		timeTillNextAmmoSpawn -= deltaTime;
		if (timeTillNextAmmoSpawn <= 0)
		{
			SectorMap sectorMap = Game.get().getScene().getSectorMap();
			sectorMap.updateEmptySectors();
			Sector sector = sectorMap.getRandomEmptySector();
			if (sector != null)
			{
				ExtraAmmo ammo = new ExtraAmmo(sector.getPosition());
				Game.get().getScene().addPowerup(ammo);
				timeTillNextAmmoSpawn = timeBetweenAmmoSpawns;
			}
		}

		if (Game.get().getScene().getTanks().size() <= 1)
		{
			timeToDeclareWinner -= deltaTime;

			if (gameOver)
			{
				winScreenTime -= deltaTime;
				if (winScreenTime <= 0)
				{
					Game.get().endGame();
				}
			} else if (Game.get().getScene().getTanks().size() == 0)
			{
				gameOver = true;
				displayEndText("DRAW!", Color.BLACK);
			} else if (Game.get().getScene().getBullets().size() == 0 || timeToDeclareWinner <= 0)
			{
				gameOver = true;
				Tank winner = Game.get().getScene().getTanks().get(0);
				TankColor winnerColor = winner.getColor();
				String winnerString = winnerColor + " WINS!";
				displayEndText(winnerString, winnerColor.getCorrespondingColor());
			}
		}
	}

	public void displayEndText(String text, Color color)
	{
		int drawLayer = DrawLayer.DISPLAY.getValue();
		String font = "Comic Sans MS";
		Camera camera = Game.get().getCamera();
		double height = camera.verticalFOV / 4;
		Vector2 position = new Vector2(camera.position.x, camera.position.y + height / 2);
		Text winText = new Text(drawLayer, text, font, Font.BOLD, color, height, position);
		Game.get().getScene().addGraphic(winText);
	}

}
