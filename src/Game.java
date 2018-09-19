import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Game extends JFrame implements ActionListener{
	
	private int delay = 5;
	private Timer timer;
	private static boolean newStateIsSet;
	
	
	private static State state;
	
	private Stack<JPanel> gameStates;
	
	/**
	 * construct the main game
	 */
	public Game(){
		this.setTitle("Flappy Bird");
		this.setPreferredSize(new Dimension(FlappyBird.WIDTH, FlappyBird.HEIGHT));
		this.setResizable(false);
		
		//store the panel of each state in stack
		gameStates = new Stack<>();
		
		//game start at the main menu state
		gameStates.push(new Menu());
		this.add(gameStates.peek());
		
		//use timer to check if new state is set
		timer = new Timer(delay,this);
		timer.start();
	}
	
	/**
	 * set state of game to new state
	 * @param state state of game to set to
	 */
	static void setState(State state){
		Game.state = state;
		newStateIsSet = true;
	}
		
	@Override
	public void actionPerformed(ActionEvent e){
		//check if new state is set every delay time of timer
		//check which state is set to then remove old state from stack
		//and add new state to the stack
		if(newStateIsSet){
			this.remove(gameStates.pop());
			if(state == State.PLAY){	
				gameStates.push(new FlappyBird());
			}
			else if(state == State.GAME_OVER){
				gameStates.push(new GameOver());
			}
			else{
				gameStates.push(new Menu());
			}
			this.add(gameStates.peek());
			newStateIsSet = false;
			gameStates.peek().requestFocus();

			//after add new JPanel to JFrame, need to revalidate 
			this.revalidate();
		}
	}
	
	public static void main(String[] args){
		//create the game
		Game game = new Game();
		
		
		game.pack();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		game.setVisible(true);

	}	
}
