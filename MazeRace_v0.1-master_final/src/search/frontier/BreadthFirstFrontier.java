package search.frontier;
import search.*;

import java.util.LinkedList;
import java.util.Queue;



public class BreadthFirstFrontier implements Frontier
{

    public BreadthFirstFrontier()
    {
        current_capacity = 0;
        max_capacity = 0;
    }

    public void add_node( Node newNode)
    {
        current_capacity++;
        update_max();
        fifoQueue.add(newNode);
    }
    public void clear()
    {
        current_capacity = 0;
        max_capacity = 0;
        fifoQueue.clear();
    }
    public boolean is_empty()
    {
        return fifoQueue.isEmpty();
    }
    public Node remove_node()
    {
        current_capacity--;
        return fifoQueue.poll();
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
    Queue<Node> fifoQueue = new LinkedList<Node>();     // keeping the frontier
}