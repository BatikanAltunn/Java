package src.Game;

import src.utilities.SoundManager;
import src.utilities.Vector2D;
import java.awt.*;
import java.util.ArrayList;
import static src.Game.Constants.FRAME_HEIGHT;
import static src.Game.Constants.FRAME_WIDTH;
import java.util.List;
import java.util.Random;

public class Enemy extends GameObject {
    public Sprite sprite;
    public double rotationPerFrame;
    public Vector2D direction;

    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;
    public boolean isLarge = true;
    public List<Enemy> spawnedEnemys = new ArrayList<Enemy>();

    public Enemy(GameS g, Vector2D pos, double vx, double vy, boolean isLarge, Sprite spr) {
        super(g, pos, new Vector2D(vx, vy), spr.getRadius());
        this.isLarge = isLarge;
        double dir = Math.random() * 2 * Math.PI;
        direction = new Vector2D(Math.cos(dir), Math.sin(dir));
        position = new Vector2D(pos);
        sprite = new Sprite(spr.image, position, direction, spr.width, spr.height);
        radius = sprite.getRadius();
        rotationPerFrame = Math.random()  * 0.1;

    }

    public void draw(Graphics2D g) {
        sprite.draw(g);
    }

    public Enemy(GameS g) {
        super(g,new Vector2D(Math.random() * FRAME_WIDTH, Math.random() + FRAME_HEIGHT), new Vector2D(0, 0), 0);
        double vx = Math.random() * MAX_SPEED;
        if (Math.random() < 0.5) vx *= -1;
        double vy = Math.random() * MAX_SPEED;
        if (Math.random() < 0.5) vy *= -1;
        velocity.set(new Vector2D(vx, vy));
        double width = Math.min(Math.max(20+new Random().nextGaussian()*30, 30), 50);
        Image im = Sprite.ENEMY1;
        double height = width * im.getHeight(null)/im.getWidth(null);
        double dir = Math.random() * 2 * Math.PI;
        direction = new Vector2D(Math.cos(dir), Math.sin(dir));
        sprite = new Sprite(im, position, direction, width, height);
        radius = sprite.getRadius();
        rotationPerFrame = Math.random()  * 0.1;
    }
    
    @Override
    public void hit() {
        SoundManager.play(SoundManager.bangMedium);
        game.explosion(this);
        super.hit();
    }

    public void update() {
        super.update();
        direction.rotate(rotationPerFrame);
    }
}
