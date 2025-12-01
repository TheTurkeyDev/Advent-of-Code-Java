package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Direction;
import dev.theturkey.aoc.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Year2024Day06 extends AOCPuzzle
{
	public Year2024Day06()
	{
		super("2024", "6");
	}

	private boolean isCycle(char[][] map, Direction dir, Point guardLoc)
	{
		Set<PointDirection> visited = new HashSet<>();
		while(true)
		{
			visited.add(new PointDirection(guardLoc.row(), guardLoc.col(), dir));
			Point next = guardLoc.directionOffset(dir);
			if(!(next.row() >= 0 && next.row() < map.length && next.col() >= 0 && next.col() < map[0].length))
				return false;

			if(map[next.row()][next.col()] == '#')
				dir = dir.cycleCW();
			else
				guardLoc = next;

			if(visited.contains(new PointDirection(guardLoc.row(), guardLoc.col(), dir)))
				return true;
		}
	}

	@Override
	public void solve(List<String> input)
	{
		Direction dir = Direction.UP;
		Point guardLoc = new Point(0, 0);
		char[][] map = new char[input.size()][input.get(0).length()];

		for(int row = 0; row < input.size(); row++)
		{
			String rowStr = input.get(row);
			for(int col = 0; col < rowStr.length(); col++)
			{
				char c = rowStr.charAt(col);
				map[row][col] = c;
				if(c == '^')
					guardLoc = new Point(row, col);
			}
		}

		Set<Point> visited = new HashSet<>();
		Set<Point> obstructionPos = new HashSet<>();
		while(true)
		{
			visited.add(new Point(guardLoc.row(), guardLoc.col()));
			Point next = guardLoc.directionOffset(dir);
			if(!(next.row() >= 0 && next.row() < map.length && next.col() >= 0 && next.col() < map[0].length))
				break;

			if(map[next.row()][next.col()] == '#')
				dir = dir.cycleCW();
			else
				guardLoc = next;

			Point obstruction = guardLoc.directionOffset(dir);
			int or = obstruction.row();
			int oc = obstruction.col();
			if(or >= 0 && or < map.length && oc >= 0 && oc < map[0].length && map[or][oc] == '.' && !visited.contains(obstruction))
			{
				char[][] mapClone = new char[map.length][];
				for(int i = 0; i < map.length; i++)
					mapClone[i] = map[i].clone();
				mapClone[or][oc] = '#';
				if(isCycle(mapClone, dir, guardLoc))
					obstructionPos.add(obstruction);
			}
		}

		lap(visited.size());
		lap(obstructionPos.size());
	}

	private record PointDirection(int row, int col, Direction direction)
	{
		@Override
		public String toString()
		{
			return "PointDirection{" +
					"row=" + row +
					", col=" + col +
					", dir=" + direction +
					'}';
		}

		@Override
		public boolean equals(Object o)
		{
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			PointDirection point = (PointDirection) o;
			return row == point.row && col == point.col && direction == point.direction;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(row, col, direction);
		}
	}
}
