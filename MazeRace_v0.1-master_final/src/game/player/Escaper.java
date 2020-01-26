package game.player;

import additional.Location;
import additional.Utils;
import maze.Board;
import search.Action;
import search.State;

import java.awt.*;
import java.util.Map;
import java.util.Stack;

public class Escaper extends Player {

    Map<State, Action> minMaxStrategy;
    Map<State, Action> AStarStrategy;


    public Escaper(Location loc, Board b) {
        super(loc, b);
        myColor = Color.GREEN;
    }


    public void update(Graphics g, Board board)
    {
        //if(Utils.isWithin(this, board.))


        for(int i = 0; i < base_speed; ++i)
        {
            draw(g);
        }
    }



}

