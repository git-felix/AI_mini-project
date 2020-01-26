package maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import additional.Cell_Status;
import additional.Location;
import additional.Utils;

public class Wyrm
{
    // variables <3
    Stack<Path> paths_stack;
    Stack<Room> rooms_stack;
    static Cell[][] maze;
    static Board board;



    public Wyrm(Board curr_board )
    {
        board = curr_board;
        maze = curr_board.get_maze();
        paths_stack = new Stack<Path>();
        rooms_stack = new Stack<Room>();
    }

    public static Board getBoard()
    {
        return board;
    }
    
    public void create_maze(int num)
    {
        create_rooms(num);
        carve();
        merge_rooms_paths();
        cleanup(num);
        last_cleaup(num);
        //merge_rooms_paths();
        //cleanup(1);

//        merge_rooms_paths();

        board.update();
        System.out.println("DONE!");
    }


    void last_cleaup(int num)
    {
        // very dum function
        // loops through all the cells in the matrix and checks if there are any paths left that have dead ends
        
        for(int i = 0; i < maze.length; ++i)
        {
            for(int j = 0; j < maze[i].length; j++)
            {
                if(maze[i][j].status != Cell_Status.PATH )
                    continue;
                
                if(maze[i][j].get_path().is_dead_end(new Location(i,j)))
                {
                    maze[i][j].get_path().add_dead_end(new Location(i,j));
                    paths_stack.push(maze[i][j].get_path());
                }
            }
        }
        cleanup(num);
    }

    /* Utility Functions */

    static Location getLocation(Location curr_loc)
    {
        ArrayList<Location> temp_list = get_desired_cells(curr_loc, Cell_Status.NOTHING, null);
        if( temp_list == null)
            return null;
        return get_rand_loc(temp_list);
    }


    public static ArrayList<Location> get_desired_cells( Location curr_loc, Cell_Status cell_desired1)
    {
        return get_desired_cells(curr_loc, cell_desired1, null);
    }

    static ArrayList<Location> get_desired_cells( Location curr_loc, Cell_Status cell_desired1, Cell_Status cell_desired2)
    {
        ArrayList<Location> list_of_cells = new ArrayList<Location>();
        int curr_row = curr_loc.get_row();
        int curr_col = curr_loc.get_column();

        if(cell_desired2 == null)
            cell_desired2 = cell_desired1;

        // UP
        if(curr_row - 1 >= 0)
            if(maze[curr_row - 1][curr_col].status == cell_desired1 || maze[curr_row - 1][curr_col].status == cell_desired2)
                list_of_cells.add(new Location(curr_row - 1, curr_col));

        // LEFT
        if(curr_col - 1 >= 0)
            if(maze[curr_row][curr_col - 1].status == cell_desired1 || maze[curr_row][curr_col - 1].status == cell_desired2)
                list_of_cells.add(new Location(curr_row, curr_col - 1));

        // DOWN
        if(curr_row + 1 < maze.length)
            if(maze[curr_row + 1][curr_col].status == cell_desired1 || maze[curr_row + 1][curr_col].status == cell_desired2) 
                list_of_cells.add(new Location(curr_row + 1, curr_col));

        // RIGHT
        if(curr_col + 1 < maze[curr_row].length)
            if(maze[curr_row][curr_col + 1].status == cell_desired1 || maze[curr_row ][curr_col + 1].status == cell_desired2)
                list_of_cells.add(new Location(curr_row, curr_col + 1));

        if(list_of_cells.size() == 0)
            return null;

        return list_of_cells;
    }

    static Location get_rand_loc(ArrayList<Location> possible_loc)
    {
        Random rand = new Random();
        //return possible_loc.get(0);
        return possible_loc.get(rand.nextInt(possible_loc.size())); 
    }

    Path get_path()
    {
        if(paths_stack.size() == 0)
            return null;
        return paths_stack.pop();
    }

    void add_path(Path p)
    {
        paths_stack.add(p);
    }

    Path get_room()
    {
        if(rooms_stack.size() == 0)
            return null;
        return paths_stack.pop();
    }

    void add_room(Room p)
    {
        rooms_stack.add(p);
    }

    // changes surrounding -1 into 0, means changes nothing into walls
    static void wallify(Location curr_loc)
    {
        ArrayList<Location> nothing_cells = get_desired_cells(curr_loc, Cell_Status.NOTHING, null);

        if(nothing_cells == null)
            return;

        for(int i = 0; i < nothing_cells.size(); i++)
        {
            maze[nothing_cells.get(i).get_row()][nothing_cells.get(i).get_column()].status = Cell_Status.WALL;
        }
    }

