package DroneSimulation;

import java.util.Random;

import javax.swing.text.html.HTMLDocument.Iterator;

import java.util.ArrayList;
import java.io.*;

public class GUIDroneArena{
  //Arena size
  public int x;
  public int y;
  //Object hitboxes
  private double playerHB = 8;
  private double drone_hitbox = 5; 
  private double obstHitbox_X = 28;
  private double obstHitbox_Y = 35;
  private double zombieHB = 13;
  private double LaserHB_X = 50;
  private double LaserHB_Y = 3;
  //Player 
  Player player = null;
  //Array lists of different objects in arena
  private static ArrayList <GUIDrone> Drones;
  private static ArrayList <GUIObstacle> Obstacles;
  private static ArrayList <GUIZombieDrone> ZombieDrones;
  private static ArrayList <GUILaser> Lasers;
 
  //Constructor
  GUIDroneArena(int x, int y){
	  //initialise array lists
	  Drones = new ArrayList<GUIDrone>();
	  Obstacles = new ArrayList<GUIObstacle>();
	  ZombieDrones = new ArrayList<GUIZombieDrone>();
	  Lasers = new ArrayList<GUILaser>();
	  this.x = x;
	  this.y = y;
  }
  
//Methods to add objects
  
  //Add a drone randomly
  public void addDrone(GUIDroneArena arena){
	  //random coordinates
	  double newX = Math.random() * (arena.getX()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;;
	  double newY = Math.random() * (arena.getY()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;;
	  //check if there is no collision with obstacle or drone on spawn point
	  while(getDroneAt(newX, newY) != null || getObstacle(newX, newY) != null || getZombieDrone(newX, newY) != null) {
		  newX = Math.random() * (arena.getX()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;
		  newY = Math.random() * (arena.getY()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;
		  
	  }
	  GUIDrone d = new GUIDrone(newX, newY,arena);
	  Drones.add(d);
  }
  
  //Add Player randomly
  public void addPlayer(GUIDroneArena arena) {
	      if(player != null) {
	    	  return;
	      }
		  //random coordinates
		  double x = Math.random() * (arena.getX()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;
		  double y = Math.random() * (arena.getY()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;
		  //check if there is no collision with drone or obstacle on spawn point
		  while(getDroneAt(x, y) != null || getObstacle(x, y) != null || getZombieDrone(x,y) != null) {
			  x = Math.random() * (arena.getX()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;
			  y = Math.random() * (arena.getY()-drone_hitbox*2 - drone_hitbox*2 + 1) + drone_hitbox*2;
		  }
		  player = new Player(x,y,arena);
  }
  
  //Add a boulder randomly
  public void addObstacle(GUIDroneArena arena) {
	  //random coordinates
	  double x = Math.random() * (arena.getX()-obstHitbox_X*2 - obstHitbox_X*2 + 1) + obstHitbox_X*2;
	  double y = Math.random() * (arena.getY()-obstHitbox_Y*2 - obstHitbox_Y*2 + 1) + obstHitbox_Y*2;
	  //check if there is no collision with drone or obstacle on spawn point
	  while(getDroneAt(x, y) != null || getObstacle(x, y) != null || getZombieDrone(x,y) != null) {
		  x = Math.random() * (arena.getX()-obstHitbox_X*2 - obstHitbox_X*2 + 1) + obstHitbox_X*2;
		  y = Math.random() * (arena.getY()-obstHitbox_Y*2 - obstHitbox_Y*2 + 1) + obstHitbox_Y*2;
	  }
	  GUIObstacle d = new GUIObstacle(x,y,arena);
	  Obstacles.add(d);
	  
  }
  
  //Add a zombie drone randomly
  public void addZombieDrone(GUIDroneArena arena) {
		//random coordinates 
		  double x = Math.random() * (arena.getX()-zombieHB*2 - zombieHB*2 + 1) + zombieHB*2;
		  double y = Math.random() * (arena.getY()-zombieHB*2 - zombieHB*2 + 1) + zombieHB*2;
		  //check if there is no collision with drone or obstacle on spawn point
		  while(getDroneAt(x, y) != null || getObstacle(x, y) != null || getZombieDrone(x,y) != null) {
			  x = Math.random() * (arena.getX()-zombieHB*2 - zombieHB*2 + 1) + zombieHB*2;
			  y = Math.random() * (arena.getY()-zombieHB*2 - zombieHB*2 + 1) + zombieHB*2;
		  }
		  //initialise new object with acquired coordinates and add object to respective array list
		  GUIZombieDrone d = new GUIZombieDrone(x,y,arena);
		  ZombieDrones.add(d);
	  }
	  
//Polymorphism: Add a zombie drone at specific coordinates
  public void addZombieDrone(GUIDroneArena arena, double x, double y) {
		  //add zombie drone at x and y
		  GUIZombieDrone d = new GUIZombieDrone(x,y,arena);
		  ZombieDrones.add(d);
	  }
  
  //Add a Laser randomly
  public void addLaser(GUIDroneArena arena) {
	  double x = -LaserHB_X;
	  double y = Math.random() * (arena.getY()-LaserHB_Y*2 - LaserHB_Y*2 + 1) + zombieHB*2;
	  GUILaser d = new GUILaser(x,y,arena);
	  Lasers.add(d);
	  
  }
  
  //Polymorphism: Add a Laser at mouse's height
  public void addLaser(GUIDroneArena arena,double y) {
	  double x = -LaserHB_X;
	  GUILaser d = new GUILaser(x,y,arena);
	  Lasers.add(d);
	  
  }
  
//Methods to remove specific objects during the animation
  //remove the player
  public void removePlayer() {
	  if(player != null) {
		  player = null;
	  }
  }
  
  //remove laser
  public void removeLaser(int index) {
	  Lasers.remove(index);
  }
  
  //Remove drone
  public void removeDrone(int index) {
    Drones.remove(index);
  }
  
  //Method to check if the player is closer to zombie drone than the given drone
  public boolean compareWithPlayer(double zombx, double zomby, GUIDrone drone) {
	  boolean b = false;
	  if(drone == null) {
		  return true;
	  }
	  double d_distance = Math.abs(zombx - drone.getX()) + Math.abs(zomby - drone.getY());
	  double player_distance = Math.abs(zombx - player.getX()) + Math.abs(zomby - player.getY());
	  if(d_distance > player_distance) {
		  return b = true;
	  }
	  return b;
  }
  
  
  public void fillDrones(double x, double y, GUIDroneArena arena) {
	  GUIDrone d = new GUIDrone(x,y,arena);
	  Drones.add(d);
  }
  
  public void fillObstacles(double x, double y, GUIDroneArena arena) {
	  GUIObstacle d = new GUIObstacle(x,y,arena);
	  Obstacles.add(d);
  }
  
  public void fillZombieDrones(double x, double y, GUIDroneArena arena) {
	  GUIZombieDrone d = new GUIZombieDrone(x,y,arena);
	  ZombieDrones.add(d);
  }
  
  public void fillPlayer(double x, double y, GUIDroneArena arena) {
	  player = new Player(x,y,arena);
  }
  
  //Draws all the objects onto the arena
  public void ShowObjects(MyCanvas c) {
	  c.clearCanvas();
	  for(GUIDrone element : Drones) {
		  SimpleDrone drone = new SimpleDrone(element.getX(), element.getY());
		  drone.drawDrone(c,element.getX(), element.getY());
	  }
	  for(GUIObstacle element : Obstacles) {
		  SimpleDrone obstacle = new SimpleDrone(element.getX(), element.getY());
		  obstacle.drawObstacle(c,element.getX(), element.getY());
	  }
	  for(GUIZombieDrone element : ZombieDrones) {
		  SimpleDrone ZombieDrone = new SimpleDrone(element.getX(), element.getY());
		  ZombieDrone.drawZombieDrone(c,element.getX(), element.getY());
	  }
	  for(GUILaser element : Lasers) {
		  SimpleDrone Laser = new SimpleDrone(element.getX(), element.getY());
		  Laser.drawLaser(c,element.getX(), element.getY());
	  }
	  if(this.player != null) {
		  SimpleDrone User = new SimpleDrone(player.getX(), player.getY());
		  User.drawPlayer(c, player.getX(), player.getY());
	  }
  }
  
//Collision methods
  
  //Check if there is collision with a drone
  public GUIDrone droneCollision(double newspeedX,double newspeedY, double x, double y) {
	  for(GUIDrone element : Drones){
		  //if drone is itself, continue to next drone
		  if(element.getX() == x && element.getY() == y) {
			  continue;
		  }
		  //if there is a collision with another drone at next position, return true
		  if(element.getX()-drone_hitbox < x+newspeedX && x+newspeedX < element.getX()+drone_hitbox) {
			  if(element.getY()-drone_hitbox < y+newspeedY && y+newspeedY < element.getY()+drone_hitbox) {
				  return element;
			  }
		  }
	  }
	  //else return false
	 return null;
  }
  
  //Check if there is collision with a zombie
  public GUIZombieDrone zombieCollision(double newspeedX,double newspeedY, double x, double y) {
	  for(GUIZombieDrone element : ZombieDrones){
		  //if drone is itself, continue to next drone
		  if((float)element.getX() == x && (float)element.getY() == y) {
			  continue;
		  }
		  //if there is a collision with another drone at next position, return that drone
		  if(element.getX()-zombieHB < x+newspeedX && x+newspeedX < element.getX()+zombieHB) {
			  if(element.getY()-zombieHB < y+newspeedY && y+newspeedY < element.getY()+zombieHB) {
				  return element;
			  }
		  }
	  }
	  //else return false
	 return null;
  }
  
  //Check if there is collision with a player
  public Player playerCollision(double newspeedX, double newspeedY, double x, double y, double hitbox) {
	  if(player == null) {
		  return null;
	  }
	  if(player.getX()-playerHB < x-newspeedX && x+newspeedX < player.getX()+playerHB) {
		  if(player.getY()-playerHB < y-newspeedY && y+newspeedY < player.getY()+playerHB) {
			  return player;
		  }
	  }
	  return null;
  }
  
  //Check if the prey was caught
  public boolean CaughtPrey(double x, double y) {
	  boolean b = false;
	  for(GUIZombieDrone element: ZombieDrones) {
		  if(element.getX()-zombieHB < x && x < element.getX()+zombieHB) {
			  if(element.getY()-zombieHB < y && y < element.getY()+zombieHB) {
				  return b = true;
			  }
		  }
	  }
	  //else return false
	 return b;
  }
  
  //Check if there is collision with an obstacle
  public GUIObstacle obstacleCollision(double newspeedX, double newspeedY, double x, double y, double hitbox_X, double hitbox_Y) {
	 for(GUIObstacle element : Obstacles){
		  if(element.getX()-obstHitbox_X < x+newspeedX && x+newspeedX < element.getX()+obstHitbox_X) {
			  if(element.getY()-obstHitbox_Y < y && y < element.getY()+obstHitbox_Y) {
				  return element;
			  }
			  if(element.getY()-obstHitbox_Y < y+newspeedY && y+newspeedY < element.getY()+obstHitbox_Y) {
				  return element;
			  }
		  }
		  if(element.getY()-obstHitbox_Y < y+newspeedY && y+newspeedY < element.getY()+obstHitbox_Y) {
			  if(element.getX()-obstHitbox_X < x && x < element.getX()+obstHitbox_X) {
				  return element;
			  }
		  }
	  }
	 return null;
  }
  
  //Polymorphism:Check if the next x position is in the obstacle x hitbox
  public GUIObstacle obstacleCollision(double newX, double x,double y, double hitbox) {
		 for(GUIObstacle element : Obstacles){
			 if(element.getX()-obstHitbox_X < x+newX-hitbox && x+newX+hitbox < element.getX()+obstHitbox_X) {
				 if(element.getY()-obstHitbox_Y < y-hitbox && y+hitbox < element.getY()+obstHitbox_Y) {
					  return element;
				  }
			 }
		  }
		 return null;
	  }
  
  //Polymorphism: Check if the next y position is in the obstacle y hitbox
     //Polymophism possible here thanks to the passed dummy
  public GUIObstacle obstacleCollision(double newY, double y,double x, double hitbox, int dummy) {
		 for(GUIObstacle element : Obstacles){
			 if(element.getY()-obstHitbox_Y < y+newY-hitbox && y+newY+hitbox < element.getY()+obstHitbox_Y) {
				 if(element.getX()-obstHitbox_X < x-hitbox && x+hitbox < element.getX()+obstHitbox_X) {
					  return element;
				  }
			 }
		  }
		 return null;
	  }
   
   //Check health and remove objects if their health is 0 or below
   void checkHealth() {
	  for(GUILaser element: Lasers) {
		  if(element.getHealth() <= 0) {
			  Lasers.remove(getLaserIndex(element));
		  }
	  }
	  for(GUIZombieDrone element: ZombieDrones) {
		  if(element.getHealth() <= 0) {
			  ZombieDrones.remove(getZombieIndex(element));
		  }
	  }
	  
	  for(GUIDrone element : Drones) {
		  if(element.getHealth() <= 0) {
			  Drones.remove(getDroneIndex(element));
		  }
	  }
	  for(GUIObstacle element : Obstacles) {
		  if(element.getHealth() <= 0) {
			  Obstacles.remove(getObstacleIndex(element));
		  }
	  }
	  
  }
  
  //attempt to move all the movable objects in the arena
  public void moveAllObjects() {
	  for(GUIZombieDrone element: ZombieDrones) {
		  element.Hunt();
	  }
	  for(GUILaser element: Lasers) {
		  element.Shoot();
	  }
	  for(GUIDrone element : Drones) {
		  element.tryToMove();
	  }
  }
  
//coordinate Getters
  public int getX() {
	   return this.x;
  }
  
  public int getY() {
	   return this.y;
  }
  
//Hitbox getters
  //Get the X laser hitbox
  public double getLaserHBX() {
	  return this.LaserHB_X;
  }
  //Get the Y laser hitbox
  public double getLaserHBY() {
	  return this.LaserHB_Y;
  }
  //Get the player hitbox
  public double getPlayerHB() {
	  return this.playerHB;
  }
  //Get the drone hitbox
  public double getDroneHB() {
	  return drone_hitbox;
  }
  //Get the zombie hitbox
  public double getZombieHB() {
	  return zombieHB;
  }
  
//Object getters
  public GUIDrone getDroneAt(double x, double y) {
	  for(GUIDrone element : Drones) {
		  if(x == element.getX() && y == element.getY()){
			  return element;
		  }
		  
	  }
	  return null;
	  
  }
  
  //Gets the closest drone
  public GUIDrone getClosestDrone(double zombx, double zomby) {
	  //If there are no drones in arena return null
	  if(Drones.isEmpty()) {
		  return null;
	  }
	  //Start with first drone in array list
	  GUIDrone d = Drones.get(0);
	  for(GUIDrone element : Drones) {
		  //Compare the distance between zombie-elementdrone and zombie-d
		  double d_distance = Math.abs(zombx - d.getX()) + Math.abs(zomby - d.getY());
		  double zomb_distance = Math.abs(zombx - element.getX()) + Math.abs(zomby - element.getY());
		  if(d_distance > zomb_distance) {
			  //if the element drone is closer,replace d with element
			  d = element;;
		  }
	  }
	  return d;
  }
  
  //Get the obstacle at x,y position
  public GUIObstacle getObstacle(double x, double y) {
	  for(GUIObstacle element : Obstacles){
		  if(element.getX()-obstHitbox_X < x && x < element.getX()+obstHitbox_X) {
			  if(element.getY()-obstHitbox_Y < y && y < element.getY()+obstHitbox_Y) {
				  return element;
			  }
		  }
	  }
	  //else return null
	  return null;
  }
  
  //Get the ZombieDrone at x,y position
  public GUIZombieDrone getZombieDrone(double x, double y) {
	  for(GUIZombieDrone element : ZombieDrones){
		  if(element.getX()-zombieHB < x && x < element.getX()+zombieHB) {
			  if(element.getY()-zombieHB < y && y < element.getY()+zombieHB) {
				  return element;
			  }
		  }
	  }
	  //else return null
	  return null;
  }
  
//Get all the object coordinates to save and load files  
  public String getAllDrones() {
	  String coordinates = "";
	  for(GUIDrone element : Drones) {
		  //Save x and y position of each object
		  coordinates = coordinates + element.getX() + " " + element.getY() + "\n";
	  }
	  //Set 0 at the end to determine when to pass on to next object
	  coordinates += "0\n";
	  return coordinates;
  }
  
  public String getAllObstacles() {
	  String coordinates = "";
	  for(GUIObstacle element : Obstacles) {
		  coordinates = coordinates + element.getX() + " " + element.getY() + "\n";
	  }
	  coordinates += "0\n";
	  return coordinates;
  }
  
  public String getAllZombieDrones() {
	  String coordinates = "";
	  for(GUIZombieDrone element : ZombieDrones) {
		  coordinates = coordinates + element.getX() + " " + element.getY() + "\n";
	  }
	  coordinates += "0\n";
	  return coordinates;
  }
  
  public String getPlayer() {
	  String coordinates = "";
	  if(player != null) {
		  coordinates = coordinates + player.getX() + " " + player.getY() + "\n";
	  }else {
		  coordinates = "0";
	  }
	  return coordinates;
  }
  
//Index getters
  public int getZombieIndex(GUIZombieDrone zombie) {
	  //i is the index
	  int i = 0;
	  for(GUIZombieDrone element: ZombieDrones) {
		  if(element == zombie)
			  //If we reached the object we're looking for, return the current index
			  return i;
		  //If not, then we increment the index and move on to the next object
		  i++;
	  }
	  return i;
  }
  
  public int getObstacleIndex(GUIObstacle boulder) {
	  int i = 0;
	  for(GUIObstacle element: Obstacles) {
		  if(element == boulder)
			  return i;
		  i++;
	  }
	  return i;
  }
  
  public int getDroneIndex(GUIDrone drone) {
	  int i = 0;
	  for(GUIDrone element: Drones) {
		  if(element == drone) {
			  return i;
		  }
		  i++;
	  }
	  return i;
  }
  
  public int getLaserIndex(double x) {
	  int i = -1;
	  for(GUILaser element : Lasers) {
		  i++;
		  if(element.getX()-LaserHB_X < x) {
			  return i;
		  }
	  }
	  return i;
  }
  
  public int getLaserIndex(GUILaser Laser){
	  int i = 0;
	  for(GUILaser element : Lasers) {
		  i++;
		  if(element == Laser) {
			  return i;
		  }
	  }
	  return i;
  }
  
 
  
//return information on the arena and all the objects within it
  public String toString() {
	  String res = "Arena size is x = " + this.x + " y = " + this.y + "\n";
	  for(GUIDrone element : Drones)
		  res = res + element + "\n";
	  for(GUIObstacle element : Obstacles)
		  res = res + element + "\n";
	  for(GUIZombieDrone element : ZombieDrones)
		  res = res + element + "\n";
	  for(GUILaser element : Lasers)
		  res = res + element + "\n";
	  if(player != null) {
		  res = res + player; 
	  }
	  
	  return res;
	  
  }
}