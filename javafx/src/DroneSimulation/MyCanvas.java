package DroneSimulation;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

/**
 * @author shsmchlr
 *  Class to handle a canvas, used by different GUIs
 */
public class MyCanvas {
	int xCanvasSize = 512;				// constants for relevant sizes, default values set
	int yCanvasSize = 512;
    GraphicsContext gc; 

    //Constructor
    public MyCanvas(GraphicsContext g, int xcs, int ycs) {
    	gc = g;
    	xCanvasSize = xcs;
    	yCanvasSize = ycs;
    }
    
//Getters
    public int getXCanvasSize() {
    	return xCanvasSize;
    }
    public int getYCanvasSize() {
    	return yCanvasSize;
    }
    
    //Clear the canvas
    public void clearCanvas() {
		gc.clearRect(0,  0,  xCanvasSize,  yCanvasSize);		// clear canvas
    }
    
	//Draws image at x,y position with size sz
	public void drawImage (Image i, double x, double y, double sz) {
			// to draw centred at x,y, give top left position and x,y size
			// sizes/position in range 0.. canvassize 
		gc.drawImage(i, x - sz/2, y - sz/2, sz, sz);
	}
}

	
	
