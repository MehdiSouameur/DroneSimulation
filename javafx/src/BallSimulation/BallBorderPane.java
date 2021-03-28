/**
 * 
 */
package BallSimulation;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author shsmchlr
 *
 */
public class BallBorderPane extends Application {
	private int xCanvasSize = 400, yCanvasSize = 500;	// size of canvas
    private MyCanvas mc; 								// canvas in which system drawn
    private OneBallWorld obw;							// world with a bouncing ball
    private boolean animationOn = false;						// are we animating?
    private VBox rtPane;								// pane in which info on ball listed
    private Random rgen = new Random();			// random number generator

	 /**
	  * Function to show a message, 
	  * @param TStr		title of message block
	  * @param CStr		content of message
	  */
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
		 showMessage("Help", "A program that shows a ball that moves and can start/stop animation." + "\n" +
	                         "You can click on the canvas to put the ball there" + "\n" +
                   			 "You can press a button to place the ball randomly");
	 }

	/**
	 * Function to set up the menu
	 */
	public MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();		// create menu

		Menu mHelp = new Menu("Help");			// have entry for help
					// then add sub menus for About and Help
					// add the item and then the action to perform
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
	           @Override
	           public void handle(ActionEvent actionEvent) {
	            	showAbout();				// show the about message
	           }	
		});
		MenuItem mfHelp = new MenuItem("Help");
		mfHelp.setOnAction(new EventHandler<ActionEvent>() {
	           @Override
	           public void handle(ActionEvent actionEvent) {
	            	showHelp();				// show the about message
	           }	
		});
		mHelp.getItems().addAll(mAbout, mfHelp); 	// add submenu to Help
			
				// now add File menu, which here only has Exit
		Menu mFile = new Menu("File");				// create File Menu
		MenuItem mExit = new MenuItem("Exit");		// and Exit submenu
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {		// and add handler
		        System.exit(0);						// quit program
		    }
		});
		mFile.getItems().addAll(mExit);	// add Exit submenu to File
			
		menuBar.getMenus().addAll(mFile, mHelp);	// menu has File and Help
			
		return menuBar;					// return the menu, so can be added
	}
	
	/**
	 * show where ball is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
				// now create label
		Label l = new Label(obw.toString());
		rtPane.getChildren().add(l);				// add label to pane	
	}

	/**
		 * set up the mouse event handler, so when click on canvas, put ball there
		 * @param canvas
		 */
		private void setMouseEvents (Canvas canvas) {
		       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
		    	       new EventHandler<MouseEvent>() {
		    	           @Override
		    	           public void handle(MouseEvent e) {
		    	        	   obw.setXY(e.getX(), e.getY());	// put ball at e.x, e.y
		    	        	   obw.drawWorld(mc);				// draw world with ball where mouse clicked
		    	        	   drawStatus();					// update panel
		    	           }
		    	       });
		}

		/**
		 * set up the buttons and return so can add to borderpane
		 * @return
		 */
	    private HBox setButtons() {
	    			// create button
	    	Button btnBottom = new Button("Random Ball");
	    			// now add handler
	    	btnBottom.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			obw.setXY(rgen.nextDouble()* xCanvasSize, rgen.nextDouble()* yCanvasSize);
	    			obw.drawWorld(mc);
	    				// and its action to draw earth at random angle
	    			drawStatus();
	    		}
	    	});
	    	Button btnAnimOn = new Button("Start");
			// now add handler
	    	btnAnimOn.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			animationOn = true;
	    		}
	    	});
	    	
	    	Button btnAnimOff = new Button("Stop");
			// now add handler
	    	btnAnimOff.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			animationOn = false;
	    		}
	    	});
	    	
	    	return new HBox(btnBottom, new Label(" Animation: "), btnAnimOn, btnAnimOff);
	    }
		
    
	@Override
	public void start(Stage stagePrimary) throws Exception {
		// TODO Auto-generated method stub
		stagePrimary.setTitle("Ball BorderPane Demonstrator");

	    BorderPane bp = new BorderPane();			// create border pane

	    bp.setTop(setMenu());						// create menu, add to top

	    Group root = new Group();					// create group
	    Canvas canvas = new Canvas( xCanvasSize, yCanvasSize );
	    											// and canvas to draw in
	    root.getChildren().add( canvas );			// and add canvas to group
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), xCanvasSize, yCanvasSize);
	    mc.setFillColour(Color.GREEN);
					// create MyCanvas passing context on canvas onto which images put
	    obw = new OneBallWorld();					// create object for world with a ball
	    
	    bp.setCenter(root);							// put group in centre pane

	    rtPane = new VBox();						// set vBox for listing data
	    bp.setRight(rtPane);						// put in right pane

	    bp.setBottom(setButtons());					/// add button to bottom
	    setMouseEvents(canvas);						// set mouse handler

		// for animation, note start time
	    new AnimationTimer()			// create timer
    	{
    		public void handle(long currentNanoTime) {
    				// define handle for what do at this time
    			if (animationOn) {
	    			obw.updateWorld(mc);			// find new position of ball 
	    			obw.drawWorld(mc);				// draw ball in new position
	    			drawStatus();
   			}	
    		}
    	}.start();					// start it

	    Scene scene = new Scene(bp, xCanvasSize*1.5, yCanvasSize*1.2);
	    								// create scene so bigger than canvas, 

	    stagePrimary.setScene(scene);
	    stagePrimary.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);			// launch the GUI

	}

}
