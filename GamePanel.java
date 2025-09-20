package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN settings
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48 x 48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//FPS
	int FPS = 60;
	public int score = 0;

	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this,keyH);
	
	//set player default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	public AssetSetter aSetter = new AssetSetter(this);
	public SuperObject obj[] = new SuperObject[10];
	
	public void checkObjectCollision() {
	    // player rectangle (assumes player occupies one tileSize square)
	    Rectangle playerRect = new Rectangle(player.x, player.y, tileSize, tileSize);

	    for (int i = 0; i < obj.length; i++) {
	        if (obj[i] != null) {
	            Rectangle objRect = new Rectangle(obj[i].worldX, obj[i].worldY, tileSize, tileSize);
	            if (playerRect.intersects(objRect)) {
	                pickUpObject(i);
	                System.out.println("Player at: " + player.x + "," + player.y +
	                        " | Diamond at: " + obj[i].worldX + "," + obj[i].worldY); //maybe removed

	            }
	        }
	    }
	}

	public void pickUpObject(int i) {
	    if (obj[i] == null) return;

	    // increment score / do whatever when diamond picked
	    System.out.println("Picked up: " + obj[i].name);
	    score++;

	    // respawn the same object at a new random tile (snake/apple-like behavior)
	    // uses AssetSetter.placeOnRandomTile which avoids player's tile
	    aSetter.placeOnRandomTile(obj[i]);
	    // if you want to remove it instead of respawning, use: obj[i] = null;
	}

	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	public void setupGame() {
		aSetter.setObject();
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;  // 0.0166666 sex
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) {
				
		
		update();
			
			
		repaint();
			
			
			
		try {
			double remainingTime = nextDrawTime - System.nanoTime();
			remainingTime = remainingTime/1000000000;
				
			if(remainingTime < 0) {
				remainingTime = 0;
		}
				
			Thread.sleep((long) remainingTime);
				
			nextDrawTime += drawInterval;
				
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
		}
			
		
		}
}
	
			
		
		
		
	
	public void update() {
		
		player.update();
		checkObjectCollision();
	
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		
		player.draw(g2);
		// draw objects
		for (int i = 0; i < obj.length; i++) {
		    if (obj[i] != null) {
		        obj[i].draw(g2, this);
		    }
		}

		// simple score text
		g2.setColor(Color.white);
		g2.drawString("Score: " + score, 10, 20);

		
		
		g2.dispose();
	}

}

