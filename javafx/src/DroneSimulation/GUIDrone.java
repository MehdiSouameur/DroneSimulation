package DroneSimulation;

import DroneSimulation.MyCanvas;

import java.util.Random;


import DroneSimulation.GUIDroneArena;

public class GUIDrone {
   //Drone's Health
   private int health;
   //Drone Coordinates
   private double x;
   private double y;
   //Drone speed
   private int Drone_Speed = 5; //Drone speeds
   //The values which will Increment the coordinates
   double newspeedX; 
   double newspeedY;
   //Drone hitbox
   private double drone_hitbox = 5;
   //Drone's number
   private static int num = 0;
   private int num_id;
   //Arena the drone will be in
   public GUIDroneArena arena;
   
   //Constructor
   public GUIDrone(double x, double y, GUIDroneArena arena){
	   this.health = 1;
	   this.x = x;
	   this.y = y;
	   newspeedX = Math.random() * (Drone_Speed - -Drone_Speed + 1) + -Drone_Speed;
	   newspeedY = Math.random() * (Drone_Speed - -Drone_Speed + 1) + -Drone_Speed;
	   this.arena = arena;
       this.num_id = num;
       num++;
   }
   
   //Function to move drones
   public void tryToMove() {
	   // if we hit left wall, go right randomly
	   if(x < drone_hitbox*2) {
		   newspeedX = Math.random() * (Drone_Speed - 0 + 1) + 0;
		   newspeedY = Math.random() * (Drone_Speed- -Drone_Speed + 1) + -Drone_Speed;
	   }
	   // if we hit right wall, go left randomly
	   if(x > arena.x - drone_hitbox*2) {
		   newspeedX = Math.random() * (0 - -Drone_Speed + 1) + -Drone_Speed;
		   newspeedY = Math.random() * (Drone_Speed - -Drone_Speed + 1) + -Drone_Speed;
	   }
	   // if we hit ceiling, go down randomly
	   if(y < drone_hitbox*2) {
		   newspeedX = Math.random() * (Drone_Speed - -Drone_Speed + 1) + -Drone_Speed;
		   newspeedY = Math.random() * (Drone_Speed - 0 + 1) + 0;
	   }
	   // if we hit bottom, go up randomly
	   if(y > arena.y -drone_hitbox*2) {
		   newspeedX = Math.random() * (Drone_Speed - -Drone_Speed + 1) + -Drone_Speed;
		   newspeedY = Math.random() * (0 - -Drone_Speed + 1) + -Drone_Speed;
	   }
	   //adjust coordinates with newly found speed
	   GUIObstacle obst_collision = arena.obstacleCollision(newspeedX, newspeedY, this.x, this.y,arena.getDroneHB(),arena.getDroneHB());
	   GUIDrone drone_collision = arena.droneCollision(newspeedX, newspeedY, this.x, this.y);
	   GUIZombieDrone zomb_collision = arena.zombieCollision(newspeedX, newspeedY, this.y, this.x);
	   Player player = arena.playerCollision(newspeedX, newspeedY, this.x, this.y, arena.getDroneHB());
	   //Check if the drone doesn't collide with another object on spawn
	   while(obst_collision != null || drone_collision != null || zomb_collision != null || player != null) {
		   //If there is a collision, find a new position, then test for collision again
		   newspeedX = Math.random() * (Drone_Speed - -Drone_Speed + 1) + -Drone_Speed;
		   newspeedY = Math.random() * (Drone_Speed - -Drone_Speed + 1) + -Drone_Speed;
		   obst_collision = arena.obstacleCollision(newspeedX, newspeedY, this.x, this.y,arena.getDroneHB(),arena.getDroneHB());
		   drone_collision = arena.droneCollision(newspeedX, newspeedY, this.x, this.y);
		   zomb_collision = arena.zombieCollision(newspeedX, newspeedY, this.y, this.x);
		   player = arena.playerCollision(newspeedX, newspeedY, this.x, this.y, arena.getDroneHB());
	   }
	   //Move the drone
	   x += newspeedX;
	   y += newspeedY;
   }
   
   //reduce the health by 1
   public void reduceHealth() {
  	 this.health--;
   }
   
//Getters and setters
   
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
   
   //Return the information of the drone
   public String toString() {
	  String res = "Drone " + get_num_id() + " is at x = " + (int)x +" and y = " + (int)y;
	  return res; 
   }
}

