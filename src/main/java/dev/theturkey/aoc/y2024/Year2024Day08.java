package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Year2024Day08 extends AOCPuzzle
{
	public Year2024Day08()
	{
		super("2024", "8");
	}


	@Override
	public void solve(List<String> input)
	{
		Map<Character, List<Point>> nodeLocations = new HashMap<>();
		int height = input.size();
		int width = input.get(0).length();
		for(int row = 0; row < height; row++)
		{
			String rowStr = input.get(row);
			for(int col = 0; col < width; col++)
			{
				char c = rowStr.charAt(col);
				if(c == '.')
					continue;
				List<Point> locs = nodeLocations.computeIfAbsent(c, k -> new ArrayList<>());
				locs.add(new Point(row, col));
			}
		}

		Set<Point> antiNodeLocs = new HashSet<>();
		for(char c : nodeLocations.keySet())
		{
			List<Point> nodes = nodeLocations.get(c);
			for(int i = 0; i < nodes.size(); i++)
			{
				Point p1 = nodes.get(i);
				for(int j = i + 1; j < nodes.size(); j++)
				{
					Point p2 = nodes.get(j);
					int rise = p1.row() - p2.row();
					int run = p1.col() - p2.col();
					Point pp = new Point(p2.row() - rise, p2.col() - run);
					if(pp.row() >= 0 && pp.row() < height && pp.col() >= 0 && pp.col() < width)
						antiNodeLocs.add(pp);
					pp = new Point(p1.row() + rise, p1.col() + run);
					if(pp.row() >= 0 && pp.row() < height && pp.col() >= 0 && pp.col() < width)
						antiNodeLocs.add(pp);
				}
			}
		}

		lap(antiNodeLocs.size());

		antiNodeLocs = new HashSet<>();
		for(char c : nodeLocations.keySet())
		{
			List<Point> nodes = nodeLocations.get(c);
			for(int i = 0; i < nodes.size(); i++)
			{
				Point p1 = nodes.get(i);
				for(int j = i + 1; j < nodes.size(); j++)
				{
					Point p2 = nodes.get(j);
					int rise = p1.row() - p2.row();
					int run = p1.col() - p2.col();
					for(int k = 0; k < width + height; k++)
					{
						Point pp = new Point(p2.row() - (rise * k), p2.col() - (run * k));
						boolean pp1Inside = pp.row() >= 0 && pp.row() < height && pp.col() >= 0 && pp.col() < width;
						if(pp1Inside)
							antiNodeLocs.add(pp);
						pp = new Point(p1.row() + (rise * k), p1.col() + (run * k));
						boolean pp2Inside = pp.row() >= 0 && pp.row() < height && pp.col() >= 0 && pp.col() < width;
						if(pp2Inside)
							antiNodeLocs.add(pp);

						if(!pp1Inside && !pp2Inside)
							break;
					}
				}
			}
		}

		lap(antiNodeLocs.size());
	}
}
