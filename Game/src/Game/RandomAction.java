package src.Game;

public class RandomAction implements Controller{
    Action action = new Action();

    @Override
    public Action action() {
        double shoot = Math.random();
        action.shoot = shoot > 0.95;
        double thrust = Math.random();
        action.thrust = thrust>0.7 ? 1 : 0;
        double turn = Math.random();
        action.turn = turn < 0.15 ? -1 : turn> 0.85 ? 1 : 0;
        return action;
    }
}
