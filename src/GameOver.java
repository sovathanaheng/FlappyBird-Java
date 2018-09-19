import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class GameOver extends JPanel implements MouseListener{
	
	ImageIcon backgroundIcon;
	Image background;
	
	ImageIcon gameOverIcon;
	Image gameOverImg;
	
	ImageIcon mainMenuIcon;
	Image mainMenuImg;

	ImageIcon playBtnIcon;
	Image playBtnImg;
	
	ImageIcon menuIcon;
	Image menuImg;
	
	ImageIcon scoreBoardIcon;
	Image scoreBoardImg;
	
	ImageIcon newHighScoreIcon;
	Image newHighScoreImg;
	
	ImageIcon groundIcon;
	Image groundImg;
	
	ImageIcon birdIcon;
	Image birdImg;
	
	int menuX;
	int menuY;
	int gameOverX;
	int gameOverY;
	int playBtnX;
	int playBtnY;
	int scoreBoardX;
	int scoreBoardY;
	
	/**
	 * construct the game over state
	 */
	public GameOver(){
		loadImage();
		
		//compute the appropriate location for each image
		menuX = 10;
		menuY = 10;
		gameOverX = FlappyBird.WIDTH/2 - gameOverIcon.getIconWidth()/2;
		gameOverY = (int) (FlappyBird.HEIGHT * 0.2);
		playBtnX = FlappyBird.WIDTH/2 - playBtnIcon.getIconWidth()/2;
		playBtnY = (int) (FlappyBird.HEIGHT * 0.7);
		scoreBoardX = FlappyBird.WIDTH/2 - scoreBoardIcon.getIconWidth()/2;
		scoreBoardY = FlappyBird.HEIGHT/2 - scoreBoardIcon.getIconHeight()/2;
				
		this.addMouseListener(this);
	}
	
	/**
	 * load all the images needed to draw to the JPanel
	 */
	private void loadImage(){
		gameOverIcon = new ImageIcon("gameOver.png");
		gameOverImg = gameOverIcon.getImage();
		
		backgroundIcon = new ImageIcon("background.png");
		background = backgroundIcon.getImage();
		
		playBtnIcon = new ImageIcon("play.png");
		playBtnImg = playBtnIcon.getImage();
		
		menuIcon = new ImageIcon("menu.png");
		menuImg = menuIcon.getImage();
		
		scoreBoardIcon = new ImageIcon("scoreBoard.png");
		scoreBoardImg = scoreBoardIcon.getImage();
		
		newHighScoreIcon = new ImageIcon("new.png");
		newHighScoreImg = newHighScoreIcon.getImage();
		
		groundIcon = new ImageIcon("ground.png");
		groundImg = groundIcon.getImage();
		
		birdIcon = new ImageIcon("birdDown.png");
		birdImg = birdIcon.getImage();
	}
	
	/**
	 * paint the component by calling doDrawing method
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

	/**
	 * paint all the images and score to the screen
	 * @param g graphics
	 */
    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        //draw all the images to screen
		g2d.drawImage(background,0,0, this);
		g2d.drawImage(groundImg, 0, FlappyBird.GROUND_LEVEL, this);
		g2d.drawImage(birdImg, FlappyBird.BIRD_X, FlappyBird.GROUND_LEVEL - birdIcon.getIconHeight(),this);
		g2d.drawImage(menuImg, menuX, menuY, this);
		g2d.drawImage(gameOverImg, gameOverX, gameOverY, this);
		g2d.drawImage(playBtnImg, playBtnX, playBtnY, this);
		g2d.drawImage(scoreBoardImg, scoreBoardX, scoreBoardY, this);	
		
		//draw the current score and high score to screen
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Sans Serif", Font.BOLD, 65) );
		g2d.drawString(String.valueOf(FlappyBird.getCurrentScore()), scoreBoardX + 300, scoreBoardY + 92);
		g2d.drawString(String.valueOf(FlappyBird.getHighScore()), scoreBoardX + 300, scoreBoardY + 200);
		
		//check if there is new high score, print the new-high-score label
		if(FlappyBird.hasNewHighScore()){
			g2d.drawImage(newHighScoreImg, scoreBoardX + 170, scoreBoardY + 165, this);
		}
    }

 
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		//if menu button is clicked
		if(x >= menuX && x <= menuX + menuIcon.getIconWidth()){
			if(y >= menuY && y <= menuY + menuIcon.getIconHeight()){
				//go to the menu
				Game.setState(State.MENU);
			}
		}
		//if play button is clicked
		else if(x >= playBtnX && x <= playBtnX + playBtnIcon.getIconWidth()){
			if(y >= playBtnY && y <= playBtnY + playBtnIcon.getIconHeight()){		
				//play new game
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
