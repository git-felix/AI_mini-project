package search;

import java.util.ArrayList;
import java.util.Set;

import maze.Cell;


public class HeuristicFunction implements NodeFunction
{
    public HeuristicFunction(  ArrayList<Cell> set_cells, Cell start_cell )
    {
        initialCell = start_cell;
        all_cells = set_cells;
    }

    public int get_node_value(Node node) 
    {
//         current_cell = ((TourState)node.state).get_current_city();
//         visited_cities = ((TourState)node.state).get_visited_cities();
//         int max = Integer.MIN_VALUE;
//         int current_length = 0;
//         for( City city : all_cities )
//         {
//             if( !visited_cities.contains(city) )
//             {
//                 current_length = city.getShortestDistanceTo(current_city);
//                 if( current_length > max )
//                 {
//                     max = current_length;
//                     temp_max_city = city;
//                 }
//             }
//         }
//         int value = temp_max_city.getShortestDistanceTo(current_city);
//         if(visited_cities.size() == 0)
//             return value;
//        // return value + temp_max_city.getShortestDistanceTo(initialCity);
        return 0;
    }

    Cell initialCell;
    Cell temp_max_cell;
    Cell current_cell;
    Set<Cell> visited_cells;
    ArrayList<Cell> all_cells;
}