    /* Utility Functions end here */



    /* Functions Needed For Create_Rooms */

    public void create_rooms(int attempts)
    {
        int counter = 0;
        while(counter <= attempts)
        {
            Room tempRoom = new Room(board, counter);
            if(tempRoom.create_room())
                add_room(tempRoom);

            counter++;
        }
    }


    public void merge_rooms_paths()
    {
        int maxTries = 2;
        int roomsMerged = 0;

        for(int tries = 0; tries < maxTries; ++tries) {
            if(roomsMerged > rooms_stack.size() - 1)
                tries = maxTries;

            for (int m = 0; m < rooms_stack.size(); ++m) {
                board.update();
                Utils.wait(1);

                if(roomsMerged == rooms_stack.size())
                    return;

                Room tempRoom = rooms_stack.get(m);
                if (tempRoom.get_path() != null)
                    continue;

                ArrayList<Location> borderCells = new ArrayList<Location>();


                for (int i = 0; i < tempRoom.room_cells.size(); ++i) {
                    ArrayList<Location> walls = get_desired_cells(tempRoom.room_cells.get(i), Cell_Status.WALL);
                    if (walls == null)
                        continue;

                    for (int j = 0; j < walls.size(); ++j) {
                        borderCells.add(walls.get(j));
                    }

                }


                while (borderCells.size() > 0) {
                    int randIndex = Utils.get_random(0, borderCells.size() - 1);

                    Location potentialDoor = borderCells.get(randIndex);

                    ArrayList<Location> pathsNextDoor = get_desired_cells(potentialDoor, Cell_Status.PATH);
                    if (pathsNextDoor == null) {
                        borderCells.remove(randIndex);
                        continue;
                    }

                    if (pathsNextDoor.size() > 0) {
                        Cell door = maze[potentialDoor.get_row()][potentialDoor.get_column()];
                        Cell doorEntrance = maze[pathsNextDoor.get(0).get_row()][pathsNextDoor.get(0).get_column()];

                        Path overtakingPath = doorEntrance.get_path();

                        /* Remove entrance from dead end */
                        if (overtakingPath.is_dead_end(pathsNextDoor.get(0))) {
                            overtakingPath.dead_end.remove(pathsNextDoor.get(0));
                        }

                        door.set_path(overtakingPath);
                        door.status = doorEntrance.status;
                        tempRoom.set_path(overtakingPath);

                        for (int i = 0; i < tempRoom.room_cells.size(); ++i) {
                            overtakingPath.path_cells.add(tempRoom.room_cells.get(i));
                        }
                        overtakingPath.path_cells.add(borderCells.get(randIndex));
                        ++roomsMerged;
                        break;
                    }

//                borderCells.remove(randIndex);
                }
            }


        }
    }

    public Location getRandRoomCell()
    {
        int randRoomNumb = Utils.get_random(0, rooms_stack.size() - 1);
        Room randRoom = rooms_stack.get(randRoomNumb);

        int randCellNumb = Utils.get_random(0, randRoom.room_cells.size() - 1);
        Location randCell = randRoom.room_cells.get(randCellNumb);

        return randCell;
    }

    /* Functions Needed For Creating_Rooms end here */

    public static Cell[][] getMaze(){return  maze;}


    /* Functions Needed For Carving */

    // loop through the matrix and fill everything up
    public void carve()
    {
        // creating paths at locations where we find NOTHING(-1)
        int id = 0;
        while(check() != null)
        {
            Location curr_loc = check();
            Path tempPath = new Path(id, board, curr_loc);
            tempPath.create_path();
            add_path(tempPath);
            id++;
        }    
    }

    // checks to see if there are any -1 left on the board so that we can fill them up with paths and walls
    Location check()
    {
        Location current_loc = new Location(-1,-1);
        for(int i = 0; i < maze.length; i = i + 1)
        {
            for(int j = 0; j< maze[i].length; j = j + 1)
            {
                if(maze[i][j].status.value == -1)
                {
                    current_loc.set_row(i);
                    current_loc.set_column(j);
                    return current_loc;
                }
            }
        }

        if(current_loc.get_column() == -1 && current_loc.get_row() == -1)
            return null;

        return current_loc;
    }

    /* Functions Needed For Carving end here */
    

    /* Functions Needed For Cleanup */

