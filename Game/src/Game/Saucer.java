package src.Game;

import java.awt.*;
import static src.Game.Constants.FRAME_HEIGHT;
import static src.Game.Constants.FRAME_WIDTH;
import src.utilities.SoundManager;
import src.utilities.Vector2D;

public class Saucer extends  Ship{
    public static final int HEIGHT = 12;
    public static final int WIDTH = 24;
    public static final int WIDTH_ELLIPSE = 20;
    public Color colorBelt;
    public Sprite sprite;

    public Saucer(GameS g, Controller ctrl, Color colorBody, Color colorBelt){
        super(g,new Vector2D(FRAME_WIDTH*Math.random(), FRAME_HEIGHT*Math.random()), new Vector2D(0, -1), 10);
        this.ctrl = ctrl;
        direction = new Vector2D(0,-1);
        thrusting = false;
        bullet = null;
        color = colorBody;
        this.colorBelt = colorBelt;
        Image im = Sprite.ENEMY2;
        sprite = new Sprite(im, position, direction, 25, 25); 
    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    @Override
    public boolean canCollide(GameObject other) {
        return !(other instanceof PlayerShip);
    }

    public void hit() {
        super.hit();
        SoundManager.play(SoundManager.bangLarge);
        game.explosion(this);
    }
    
}
