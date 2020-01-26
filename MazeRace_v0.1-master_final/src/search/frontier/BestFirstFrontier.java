package search.frontier;
import search.*;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstFrontier implements Frontier
{
    public BestFirstFrontier(NodeFunction esim)
    {
        node_val_getter = esim;
    }

    public void add_node(Node newNode)
    {
        current_capacity++;
        update_max();
        newNode.value = node_val_getter.get_node_value(newNode);
        p_queue.add(newNode);
    }


    public void clear() 
    {
        max_capacity = 0;
        current_capacity = 0;
        p_queue.clear();
    }


    public boolean is_empty() 
    {
        return p_queue.isEmpty();
    }


    public Node remove_node() 
    {
        current_capacity--;
        return p_queue.poll();
    }


    public void update_max()
    {
        if(current_capacity > max_capacity)
            max_capacity = current_capacity;
    }

    public int get_max_capacity() 
    {
        return max_capacity;
    }



    // class variables
    int current_capacity;
    int max_capacity;
    NodeFunction node_val_getter; 


    PriorityQueue<Node> p_queue = new PriorityQueue<Node>( new Comparator<Node>()
    {
        public int compare(Node n1, Node n2)
        {            
            if( n1.value < n2.value )
                return -1;
            if( n1.value > n2.value )   
                return 1;
            return 0;
        }
    });
}