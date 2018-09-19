import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;




public class Pipe {
	private Image topPipe;
	private Image bottomPipe;

	private static int space = 0;
	
	private int x;
	private int topY;
	private int botY;
	private int width;
	
	private double velX;
	private double velY;
	
	private static final int GAP = 250;
	
	private ImageIcon topPipeIcon;
	private ImageIcon botPipeIcon;
	
	private Rectangle botBounds;
	private Rectangle topBounds;
	
	private int score;
	boolean scoreAdded;
		
	private Level level;
	
	/**
	 * construct the pipe object and set its initial 
	 * position and velocity
	 */
	public Pipe() {
		
		//compute initial position x and y for top pipes and bottom pipes
		x = computeX();
		topY = computeTopY();
		botY = computeBotY();
		
		setVelocity();
		loadImage();

		width = topPipe.getWidth(null);
		
		//use Rectangle to check boundary for collision detection
		topBounds = new Rectangle(x, topY, topPipe.getWidth(null), topPipe.getHeight(null));
		botBounds = new Rectangle(x, botY, bottomPipe.getWidth(null), bottomPipe.getHeight(null));
		
	}

	/**
	 * @return a random y position for top pipe
	 */
	private int computeTopY(){
		Random r = new Random();
		return r.nextInt(400) - 700;	
	}
	
	/**
	 *compute the y position of bottom pipe depending 
	 *on the y position of top pipe,
	 * @return y position of bottom pipe
	 */
	private int computeBotY(){
		return topY + 800 + GAP;  
	}
	
	
	/**
	 * compute the x position of each pipe
	 * each pipe position x is computed to the left of the frame
	 * each pipe is separated by an amount of space
	 * @return
	 */
	private int computeX(){
		int x = FlappyBird.WIDTH + space;
		space += 400;
		return x;
	}
	
	/**
	 * loading the image files
	 */
	private void loadImage(){
		topPipeIcon = new ImageIcon("topPipe.png");
		botPipeIcon = new ImageIcon("bottomPipe.png");
        topPipe = topPipeIcon.getImage();
		bottomPipe = botPipeIcon.getImage();		
	}
	
	/**
	 * updating the states of the pipe
	 */
	public void tick() {
		
		//update the position of pipe
		x += velX;
		topY += velY; 
		botY += velY;
		
		//pipe passed the bird, increment the score
		if(x + width < FlappyBird.BIRD_X && !scoreAdded){ 
			score++;
			scoreAdded = true;
		}
		
		//reposition the pipe when it moved out of screen
		if(x < width * -1){
			//keep constant space between each pipe after repositioned
			x = space - width; 
			topY = computeTopY();
			botY = computeBotY();
			scoreAdded = false;
		}
		
		updateVelY();
		
		//update position of rectangle every time pipe position is updated
		topBounds.setLocation(x, topY);
		botBounds.setLocation(x, botY);
	}
	
	/**
	 * set x velocity and y velocity of pipe
	 * y velocity of pipe depends on the level of difficulty of game
	 */
	private void setVelocity(){
		velX = -5;
		level = FlappyBird.getLevel();
		if(level == Level.EASY){
			velY = 0;
		}else if(level == Level.MEDIUM){
			velY = -1;
		}else{
			velY = -5;
		}
	}
	
	/**
	 * Update Y velocity of pipe
	 * pipe move down when top pipe almost reach ceiling
	 * pipe move up when top pipe almost reach the ground
	 */
	private void updateVelY(){
		if(topY + topPipeIcon.getIconHeight() <= FlappyBird.HEIGHT  * 0.1
				|| topY + topPipeIcon.getIconHeight() >= FlappyBird.HEIGHT * 0.5){
			velY = velY * -1;
		}
	}
	
	/**
	 * rendering the image to screen
	 * @param g graphics
	 */
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(topPipe, x, topY, null);
		g2d.drawImage(bottomPipe, x, botY, null);		
	}
	
	/**
	 * checking collision with other rectangle
	 * @param other other rectangle
	 * @return true if collided, false otherwise
	 */
	public boolean collides(Rectangle other){
		return other.intersects(topBounds) || other.intersects(botBounds);
	}
	
	/**
	 * stop the pipe from moving
	 */
	public void stop(){
		velX = 0; 
		velY = 0;
	}
	
	/**
	 * @return return the x position of the pipe
	 */
	public int getPosX(){
		return x;
	}
	
	/**
	 * each pipe keep track of it own score, 
	 * @return the score 
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * reset space to 0
	 * should be called every time the game is over
	 */
	public static void resetSpace(){
		space = 0;
	}	
}
