package game.player;

import additional.DIRECTION;
import additional.Location;
import additional.Utils;
import game.game_additional.Exit;
import game.game_additional.Item;
import maze.Board;
import search.SearchUtils.PathState;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;


public class Player
{
    public Location    current_loc;    // location on the board
    Item        has_item;       // item the player is currently holding
    Integer     base_speed;     // how many cells per turn
    Integer     hit_points;     // health points
    Integer     attack;         // damage inflicted on other players
    Boolean     alive;          // to be or not to be
    Board       board;

    Color myColor;

    public Player(Location loc, Board b)
    {
        current_loc = loc;
        has_item = null;
        base_speed = 1;
        hit_points = 100;
        attack = 0;
        alive = true;
        board = b;

        myColor = null;
    }   

    public PathState get_state()
    {
        return new PathState(new LinkedHashSet<Location>(), current_loc);
    }

    void move(DIRECTION dir)
    {
        switch(dir) {
            case UP:
                current_loc.set_row(current_loc.get_row() - 1 );
                break;
            case DOWN:
                current_loc.set_row(current_loc.get_row() + 1 );
                break;
            case LEFT:
                current_loc.set_column(current_loc.get_column() - 1 );
                break;
            case RIGHT:
                current_loc.set_column(current_loc.get_column() + 1 );
                break;
            default:
                return;     // stay in place   
          }
    }

    void Attack(Player pl)
    {
        pl.set_health(pl.get_health() - attack);
    }

    void is_alive()
    {
        if(hit_points <= 0)
            alive = false;
        else 
            alive = true;
    }

    void set_health(Integer new_health)
    {
        hit_points = new_health;
        is_alive();
    }

    Integer get_health()
    {
        return hit_points;
    }

    //void search()

    void use_item(Item item)
    {
        if(item instanceof Exit)
        {
            board.set_status(false);
        }
    }

    int distance(Player otherPlayer)
    {
        return Utils.distance(this.current_loc, otherPlayer.current_loc);
    }

    public void draw(Graphics g)
    {
        g.setColor(myColor);
        g.fillRect(current_loc.get_column() * board.cellSize, current_loc.get_row() * board.cellSize,  board.cellSize, board.cellSize);
    }

    public void update(Graphics g, Board board){};

};