package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Direction;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Year2024Day20 extends AOCPuzzle
{
	private static final Map<Point, Integer> mapScores = new HashMap<>();

	public Year2024Day20()
	{
		super("2024", "20");
	}

	private int adjPaths(char[][] map, int row, int col)
	{
		if(map[row][col] != '#')
			return 0;
		return (map[row - 1][col] != '#' ? 1 : 0) +
				(map[row + 1][col] != '#' ? 1 : 0) +
				(map[row][col - 1] != '#' ? 1 : 0) +
				(map[row][col + 1] != '#' ? 1 : 0);
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

		PossiblePath normal = getTimeToSolve(start, map);
		if(normal == null)
		{
			System.out.println("Failed to find normal path...");
			return;
		}
		int normalTime = normal.score;
		Map<Point, Integer> pointScores = new HashMap<>();

		for(int i = normal.path.size() - 1; i >= 0; i--)
			pointScores.put(normal.path.get(i), normalTime - i);

		lap(getNumCheats(2, normal.path, pointScores));
		lap(getNumCheats(20, normal.path, pointScores));
	}

	private int getNumCheats(int dist, List<Point> path, Map<Point, Integer> pointScores)
	{
		Set<String> cheats = new HashSet<>();
		for(Point p : path)
		{
			int currentScore = pointScores.get(p);
			for(int x = -dist; x <= dist; x++)
			{
				for(int y = -dist; y <= dist; y++)
				{
					int distUsed = Math.abs(x) + Math.abs(y);
					if(distUsed > dist)
						continue;
					Point np = p.add(new Point(y, x));
					if(pointScores.containsKey(np) && currentScore - pointScores.get(np) - distUsed >= 100)
						cheats.add(p + "  | " + np);

				}
			}
		}

		return cheats.size();
	}

	private PossiblePath getTimeToSolve(Point point, char[][] map)
	{
		List<PossiblePath> queue = new ArrayList<>();
		queue.add(new PossiblePath(point, 0, new ArrayList<>()));
		while(!queue.isEmpty())
		{
			PossiblePath pp = queue.remove(0);

			for(Direction d : Direction.values())
			{
				Point offsetPoint = pp.p.directionOffset(d);
				char c = map[offsetPoint.row()][offsetPoint.col()];
				if(c == '#' || pp.path.contains(offsetPoint))
					continue;

				ArrayList<Point> newPath = new ArrayList<>(pp.path);
				newPath.add(pp.p);
				if(c == 'E')
				{
					newPath.add(offsetPoint);
					return new PossiblePath(offsetPoint, pp.score + 1, newPath);
				}
				queue.add(new PossiblePath(offsetPoint, pp.score + 1, newPath));
			}
		}

		return null;
	}

	private record PossiblePath(Point p, int score, List<Point> path)
	{

	}
}