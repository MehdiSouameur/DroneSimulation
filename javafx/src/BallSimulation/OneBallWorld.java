/**
 * 
 */
package BallSimulation;

/**
 * @author shsmchlr
 *
 */
public class OneBallWorld {
	private double ballX, ballY, ballRad, ballAngle, ballSpeed;
		// position and size of ball + angle traveling and speed
	
	public OneBallWorld() {
		ballX = 100;
		ballY = 180;
		ballRad = 100;
		ballAngle = 45;
		ballSpeed = 2;
	}
	/**
	 * function to check where ball is, and adjust angle if is hitting side of canvas
	 * @param xSize		max x size of arena
	 * @param ySize
	 */
	public void checkBall(MyCanvas mc) {
		if (ballX < ballRad || ballX > mc.getXCanvasSize() - ballRad) ballAngle = 180 - ballAngle;
			// if ball hit (tried to go through) left or right walls, set mirror angle, being 180-angle
		if (ballY < ballRad || ballY > mc.getYCanvasSize() - ballRad) ballAngle = -ballAngle;
			// if ball hit (tried to go through) top or bottom walls, set mirror angle, being -angle
	}
	/**
	 * move the ball according to speed and angle
	 */
	public void adjustBall() {
		double radAngle = ballAngle*Math.PI/180;	// put angle in radians
		ballX += ballSpeed * Math.cos(radAngle);		// new X position
		ballY += ballSpeed * Math.sin(radAngle);		// new Y position
	}
	/**
	 * update the world -
	 * @param mc	canvas in which drawn
	 */
	public void updateWorld(MyCanvas mc) {
		checkBall(mc);			// check if about to hit side of wall, adjust angle if so
		adjustBall();			// calculate new position
	}
	/**
	 * draw the ball into the canvas mc
	 * @param mc
	 */
	public void drawWorld(MyCanvas mc) {
		mc.clearCanvas();
		mc.showCircle(ballX, ballY, ballRad, 'g');					// call interface's routine
	}
	/**
	 * set ball to position x,y
	 * @param x
	 * @param y
	 */
	public void setXY(double x, double y) {
		ballX = x;
		ballY = y;
	}
	/**
	 * return string describing ball and its position
	 */
	public String toString() {
		return "Ball at " + Math.round(ballX) + ", " + Math.round(ballY);
	}

}
