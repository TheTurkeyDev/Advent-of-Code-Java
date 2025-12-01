package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Direction;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Year2024Day12 extends AOCPuzzle
{
	public Year2024Day12()
	{
		super("2024", "12");
	}

	public static char[][] map;

	public int countSubSides(List<Point> sidePoints, String key, boolean isRow)
	{
		int sideCount = 0;
		boolean lastHad = false;
		int val = Integer.parseInt(key.substring(0, key.indexOf(",")));
		Point min = sidePoints.stream().min(Comparator.comparingInt(isRow ? Point::row : Point::col)).orElse(new Point(-1, -1));
		Point max = sidePoints.stream().max(Comparator.comparingInt(isRow ? Point::row : Point::col)).orElse(new Point(-1, -1));
		for(int v = (isRow ? min.row() : min.col()); v <= (isRow ? max.row() : max.col()); v++)
		{
			boolean contains = sidePoints.contains(isRow ? new Point(v, val) : new Point(val, v));
			if(!lastHad && contains)
			{
				sideCount++;
				lastHad = true;
			}
			else if(lastHad && !contains)
			{
				lastHad = false;
			}
		}

		return sideCount;
	}

	public int countSides(Map<String, List<Point>> sides)
	{
		int sideCount = 0;
		for(String key : sides.keySet())
			sideCount += countSubSides(sides.get(key), key, key.contains("RIGHT") || key.contains("LEFT"));
		return sideCount;
	}

	public int[] getPlot(Point p)
	{
		char plotChar = map[p.row()][p.col()];
		Map<String, List<Point>> sides = new HashMap<>();
		int perimeter = 0;
		List<Point> locs = new ArrayList<>();
		List<Point> toVisit = new ArrayList<>();
		toVisit.add(p);

		while(!toVisit.isEmpty())
		{
			Point pos = toVisit.remove(0);
			if(locs.contains(pos))
				continue;
			map[pos.row()][pos.col()] = '.';
			locs.add(pos);
			for(Direction d : Direction.values())
			{
				Point off = pos.directionOffset(d);
				if(locs.contains(off))
					continue;

				if(off.within(map.length, map[0].length) && map[off.row()][off.col()] == plotChar)
				{
					toVisit.add(off);
					continue;
				}

				perimeter++;
				int num = d == Direction.LEFT || d == Direction.RIGHT ? pos.col() : pos.row();
				sides.computeIfAbsent(num + "," + d, k -> new ArrayList<>()).add(pos);
			}
		}
		return new int[]{locs.size() * perimeter, locs.size() * countSides(sides)};
	}

	@Override
	public void solve(List<String> input)
	{
		int width = input.get(0).length();
		int height = input.size();
		map = new char[height][width];
		for(int row = 0; row < height; row++)
			for(int col = 0; col < width; col++)
				map[row][col] = input.get(row).charAt(col);

		long part1 = 0;
		long part2 = 0;
		for(int row = 0; row < height; row++)
		{
			for(int col = 0; col < width; col++)
			{
				if(map[row][col] == '.')
					continue;
				int[] answers = getPlot(new Point(row, col));
				part1 += answers[0];
				part2 += answers[1];
			}
		}
		lap(part1);
		lap(part2);
	}
}