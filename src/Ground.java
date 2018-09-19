import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Ground {
	
	private Image ground1;
	private Image ground2;
	private ImageIcon groundIcon;
	
	private int x1;
	private int x2;
	private int y;
	private int width;
	
	private int velocity;
	
	/**
	 * construct the ground object and set the position and velocity
	 */
	public Ground(){
		loadImage();
		width = ground1.getWidth(null);
		
		//set position and velocity for the two ground images
		x1 = 0;
		x2 = x1 + width;
		y = FlappyBird.GROUND_LEVEL;
		velocity = - 5;
	}
	
	/**
	 * load the two images for the ground
	 */
	private void loadImage(){
		groundIcon = new ImageIcon("ground.png");
        ground1 = groundIcon.getImage();
        ground2 = groundIcon.getImage();
	}
	
	/**
	 * draw the image to screen
	 * @param g
	 */
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(ground1, x1, y,  null);
		g2d.drawImage(ground2, x2, y,  null);	
	}

	/**
	 * updating the position of the images
	 * ground object need to images to move across the screen
	 * both images are next to each other, if one image moved
	 * out of screen, reposition it the right of the other image
	 */
	public void tick() {
		
		if(x1 < 0 - width){
			x1 = x2 + width;
		}
		if(x2 < 0 - width){
			x2 = x1 + width;
		}
		
		x1 += velocity;
		x2 += velocity;
	}
	
	/**
	 * 
	 * @return return the height of the ground image
	 */
	public int getHeight(){
		return ground1.getHeight(null);
	}
	
	/**
	 * stop the motion of the ground
	 */
	public void stop(){
		velocity = 0;
	}
	
}
