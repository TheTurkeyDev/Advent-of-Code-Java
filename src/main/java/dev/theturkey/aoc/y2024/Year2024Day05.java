package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Year2024Day05 extends AOCPuzzle
{
	private static final Map<Integer, List<Integer>> afterRules = new HashMap<>();
	private static final Map<Integer, List<Integer>> beforeRules = new HashMap<>();

	public Year2024Day05()
	{
		super("2024", "5");
	}

	private int[] getInvalid(List<Integer> parts)
	{
		for(int i = 0; i < parts.size(); i++)
		{
			int num = parts.get(i);

			List<Integer> afterRuleForNum = afterRules.getOrDefault(num, new ArrayList<>());
			int invalidNum = parts.subList(0, i).stream().filter(afterRuleForNum::contains).findFirst().orElse(-1);
			if(invalidNum != -1)
				return new int[]{num, invalidNum};

			List<Integer> beforeRuleForNum = beforeRules.getOrDefault(num, new ArrayList<>());
			invalidNum = parts.subList(i + 1, parts.size()).stream().filter(beforeRuleForNum::contains).findFirst().orElse(-1);
			if(invalidNum != -1)
				return new int[]{num, invalidNum};
		}
		return new int[]{-1, -1};
	}

	@Override
	public void solve(List<String> input)
	{
		boolean rules = true;
		List<String> updates = new ArrayList<>();
		for(String s : input)
		{
			if(s.isBlank())
			{
				rules = false;
				continue;
			}

			if(rules)
			{
				String[] parts = s.split("\\|");
				int first = Integer.parseInt(parts[0]);
				int second = Integer.parseInt(parts[1]);

				afterRules.computeIfAbsent(first, i -> new ArrayList<>()).add(second);
				beforeRules.computeIfAbsent(second, i -> new ArrayList<>()).add(first);
			}
			else
			{
				updates.add(s);
			}
		}

		int answer = 0;
		int answer2 = 0;
		for(String s : updates)
		{
			List<Integer> parts = new ArrayList<>(Arrays.stream(s.split(",")).map(Integer::parseInt).toList());
			int[] invalid = getInvalid(parts);
			if(invalid[0] == -1)
			{
				answer += parts.get(parts.size() / 2);
			}
			else
			{
				while(invalid[0] != -1)
				{
					Collections.swap(parts, parts.indexOf(invalid[0]), parts.indexOf(invalid[1]));
					invalid = getInvalid(parts);
				}
				answer2 += parts.get(parts.size() / 2);
			}
		}

		lap(answer);
		lap(answer2);
	}
}
