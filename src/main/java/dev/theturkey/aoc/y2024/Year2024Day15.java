package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Direction;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Year2024Day15 extends AOCPuzzle
{
	public Year2024Day15()
	{
		super("2024", "15");
	}

	@Override
	public void solve(List<String> input)
	{
		int height = 0;
		for(int y = 0; y < input.size(); y++)
		{
			if(input.get(y).isBlank())
			{
				height = y;
				break;
			}
		}
		Point robot = new Point(0, 0);
		Point robot2 = new Point(0, 0);
		char[][] map = new char[height][input.get(0).length()];
		char[][] map2 = new char[height][input.get(0).length() * 2];
		for(int y = 0; y < height; y++)
		{
			String line = input.get(y);
			for(int x = 0; x < line.length(); x++)
			{
				char c = line.charAt(x);
				map[y][x] = c;
				switch(c)
				{
					case '#', '.' ->
					{
						map2[y][x * 2] = c;
						map2[y][x * 2 + 1] = c;
					}
					case 'O' ->
					{
						map2[y][x * 2] = '[';
						map2[y][x * 2 + 1] = ']';
					}
					case '@' ->
					{
						map2[y][x * 2] = c;
						map2[y][x * 2 + 1] = '.';
					}
				}
				if(c == '@')
				{
					robot = new Point(y, x);
					robot2 = new Point(y, x * 2);
				}
			}
		}

		List<Direction> steps = new ArrayList<>();
		for(int y = height + 1; y < input.size(); y++)
		{
			String line = input.get(y);
			for(int x = 0; x < line.length(); x++)
			{
				switch(line.charAt(x))
				{
					case '^' -> steps.add(Direction.UP);
					case '>' -> steps.add(Direction.RIGHT);
					case 'v' -> steps.add(Direction.DOWN);
					case '<' -> steps.add(Direction.LEFT);
				}
			}
		}

		for(Direction d : steps)
		{
			robot = move1(map, robot, d);
			robot2 = move2(map2, robot2, d);
		}

		long part1 = 0;
		for(int y = 0; y < map.length; y++)
			for(int x = 0; x < map[y].length; x++)
				if(map[y][x] == 'O')
					part1 += (100L * y) + x;
		lap(part1);

		long part2 = 0;
		for(int y = 0; y < map2.length; y++)
			for(int x = 0; x < map2[y].length; x++)
				if(map2[y][x] == '[')
					part2 += (100L * y) + x;
		lap(part2);
	}

	private Point move1(char[][] map, Point robot, Direction d)
	{
		boolean canMove = false;
		Point p = robot;
		List<Point> toMove = new ArrayList<>();
		do
		{
			p = p.directionOffset(d);
			if(map[p.row()][p.col()] == '.')
			{
				canMove = true;
				break;
			}
			toMove.add(p);
		} while(map[p.row()][p.col()] != '#');

		if(!canMove)
			return robot;

		for(int i = toMove.size() - 1; i >= 0; i--)
		{
			Point pt = toMove.get(i);
			Point np = pt.directionOffset(d);
			map[np.row()][np.col()] = map[pt.row()][pt.col()];
		}
		map[robot.row()][robot.col()] = '.';
		robot = robot.directionOffset(d);
		map[robot.row()][robot.col()] = '@';
		return robot;
	}

	private Point move2(char[][] map, Point robot, Direction d)
	{
		boolean canMove = false;
		Point p = robot;
		Set<Point> boxes = new HashSet<>();
		if(d == Direction.RIGHT || d == Direction.LEFT)
		{
			char c;
			do
			{
				p = p.directionOffset(d);
				c = map[p.row()][p.col()];
				if(c == '.')
				{
					canMove = true;
					break;
				}
				if(c == '[')
					boxes.add(p);
			} while(c != '#');
		}
		else
		{
			boolean wallFound = false;
			List<Point> lastBoxes = new ArrayList<>();
			p = p.directionOffset(d);
			char nextChar = map[p.row()][p.col()];
			if(nextChar == '[')
				lastBoxes.add(p);
			else if(nextChar == ']')
				lastBoxes.add(new Point(p.row(), p.col() - 1));
			else if(nextChar == '#')
				wallFound = true;
			else if(nextChar == '.')
				canMove = true;

			while(!wallFound && !lastBoxes.isEmpty())
			{
				boolean allEmpty = true;
				List<Point> nextBoxes = new ArrayList<>();
				for(Point box : lastBoxes)
				{
					for(int j = 0; j < 2; j++)
					{
						Point nextP = new Point(box.row(), box.col() + j).directionOffset(d);
						char nc = map[nextP.row()][nextP.col()];
						if(nc != '.')
							allEmpty = false;
						if(nc == '#')
						{
							wallFound = true;
							break;
						}

						if(nc == '[')
							nextBoxes.add(nextP);
						else if(nc == ']')
							nextBoxes.add(new Point(nextP.row(), nextP.col() - 1));
					}
				}

				boxes.addAll(lastBoxes);
				lastBoxes.clear();
				lastBoxes.addAll(nextBoxes);
				if(allEmpty)
				{
					canMove = true;
					break;
				}
			}
		}

		if(!canMove)
			return robot;

		//Clear out box locations
		for(Point boxP : boxes)
		{
			map[boxP.row()][boxP.col()] = '.';
			map[boxP.row()][boxP.col() + 1] = '.';
		}
		//Set new box locations
		for(Point boxP : boxes)
		{
			Point boxNP = boxP.directionOffset(d);
			map[boxNP.row()][boxNP.col()] = '[';
			map[boxNP.row()][boxNP.col() + 1] = ']';
		}

		map[robot.row()][robot.col()] = '.';
		robot = robot.directionOffset(d);
		map[robot.row()][robot.col()] = '@';
		return robot;
	}
}