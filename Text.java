import java.awt.*;
import java.awt.event.*; 
import java.util.*;
import javax.swing.*; 

public class Text extends Rectangle {
	
	static int GAME_WIDTH; 
	static int GAME_HEIGHT; 
	int player1; 
	int player2; 
	
	Text(int GAME_WIDTH, int GAME_HEIGHT)
	{
		Text.GAME_WIDTH = GAME_WIDTH; 
		Text.GAME_HEIGHT = GAME_HEIGHT; 
		
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("Times New Roman", Font.BOLD, 60));
		g.drawString(String.valueOf(player1 / 10) + String.valueOf(player1 % 10), (GAME_WIDTH / 2) - 85, 50);
		g.drawString(String.valueOf(player2 / 10) + String.valueOf(player2 % 10), (GAME_WIDTH / 2) + 20, 50);
		g.setColor(Color.WHITE);
		g.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);
		
	}

}
