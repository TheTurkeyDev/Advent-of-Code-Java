package dev.theturkey.aoc.y2025;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.List;

public class Year2025Day09 extends AOCPuzzle
{
	public Year2025Day09()
	{
		super("2025", "9");
	}

	@Override
	public void solve(List<String> input)
	{
		List<Point> points = input.stream().map(line ->
		{
			String[] parts = line.split(",");
			return new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
		}).toList();

		List<Line> lines = new ArrayList<>();
		for(int i = 0, j = points.size() - 1; i < points.size(); j = i++)
			lines.add(new Line(points.get(j), points.get(i)));

		long part1 = 0;
		long part2 = 0;
		for(int i = 0; i < points.size(); i++)
		{
			Point p1 = points.get(i);
			for(int j = i + 1; j < points.size(); j++)
			{
				Point p2 = points.get(j);
				Rectangle area = new Rectangle(p1, p2);
				long areaSize = area.getAreaSize();
				if(areaSize > part1)
					part1 = areaSize;

				if(!area.intersects(lines))
					part2 = Math.max(part2, areaSize);
			}
		}

		lap(part1);
		lap(part2);
	}

	private record Line(Point p1, Point p2)
	{
	}


	//Sourced from u/ash30342 on Reddit https://github.com/ash42/adventofcode/blob/main/adventofcode2025/src/nl/michielgraat/adventofcode2025/day09/Rectangle.java
	private record Rectangle(Point p1, Point p2)
	{
		public long getAreaSize()
		{
			return (long) (Math.abs(p1.col() - p2.col()) + 1) * (Math.abs(p1.row() - p2.row()) + 1);
		}

		// Based upon https://kishimotostudios.com/articles/aabb_collision/
		public boolean intersects(List<Line> edges)
		{
			long minX = Math.min(p1.col(), p2.col());
			long maxX = Math.max(p1.col(), p2.col());
			long minY = Math.min(p1.row(), p2.row());
			long maxY = Math.max(p1.row(), p2.row());

			for(Line l : edges)
			{
				long eMinX = Math.min(l.p1().col(), l.p2().col());
				long eMaxX = Math.max(l.p1().col(), l.p2().col());
				long eMinY = Math.min(l.p1().row(), l.p2().row());
				long eMaxY = Math.max(l.p1().row(), l.p2().row());
				// The idea is that two rectangles collide if they intersect on both axes.
				// In this case we are checking if the rectangle collides with one of the edges
				// of the polygon.
				//
				// So what we are checking here if the rectangle is above, below, left or right
				// of the edge.
				//
				// Since we are talking about comparing an edge with a rectangle here, this also
				// works if the rectangle is completely inside of the polygon (because then it
				// will not intersect with any of the edges).
				//
				// Note that a rectangle completely outside the polygon also does not intersect
				// with any of the edges. For most cases this is no problem (as all rectangles
				// we generate have corners which are red tile), but for some cases (like the
				// rectangle defined by (2,5) to (9,7) in the test input) this gives incorrect
				// results. Because of the way the input is constructed (basically like a big
				// circle or square) this does not lead to problems for the actual answer.
				//
				// This also works (the rectangle does not intersect with an edge) if one of the
				// edges of the polygon also happens to be the edge of the rectangle (as we are
				// using < and >, and not <= and =>).
				if(minX < eMaxX && maxX > eMinX && minY < eMaxY && maxY > eMinY)
					return true;
			}
			return false;
		}
	}
}