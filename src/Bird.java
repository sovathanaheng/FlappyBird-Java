import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;


public class Bird {
	private Image bird;
	
	private int x;
	private int y;
	
	private double velocity;
	private double gravity;
	private double lift;
	
	private Rectangle bounds;
	
	private ImageIcon birdUp;
	private ImageIcon birdDown;
	
	private boolean isDead;
	
	/**
	 * construct the bird object and set the position
	 * velocity, and gravity
	 * @param x position x of the bird to set to
	 * @param y position y of the bird to set to
	 */
	public Bird(int x, int y) {
		this.x = x;
		this.y = y;		
		velocity = 0.5; 
		gravity = 0.5;  
		lift = -120;  
		
		loadImage();
		
		//use rectangle as boundary to check collision
		bounds = new Rectangle(x, y, bird.getWidth(null), bird.getHeight(null));		
	}
	
	/**
	 * load all images file needed
	 */
	private void loadImage(){
		birdUp = new ImageIcon("birdUp.png");
		birdDown = new ImageIcon("birdDown.png");
		bird = birdUp.getImage();
	}
	
	/**
	 * 
	 * @return  y position of bird
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 
	 * @return the height of bird image
	 */
	public int getHeight(){
		return bird.getHeight(null);
	}
	
	/**
	 * update the velocity, position, and image of the bird
	 * velocity is depend on gravity
	 */
	public void tick() {
		//when bird fall down, after velocity is more than 9, 
		//set the image of the bird to the rotated image of the bird
		if(velocity > 9){
			bird = birdDown.getImage();
		}
		
		//updating the velocity and y position of bird
		velocity += gravity;
		y += velocity;

		//make sure bird never fall below the ground
		if(y > FlappyBird.GROUND_LEVEL - bird.getHeight(null)){
			y = FlappyBird.GROUND_LEVEL - bird.getHeight(null);
			velocity = 0;
		}
		//make sure bird never go above the ceiling of screen
		else if(y < 0){
			y = 0;
			velocity = 0;
		}
		
		//update the rectangle position to the bird position
		bounds.setLocation(x, y);
	}

	/**
	 * draw the image of the bird to screen
	 * @param g
	 */
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bird, x, y,  null);
	}
	
	/**
	 * bird fly up the amount of lift
	 */
	public void up(){
		if(!isDead){
			bird = birdUp.getImage();
			velocity = 0;
			y += lift;
		}	
	}
	
	/**
	 * 
	 * @return true when the bird fall down to the ground
	 */
	public boolean isOnGround(){
		if(y >= FlappyBird.GROUND_LEVEL - bird.getHeight(null)){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return the rectangle for purpose of collision detection
	 */
	public Rectangle getBounds(){
		return bounds;
	}
	
	/**
	 * set boolean isDead to true
	 */
	public void setDead(){
		isDead = true;
	}
	
}
