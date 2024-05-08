package src.Game;

import static src.Game.Constants.DT;
import src.utilities.SoundManager;
import src.utilities.Vector2D;
import java.awt.*;

public abstract class Ship extends GameObject {
    public Bullet bullet;
    protected Controller ctrl;
    public Vector2D direction;
    public boolean thrusting;
    public Color color;
    public int XP[] = { -8, 0, 8, 0 }, YP[] = { 8, 4, 8, -8 };
    public static final double STEER_RATE = 2 * Math.PI;
    public static final double MAG_ACC = 200;
    public static final double DRAG = 0.01;
    public static final double DRAWING_SCALE = 1.5;
    public static final int MUZZLE_VELOCITY = 100;

    public Ship(GameS g, Vector2D position, Vector2D velocity, double radius) {
        super(g, position, velocity, radius);
    }

    protected void mkBullet() {
        Vector2D bulletPos = new Vector2D(position);
        Vector2D bulletVel = new Vector2D(velocity);
        bulletVel.addScaled(direction, PlayerShip.MUZZLE_VELOCITY);
        bullet = new Bullet(game,bulletPos, bulletVel, this instanceof PlayerShip);
        bullet.position.addScaled(direction, (radius + bullet.radius) * 2);
        SoundManager.fire();
    }

    @Override
    public void update() {
        Action action = ctrl.action();
        if (action.shoot) {
            mkBullet();
            action.shoot = false;
        }
        thrusting = action.thrust != 0;
        direction.rotate(action.turn * PlayerShip.STEER_RATE * DT);
        velocity = new Vector2D(direction).mult(velocity.mag());
        velocity.addScaled(direction, PlayerShip.MAG_ACC * DT * action.thrust);
        velocity.addScaled(velocity, -PlayerShip.DRAG);
        super.update();
    }

    public boolean canHit(GameObject other) {
        return (other instanceof Enemy  || other instanceof PlayerShip);
    }

}
