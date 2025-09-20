package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {

    public OBJ_Key() {
        name = "Diamond";
        try {
            // put the file at: resources/objects/diamond.png (see note below)
            image = ImageIO.read(getClass().getResourceAsStream("/objects/diamond-sh.png"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            // fallback: image stays null so you can detect load problems
        }
    }
}
