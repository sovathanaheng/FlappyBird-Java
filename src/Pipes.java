import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Pipes {
	private ArrayList<Pipe> pipes;
	private int score;
	
	/**
	 * construct the pipes object that will contain
	 * 3 pipe objects
	 */
	public Pipes(){
		pipes = new ArrayList<>();
		
		for(int i = 0; i<3; i++){	
			pipes.add(new Pipe());
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < pipes.size(); i++){
			pipes.get(i).render(g);
		}
	}
	
	public void tick(){
		for(int i = 0; i < pipes.size(); i++){
			pipes.get(i).tick();
		}
	}
	
	public boolean collided(Bird bird){
				
		for(int i = 0; i < pipes.size(); i++){
    		if (pipes.get(i).collides(bird.getBounds())){
    			return true;
    		}
    	}
		
		return false;
	}
	
	public int getScore(){
		score = 0;
		for(int i = 0; i < pipes.size(); i++){
    		score += pipes.get(i).getScore();
    	}
		return score;
	}
	
	public void stop(){
		for(int i = 0; i < pipes.size(); i++){
			pipes.get(i).stop();
		}
	}
}
