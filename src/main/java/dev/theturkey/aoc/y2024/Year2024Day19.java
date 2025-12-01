package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Year2024Day19 extends AOCPuzzle
{
	private static final Map<String, Long> cache = new HashMap<>();

	public Year2024Day19()
	{
		super("2024", "19");
	}

	private long getPatternCompositions(String pattern, List<String> towels)
	{
		if(cache.containsKey(pattern))
			return cache.get(pattern);

		long ret = 0;
		for(String t : towels)
			if(pattern.startsWith(t))
				ret += getPatternCompositions(pattern.substring(t.length()), towels);
		cache.put(pattern, ret);
		return ret;
	}

	@Override
	public void solve(List<String> input)
	{
		List<String> towels = Arrays.stream(input.get(0).split(", ")).toList();
		cache.put("", 1L);
		int part1 = 0;
		long part2 = 0;
		for(String pattern : input.subList(2, input.size()))
		{
			long paths = getPatternCompositions(pattern, towels);
			part1 += paths > 0 ? 1 : 0;
			part2 += paths;
		}
		lap(part1);
		lap(part2);
	}
}