// Team 10 Workshop 15
package snakeladder.game;

import ch.aplu.jgamegrid.Location;

// This class to apply the template of ToggleStrategy interface, used for simulated player
public class RealToggleStrategy implements ToggleStrategy{
    public boolean checkWhetherToggle(NavigationPane np, GamePane gp){
        int numberOfDice = np.getNumberOfDice();
        int currentPlayerIndex = gp.getCurrentPuppetIndex();
        if (currentPlayerIndex == 0){
            int nextPlayerIndex = 1;
        }
        else{
            int nextPlayerIndex = 0;
        }

        int num_snake = 0;
        int num_ladder = 0;

        // calculate number of snakes and ladders
        int currentPosition = gp.getAnotherPuppet().getCellIndex();
        for (int i = (currentPosition + numberOfDice); i < (currentPosition + numberOfDice*6); i++){
            Connection con = gp.getConnectionOn(i);
            if (con != null){
                if (con.cellEnd - con.cellStart > 0){
                    num_ladder += 1;
                }
                else{
                    num_snake += 1;
                }
            }
        }
        if (num_ladder >= num_snake){return true;}
        return false;
    }

}
