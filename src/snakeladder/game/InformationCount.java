// Team 10 Workshop 15
package snakeladder.game;

import java.util.HashMap;
import java.util.Map;

// This class is for print out the players' information as they rolled dice and step on connection
public class InformationCount {
    String playerIndex;
    Map<Integer, Integer> rolledMap = new HashMap<>();  // store information for rolled number
    int travelDown, travelUp;

    public InformationCount(String playerIndex) {
        this.playerIndex = playerIndex;
    }

    // count rolled number for each player
    public void rolled(int nb){
        if (rolledMap.containsKey(nb)){
            rolledMap.put(nb, rolledMap.get(nb) + 1);
        }
        else{
            rolledMap.put(nb, 1);
        }
    }

    public void updateTravelDown(){
        travelDown+=1;
    }
    public void updateTravelUp(){
        travelUp+=1;
    }

    @Override
    public String toString() {
        String stats  = "Player " + playerIndex + " rolled: " + rolledMap.entrySet().toString() + "\n" + "Player" + playerIndex +
                "traversed: " + "up-" + travelUp + ", down-" + travelDown;

        return stats;
    }
}
