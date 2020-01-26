package additional;

import java.util.Random;

public class Utils {
    public static int get_random( int min, int max )
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int distance(Location first, Location second)
    {
        int dr = second.get_row() - first.get_row();
        int dc = second.get_column() - first.get_column();

        return (int) Math.sqrt(dr * dr + dc * dc);
    }

    public static boolean isWithin(Location origin, Location point, int radius)
    {
        if(distance(origin, point) < radius)
            return true;
        return false;
    }
}
