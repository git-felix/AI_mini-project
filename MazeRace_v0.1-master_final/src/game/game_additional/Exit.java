package game.game_additional;

import game.game;
import additional.Location;
import game.player.Player;
import maze.Board;

import java.awt.*;

public class Exit extends Item 
{

    public Exit(Location loc, Board b)
    {
        super(b);

        Weight = 0;
        current_loc = loc;

        myColor = Color.RED;
    }
    
    void use(Player player)
    {
        // game over Player Has Reached the End
        game.set_status(false);
    }


}