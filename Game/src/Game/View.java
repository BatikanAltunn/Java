package src.Game;

import javax.swing.*;
import java.awt.geom.AffineTransform;
import static src.Game.Constants.FRAME_HEIGHT;
import static src.Game.Constants.FRAME_WIDTH;
import java.awt.*;

public class View extends JComponent {
    private GameS game;
    Image im = Constants.BACKG;
    AffineTransform bgTransf;

    public View(GameS game){
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = imWidth > FRAME_WIDTH ? FRAME_WIDTH / imWidth : 1;
        double stretchy = imHeight > FRAME_HEIGHT ? FRAME_HEIGHT / imHeight : 1;
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }
    
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(im, bgTransf, null);
        synchronized (GameS.class) {
            for (GameObject object : game.objects)
                object.draw(g);
            for (Particle p : game.particles)
                p.draw(g);
        }
        g.setColor(Color.red);
        g.setFont(new Font("dialog", Font.BOLD, 20));g.drawString("Level: "+GameS.getLevel(), 20, FRAME_HEIGHT-20);
        g.drawString("Score: "+GameS.getScore(), FRAME_WIDTH/3+20, FRAME_HEIGHT-20);
        g.drawString("Lives: "+GameS.getLives(), 2*FRAME_WIDTH/3+20, FRAME_HEIGHT-20);
        if (GameS.getLives()<=0){
            g.drawString("GAME OVER Score "+GameS.getScore(), FRAME_WIDTH/2-100, FRAME_HEIGHT/2-20);
            GameS.lives=0;
        }
    }

    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
