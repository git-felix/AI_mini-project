package additional;

public enum Cell_Status 
{
    NOTHING,    // -1 
    WALL,       // 0
    PATH,       // 1
    ROOM,       // 2
    TEST;       // 3
    public int value = -1 + ordinal();
};