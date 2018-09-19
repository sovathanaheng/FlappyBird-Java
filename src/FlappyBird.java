import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class FlappyBird extends JPanel implements ActionListener {

	public static final int WIDTH = 700;
	public static final int HEIGHT = 1000;
	private static final int DELAY = 5;
	 
	public static final int BIRD_X = 100;
	public static final int BIRD_Y = 400;
	public static final int GROUND_LEVEL = 820;
	
	private Timer timer;

	private Bird bird;
	private Ground ground;
	
	private Image backgroundImg;
	
	
	private int score;
	
	private Pipes pipes;
	
	private Boolean birdIsDead;

	private static int currentScore;
	private static int highScore;
	private static Level level;
	private static boolean hasNewHighScore;
	
	/**
	 * constructor
	 * create new game state
	 */
	public FlappyBird(){	

		ground = new Ground();
		pipes = new Pipes();
		bird = new Bird(BIRD_X, BIRD_Y);
		
		//listen to the space key
		this.requestFocus();
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		this.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_SPACE){
					bird.up();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				//not use
			}  
			@Override
			public void keyTyped(KeyEvent e) {
				//not use
			}		
		});
		
		birdIsDead = false;
		hasNewHighScore = false;
		
		timer = new Timer(DELAY, this);
		timer.start();	
	}
		
	/**
	 * override the paintComponent method of JPanel 
	 * to paint more images
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

	/**
	 * paint all images to the screen
	 * @param g
	 */
    private void doDrawing(Graphics g) {
        //cast graphics to graphics2D
        Graphics2D g2d = (Graphics2D) g;
        
        //read background image
        try {
			backgroundImg = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //paint background
		g2d.drawImage(backgroundImg,0,0, this);	
		
		//render all game objects
		pipes.render(g2d);
		bird.render(g2d);
		ground.render(g2d);
		
		//draw the score
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Sans Serif", Font.BOLD, 70) );
		g2d.drawString(String.valueOf(score), WIDTH/2, 100);

    }

    /**
     * call collision detection method of pipes class to check
     * if bird collided with the pipe
     * return true if bird collided with the pipes
     * @return true if bird collided with ground or ceiling
     * return false if bird has not collided with anything
     */
    private boolean birdIsCollided(){
    	//check if bird collide with the ground or ceiling
    	if(bird.getY() <= 0 || bird.getY() >= GROUND_LEVEL - bird.getHeight()){
    		return true; 
    	}
    	//check if bird collide with pipe
    	return pipes.collided(bird);
    }
    
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//timer called this method every 5 millisecond  
		
		//call all the object to update themselves
		bird.tick();
		ground.tick();
		pipes.tick();
		
		if(birdIsCollided()){
			
			birdIsDead = true;
			
			//set the dead flag of the bird to true so that pressing space bar
			//does not make the bird fly up anymore after it is dead
			bird.setDead();
			
			//reset the space of the pipe to zero after game over
			Pipe.resetSpace();
			
			//update the high score after game over
			updateHighScore();
			
			//stop the motion of the pipes and ground
			pipes.stop();
			ground.stop();
		
			//after the bird fell to the ground when it is dead
			if(bird.isOnGround()){
				timer.stop();
				//set the game state to the game over state
				Game.setState(State.GAME_OVER);
			}
		}
		//keep updating the current score 
		updateScore();
		repaint();

		
	}
		
	/**
	 * update the current score while bird is not dead yet
	 * get the score from the pipes class
	 */
	private void updateScore(){
		score = pipes.getScore();
	}
	
	/**
	 * update high score
	 * if current score beat high score, set new high score 
	 * to current score
	 */
	private void updateHighScore(){
		//get the current score
		currentScore = pipes.getScore();
		int lineToWrite = 0;
		List<Integer> highScores = new ArrayList<>();
		
		//read the 3 high scores for each level to the arraylist
		//high scores for all 3 levels are stored in one single file
		try(Scanner in = new Scanner(new File("highScore.txt"));){
			while(in.hasNext()){
					highScores.add(Integer.valueOf(in.next()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//get high score for the current level of difficulty
		//first line in file is high score for easy level
		//the second line is medium and the last is hard
		if(level == Level.EASY ){
			highScore = highScores.get(0);
			lineToWrite = 0;
		}
		else if(level == Level.MEDIUM){
			highScore = highScores.get(1);
			lineToWrite = 1;
		}
		else if(level == Level.HARD){
			highScore = highScores.get(2);
			lineToWrite = 2;
		} 
		
		//check if current score beat the old high score
		if(currentScore > highScore){
			highScore = currentScore;
			hasNewHighScore = true;
		}
		
		//write the old high scores back to the file
		try(PrintWriter out = new PrintWriter("highScore.txt");){
			for(int i=0; i<3; i++){
				//write the new high score to the file at appropriate line
				if(i == lineToWrite){
					out.println(highScore);
				}
				else{
					out.println(highScores.get(i));
				}
			}
		} catch (FileNotFoundException e) {		
			e.printStackTrace();
		}
	}
		
	/**
	 *
	 * @return return the current score
	 */
	public static int getCurrentScore(){
		return currentScore;
	}
	
	/**
	 * 
	 * @return return high score for current level
	 */
	public static int getHighScore(){
		return highScore;
	}
	
	/**
	 * 
	 * @return true if new high score is set, false otherwise
	 */
	public static boolean hasNewHighScore(){
		return hasNewHighScore;
	}
	
	/**
	 * set level of difficulty of the game
	 * @param level level of difficulty to set to
	 */
	public static void setLevel(Level level){
		FlappyBird.level = level;
	}
	
	/**
	 * 
	 * @return the current level of difficulty
	 */
	public static Level getLevel(){
		return FlappyBird.level;
	}
	
	
}
