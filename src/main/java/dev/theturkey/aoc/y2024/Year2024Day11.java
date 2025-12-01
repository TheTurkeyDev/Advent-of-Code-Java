package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.HashMap;
import java.util.List;

public class Year2024Day11 extends AOCPuzzle
{
	public static final HashMap<String, Long> cache = new HashMap<>();

	public Year2024Day11()
	{
		super("2024", "11");
	}

	public long calcNumStones(long stone, int blinkTimes)
	{
		if(blinkTimes == 0)
			return 1;

		String key = stone + "-" + blinkTimes;
		if(cache.containsKey(key))
			return cache.get(key);

		if(stone == 0)
		{
			long ans = calcNumStones(1, blinkTimes - 1);
			cache.put(key, ans);
			return ans;
		}

		String stoneStr = String.valueOf(stone);
		if(stoneStr.length() % 2 == 0)
		{
			long left = Long.parseLong(stoneStr.substring(0, stoneStr.length() / 2));
			long leftAns = calcNumStones(left, blinkTimes - 1);

			long right = Long.parseLong(stoneStr.substring(stoneStr.length() / 2));
			long rightAns = calcNumStones(right, blinkTimes - 1);

			cache.put(key, leftAns + rightAns);
			return leftAns + rightAns;
		}

		long ans = calcNumStones(stone * 2024, blinkTimes - 1);
		cache.put(key, ans);
		return ans;
	}

	@Override
	public void solve(List<String> input)
	{
		long part1 = 0;
		long part2 = 0;
		for(String s : input.get(0).split(" "))
		{
			int stone = Integer.parseInt(s);
			part1 += calcNumStones(stone, 25);
			part2 += calcNumStones(stone, 75);
		}

		lap(part1);
		lap(part2);
	}
}