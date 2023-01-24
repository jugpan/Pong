import java.awt.*;
import java.awt.event.*; 
import java.util.*;
import javax.swing.*; 

public class GamePanel extends JPanel implements Runnable {
	
	static final int GAME_WIDTH = 1000; 
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20; 
	static final int PADDLE_WIDTH = 25; 
	static final int PADDLE_HEIGHT = 100; 
	Thread gameThread; 
	Image image; 
	Graphics graphics; 
	Random random; 
	Paddles paddle1, paddle2; 
	Ball ball; 
	Text score; 
	
	GamePanel()
	{
		newPaddles(); 
		newBall(); 
		score = new Text(GAME_WIDTH, GAME_HEIGHT); 
		this.setFocusable(true);
		this.addKeyListener(new AL()); 
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this); 
		gameThread.start();
	}
	
	public void newBall()
	{
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
	}
	
	public void newPaddles()
	{
		paddle1 = new Paddles(0,(GAME_HEIGHT/2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1); 
		paddle2 = new Paddles(GAME_WIDTH - PADDLE_WIDTH,(GAME_HEIGHT/2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2); 

	}
	
	public void paint(Graphics g)
	{
		image = createImage(getWidth(), getHeight()); 
		graphics = image.getGraphics(); 
		draw(graphics); 
		g.drawImage(image, 0, 0, this); 
	}
	
	public void draw(Graphics g)
	{
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g); 
	}
	
	public void move()
	{
		paddle1.y += paddle1.ySpeed; 
		paddle2.y += paddle2.ySpeed;  
		ball.move(); 
	}
	
	public void checkCollision()
	{
		//ball off edges
		if(ball.y <= 0)
			ball.setYDirection(-ball.ySpeed);
		if(ball.y >= GAME_HEIGHT - BALL_DIAMETER)
			ball.setYDirection(-ball.ySpeed);
		//ball off paddles
		if(ball.intersects(paddle1))
		{
			ball.xSpeed = Math.abs(ball.xSpeed);
			ball.xSpeed++; 
			
			ball.setXDirection(ball.xSpeed);
			ball.setYDirection(ball.ySpeed);
		}
		
		if(ball.intersects(paddle2))
		{
			ball.xSpeed = Math.abs(ball.xSpeed);
			ball.xSpeed++; 
			
			ball.setXDirection(-ball.xSpeed);
			ball.setYDirection(-ball.ySpeed);
		}
		
		//stops paddles at edges
		if(paddle1.y <= 0)
		{
			paddle1.y = 0;
		}
		if(paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
		{
			paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT; 
		}
		if(paddle2.y <= 0)
		{
			paddle2.y = 0;
		}
		if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
		{
			paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT; 
		}
		
		//score system
		if(ball.x <= 0)
		{
			score.player2++; 
			newPaddles(); 
			newBall(); 
		}
		if(ball.x >= GAME_WIDTH - BALL_DIAMETER)
		{
			score.player1++; 
			newPaddles(); 
			newBall(); 
		}
		
	}
	
	public void run()
	{
		double timePerFrame = 1000000000.0 / 60.0; 
		double timePerUpdate = 1000000000.0 / 200.0;
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0; 
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true)
		{
			long currentTime = System.nanoTime(); 
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime; 
			
			if(deltaU >= 1)
			{
				move(); 
				checkCollision(); 
				updates++;
				deltaU--; 
			}
			
			if(deltaF >= 1)
			{
				repaint();
				deltaF--;
				frames++; 	
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000)
			{
				lastCheck = System.currentTimeMillis(); 
				System.out.println("PONG");
				frames = 0; 
				updates = 0;
			}
				
		}
		
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e)
		{
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e)
		{
			paddle1.keyReleased(e);
			paddle2.keyReleased(e); 
			
		}
	}

}
