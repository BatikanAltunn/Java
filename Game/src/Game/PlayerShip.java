package src.Game;

import src.utilities.SoundManager;
import src.utilities.Vector2D;
import java.awt.*;
import java.awt.geom.AffineTransform;
import static src.utilities.SoundManager.bangLarge;
import static src.Game.Constants.*;

public class PlayerShip extends Ship {

    Sprite sprite;

    public PlayerShip(GameS g,Controller ctrl) {
        super(g,new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2), new Vector2D(0, -1), 10);
        this.ctrl = ctrl;
        direction = new Vector2D(0,-1);
        thrusting = false;
        bullet = null;
        color = Color.RED;
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(Color.RED);
        g.fillPolygon(XP, YP, XP.length);
        g.setTransform(at);
    }

    @Override
    public void hit() {
        super.hit();
        GameS.loseLife();
        SoundManager.play(bangLarge);
        game.explosion(this);
        
    }
}