package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Year2024Day02 extends AOCPuzzle
{
	public Year2024Day02()
	{
		super("2024", "2");
	}

	@Override
	public void solve(List<String> input)
	{
		List<List<Integer>> parsed = new ArrayList<>();
		for(String s : input)
			parsed.add(Arrays.stream(s.split(" ")).map(Integer::parseInt).toList());

		int answer = 0;

		for(List<Integer> nums : parsed)
			if(isSafe(nums))
				answer++;

		lap(answer);

		// Inline version of above
		// lap(parsed.stream().filter(n -> getUnsafeIndex(n) == -1).count());

		answer = 0;
		for(List<Integer> nums : parsed)
		{
			//I hate this strategy, but my other attempts failed...
			int at = -1;
			boolean safe;
			do{
				List<Integer> numsCopy = new ArrayList<>(nums);
				if(at != -1)
					numsCopy.remove(at);
				safe = isSafe(numsCopy);
				at++;
			}while(!safe && at < nums.size());

			if(safe)
				answer++;
		}

		lap(answer);
	}

	public boolean isSafe(List<Integer> nums)
	{
		int firstDiff = nums.get(0) - nums.get(1);
		if(firstDiff == 0)
			return false;

		boolean inc = firstDiff < 0;
		for(int i = 0; i < nums.size() - 1; i++)
		{
			int diff = nums.get(i) - nums.get(i + 1);
			int diffAbs = Math.abs(diff);
			boolean valid = ((diff < 0 && inc) || (diff > 0 && !inc)) && diffAbs >= 1 && diffAbs <= 3;
			if(!valid)
				return false;
		}

		return true;
	}
}
