package search;

import additional.Cell_Status;
import additional.Location;
import additional.Utils;
import maze.Board;
import maze.Cell;
import maze.Wyrm;
import search.*;
import search.SearchUtils.PathState;
import search.frontier.*;

import java.util.ArrayList;
import java.util.Map;

public class GraphSearch implements Search
{
    public GraphSearch(Frontier obj)
    {
        nodes_generated = 0;
        frontier = obj;
    }

    public Node findStrategy(State initialConfiguration, GoalTest goalTest)
    {
        nodes_generated = 1;    
        frontier.add_node(new Node(null, null, initialConfiguration, 0, 0));
        while(!frontier.is_empty())
        {
            nodes_generated++;
            Node node = frontier.remove_node();

            // adding to the list of states we have been in
            explored.add(node.state);

            if(goalTest.isGoal(node.state))
            {
                //print();
                frontier.clear();
                return node;
            }
                
            else
            {

                for(Action action : node.state.getApplicableActions())
                {
                    State newState = node.state.getActionResult(action);
                    PathState temp = (PathState)newState;


                    Cell[][] maze = Wyrm.getMaze();
                    Location tempLoc = temp.get_current_Location();
                    maze[tempLoc.get_row()][tempLoc.get_column()].status = Cell_Status.TEST;
                    Board b = Wyrm.getBoard();
                    b.update();
                    Utils.wait(1);


                    // checking if we have been in the state, just pass it by,
                    if(!explored.contains(temp))
                    {
                        frontier.add_node(new Node(node, action, newState, node.depth + 1, 0));
                    }
                }
            }
        }
        //print();
        frontier.clear();
        return null;
    }
//
//    void print()
//    {
//        System.out.println("Number Of Nodes Generated: " + get_max_generated());
//        System.out.println("Max Nodes Kept in Frontier: " + frontier.get_max_capacity());
//        System.out.println();
//    }

    public int get_max_generated()
    {
        return nodes_generated;
    }

    // class variables

    ArrayList<State> explored = new ArrayList<State>();
    int nodes_generated;
    Frontier frontier;


    @Override
    public int get_states_generated() {
        return 0;
    }
}