    // for every dead end it deletes some, and if it can connect paths it will.
    public void cleanup(int limit)       // on aisle 3!
    {
        Path temp_path = get_path();

        while(temp_path != null)
        {
            Location dead_end = temp_path.get_dead_end();
            int counter = 0;
            while(dead_end != null /*&&  counter < limit */)
            {
                if(!temp_path.is_dead_end(dead_end)  )
                {
                    dead_end = temp_path.get_dead_end();
                    continue;
                }

                if(loopy_thingy(dead_end))
                {
                    counter++;
                }
                dead_end = temp_path.get_dead_end();
            }
            if(temp_path.dead_end.size() != 0)
                paths_stack.add(temp_path); 

            temp_path = get_path();
                      
        }
    } 

    // finds the closest path from nearest walls, checks to see if the path chosen is the same path or not
    ArrayList<Location> find_closest_path(Location curr_loc, Path from_which_path)
    {
        ArrayList<Location> close_path = get_desired_cells(curr_loc, Cell_Status.PATH); // , Cell_Status.ROOM

        if(close_path == null)
            return null;

        // filters the paths tha tare the same
        for(int i = 0; i < close_path.size(); ++i)
        {
            if(maze[close_path.get(i).get_row()][close_path.get(i).get_column()].get_path() == from_which_path)
            {
                close_path.remove(i);
                i--;
            }
        }

        if(close_path.size() == 0)
            return null;

            return close_path;
    }


    // merges paths if there are walls who have different paths near them else puts a -1 
    boolean loopy_thingy(Location curr_loc )
    {
        ArrayList<Location> wall_locations = get_desired_cells(curr_loc, Cell_Status.WALL);

        int curr_col = curr_loc.get_column();
        int curr_row = curr_loc.get_row();

        Path curr_path = maze[curr_row][curr_col].get_path();


        Location temp_loc = null;
        ArrayList<Location> all_paths = null;
        Cell wall_cell = null;
        int size = Integer.MAX_VALUE;

        // looping through every possibility to see if there is a one where the wall has only 1 path nearby 
        for (int i = 0; i < wall_locations.size(); i++)
        {
            ArrayList<Location> temp_all_paths = find_closest_path(wall_locations.get(i), curr_path);

            if(temp_all_paths == null)
            {
                continue;
            }

                if(size >= temp_all_paths.size())
            {
                size = temp_all_paths.size();
                temp_loc = wall_locations.get(i);
                wall_cell = maze[temp_loc.get_row()][temp_loc.get_column()];
                all_paths = temp_all_paths;
            }
        }   

/* START COMMENT */
        if( size >= 2 || temp_loc == null || all_paths == null || wall_cell == null)
        {
            ArrayList<Location> new_dead_ends = get_desired_cells(curr_loc, Cell_Status.PATH);

            maze[curr_row][curr_col].status = Cell_Status.WALL;


            if(!(new_dead_ends == null))
            {
                for(int i = 0; i < new_dead_ends.size(); ++i)
                {
                    Location new_dead_end = new_dead_ends.get(i);
                    if(maze[curr_row][curr_col].get_path().is_dead_end(new_dead_end))
                        maze[curr_row][curr_col].get_path().add_dead_end(new Location(new_dead_end.get_row(), new_dead_end.get_column()));
                }
            }

            maze[curr_row][curr_col].get_path().remove_path_cell(curr_loc);
            maze[curr_row][curr_col].get_path().dead_end.remove(curr_loc);


            board.update();
            Utils.wait(1);
            return false;
        }
/* END COMMENT */


        Location new_path = all_paths.get(0);

        Cell new_path_cell = maze[new_path.get_row()][new_path.get_column()];

        maze[temp_loc.get_row()][temp_loc.get_column()].status = Cell_Status.PATH;
        maze[temp_loc.get_row()][temp_loc.get_column()].set_path(maze[curr_row][curr_col].get_path());
        maze[temp_loc.get_row()][temp_loc.get_column()].get_path().path_cells.add(temp_loc);


        board.update();

        if(maze[temp_loc.get_row()][temp_loc.get_column()].get_path().path_cells.size() >= new_path_cell.get_path().path_cells.size() )
        {
            maze[temp_loc.get_row()][temp_loc.get_column()].get_path().merge(new_path_cell.get_path());
        }
        else
        {
            new_path_cell.get_path().merge(maze[temp_loc.get_row()][temp_loc.get_column()].get_path());
        }

        if(maze[curr_row][curr_col].get_path().is_dead_end(curr_loc))
        {
            maze[curr_row][curr_col].get_path().add_dead_end(curr_loc);
        }

            return true;
    }

    /* Functions Needed For Cleaning_up end here */

}

