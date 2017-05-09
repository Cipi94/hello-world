package lorenzo;

import java.util.ArrayList;
import java.util.Random;
// mirkolino patatos
/**
 * Created by aewwc on 08/05/2017.
 */
public class Board {

    int idBoard;
    ArrayList <Player> turnOrder[];
    DevelopmentCard Tower[4][4];
    Random dices[3];
    ArrayList <Player> councilPalace[];
    ManipulateResources market[4];

    public Random[] getDices() {
        return dices;
    }

    public void rollDices(Random[] dices) {
        this.dices = dices;
    }

    public int (int tower, int card)

}



