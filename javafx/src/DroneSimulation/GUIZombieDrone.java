package DroneSimulation;

public class GUIZombieDrone {
	//coordinates
	 private int health;
     private double x;
     private double y;
     //timer when created to add gap between zombies
     private int Creation_Timer;
     //Number of zombie drone
     private static int num = 0;
     private int num_id;
     //Number of infections by zombie drone
     private int infections = 0;
     //The arena which the zombie drone will be in
     public GUIDroneArena arena;
     
     //constructor initialising some attributes
     public GUIZombieDrone(double x, double y, GUIDroneArena arena) {
    	 this.health = 2;
    	 Creation_Timer = 100;
    	 this.x = x;
    	 this.y = y;
    	 this.arena = arena;
    	 this.num_id = num;
         num++;
     }
     
     //Hunt the closest drone in the arena
     public void Hunt() {
    	 //Timer on spawn to allow distance between zombie drones
    	 if(Creation_Timer>0) {
    		 Creation_Timer--;
    		 return;
    	 }
    	 //Get the prey which is the closest drone
    	 GUIDrone prey = arena.getClosestDrone(this.x, this.y);
    	 int preyIndex = arena.getDroneIndex(prey);
    	 //This checks if there is a player in the arena
    	 if(arena.player != null) {
    		 double playerX = arena.player.getX();
    		 double playerY= arena.player.getY();
    		 //If the Player is closer than the closest non-player drone, then chase the player instead
    		 if(arena.compareWithPlayer(this.x, this.y, prey) == true) {
    			 double newX = getIncrementX(arena.player.getX());
        		 double newY = getIncrementY(arena.player.getY());
        		 //This checks for collision with an obstacle
        		 GUIObstacle obstCollision = arena.obstacleCollision(newX, newY, (float)this.x, (float)this.y, arena.getZombieHB(),arena.getZombieHB());
        		 if(obstCollision == null) {
        			 //Move Zombie Drone towards the prey
        		     this.x += newX;
        		     this.y += newY;
        		 }
        		 //If zombie catches player, delete player and replace with a zombie drone
        		 if(arena.playerCollision(newX, newY, this.x, this.y, arena.getZombieHB()) != null){
        			 arena.removePlayer();
        			 arena.addZombieDrone(arena, playerX, playerY);
        			 this.infections++;
        		 }
    		 }
    	 }
    	 //Condition to make sure there are drones in the arena
    	 if(prey!=null) {
    	 //code to make the zombie drone go closer to the prey	 
    		 //this determines by how much the coordinates will be incremented
    		 double newX = getIncrementX(prey.getX());
    		 double newY = getIncrementY(prey.getY());
    		 //This checks for collision with a boulder
    		 GUIObstacle collision = arena.obstacleCollision(newX, newY, (float)this.x, (float)this.y, arena.getZombieHB(),arena.getZombieHB());
    		 if(collision == null) {
    			 //move the zombie drone towards the prey
    		     this.x += newX;
    		     this.y += newY;
    		 }
    		 //If zombie drone catches prey, delete prey and replace with a zombie drone
    		 if(arena.droneCollision(newX, newY, (float)this.x, (float)this.y) != null) {
    			 arena.removeDrone(preyIndex);
    			 arena.addZombieDrone(arena, this.x+newX+arena.getZombieHB(), this.y+newY+arena.getZombieHB());
    			 this.infections++;
    		 }
    	 }
    	 
    	 
     }
     
     //Returns value which determines next x position
     public double getIncrementX(double preyX) {
     /*this code will return newX
       newX will be between 1 and 3 or between -1 and -3*/
    	 double x_distance = preyX-this.x;
	
		 if(0 < x_distance && x_distance < 170)
			 x_distance = 170;
		 if(0 > x_distance && x_distance > -170) 
			 x_distance = -170;
		 
		 double newX = 512/x_distance;
		 return newX;
     }
     
     //Returns value which determines next Y position
     public double getIncrementY(double preyY) {
     /*this code will return newY
         newY will be between 1 and 3 or between -1 and -3*/
		 double y_distance = preyY-this.y;
		
		 if(0 < y_distance && y_distance < 170)
			 y_distance = 170;
		 if(0 > y_distance && y_distance > -170) 
			 y_distance = -170;
		 
		 
		 double newY = 512/y_distance;
		 return newY;
     }
     
     //Reduces health by 1
     public void reduceHealth() {
    	 this.health--;
     }
     
//Setters and Getters
     
     public void setX(int x) {
  	   this.x = x;
     }
     
     public void setY(int y) {
  	   this.y = y;
     }
  
     public double getX() {
  	   return this.x;
     }
     
     public double getY() {
  	   return this.y;
     }
     
     public int get_num_id() {
  	   return this.num_id;
     }
     
     public int getHealth() {
  	   return this.health;
     }
     
     //Method which returns drone information as a string
     public String toString() {
   	  String res = "Zombie Drone " + get_num_id() + " is at x = " + (int)x +" and y = " + (int)y;
   	  res += "\nInfections: " + this.infections;
   	  return res; 
      }
}
