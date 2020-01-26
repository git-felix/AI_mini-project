package search;

import search.NodeFunction;


public class AStarFunction implements NodeFunction
{

    public AStarFunction( NodeFunction obj )
    {
        heuristic_function = obj;
    }   

    public int get_node_value(Node node)
    {
        int hn_distance = heuristic_function.get_node_value(node);
        return (hn_distance + node.get_cost_path());
    }

    NodeFunction heuristic_function;
}