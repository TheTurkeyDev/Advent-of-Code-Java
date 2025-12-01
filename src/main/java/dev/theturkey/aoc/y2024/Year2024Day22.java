package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Year2024Day22 extends AOCPuzzle
{

	private static final Map<String, Long> bananasForChanges = new HashMap<>();

	public Year2024Day22()
	{
		super("2024", "22");
	}

	@Override
	public void solve(List<String> input)
	{
		long answer = 0;
		for(String s : input)
		{
			long num = Long.parseLong(s);
			answer += calcSecretX(num);
		}
		lap(answer);

		long value = bananasForChanges.values().stream().max((a, b) -> Math.toIntExact(a - b)).orElse(-1L);
		lap(value);
	}

	private long calcSecretX(long secret)
	{
		long last = -1;
		List<Long> runningChanges = new ArrayList<>();
		List<String> usedKeys = new ArrayList<>();
		long part1 = 0;
		for(int i = 0; i < 2001; i++)
		{
			long step1 = prune(mix(secret, secret * 64L));
			long step2 = prune(mix(step1, step1 / 32L));
			secret = prune(mix(step2, step2 * 2048L));

			if(i == 1999)
				part1 = secret;

			long cost = secret % 10;
			if(last != -1)
			{
				runningChanges.add(cost - last);
				if(runningChanges.size() > 4)
					runningChanges.remove(0);

				StringBuilder k = new StringBuilder();
				for(long key : runningChanges)
					k.append(key).append(",");
				String kStr = k.toString();
				if(!usedKeys.contains(kStr) && runningChanges.size() == 4)
				{
					bananasForChanges.compute(kStr, (key, v) -> v == null ? cost : v + cost);
					usedKeys.add(kStr);
				}
			}
			last = cost;
		}
		return part1;
	}

	private long mix(long secret, long mix)
	{
		return secret ^ mix;
	}

	private long prune(long secret)
	{
		return secret % 16777216;
	}
}