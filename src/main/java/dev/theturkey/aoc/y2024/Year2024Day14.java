package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Point;

import java.util.ArrayList;
import java.util.List;

public class Year2024Day14 extends AOCPuzzle
{
	public static int width = 101;
	public static int midX = 50;
	public static int height = 103;
	public static int midY = 51;

	public Year2024Day14()
	{
		super("2024", "14");
	}

	private void moveRobotXTimes(Robot robot, int times)
	{
		Point np = robot.pos.add(robot.vel.mul(times));
		int newX = np.col() % width;
		if(newX < 0)
			newX += width;
		int newY = np.row() % height;
		if(newY < 0)
			newY += height;

		robot.pos = new Point(newY, newX);
	}

	@Override
	public void solve(List<String> input)
	{
		List<Robot> robots = new ArrayList<>();
		List<Robot> robots2 = new ArrayList<>();
		for(String line : input)
		{
			String[] parts = line.split(" ");
			String[] posParts = parts[0].substring(2).split(",");
			String[] velParts = parts[1].substring(2).split(",");
			Point pos = new Point(Integer.parseInt(posParts[1]), Integer.parseInt(posParts[0]));
			Point vel = new Point(Integer.parseInt(velParts[1]), Integer.parseInt(velParts[0]));
			robots.add(new Robot(pos, vel));
			robots2.add(new Robot(pos, vel));
		}

		for(Robot robot : robots)
			moveRobotXTimes(robot, 100);

		int[] quads = new int[4];
		for(Robot robot : robots)
		{
			boolean upper = robot.pos.row() < midY;
			boolean lower = robot.pos.row() > midY;
			boolean left = robot.pos.col() < midX;
			boolean right = robot.pos.col() > midX;
			if(upper && left)
				quads[0]++;
			else if(upper && right)
				quads[1]++;
			else if(lower && left)
				quads[2]++;
			else if(lower && right)
				quads[3]++;
		}

		lap(quads[0] * quads[1] * quads[2] * quads[3]);

		Robot r = robots2.get(0);
		Point start = r.pos;
		int loop = 0;
		do
		{
			moveRobotXTimes(r, 1);
			loop++;
		} while(!r.pos.equals(start));

		lap(loop);//Not the actual answer, just how long till it loops back to the beginning
	}

	private static class Robot
	{
		public Point pos;
		public Point vel;

		public Robot(Point pos, Point vel)
		{
			this.pos = pos;
			this.vel = vel;
		}
	}
}
