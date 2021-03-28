package DroneSimulation;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import DroneSimulation.MyCanvas;


public class DroneBorderPane extends Application{
	private VBox rtPane;// pane in which info on system listed
    private MyCanvas mc;						// canvas into which system drawn
    private int canvasSize = 512;	
    private boolean animation = false;  //Determines whether animation is on or off
    GUIDroneArena MyArena = new GUIDroneArena(canvasSize, canvasSize);    
    MyTimer timer = new MyTimer();
    //FileChooser to save files
    JFileChooser chooser = new JFileChooser();
    
    //File filter sets target files to txt
    FileFilter filter = new FileFilter() {
    	
    	public boolean accept(File f) {
    	if (f.getAbsolutePath().endsWith(".txt")) 
    		return true;
    	if (f.isDirectory()) 
    		return true;
    	
    	return false;
    	}
    	
    	public String getDescription() {
    	   return "txt";
    	}
    };
    
    //Save the arena as a list of different values in a text file
    public void save() {
    	chooser.setFileFilter(filter);
    	int returnVal = chooser.showSaveDialog(null);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File selFile = chooser.getSelectedFile();
    		File currDir = chooser.getCurrentDirectory();
    		System.out.println("You chose to save into file: "+ selFile.getName() + " in the dir " + currDir.getAbsolutePath());
    		try {
    			FileWriter writer = new FileWriter(selFile);
    			selFile.createNewFile();
    			writer.write(MyArena.x + " " + MyArena.y + "\n");
    			writer.write(MyArena.getAllDrones());
    			writer.write(MyArena.getAllObstacles());
    			writer.write(MyArena.getAllZombieDrones());
    			writer.write(MyArena.getPlayer());
    			writer.close();
    			
    		}
    		catch(Exception e) {
    			e.getStackTrace();
    		}
    	}
    }
    
    public void open() {
    	chooser.setFileFilter(filter);
    	int returnVal = chooser.showOpenDialog(null);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		File selFile = chooser.getSelectedFile();
    		System.out.println("You chose to open this file: " + selFile.getName());
    		if(selFile.isFile()){ 
    			try {
        			Scanner read = new Scanner(selFile);
        			MyArena = new GUIDroneArena(read.nextInt(), read.nextInt());
        			double test = read.nextDouble();
        			while (test != 0) {
        				MyArena.fillDrones(test, read.nextDouble(), MyArena);
        				test = read.nextDouble();
        			}
        			test = read.nextDouble();
        			while(test != 0) {
        				MyArena.fillObstacles(test, read.nextDouble(), MyArena);
        				test = read.nextDouble();
        			}
        			test = read.nextDouble();
        			while(test != 0) {
        				MyArena.fillZombieDrones(test, read.nextDouble(), MyArena);
        				test = read.nextDouble();
        			}
        			test = read.nextDouble();
        			if(test != 0) {
        				MyArena.fillPlayer(test, read.nextDouble(), MyArena);
        			}else {
        				MyArena.removePlayer();
        			}
        			MyArena.ShowObjects(mc);
        			Label label = new Label(MyArena.toString());
         			rtPane.getChildren().clear();
         			label.setTextFill(Color.web("#FFFFFF"));
         			rtPane.getChildren().add(label);
         			 
        			read.close();
        			
        		}
        		catch(Exception e) {
        			e.getStackTrace();
        		}
    		}
    	}
    }
    
    private void showMessage(String TStr, String CStr) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(TStr);
	    alert.setHeaderText(null);
	    alert.setContentText(CStr);

	    alert.showAndWait();
   }
    /**
     * function to show in a box ABout the programme
     */
    private void showAbout() {
    	 showMessage("About", "RJM's BorderPane Demonstrator");
     }

    /**
     * function to show in a box Help the programme
     */
    private void showHelp() {
    	 showMessage("Help", "A drone Simulation with Zombies!, click in the arena to shoot lasers" + "\n" +
                             "Add a player and move with WASD, avoid the zombies!" + "\n" +
                   			 "You can press a button to place the ball randomly");
     }

    /**
     * Function to set up the menu
     */
    public MenuBar setMenu() {
    	MenuBar menuBar = new MenuBar();		// create menu

    	Menu mHelp = new Menu("Help");
    	    // have entry for help
    //add sub menus which give information on objects in the arena
    	MenuItem mAbout = new MenuItem("About");
    	mAbout.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                	showMessage("About","A drone simulation with Zombies!\nCheck below About for more information");				
               }	      
    	});
    	MenuItem mfdrone = new MenuItem("Drones");
    	mfdrone.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                	showMessage("Drones","Press the add drone button to add drones at a random position\n"+
                                         "Drones bounce off walls and objects in random directions!");				
               }	
    	});
    	MenuItem mfboulder = new MenuItem("Obstacles");
    	mfboulder.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                	showMessage("Obstacles","Press the add obstacle button to add a boulder at a random position\n"+
                                         "Boulders get in the way and force objects to move differently!");				
               }	
    	});
    	MenuItem mfzombie = new MenuItem("Zombies");
    	mfzombie.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                	showMessage("Obstacles","Press the add zombiedrone button to add a zombie at a random position."+
                                         " Zombies chase down drones and the player and turn them into zombies!");				
               }	
    	});
    	MenuItem mflaser = new MenuItem("Lasers");
    	mflaser.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                	showMessage("Lasers","Click in the arena or press the shoot laser button to shoot lasers."+
                                         " Lasers can destroy every object in the arena!");				
               }	
    	});
    	MenuItem mfplayer = new MenuItem("Player");
    	mfplayer.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                	showMessage("Player","press the add player button to add a player."+
                                         " Use WASD to move the player! avoid getting infected by zombies!");				
               }	
    	});
    	
    	mHelp.getItems().addAll(mAbout, mfdrone, mfboulder, mfzombie, mflaser, mfplayer); 	// add submenu to Help
    		
    			// now add File menu, which here only has Exit
    	Menu mFile = new Menu("File");				// create File Menu
    	MenuItem mExit = new MenuItem("Exit");		// and Exit submenu
    	mExit.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent t) {		// and add handler
    	        System.exit(0);						// quit program
    	    }
    	});
    	MenuItem mSave = new MenuItem("Save");		
    	mSave.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent t) {		
    	        save();					
    	    }
    	});
    	MenuItem mLoad = new MenuItem("Load");		
    	mLoad.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent t) {		
    	        open();				
    	    }
    	});
    	
    	mFile.getItems().addAll(mExit, mSave, mLoad);	// add Exit submenu to File
    		
    	menuBar.getMenus().addAll(mFile, mHelp);	// menu has File and Help
    		
    	return menuBar;					// return the menu, so can be added
    } 

    
    //Set up all the buttons 
    private HBox setButtons() {
		// create button
    	 Button btnBottom1 = new Button("Add Drone");
    	 Button btnBottom2 = new Button("Start/Stop moving");
    	 Button btnBottom3 = new Button("Clear Canvas");
    	 Button btnBottom4 = new Button("Add obstacle");
    	 Button btnBottom5 = new Button("Add Zombie Drone");
    	 Button btnBottom6 = new Button("Shoot laser");
    	 Button btnBottom7 = new Button("Add Player");
         
    	 //Add drone button
         btnBottom1.setOnAction(new EventHandler<ActionEvent>() {
     		@Override
     		public void handle(ActionEvent event) {
     			//add Drones then draw them
     			 
     			MyArena.addDrone(MyArena);
     			MyArena.ShowObjects(mc);
     			//Update information on right pane
     			Label label = new Label(MyArena.toString());
     			rtPane.getChildren().clear();
     			label.setTextFill(Color.web("#FFFFFF"));
     			rtPane.getChildren().add(label);
     			 }
     	});
         //Start/Stop animation button
         btnBottom2.setOnAction(new EventHandler<ActionEvent>() {
      		@Override
      		public void handle(ActionEvent event){
      			//Move all drones
      			 if(animation == false) {
      				 timer.start();
      				 animation = true;
      			 }else {
      				 timer.stop();
      				 animation = false;
      			 } 
      		}
      	});
         //Reset simulation button
         btnBottom3.setOnAction(new EventHandler<ActionEvent>() {
      		@Override
      		public void handle(ActionEvent event) {
      			// CLear all drones that are drawn
      		  animation = false;
      		  timer.stop();
      		  mc.clearCanvas();
      		  MyArena = new GUIDroneArena(512, 512);
      		  MyArena.removePlayer();
      		//Update information on right pane
   			Label label = new Label(MyArena.toString());
   			rtPane.getChildren().clear();
   			label.setTextFill(Color.web("#FFFFFF"));
   			rtPane.getChildren().add(label);
      			 
      		}
      	});
         //Add obstacle button
        btnBottom4.setOnAction(new EventHandler<ActionEvent>() {
       		@Override
       		public void handle(ActionEvent event) {
       			// CLear all drones that are drawn
       		    MyArena.addObstacle(MyArena);
       		    MyArena.ShowObjects(mc);
       		//Update information on right pane
     			Label label = new Label(MyArena.toString());
     			rtPane.getChildren().clear();
     			label.setTextFill(Color.web("#FFFFFF"));
     			rtPane.getChildren().add(label);
       		}
       	});
        //Add ZombieDrone button
        btnBottom5.setOnAction(new EventHandler<ActionEvent>() {
     		@Override
     		public void handle(ActionEvent event) {
     			//add Drones then draw them
     			 
     			 MyArena.addZombieDrone(MyArena);
     			 MyArena.ShowObjects(mc);
     			//Update information on right pane
      			Label label = new Label(MyArena.toString());
      			rtPane.getChildren().clear();
      			label.setTextFill(Color.web("#FFFFFF"));
      			rtPane.getChildren().add(label);
     			 }
     	});
        //Shoot laser button
        btnBottom6.setOnAction(new EventHandler<ActionEvent>() {
     		@Override
     		public void handle(ActionEvent event) {
     			//add Drones then draw them
     			 
     			 MyArena.addLaser(MyArena);
     			 MyArena.ShowObjects(mc);
     			}
     	});
        //Add player button
        btnBottom7.setOnAction(new EventHandler<ActionEvent>() {
     		@Override
     		public void handle(ActionEvent event) {
     			//add Drones then draw them
     			 
     			 MyArena.addPlayer(MyArena);
     			 MyArena.ShowObjects(mc);
     			}
     	});
         
        	return new HBox(btnBottom1, btnBottom2, btnBottom3, btnBottom4, btnBottom5, btnBottom6, btnBottom7);
    }
    
    //Mouse event which shoots laser at mouse click's height
    private void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   MyArena.addLaser(MyArena,e.getY());					// update panel
	    	           }
	    	       });
	}
    

    
	public void start(Stage stagePrimary) throws Exception {
		stagePrimary.setTitle("Drone Simulation BorderPane Demonstrator");

	    BorderPane bp = new BorderPane();			// create border pane

	  

	    Group root = new Group();					// create group
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    											// and canvas to draw in
	    root.getChildren().add( canvas );// and add canvas to group
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), canvasSize, canvasSize);
	    
				
	    
	    //setMouseEvents(canvas);						
	    bp.setCenter(root);							// put group in centre pane

	    rtPane = new VBox();	
	    BackgroundFill background_fill = new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY);
	    rtPane.setBackground(new Background(background_fill));
	    // set vBox for listing data
	    bp.setRight(rtPane);						// put in right pane
	    Label label = new Label(MyArena.toString());
		rtPane.getChildren().clear();
		label.setTextFill(Color.web("#FFFFFF"));
		rtPane.getChildren().add(label);

	    bp.setBottom(setButtons());					/// add button to bottom
	    setMouseEvents(canvas);	
	    
	    bp.setTop(setMenu()); //add menu to top of borderpane

 	    Scene scene = new Scene(bp, canvasSize*1.5, canvasSize*1.2);
	    								// create scene so bigger than canvas, 
 	    
 	    //Player movement handlers
	    scene.addEventHandler(KeyEvent.ANY, (key) -> {
	        if(key.getCode()==KeyCode.W) {
	        	if(MyArena.player != null) {
	        		MyArena.player.up();
	        	}
	            }
	        if(key.getCode()==KeyCode.A) {
	        	if(MyArena.player != null) {
	        		MyArena.player.left();
	        	}
	            }
	        if(key.getCode()==KeyCode.S) {
	        	if(MyArena.player != null) {
	        		MyArena.player.down();
	        	}
	            }
	        if(key.getCode()==KeyCode.D) {
	        	if(MyArena.player != null) {
	        		MyArena.player.right();
	        	}
	           }
	    });
	    
	    stagePrimary.setScene(scene);
	    
	    stagePrimary.show();
	  
	    
	}

	public static void main(String[] args) {
	    Application.launch(args);
	}
	
	//Animation subclass which does the animation
	private class MyTimer extends AnimationTimer{
		private long prevtime = 0;
		
		@Override
		public void handle(long now) {
			long dt = now - prevtime;
			if(dt > 1) {
		        prevtime = now;
		       // move every drone
			   MyArena.moveAllObjects();
			   //Draw the objects
			   MyArena.ShowObjects(mc);;
			   MyArena.checkHealth();
			   // right pane with object information
			   Label label = new Label(MyArena.toString());
			   rtPane.getChildren().clear();
			   label.setTextFill(Color.web("#FFFFFF"));
			   rtPane.getChildren().add(label);
			}
			
				
				
			
			
		}
	}
}
