package src.Game;

public class AimNShoot implements Controller{public static final double SHOOTING_DISTANCE = Bullet.BULLET_LIFE
        * Ship.MUZZLE_VELOCITY;

    public static final double SHOOTING_ANGLE = Math.PI / 12;
    public GameS game;
    private Action action = new Action();

    public AimNShoot(GameS game) {
        this.game = game;
    }

    @Override
    public Action action() {
        GameObject target = Controllers.findTarget(game.playerShip, game.objects);
        if (target == null) {
            action.turn = 0;
            action.shoot = false;
            action.thrust = 0;
            return action;
        }
        action.turn = Controllers.aim(game.playerShip, target);
        if (action.turn==0) {
            double distanceToTarget = game.playerShip.distance(target);
            action.shoot = distanceToTarget*Constants.DT < SHOOTING_DISTANCE + target.radius;
            action.thrust = 1;
        }
        return action;
    }

}
