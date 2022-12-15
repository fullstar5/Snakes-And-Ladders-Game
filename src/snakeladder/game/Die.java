// Team 10 Workshop 15
package snakeladder.game;

import ch.aplu.jgamegrid.Actor;

public class Die extends Actor {
  private NavigationPane np;
  private int nb;

  // new attributes
  private DiceRoller diceRoller;
  private int index;



  Die(int nb, DiceRoller diceRoller, int index)
  {
    super("sprites/pips" + nb + ".gif", 7);
    this.nb = nb;

    this.diceRoller = diceRoller;
    this.index = index;
  }


  public void act()
  {
    showNextSprite();
    if (getIdVisible() == 6)
    {
      setActEnabled(false);

      // end of rolling
      diceRoller.finishedRolling(index);
    }
  }

}
