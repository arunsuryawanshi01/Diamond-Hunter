package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gp) {
        if (image != null) {
            // draw scaled to tile size so it snaps to your grid
            g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
        }
    }
}
