package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.List;

public class Year2024Day03 extends AOCPuzzle
{
	public Year2024Day03()
	{
		super("2024", "3");
	}

	@Override
	public void solve(List<String> input)
	{
		int sum = 0;
		int sum2 = 0;
		boolean ignore = false;
		for(String l : input)
		{
			for(int i = 0; i < l.length(); i++)
			{
				if(l.charAt(i) == 'm' || l.charAt(i) == 'd')
				{
					int endIndex = l.indexOf(")", i);
					String part = l.substring(i, endIndex + 1);
					if(part.matches("mul\\(\\d+,\\d+\\)"))
					{
						String[] parts = part.substring(4, part.length() - 1).split(",");
						int prod = Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
						sum += prod;
						if(!ignore)
							sum2 += prod;
						i = endIndex;
					}
					else if(part.matches("do\\(\\)"))
					{
						ignore = false;
						i = endIndex;
					}
					else if(part.matches("don't\\(\\)"))
					{
						ignore = true;
						i = endIndex;
					}
				}
			}
		}

		lap(sum);
		lap(sum2);
	}
}
