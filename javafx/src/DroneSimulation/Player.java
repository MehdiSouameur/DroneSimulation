package DroneSimulation;

public class Player {
	private int dummy = 0;
	private int health;
	private int speed = 8;
    private double x;
    private double y;
    GUIDroneArena arena;
    
    public Player(double x, double y, GUIDroneArena arena) {
    	this.health = 1;
    	this.x = x;
    	this.y = y;
    	this.arena = arena;
    }
    
    public void up() {
    	if(this.y-speed-arena.getPlayerHB() > 0 && arena.obstacleCollision(-speed, this.y, this.x, arena.getPlayerHB(), dummy) == null) {
    		this.y-=speed;
    	}
    	
    }
    public void down() {
    	if(this.y+speed+arena.getPlayerHB() < arena.getY() && arena.obstacleCollision(speed, this.y, this.x, arena.getPlayerHB(), dummy) == null) {
    		this.y+=speed;
    	}
    }
    public void left() {
    	if(this.x-speed-arena.getPlayerHB() > 0 && arena.obstacleCollision(-speed, this.x, this.y, arena.getPlayerHB()) == null) {
    		this.x-=speed;
    	}
    }
    public void right() {
    	if(this.x+speed+arena.getPlayerHB() < arena.getX() && arena.obstacleCollision(speed, this.x, this.y, arena.getPlayerHB()) == null) {
    		this.x+=speed;
    	}
    }
    
    public void UpRight() {
    	if(this.y+speed+arena.getPlayerHB() > 0 && this.y-speed-arena.getPlayerHB() > 0) {
    		this.x+=speed;
    		this.y-=speed;
    	}
    }
    
    public double getX() {
    	return this.x;
    }
    public double getY() {
    	return this.y;
    }
    public int getHealth() {
    	return this.health;
    }
    
    public String toString() {
  	  String res = "Player is at x = " + (int)x +" and y = " + (int)y;
  	  return res; 
     }
}
