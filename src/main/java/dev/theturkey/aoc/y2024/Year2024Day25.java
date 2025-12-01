package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Year2024Day25 extends AOCPuzzle
{
	public Year2024Day25()
	{
		super("2024", "25");
	}

	@Override
	public void solve(List<String> input)
	{
		List<int[]> keys = new ArrayList<>();
		List<int[]> locks = new ArrayList<>();
		boolean defined = false;
		boolean isKey = false;
		int[] heights = new int[5];
		int lightHeight = 0;

		// This is so ugly because of how I load in inputs...
		for(String line : input)
		{
			if(line.isBlank())
			{
				if(isKey)
					keys.add(heights);
				else
					locks.add(heights);
				defined = false;
				continue;
			}

			if(!defined)
			{
				lightHeight = 0;
				heights = new int[5];
				isKey = !line.equals("#####");
				defined = true;
				continue;
			}

			lightHeight++;

			if(isKey)
			{
				for(int i = 0; i < line.length(); i++)
				{
					if(heights[i] > 0)
						continue;

					if(line.charAt(i) == '#')
						heights[i] = 6 - lightHeight;
				}
			}
			else
			{
				for(int i = 0; i < line.length(); i++)
					if(line.charAt(i) == '#')
						heights[i] = lightHeight;
			}
		}

		if(isKey)
			keys.add(heights);
		else
			locks.add(heights);

		int answer = 0;
		for(int[] key : keys)
		{
			for(int[] lock : locks)
			{
				boolean valid = true;
				for(int i = 0; i < key.length; i++)
				{
					if(key[i] + lock[i] > 5)
					{
						valid = false;
						break;
					}
				}
				String keyStr = String.join(",", Arrays.stream(key).mapToObj(String::valueOf).toList());
				String lockStr = String.join(",", Arrays.stream(lock).mapToObj(String::valueOf).toList());

				if(valid)
					answer++;
			}
		}

		lap(answer);
		lap("All 50 stars!");
	}
}