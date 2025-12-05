package dev.theturkey.aoc.y2025;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.List;

public class Year2025Day05 extends AOCPuzzle
{
	public Year2025Day05()
	{
		super("2025", "5");
	}

	@Override
	public void solve(List<String> input)
	{
		int part1 = 0;
		List<Range> ranges = new ArrayList<>();
		boolean parsingRanges = true;

		for(String line : input)
		{
			if(line.isBlank())
			{
				parsingRanges = false;
				continue;
			}

			if(parsingRanges)
			{
				String[] parts = line.split("-");
				long low = Long.parseLong(parts[0]);
				long high = Long.parseLong(parts[1]);
				ranges.add(new Range(low, high));
			}
			else
			{
				long id = Long.parseLong(line);
				for(Range r : ranges)
				{
					if(r.low <= id && r.high >= id)
					{
						part1++;
						break;
					}
				}
			}
		}

		lap(part1);

		List<Range> toAdd = new ArrayList<>(ranges);
		ranges.clear();
		while(!toAdd.isEmpty())
		{
			Range r = toAdd.remove(0);
			int updated = -1;
			for(int i = 0; i < ranges.size(); i++)
			{
				Range r2 = ranges.get(i);
				if(((r2.low <= r.low && r2.high >= r.low) || (r2.low <= r.high && r2.high >= r.high)) || ((r.low <= r2.low && r.high >= r2.low) || (r.low <= r2.high && r.high >= r2.high)))
				{
					ranges.set(i, new Range(Math.min(r2.low, r.low), Math.max(r2.high, r.high)));
					updated = i;
					break;
				}
			}
			if(updated == -1)
			{
				ranges.add(r);
			}
			else
			{
				List<Range> toRemove = ranges.subList(updated, ranges.size());
				toAdd.addAll(toRemove);
				ranges.removeAll(toRemove);
			}
		}

		long part2 = 0;
		for(Range r : ranges)
			part2 += (r.high - r.low) + 1;
		lap(part2);
	}

	private record Range(long low, long high)
	{

	}
}