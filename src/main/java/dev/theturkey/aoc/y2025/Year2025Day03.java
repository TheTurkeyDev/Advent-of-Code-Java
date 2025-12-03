package dev.theturkey.aoc.y2025;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.List;

public class Year2025Day03 extends AOCPuzzle
{
	public Year2025Day03()
	{
		super("2025", "3");
	}

	@Override
	public void solve(List<String> input)
	{
		long part1 = 0;
		for(String s : input)
			part1 += getJoltage(s, 2);
		lap(part1);

		long part2 = 0;
		for(String s : input)
			part2 += getJoltage(s, 12);
		lap(part2);
	}

	private long getJoltage(String line, int numBatteries)
	{
		if(line.length() == numBatteries)
			return Long.parseLong(line);

		int numBatOneLess = numBatteries - 1;

		int largestIndex = 0;
		char largest = '0';
		for(int i = 0; i < line.length() - numBatOneLess; i++)
		{
			char c = line.charAt(i);
			if(largest < c)
			{
				largest = c;
				largestIndex = i;
			}
		}

		String lineRemain = line.substring(largestIndex + 1);
		long joltage = largest - '0';
		if(numBatteries > 1)
			joltage = (joltage * (long) Math.pow(10, numBatOneLess)) + getJoltage(lineRemain, numBatOneLess);
		return joltage;
	}
}
