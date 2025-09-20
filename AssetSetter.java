package main;

import java.util.Random;
import object.OBJ_Key;
import object.SuperObject;

public class AssetSetter {

    GamePanel gp;
    Random rand = new Random();

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // create one diamond and place it randomly on the current screen
        OBJ_Key diamond = new OBJ_Key();
        placeOnRandomTile(diamond);
        gp.obj[0] = diamond; // use slot 0 for the single diamond
    }

    // make this public so GamePanel can reuse it when respawning
    public void placeOnRandomTile(SuperObject obj) {
        boolean placed = false;

        while (!placed) {
            int col = rand.nextInt(gp.maxScreenCol); // 0 .. maxScreenCol-1
            int row = rand.nextInt(gp.maxScreenRow); // 0 .. maxScreenRow-1

            int x = col * gp.tileSize;
            int y = row * gp.tileSize;

            // avoid player's current tile (so it doesn't spawn under player)
            if (x == gp.playerX && y == gp.playerY) {
                continue;
            }

            // optional: check tile collisions if you have a collision map
            // if (gp.tileM.isBlocked(col, row)) continue;

            obj.worldX = x;
            obj.worldY = y;
            placed = true;
        }
    }
}
