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
			int amount = Integer.parseInt(s.substring(1));
			part2 += (amount / 100);

			if(s.charAt(0) == 'R')
			{
				dialNum = dialNum + (amount % 100);
				if(dialNum > 99)
				{
					part2 += 1;
					dialNum -= 100;
				}
			}
			else
			{
				boolean at0 = dialNum == 0;
				dialNum = dialNum - (amount % 100);
				if(dialNum < 0)
				{
					part2 += at0 ? 0 : 1;
					dialNum += 100;
				}
				else if(dialNum == 0)
				{
					part2 += 1;
				}
			}

			if(dialNum == 0)
				part1++;
		}

		lap(part1);
		lap(part2);
	}
}
