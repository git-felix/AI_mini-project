package maze;

import additional.Cell_Status;
import additional.Location;
import additional.Utils;

import java.util.ArrayList;
import java.util.Random;

//import additional.Location;

public class Room
{
    ArrayList<Location> room_cells;
    double ID;
    Location initialLocation;
    int width;
    int height;
    private Path belongs_to_path;
    Board board;
    Cell[][] maze;


    public Room(Board b, double id)
    {
        board = b;
        room_cells = new ArrayList<Location>();
        maze = b.get_maze();

        belongs_to_path = null;
        int maze_width = maze[0].length;
        int maze_height = maze.length;
        int scale = 3;
        ID = id;

        int max_height = maze_height / scale;   // setting the max limit for the height
        int max_width = maze_width   / scale;     // setting the max limit for the width

        int row = get_random(1, maze_height - max_height - 3);
        int col = get_random(1, maze_width - max_width - 3);

        // randomly selecting a number in between min and maximum width/height
        width = get_random(3, max_height );
        height = get_random(3, max_width );


        initialLocation = new Location(row,col);

    }

    public int get_random( int min, int max ) 
    {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

    // check to see if the room collides with other rooms
    boolean collision_check(Location curr_loc)
    {
        int row = curr_loc.get_row();
        int column = curr_loc.get_column();


        for(int i = row; i < row + height; ++i)
        {
            for(int j = column; j < column + width; ++j)
            {
                if(i > maze.length - 1 || j > maze[0].length - 1 || maze[i][j].status == Cell_Status.ROOM || maze[i][j].status == Cell_Status.WALL )
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean create_room()
    {
        Cell[][] maze = board.get_maze();

        int row = initialLocation.get_row();
        int col = initialLocation.get_column();

        if( collision_check(initialLocation) )
        {
            return false;
        }
        else
        {
            // looping everything and setting ROOMS
            for( int i = row; i < row + height; i++)
            {
                for(int j = col; j < col + width; j++)
                {
                    maze[i][j].status = Cell_Status.ROOM;
                    maze[i][j].visited = true;
                    room_cells.add(new Location(i, j));
                }
            }
            update_cell_room();

            // looping through the boundaries to wallify them
            // Left wall and Right Wall
            for(int i = row - 1; i < row + height + 1; i++)
            {
                if( i > maze.length || i < 0)
                    continue;
                if(col - 1 > -1)
                    maze[i][col - 1].status = Cell_Status.WALL;
                maze[i][col + width].status = Cell_Status.WALL;
            }

            // Upper wall and Bottom Wall
            for(int i = col -1; i < col + width + 1; i++)
            {
                if(i > maze[0].length || i < 0)
                    continue;

                if(row - 1 > -1)
                    maze[row - 1][i].status = Cell_Status.WALL;
                maze[row + height][i].status = Cell_Status.WALL;


            }
        }
        return true;
    }


    public void update_cell_room()
    {
        /* Update RoomCells.belongs_to_room */
        if(room_cells.size() > 0) {
            Cell[][] tempMaze = board.get_maze();
            for (int i = 0; i < room_cells.size(); ++i) {
                Location temp = room_cells.get(i);
                tempMaze[temp.get_row()][temp.get_column()].set_room(this);
            }
        }
    }

    public void set_path(Path path)
    {
        belongs_to_path = path;

        /* Update RoomCells.belongs_to_path */
        if(room_cells.size() > 0) {
            Cell[][] tempMaze = board.get_maze();
            for (int i = 0; i < room_cells.size(); ++i) {
                Location temp = room_cells.get(i);
                tempMaze[temp.get_row()][temp.get_column()].set_path(belongs_to_path);
                tempMaze[temp.get_row()][temp.get_column()].status = Cell_Status.PATH;

            }
        }
    }

    public Path get_path(){return belongs_to_path;}
}