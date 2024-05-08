package src.Game;

import static src.Game.Constants.DT;
import java.awt.*;
import src.utilities.Vector2D;
import java.util.Random;

public class Bullet extends GameObject {
    private double lifetime;
    public static final int RADIUS = 2;
    public static final int BULLET_LIFE = 2;
    public boolean firedByShip;

    public Bullet(GameS g, Vector2D pos, Vector2D vel, boolean firedByShip) {
        super(g, pos, vel, 2);
        this.lifetime = BULLET_LIFE;
        this.firedByShip = firedByShip;
    }

    @Override
    public void update() {
        super.update();
        lifetime -= DT;
        if (lifetime <= 0) dead = true;
    }

    @Override
    public void draw(Graphics2D g)
        {
            Random randomGenerator = new Random();
            int red = randomGenerator.nextInt(256);
            int green = randomGenerator.nextInt(256);
            int blue = randomGenerator.nextInt(256);    
            g.setColor(new Color(red,green,blue));
            g.fillOval((int) position.x-RADIUS, (int) position.y-RADIUS, 3*RADIUS, 3*RADIUS);
        }
}
