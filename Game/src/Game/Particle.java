package src.Game;

import src.utilities.Vector2D;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Particle extends GameObject {
    public static final int PARTICLE_SPEED = 10;
    public static final int TTL = 3000;
    public static final double DRAG =  10;
    public static final int SIZE = 3;
    public final Color COLOR = Color.LIGHT_GRAY;
    int ttl;
    Color color;

    public Particle(GameS g,Vector2D position, Vector2D velocity) {
        super(g, new Vector2D(position), random_velocity().add(velocity), SIZE);
        ttl = Constants.RANDOM.nextInt(TTL);
        color = COLOR;
    }

    public Particle(GameS g,Vector2D position, Vector2D velocity, Color color) {
        super(g, new Vector2D(position), random_velocity().add(velocity), SIZE);
        ttl = Constants.RANDOM.nextInt(TTL);
        this.color = color;
    }

    public static Vector2D random_velocity() {
        return Vector2D.polar(Math.random() * 2 * Math.PI, Math.abs(Constants.RANDOM.nextGaussian() * PARTICLE_SPEED));
    }

    public void update() {
        super.update();
        ttl -= Constants.DELAY;
        if (ttl<=0) dead = true;
    }

    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        g.setColor(color);
        g.fillOval(0,0,SIZE, SIZE);
        g.setTransform(at);
    }

    public void hit(){

    }

    @Override
    public boolean canCollide(GameObject other) {
        return false;
    }
}
