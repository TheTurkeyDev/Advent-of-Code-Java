package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Direction;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Year2024Day16 extends AOCPuzzle
{
	public Year2024Day16()
	{
		super("2024", "16");
	}

	@Override
	public void solve(List<String> input)
	{
		char[][] map = new char[input.size()][input.get(0).length()];
		Point start = new Point(0, 0);
		for(int row = 0; row < input.size(); row++)
		{
			String rowStr = input.get(row);
			for(int col = 0; col < rowStr.length(); col++)
			{
				char c = rowStr.charAt(col);
				if(c == 'S')
					start = new Point(row, col);
				map[row][col] = rowStr.charAt(col);
			}
		}

		Map<DirectedPoint, Integer> pointLowScores = new HashMap<>();
		PriorityQueue<PossiblePath> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.score));
		queue.add(new PossiblePath(start, Direction.RIGHT, 0, new HashSet<>()));
		List<PossiblePath> lowPaths = new ArrayList<>();
		while(!queue.isEmpty())
		{
			PossiblePath pp = queue.poll();
			DirectedPoint dp = new DirectedPoint(pp.p, pp.facing);
			if(pointLowScores.computeIfAbsent(dp, k -> Integer.MAX_VALUE) < pp.score)
				continue;
			pointLowScores.put(dp, pp.score);

			for(Direction d : Direction.values())
			{
				Point offsetPoint = pp.p.directionOffset(d);
				char c = map[offsetPoint.row()][offsetPoint.col()];
				if(c == 'E' && d == pp.facing)
				{
					Set<Point> newPath = pp.path;
					newPath.add(pp.p);
					lowPaths.add(new PossiblePath(offsetPoint, d, pp.score + 1, newPath));
					queue.removeIf(ppp -> ppp.score >= pp.score + 1);
					continue;
				}
				if(c == '#')
					continue;

				Set<Point> newPath = new HashSet<>(pp.path);
				newPath.add(pp.p);
				if(d != pp.facing)
					queue.add(new PossiblePath(pp.p, d, pp.score + 1000, newPath));
				else
					queue.add(new PossiblePath(offsetPoint, d, pp.score + 1, newPath));
			}
		}

		lap(lowPaths.get(0).score);
		Set<Point> points = new HashSet<>();
		for(PossiblePath pp : lowPaths)
			points.addAll(pp.path);
		lap(points.size() + 1);
	}

	private record PossiblePath(Point p, Direction facing, int score, Set<Point> path)
	{

	}

	private record DirectedPoint(Point p, Direction facing)
	{
		@Override
		public boolean equals(Object o)
		{
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			DirectedPoint point = (DirectedPoint) o;
			return point.p.equals(this.p) && point.facing == this.facing;
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(p, facing);
		}
	}
}