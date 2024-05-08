package src.Game;

import src.utilities.JEasyFrame;
import src.utilities.Vector2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static src.Game.Constants.DELAY;

public class GameS {
    public static final int N_ENEMY = 10;
    public List<GameObject> objects;
    public List<Ship> ships;
    public List<Particle> particles;
    PlayerShip playerShip;
    Movement ctrl;

    private static int score = 0;
    static int lives = 3;
    private static int level = 1;
    public static boolean gameOver = false;

    public GameS() {
        objects = new ArrayList<GameObject>();
        ships = new ArrayList<Ship>();
        particles = new ArrayList<Particle>();
        for (int i = 0; i < N_ENEMY; i++) {
            objects.add(new Enemy(this));
        }
        ctrl = new Movement();
        playerShip = new PlayerShip(this,ctrl);
        objects.add(playerShip);
        ships.add(playerShip);
        addSaucers();
        addLIFE();
        addBOMB();
     }

    public void newLevel() {
        level++;
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
        }
        synchronized(GameS.class) {
            objects.clear();
            ships.clear();
            particles.clear();
            for (int i = 0; i < N_ENEMY + 2 * (level-1); i++) {
                objects.add(new Enemy(this));
            }
            playerShip = new PlayerShip(this,ctrl);
            objects.add(playerShip);
            ships.add(playerShip);
            addSaucers();
            addLIFE();
            addBOMB();
        }

    }

    public void newLife() {
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {
        }
        synchronized(GameS.class) {
            objects.clear();
            ships.clear();
            particles.clear();
            for (int i = 0; i < N_ENEMY + 2 * (level-1); i++) {
                objects.add(new Enemy(this));
            }
            playerShip = new PlayerShip(this,ctrl);
            objects.add(playerShip);
            ships.add(playerShip);
            addSaucers();
            addLIFE();
            addBOMB();
        }
    }

    private void addSaucers() {
        for (int i = 0; i < 7; i++) {
            System.out.println("Adding saucer");
            Controller ctrl = (i % 3 != 0 ? new RandomAction() : new AimNShoot(this));
            Color colorBody = (i % 3 != 0 ? Color.PINK : Color.GREEN);
            Random r = new Random();
            Vector2D s = new Vector2D(
                    r.nextInt(Constants.FRAME_WIDTH),
                    r.nextInt(Constants.FRAME_HEIGHT));
            Ship saucer = new Saucer(this,ctrl, colorBody, Color.white);
            objects.add(saucer);
            ships.add(saucer);
        }
    }

    private void addLIFE() {
        for (int i = 0; i < 2; i++) {
            System.out.println("Adding saucer");
            Controller ctrl = (i % 3 != 0 ? new RandomAction() : new AimNShoot(this));
            Color colorBody = (i % 3 != 0 ? Color.PINK : Color.GREEN);
            Random r = new Random();
            Vector2D s = new Vector2D(
                    r.nextInt(Constants.FRAME_WIDTH),
                    r.nextInt(Constants.FRAME_HEIGHT));
            Ship exlife = new Extralife(this,ctrl, colorBody, Color.white);
            objects.add(exlife);
            ships.add(exlife);
            
        }
    }

    private void addBOMB() {
        for (int i = 0; i < 2; i++) {
            System.out.println("Adding saucer");
            Controller ctrl = (i % 3 != 0 ? new RandomAction() : new AimNShoot(this));
            Color colorBody = (i % 3 != 0 ? Color.PINK : Color.GREEN);
            Random r = new Random();
            Vector2D s = new Vector2D(
                    r.nextInt(Constants.FRAME_WIDTH),
                    r.nextInt(Constants.FRAME_HEIGHT));
            Ship addbomb = new Bomb(this,ctrl, colorBody, Color.white);
            objects.add(addbomb);
            ships.add(addbomb);
            
        }
    }

    public void update() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject o1 = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject o2 = objects.get(j);
                o1.collisionHandling(o2);
            }
        }
        List<GameObject> alive = new ArrayList<>();
        boolean noAsteroids = true;
        boolean noSaucers = true;
        boolean noShip = true;
        for (GameObject o : objects) {
            o.update();
            if (o instanceof Enemy) {
                noAsteroids = false;
                Enemy a = (Enemy) o;
                if (!a.spawnedEnemys.isEmpty()) {
                    alive.addAll(a.spawnedEnemys);
                    a.spawnedEnemys.clear();
                }
            }
            else if (o instanceof PlayerShip) noShip = false;
            if (!o.dead) alive.add(o);
            for (Ship s:ships)
                if (s.bullet != null) {
                alive.add(s.bullet);
                s.bullet = null;
            }
            else if (o instanceof Saucer)
                noSaucers = false;
        }
        synchronized (GameS.class) {
            objects.clear();
            objects.addAll(alive);
        }
        if (noAsteroids && noSaucers) {
            newLevel();
        }
        else if (noShip) {
            newLife();
        }
    }

    public void updateParticles() {
        List<Particle> alive = new ArrayList<Particle>();
        for (Particle p:particles) {
            p.update();
            if (!p.dead)
                alive.add(p);

        }
        synchronized (GameS.class) {
            particles.clear();
            particles.addAll(alive);
        }
    }

    public static void incScore(int inc) {
        int oldScore = score;
        score += inc;
        System.out.println("Score " + score);
        if (score / 5000 > oldScore / 5000) {
            System.out.println("Adding life");
            lives++;
        }
    }

    public static void loseLife()  {
        lives--;
        if (lives==0)
            gameOver = true;
    }

    public static int getScore() {
        return score;
    }

    public static int getLevel() {
        return level;
    }

    public static int getLives() {
        return lives;
    }

    public static int addLifes() {
        return lives++;
    }
    
    public static int subscore() {
        return score=0;
    }

    public void explosion(GameObject object) {
        if (object instanceof Ship)
            for (int i = 0; i<70; i++) {
            Particle p = new Particle(this, object.position, object.velocity, ((Ship) object).color);
            particles.add(p);

        }
        else
            for (int i = 0; i<70; i++) {
            Particle p = new Particle(this, object.position, object.velocity);
            particles.add(p);
        }
    }

    public static void main(String[] args) {
        GameS game = new GameS();
        View view = new View(game);
        new JEasyFrame(view, "SCI-FI").addKeyListener(game.ctrl);
        while (!gameOver) {
            game.update();
            game.updateParticles();
            view.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (Exception e) {
            }
        }
    }
}

