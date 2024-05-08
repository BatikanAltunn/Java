package src.Game;

import src.utilities.Vector2D;
import java.awt.*;
import static src.Game.Constants.*;

public abstract class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public double radius;
    public boolean dead;
    public GameS game;



    public GameObject(GameS g, Vector2D position, Vector2D velocity, double radius) {
        game = g;
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
    }

    public void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void hit() {
        dead = true;
    }

    public boolean canCollide(GameObject other) {
        return true;
    }

    public boolean overlap(GameObject other) {
        return this.position.distWithWrap(other.position, FRAME_WIDTH, FRAME_HEIGHT) < this.radius + other.radius;
    }

    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other) && this.canCollide(other)) {
            this.hit();
            other.hit();
            if (this instanceof Enemy && other instanceof Bullet) {
                Bullet b = (Bullet) other;
                if (b.firedByShip) GameS.incScore(100);
            }
            if (this instanceof Bullet && other instanceof Enemy) {
                Bullet b = (Bullet) this;
                if (b.firedByShip) GameS.incScore(100);
            }
            if (this instanceof Saucer && other instanceof Bullet) {
                Bullet b = (Bullet) other;
                if (b.firedByShip) GameS.incScore(500);
            }
            if (this instanceof Bullet && other instanceof Saucer) {
                Bullet b = (Bullet) this;
                if (b.firedByShip) GameS.incScore(500);
            }


            if (this instanceof Extralife && other instanceof Bullet) {
                Bullet b = (Bullet) other;
                if (b.firedByShip) GameS.addLifes();
            }
            if (this instanceof Bullet && other instanceof Extralife) {
                Bullet b = (Bullet) this;
                if (b.firedByShip) GameS.addLifes();
            }


            if (this instanceof Bomb && other instanceof Bullet) {
                Bullet b = (Bullet) other;
                if (b.firedByShip) GameS.subscore();
            }
            if (this instanceof Bullet && other instanceof Bomb) {
                Bullet b = (Bullet) this;
                if (b.firedByShip) GameS.subscore();
            }








        }
    }

    public abstract void draw(Graphics2D g);

    public double distance(GameObject other) {
        return position.distWithWrap(other.position, FRAME_WIDTH, FRAME_HEIGHT);
    }
}
