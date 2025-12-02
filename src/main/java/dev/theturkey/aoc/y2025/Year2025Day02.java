package dev.theturkey.aoc.y2025;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Year2025Day02 extends AOCPuzzle
{
	public Year2025Day02()
	{
		super("2025", "2");
	}

	@Override
	public void solve(List<String> input)
	{
		String line = input.get(0);
		String[] rangesRaw = line.split(",");
		List<Range> ranges = Arrays.stream(rangesRaw).map(r ->
		{
			String[] parts = r.split("-");
			return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
		}).toList();

		long part1 = 0;
		for(Range range : ranges)
		{
			long number = range.start;
			while(number <= range.end)
			{
				String numStr = String.valueOf(number);
				if(numStr.length() % 2 == 1)
				{
					number = Long.parseLong("1" + "0".repeat(numStr.length()));
					continue;
				}

				String firstHalf = numStr.substring(0, numStr.length() / 2);
				String secondHalf = numStr.substring(numStr.length() / 2);
				if(firstHalf.equals(secondHalf))
				{
					part1 += number;
					String newNumber = String.valueOf(Long.parseLong(secondHalf) + 1);
					number = Long.parseLong(newNumber + newNumber);
				}
				else
				{
					long firstHalfNum = Long.parseLong(firstHalf);
					if(Long.parseLong(secondHalf) > firstHalfNum)
						number = Long.parseLong((firstHalfNum + 1) + String.valueOf(firstHalfNum + 1));
					else
						number = Long.parseLong(firstHalf + firstHalf);
				}
			}
		}

		lap(part1);

		long part2 = 0;
		for(Range range : ranges)
		{
			long number = range.start;
			while(number <= range.end)
			{
				String numStr = String.valueOf(number);
				for(int i = 1; i <= numStr.length() / 2; i++)
				{
					String pattern = numStr.substring(0, i);
					if(numStr.length() % pattern.length() != 0)
						continue;

					List<String> repeatChecks = new ArrayList<>();
					for(int j = i; j < numStr.length(); j += pattern.length())
						repeatChecks.add(numStr.substring(j, j + pattern.length()));

					boolean repeats = true;
					for(String check : repeatChecks)
					{
						if(!check.equals(pattern))
						{
							repeats = false;
							break;
						}
					}

					if(repeats)
					{
						part2 += number;
						break;
					}
				}
				number++;
			}
		}

		lap(part2);
	}

	private record Range(long start, long end)
	{

	}
}
