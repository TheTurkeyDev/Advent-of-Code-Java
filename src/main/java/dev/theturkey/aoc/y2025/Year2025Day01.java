package dev.theturkey.aoc.y2025;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.List;

public class Year2025Day01 extends AOCPuzzle
{
	public Year2025Day01()
	{
		super("2025", "1");
	}

	@Override
	public void solve(List<String> input)
	{
		int part1 = 0;
		int part2 = 0;
		int dialNum = 50;

		for(String s : input)
		{
			char dir = s.charAt(0);
			int amount = Integer.parseInt(s.substring(1));

			if(dir == 'R')
			{
				dialNum = dialNum + amount;
				if(dialNum >= 100)
				{
					part2 += (dialNum / 100);
					dialNum %= 100;
				}
			}
			else
			{
				boolean atO = dialNum == 0;
				dialNum = dialNum - amount;
				if(dialNum <= 0)
				{
					part2 += Math.abs(dialNum / 100) + (atO ? 0: 1);
					dialNum %= 100;
					if(dialNum < 0)
						dialNum += 100;
				}
			}

			if(dialNum == 0)
				part1++;
		}

		lap(part1);
		lap(part2);
	}
}
