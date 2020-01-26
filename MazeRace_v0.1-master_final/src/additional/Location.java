package additional;

public class Location
{
	private int row;
	private int col;

	public Location(int row, int col) 
	{
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Location)) return false;

		Location location = (Location) o;

		if (row != location.row) return false;
		return col == location.col;
	}

	public void printLoc()
	{
		System.out.println("row : " + row + " col : " + col);
	}

	@Override
	public int hashCode() {
		int result = row;
		result = 31 * result + col;
		return result;
	}

	public void set_row(int num)
	{
		row = num;
	}
	public void set_column(int num) 
	{
		col = num;
	}

	public int get_row()
	{
		return row;
	}
	public int get_column()
	{
		return col;
	}
}