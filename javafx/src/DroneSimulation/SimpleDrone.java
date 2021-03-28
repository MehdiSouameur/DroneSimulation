package DroneSimulation;

import javafx.scene.image.Image;
import DroneSimulation.MyCanvas;

public class SimpleDrone {
    private double PlayerSize = 0.045;
    private double droneSize = 0.035;
    private double obstacleSize = 0.1;
    private double zombiedroneSize = 0.1;
    private double LaserSize = 0.05;
    //PNG images of objects in the arena
    private Image Player;
    private Image drone;
    private Image obstacle;
    private Image ZombieDrone;
    private Image Laser;
    
    //Constructor initialises images
    public SimpleDrone(double x, double y) {
    	 drone = new Image(getClass().getResourceAsStream("drone.png"));
    	 obstacle = new Image(getClass().getResourceAsStream("Boulder.png"));
    	 ZombieDrone = new Image(getClass().getResourceAsStream("ZombieDrone.png"));
    	 Laser = new Image(getClass().getResourceAsStream("Laser.png"));
    	 Player = new Image(getClass().getResourceAsStream("Player.png"));
    }
    
//Methods to draw objects
    public void drawDrone(MyCanvas mc, double x, double y) {
    	drawImage(mc, drone, x, y, droneSize);
    }
    
    public void drawObstacle(MyCanvas mc, double x, double y) {
    	drawImage(mc, obstacle, x, y, obstacleSize);
    }
    
    public void drawZombieDrone(MyCanvas mc, double x, double y) {
    	drawImage(mc, ZombieDrone, x, y, zombiedroneSize);
    }
    
    public void drawLaser(MyCanvas mc, double x, double y) {
    	drawImage(mc, Laser, x, y, LaserSize);
    }
    
    public void drawPlayer(MyCanvas mc, double x, double y) {
    	drawImage(mc, Player, x, y, PlayerSize);
    }
    
     public void drawImage (MyCanvas mc, Image i, double x, double y, double sz) {
		int cs = mc.getXCanvasSize();
		mc.drawImage (i, x, y, sz*cs);		
	}
}
