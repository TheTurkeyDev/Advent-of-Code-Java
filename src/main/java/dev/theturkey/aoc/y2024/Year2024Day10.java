package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Direction;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Year2024Day10 extends AOCPuzzle
{
	public Year2024Day10()
	{
		super("2024", "10");
	}

	@Override
	public void solve(List<String> input)
	{
		List<Point> trailHeads = new ArrayList<>();
		int[][] map = new int[input.size()][input.get(0).length()];

		for(int row = 0; row < input.size(); row++)
		{
			String rowStr = input.get(row);
			for(int col = 0; col < rowStr.length(); col++)
			{
				int val = rowStr.charAt(col) - 48;
				map[row][col] = val;
				if(val == 0)
					trailHeads.add(new Point(row, col));
			}
		}

		List<String> totalTrails = new ArrayList<>();
		for(Point head : trailHeads)
			totalTrails.addAll(getEndPoints(map, head, head));
		lap(new HashSet<>(totalTrails).size());
		lap(totalTrails.size());
	}

	public List<String> getEndPoints(int[][] map, Point start, Point p)
	{
		List<String> points = new ArrayList<>();
		int currHeight = map[p.row()][p.col()];
		for(Direction d : Direction.values())
		{
			Point nextP = p.directionOffset(d);
			if(!nextP.within(map.length, map[0].length))
				continue;
			int nextHeight = map[nextP.row()][nextP.col()];
			if(nextHeight == 9 && currHeight == 8)
				points.add(start.toString() + nextP);
			else if(nextHeight == currHeight + 1)
				points.addAll(getEndPoints(map, start, nextP));
		}

		return points;
	}
}