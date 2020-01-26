package maze;

import additional.Cell_Status;
import additional.Location;
import additional.Utils;

import java.util.ArrayList;
//import java.util.Stack;
import java.util.Stack;


public class Path
{
    ArrayList<Location> path_cells;
    Location initial_location;
    Cell[][] maze;
    Board board;
    double ID; // for keeping track of the path and later on merging
    Stack<Location> dead_end;


    Path(int id, Board b, Location loc)
    {
        board = b;
        path_cells = new ArrayList<Location>();
        ID = id;
        maze = b.get_maze();
        initial_location = loc;
        dead_end = new Stack<Location>();
    }

    void create_path()
    {
        Location curr_loc = initial_location;
        while(curr_loc != null)
        {
            maze[curr_loc.get_row()][curr_loc.get_column()].visited = true;
            maze[curr_loc.get_row()][curr_loc.get_column()].status = Cell_Status.PATH;
            maze[curr_loc.get_row()][curr_loc.get_column()].set_path(this);
            // keeping track of all the cells in the path
            path_cells.add(curr_loc);

            Location curr_next_loc = Wyrm.getLocation(curr_loc);
            if(curr_next_loc == null)
            {
                add_dead_end(curr_loc);
                curr_loc = null;
                continue;
            }
            maze[curr_next_loc.get_row()][curr_next_loc.get_column()].visited = true;
            maze[curr_next_loc.get_row()][curr_next_loc.get_column()].status = Cell_Status.PATH;
            maze[curr_next_loc.get_row()][curr_next_loc.get_column()].set_path(this);
            // keeping track of all the cells in the path
            path_cells.add(curr_next_loc);
            Location curr_next_next_loc = Wyrm.getLocation(curr_next_loc);
            if(curr_next_next_loc == null)
            {
                add_dead_end(curr_next_loc);
                curr_loc = null;
                continue;
            }
            maze[curr_next_next_loc.get_row()][curr_next_next_loc.get_column()].visited = true;
            maze[curr_next_next_loc.get_row()][curr_next_next_loc.get_column()].status = Cell_Status.PATH;
            maze[curr_next_next_loc.get_row()][curr_next_next_loc.get_column()].set_path(this);


            Wyrm.wallify(curr_loc);
            Wyrm.wallify(curr_next_loc);

            board.update();

            Utils.wait(1);


            if(is_dead_end(curr_loc))
            {
                add_dead_end(curr_loc);
            }

            if(is_dead_end(curr_next_loc))
            {
                add_dead_end(curr_next_loc);
            }

            curr_loc = curr_next_next_loc;
        }
    }

    public void merge(Path path)
    {
        if(path == this)
            return;

        ArrayList<Location> path_to_merge = path.get_path_cells();
        
        for(int i = 0; i < path_to_merge.size(); i++)
        {
            maze[path_to_merge.get(i).get_row()][path_to_merge.get(i).get_column()].set_path(this);
            path_cells.add(path_to_merge.get(i));
        }

        Location temp = path.get_dead_end();
        while(temp != null)
        {
            if(is_dead_end(temp))
            {
                maze[temp.get_row()][temp.get_column()].set_path(this);
                dead_end.push(temp);
            }

            temp = path.get_dead_end();
        }
    }

    void remove_path_cell(Location cell_loc)
    {
        path_cells.remove(cell_loc);
    }

    ArrayList<Location> get_path_cells()
    {
        return path_cells;
    }

    boolean is_dead_end(Location curr_location)
    {
        if(curr_location == null)
            return false;
        int counter = 0;

        int curr_row = curr_location.get_row();
        int curr_col = curr_location.get_column();

        // UP
        if(curr_row - 1 >= 0)
        {
            if(maze[curr_row - 1][curr_col].status == Cell_Status.WALL || maze[curr_row - 1][curr_col].status == Cell_Status.NOTHING )
                counter++;
        }
        else
            counter++; // for the cases when it is out of bonds we still consider a wall      

        // LEFT
        if(curr_col - 1 >= 0)
        {
            if(maze[curr_row][curr_col - 1].status == Cell_Status.WALL || maze[curr_row][curr_col - 1].status == Cell_Status.NOTHING )
                counter++;
        }
        else
            counter++;

        // DOWN
        if(curr_row + 1 < maze.length)
        {
            if(maze[curr_row + 1][curr_col].status == Cell_Status.WALL || maze[curr_row + 1][curr_col].status == Cell_Status.NOTHING ) 
                counter++;
        }
        else
            counter++;                

        // RIGHT
        if(curr_col + 1 < maze[curr_row].length)
        {
            if(maze[curr_row][curr_col + 1].status == Cell_Status.WALL || maze[curr_row ][curr_col + 1].status == Cell_Status.NOTHING )
                counter++;
        }
        else
            counter++;


        if(counter >= 3)
            return true;

        return false;
    }

    Location get_dead_end()
    {
        if(dead_end.size() == 0)
            return null;
        return dead_end.pop();
    }

    void add_dead_end(Location location)
    {
        dead_end.push(location);
    }

}
