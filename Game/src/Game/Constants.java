package src.Game;
import java.awt.*;
import java.io.IOException;
import java.util.Random;


import src.utilities.ImageManager;
public class Constants {
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    public static final int DELAY = 20;  // milliseconds
    public static final double DT = DELAY/1000.0;  // seconds
    public static Random RANDOM = new Random();


    public static Image ENEMY1, BACKG, ENEMY2,EXLIFE,BOMB;
    static {
        try {
            ENEMY1 = ImageManager.loadImage("11");
            ENEMY2 = ImageManager.loadImage("enemy2");
            BACKG = ImageManager.loadImage("7");
            EXLIFE= ImageManager.loadImage("16");
            BOMB= ImageManager.loadImage("20");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
