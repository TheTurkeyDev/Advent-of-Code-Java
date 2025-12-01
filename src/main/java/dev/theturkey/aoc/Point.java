package dev.theturkey.aoc;


import java.util.Comparator;
import java.util.Objects;

public record Point(int row, int col) implements Comparable<Point>
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
		Point point = (Point) o;
		return row == point.row && col == point.col;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(row, col);
	}

	public Point directionOffset(Direction direction)
	{
		return switch(direction)
		{
			case UP -> new Point(row - 1, col);
			case RIGHT -> new Point(row, col + 1);
			case DOWN -> new Point(row + 1, col);
			case LEFT -> new Point(row, col - 1);
		};
	}

	public boolean within(int width, int height)
	{
		return row >= 0 && row < height && col >= 0 && col < width;
	}

	public double distanceTo(Point p)
	{
		return Math.pow(p.col - col, 2) + Math.pow(p.row - row, 2);
	}

	public Point add(Point p)
	{
		return new Point(row + p.row, col + p.col);
	}

	public Point mul(int times)
	{
		return new Point(row * times, col * times);
	}

	public Point divide(int div)
	{
		return new Point(row / div, col / div);
	}

	@Override
	public int compareTo(Point other)
	{
		return Comparator.comparing(Point::row)
				.thenComparing(Point::col)
				.compare(this, other);
	}
}