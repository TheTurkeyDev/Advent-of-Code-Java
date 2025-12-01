package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Year2024Day23 extends AOCPuzzle
{
	private static final Map<String, List<String>> paths = new HashMap<>();

	public Year2024Day23()
	{
		super("2024", "23");
	}

	@Override
	public void solve(List<String> input)
	{
		for(String s : input)
		{
			String[] parts = s.split("-");
			paths.computeIfAbsent(parts[0], k -> new ArrayList<>()).add(parts[1]);
			paths.computeIfAbsent(parts[1], k -> new ArrayList<>()).add(parts[0]);
		}

		Set<String> possible = new HashSet<>();
		for(String n1 : paths.keySet())
		{
			List<String> n1Dests = paths.get(n1);
			for(int i = 0; i < n1Dests.size(); i++)
			{
				String n2 = n1Dests.get(i);
				boolean startWithT = n1.startsWith("t") || n2.startsWith("t");
				for(int j = i + 1; j < n1Dests.size(); j++)
				{
					String n3 = n1Dests.get(j);
					if((!startWithT && !n3.startsWith("t")) || !paths.get(n2).contains(n3))
						continue;

					String key = String.join(",", Stream.of(n1, n2, n3).sorted().toList());
					possible.add(key);
				}
			}
		}
		lap(possible.size());

		//Add all nodes to their own path for part 2 to work
		for(String n : paths.keySet())
			paths.get(n).add(n);

		List<String> max = new ArrayList<>();
		for(String n1 : paths.keySet())
		{
			List<String> nodes = paths.get(n1);
			for(String n2 : nodes)
			{
				if(n1.equals(n2))
					continue;

				List<String> interAll = new ArrayList<>(paths.get(n2));
				interAll.retainAll(nodes);
				for(int i = interAll.size(); i >= 0; i--)
				{
					if(i >= interAll.size())
						continue;
					interAll.retainAll(paths.get(interAll.get(i)));
				}
				if(interAll.size() > max.size())
					max = interAll;
			}
		}
		Collections.sort(max);
		lap(String.join(",", max));
	}
}