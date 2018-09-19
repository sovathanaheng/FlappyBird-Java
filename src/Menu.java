import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;





public class Menu extends JPanel implements MouseListener{
	

	ImageIcon easyIcon;
	ImageIcon mediumIcon;
	ImageIcon hardIcon;
	
	Image easyImg;
	Image mediumImg;
	Image hardImg;
	
	ImageIcon backgroundIcon;
	ImageIcon titleIcon;
	
	Image background;
	Image titleImg;
	
	int x;
	int y1;
	int y2;
	int y3;
	int space;
	
	int titleX;
	int titleY;
	
	/**
	 * construct the main menu state
	 */
	public Menu(){
		loadImage();
		
		//set the space between each button
		space = (int) (easyIcon.getIconHeight() * 1.5);
		
		//set the position for each button
		x = FlappyBird.WIDTH/2 - easyIcon.getIconWidth()/2;
		y1 = FlappyBird.HEIGHT/3;
		y2 = y1 + space;
		y3 = y2 + space;
		
		//set the position of the title
		titleX = FlappyBird.WIDTH/2 - titleIcon.getIconWidth()/2;
		titleY = FlappyBird.HEIGHT/10;
				
		this.addMouseListener(this);
	}
	
	/**
	 * load all image files needed
	 */
	private void loadImage(){
		backgroundIcon = new ImageIcon("background.png");
		background = backgroundIcon.getImage();
		
		titleIcon = new ImageIcon("title.png");
		titleImg = titleIcon.getImage();
		
		easyIcon = new ImageIcon("easy.png");
		mediumIcon = new ImageIcon("medium.png");
		hardIcon = new ImageIcon("hard.png");
		
		easyImg = easyIcon.getImage();
		mediumImg = mediumIcon.getImage();
		hardImg = hardIcon.getImage();	
	}
	
	/**
	 * paint component by calling doDrawing method
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

	/**
	 * draw all the images to the screen
	 * @param g
	 */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
          
        //draw background
		g2d.drawImage(background,0,0, this);	
		g2d.drawImage(titleImg,titleX,titleY,this);
				
		//draw all buttons
		g2d.drawImage(easyImg, x, y1, this);
		g2d.drawImage(mediumImg, x, y2, this);
		g2d.drawImage(hardImg, x, y3, this);
    }

    //mouseListener
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//if buttons is clicked, set new level of difficulty accordingly
		//and set new play state
		if (x >= this.x && x <= this.x+easyIcon.getIconWidth()){
			
			//if Easy button is clicked
			if(y >= y1 && y <= y1+easyIcon.getIconHeight()){
				FlappyBird.setLevel(Level.EASY);
				Game.setState(State.PLAY);
			}
			
			//if Medium button is clicked
			else if(y >= y2 && y <= y2+easyIcon.getIconHeight()){
				FlappyBird.setLevel(Level.MEDIUM);
				Game.setState(State.PLAY);
			}
			//if Hard button is clicked
			else if(y >= y3 && y <= y3+easyIcon.getIconHeight()){
				FlappyBird.setLevel(Level.HARD);
				Game.setState(State.PLAY);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//not use
	}
	@Override
	public void mouseExited(MouseEvent e) {
		//not use
	}
	@Override
	public void mousePressed(MouseEvent e) {
		//not use
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//not use
	}

	
}
