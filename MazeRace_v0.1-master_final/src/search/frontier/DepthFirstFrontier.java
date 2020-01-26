package search.frontier;
import search.*;

import java.util.Stack;

public class DepthFirstFrontier implements Frontier
{
    public DepthFirstFrontier()
    {
        current_capacity = 0;
        max_capacity = 0;
    }

    public void add_node( Node newNode)
    {
        current_capacity++;
        update_max();
        lifoQueue.add(newNode);
    }

    public void clear()
    {
        current_capacity = 0;
        max_capacity = 0;
        lifoQueue.clear();
    }
    public boolean is_empty()
    {
        return lifoQueue.isEmpty();
    }
    public Node remove_node()
    {
        current_capacity--;
        return lifoQueue.pop();
    }

    public int get_max_capacity()
    {
        return max_capacity;
    }

    public void update_max()
    {
        if(current_capacity > max_capacity)
            max_capacity = current_capacity;
    }

    // class variables
    int current_capacity;
    int max_capacity;
    Stack<Node> lifoQueue = new Stack<Node>();
}