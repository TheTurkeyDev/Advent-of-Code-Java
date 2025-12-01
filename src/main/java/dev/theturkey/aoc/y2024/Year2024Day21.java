package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Year2024Day21 extends AOCPuzzle
{

	private static final char[][] keypad = {
			{'7', '8', '9'},
			{'4', '5', '6'},
			{'1', '2', '3'},
			{' ', '0', 'A'}
	};

	private static final char[][] controller = {
			{' ', '^', 'A'},
			{'<', 'v', '>'}
	};

	public Year2024Day21()
	{
		super("2024", "21");
	}

	@Override
	public void solve(List<String> input)
	{
		long part1 = 0;
		long part2 = 0;
		for(String inp : input)
		{
			long num = Long.parseLong(inp.substring(0, inp.length() - 1));
			part1 += num * getFinalCountForXRobots(inp, 3, 3);
			part2 += num * getFinalCountForXRobots(inp, 26, 26);
		}
		lap(part1);
		lap(part2);
	}

	private static final Map<String, Long> cache = new HashMap<>();

	private long getFinalCountForXRobots(String str, int depthToGo, int numLevels)
	{
		if(depthToGo == 0 || str.equals("A"))
			return str.length();

		String key = str + "-" + depthToGo;
		if(cache.containsKey(key))
			return cache.get(key);

		long total = 0;
		for(String ss : str.split("(?<=A)")) // Look-ahead so that we keep the A
		{
			Point p = depthToGo == numLevels ? new Point(3, 2) : new Point(0, 2);
			StringBuilder finalStr = new StringBuilder();
			for(char c : ss.toCharArray())
			{
				ReturnInfo afterMove = getMovesForKeyPadBtn(p, c, depthToGo == numLevels);
				finalStr.append(afterMove.buttonsPressed);
				p = afterMove.p;
			}
			total += getFinalCountForXRobots(finalStr.toString(), depthToGo - 1, numLevels);
		}
		cache.put(key, total);
		return total;
	}

	private ReturnInfo getMovesForKeyPadBtn(Point p, char b, boolean useKeyPad)
	{
		char[][] keys = useKeyPad ? keypad : controller;
		Point dest = new Point(-1, -1);
		for(int row = 0; row < keys.length; row++)
			for(int col = 0; col < keys[0].length; col++)
				if(keys[row][col] == b)
					dest = new Point(row, col);

		int colDiff = dest.col() - p.col();
		int rowDiff = dest.row() - p.row();
		String udBtns = (rowDiff < 0 ? "^" : "v").repeat(Math.abs(rowDiff));
		String lrBtns = (colDiff < 0 ? "<" : ">").repeat(Math.abs(colDiff));

		String path;
		if(useKeyPad && p.row() == 3 && dest.col() == 0)
			path = udBtns + lrBtns + "A";
		else if(useKeyPad && p.col() == 0 && dest.row() == 3)
			path = lrBtns + udBtns + "A";
		else if(!useKeyPad && p.col() == 0)
			path = lrBtns + udBtns + "A";
		else if(!useKeyPad && dest.col() == 0)
			path = udBtns + lrBtns + "A";
		else if(colDiff < 0)
			path = lrBtns + udBtns + "A";
		else
			path = udBtns + lrBtns + "A";

		return new ReturnInfo(dest, path);
	}

	private record ReturnInfo(Point p, String buttonsPressed)
	{

	}
}