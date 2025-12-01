package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.List;

public class Year2024Day01 extends AOCPuzzle
{
	public Year2024Day01()
	{
		super("2024", "1");
	}

	@Override
	public void solve(List<String> input)
	{
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();

		for(String in : input)
		{
			String[] parts = in.split(" {3}");
			left.add(Integer.parseInt(parts[0]));
			right.add(Integer.parseInt(parts[1]));
		}

		left.sort(Integer::compareTo);
		right.sort(Integer::compareTo);

		int answer = 0;
		for(int i = left.size() - 1; i >= 0; i--)
			answer += Math.abs(left.get(i) - right.get(i));
		lap(answer);

		answer = 0;
		for(int i = left.size() - 1; i >= 0; i--)
		{
			int leftNum = left.get(i);
			answer += (int) (leftNum * right.stream().filter(v -> v == leftNum).count());
		}
		lap(answer);
	}
}
