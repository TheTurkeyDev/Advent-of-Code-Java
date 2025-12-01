package dev.theturkey.aoc;


import java.util.Objects;

public record PointL(long row, long col)
{
	@Override
	public String toString()
	{
		return "Point{" +
				"row=" + row +
				", col=" + col +
				'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		PointL point = (PointL) o;
		return row == point.row && col == point.col;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(row, col);
	}

	public PointL directionOffset(Direction direction)
	{
		return switch(direction)
		{
			case UP -> new PointL(row - 1, col);
			case RIGHT -> new PointL(row, col + 1);
			case DOWN -> new PointL(row + 1, col);
			case LEFT -> new PointL(row, col - 1);
		};
	}

	public boolean within(int width, int height)
	{
		return row >= 0 && row < height && col >= 0 && col < width;
	}

	public PointL mul(long amount)
	{
		return new PointL(row * amount, col * amount);
	}
}