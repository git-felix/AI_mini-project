package search;

import additional.Location;
import additional.Utils;
import game.game_additional.Item;
import game.player.Player;

public class HueristicWeightedDiagonal implements NodeFunction
{
    public HueristicWeightedDiagonal()
    {    }

    public int get_node_value(Node node)
    {
        return 1;

    }

    public static int get_node_value(Player player, Item item)
    {
        return get_result(player.current_loc, item.current_loc, item.getWeight());
    }

    public static int get_node_value(Player player, Player item)
    {
        return get_result(player.current_loc, item.current_loc, 1);
    }

    public static int get_node_value(Item player, Item item)
    {
        return get_result(player.current_loc, item.current_loc, item.getWeight());
    }

    public static  int get_result(Location loc1, Location loc2, Integer wei)
    {
        return Utils.distance(loc1, loc2) * wei;
    }
}
