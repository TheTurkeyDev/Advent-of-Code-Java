package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Direction;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.List;

public class Year2024Day18 extends AOCPuzzle
{
	private static final int width = 70;
	private static final int height = 70;

	public Year2024Day18()
	{
		super("2024", "18");
	}

	@Override
	public void solve(List<String> input)
	{
		List<Point> fallingBytes = new ArrayList<>();
		for(String s : input)
		{
			String[] parts = s.split(",");
			fallingBytes.add(new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[0])));
		}

		List<Point> steps = getMinSteps(fallingBytes.subList(0, 1024));
		lap(steps.size());

		for(int i = 1025; i < fallingBytes.size(); i++)
		{
			Point blocker = fallingBytes.get(i - 1);
			if(steps.contains(blocker))
			{
				steps = getMinSteps(fallingBytes.subList(0, i));
				if(steps.isEmpty())
				{
					lap(blocker.col() + "," + blocker.row());
					break;
				}
			}
		}
	}

	private List<Point> getMinSteps(List<Point> fallingBytes)
	{
		List<Point> visited = new ArrayList<>();
		List<PossiblePath> queue = new ArrayList<>();
		queue.add(new PossiblePath(new Point(0, 0), new ArrayList<>()));
		while(!queue.isEmpty())
		{
			PossiblePath pp = queue.remove(0);
			if(visited.contains(pp.p))
				continue;
			visited.add(pp.p);

			for(Direction d : Direction.values())
			{
				Point offsetPoint = pp.p.directionOffset(d);
				if(offsetPoint.col() == width && offsetPoint.row() == height)
				{
					pp.path.add(offsetPoint);
					return pp.path;
				}

				if(!pp.p.within(width + 1, height + 1) || visited.contains(offsetPoint) || fallingBytes.contains(offsetPoint))
					continue;

				List<Point> newPath = new ArrayList<>(pp.path);
				newPath.add(pp.p);
				queue.add(new PossiblePath(offsetPoint, newPath));
			}
		}

		return new ArrayList<>();
	}

	private record PossiblePath(Point p, List<Point> path)
	{

	}
}