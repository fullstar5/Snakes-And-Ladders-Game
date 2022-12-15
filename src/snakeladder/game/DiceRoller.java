// Team 10 Workshop 15
package snakeladder.game;

import java.util.ArrayList;
import java.util.List;

// Response for rolling dice
public class DiceRoller {
    private List<Die> dice;         //store the dice
    private NavigationPane np;
    private int num_stepsRolled;    //rolled number

    public DiceRoller(NavigationPane np) {
        this.dice = new ArrayList<>();
        this.np = np;
        this.num_stepsRolled = 0;
    }

    // roll the dice
    public void roll(int nb){
        int size = dice.size();
        Die die = new Die(nb, this, size+1);
        dice.add(die);
        num_stepsRolled += nb;
    }

    // verify rolling finished
    public void finishedRolling(int index) {
        if (index == np.getNumberOfDice()){
            np.startMoving(num_stepsRolled);
            reseter();
        }
    }

    public List<Die> getAllDice(){
        return this.dice;
    }

    // reset the dice roller
    public void reseter(){
        this.num_stepsRolled = 0;
        this.dice.clear();
    }
}
