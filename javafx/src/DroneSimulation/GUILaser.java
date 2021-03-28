package DroneSimulation;

public class GUILaser {
   // Laser Health
   private int health;
   //Laser coordinates
   private double x; 
   private double y;
   //Laser speed
   private int LaserSpeed = 5;
   //Arena the laser will be in
   public GUIDroneArena arena;
   
   //Constructor
   public GUILaser(double x, double y, GUIDroneArena arena) {
	   this.health = 1;
	   this.x = x;
	   this.y = y;
	   this.arena = arena;
   }
   
   //Method which shoots the laser towards the right
   public void Shoot() {
	   GUIDrone droneCol = arena.droneCollision(LaserSpeed, 0, (float)this.x, (float)this.y);
	   GUIZombieDrone zombieCol = arena.zombieCollision(LaserSpeed, 0, (float)this.x, (float)this.y);
	   GUIObstacle obstCol = arena.obstacleCollision(LaserSpeed, 0, (float)this.x, (float)this.y,arena.getLaserHBX(), arena.getLaserHBY());
	   //Move Laser right once
	   this.x += LaserSpeed;
	   
	 /*Check for collision
	  * if There is collision, reduce lasers health by 1 as well as the object's health by 1 */
	   if(droneCol != null) {
		   droneCol.reduceHealth();
		   this.reduceHealth();
	   }
	   if(zombieCol != null) {
		   zombieCol.reduceHealth();
		   this.reduceHealth();
	   }
	   if(obstCol != null) {
		   obstCol.reduceHealth();
		   this.reduceHealth();
	   }
	  
	    //if Laser goes out of the arena, reduce it's health
	   if(this.x>= arena.getX()) {
		   this.reduceHealth();
	   }
	   
	   //If the Laser has no health, remove it
	   if(this.health <= 0) {
		   arena.removeLaser(arena.getLaserIndex(this.x));
	   }
	   
   }
 
//Setters and Getters
   public double getX() {
	   return this.x;
   }
   
   public double getY() {
	   return this.y;
   }
   
   public int getHealth() {
	   return this.health;
   }
   
   public void reduceHealth() {
	   this.health--;
   }
   
   //Return Laser
   public String toString() {
		  String res = "Laser";
		  return res; 
	   }
}
