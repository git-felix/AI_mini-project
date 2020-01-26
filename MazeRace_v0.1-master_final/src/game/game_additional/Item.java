package game.game_additional;

import additional.Location;
import game.player.*;
import maze.Board;

import java.awt.*;


public class Item
{
    public Location current_loc;   // where the item is

    // for later adding items
    Integer Damage;
    Integer Health;
    Integer Weight;

    Board board;

    // for drawing
    Color myColor;

    public Item(Board b)
    { board = b; }

    void use(Player player) { };

    public Integer getWeight(){return Weight;}

    public void draw(Graphics g)
    {
        g.setColor(myColor);
        g.fillRect(current_loc.get_column() * board.cellSize, current_loc.get_row() * board.cellSize,  board.cellSize, board.cellSize);
    }
}