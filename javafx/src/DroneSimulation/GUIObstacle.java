package DroneSimulation;

import java.util.Random;

import javafx.scene.image.Image;

public class GUIObstacle {
	//Boulder Health
	private int health;
	//Boulder coordinates
    private double x;
    private double y;
    //Boulder number
    private static int num = 0;
    private int num_id;
    //Boulder's arena
    public GUIDroneArena arena;
    
    
    //Constructor
    public GUIObstacle(double x, double y, GUIDroneArena arena) {
    	this.health = 3;
    	this.x = x;
    	this.y = y;
    	this.arena = arena;
    	this.num_id = num;
    	num++;
    }
    
//Getters and Setters
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
    
    public void reduceHealth() {
   	 this.health--;
    }
    
    //return obstacle information
    public String toString() {
    	String res = "Obstacle " + get_num_id() + " is at x = " + (int)x +" and y = " + (int)y;
    	return res;
    }
    
}

