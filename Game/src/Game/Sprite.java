package src.Game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import src.utilities.ImageManager;
import src.utilities.Vector2D;

public class Sprite {
    public static Image ENEMY1, BACKG, ENEMY2,EXLIFE,BOMB;
    static {
        try {
            ENEMY1 = ImageManager.loadImage("11");
            BACKG = ImageManager.loadImage("7");
            ENEMY2 = ImageManager.loadImage("enemy2");
            EXLIFE= ImageManager.loadImage("16");
            BOMB= ImageManager.loadImage("20");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Image image;
    public Vector2D position;
    public Vector2D direction;
    public double width;
    public double height;

    public Sprite(Image image, Vector2D s, Vector2D direction, double width,
                  double height) {
        this.image = image;
        this.position = s;
        this.direction = direction;
        this.width = width;
        this.height = height;
    }

    public double getRadius() {
        return (width + height) / 4.0;
    }

    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double((position.x - width / 2), position.y - height / 2, width,
                height);
    }

    public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle(), 0, 0);
        t.scale(width / imW, height / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);
    }

}

