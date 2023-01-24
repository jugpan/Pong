import java.awt.*;
import java.awt.event.*; 
import java.util.*;
import javax.swing.*; 

public class Ball extends Rectangle{
	
	Random random; 
	int xSpeed; 
	int ySpeed;  
	
	Ball(int x, int y, int width, int height)
	{
		super(x, y, width, height); 
		random = new Random(); 
		int randomX = random.nextInt(2); 
		if(randomX == 0)
		{
			randomX -= 2; 
		}
		setXDirection(randomX);
		
		int randomY = random.nextInt(2); 
		if(randomY == 0)
		{
			randomY -= 2; 
		}
		setYDirection(randomY); 
		
	}
	
	public void setXDirection(int randomXDirection)
	{
		xSpeed = randomXDirection; 
		
	}
	
	public void setYDirection(int randomYDirection)
	{
		ySpeed = randomYDirection; 
		
	}
	
	public void move()
	{
		x += xSpeed; 
		y += ySpeed; 
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.white);
		g.fillOval(x, y, height, width); 
	}

}
