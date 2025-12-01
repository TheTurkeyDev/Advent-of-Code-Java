package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Year2024Day24 extends AOCPuzzle
{
	public Year2024Day24()
	{
		super("2024", "24");
	}

	@Override
	public void solve(List<String> input)
	{
		Map<String, Node> nodes = new HashMap<>();
		boolean init = true;
		for(String s : input)
		{
			if(s.isBlank())
			{
				init = false;
				continue;
			}

			Node node = new Node();
			if(init)
			{
				String[] parts = s.split(": ");
				node.name = parts[0];
				node.answer = parts[1].equals("1");
			}
			else
			{
				String[] parts = s.split(" ");
				node.name = parts[4];
			}
			nodes.put(node.name, node);
		}


		int maxZ = 0;
		init = true;
		for(String s : input)
		{
			if(s.isBlank())
			{
				init = false;
				continue;
			}

			if(init)
				continue;

			String[] parts = s.split(" ");
			Node node = nodes.get(parts[4]);
			node.left = nodes.get(parts[0]);
			node.right = nodes.get(parts[2]);
			node.op = parts[1];
			if(node.name.charAt(0) == 'z')
			{
				int num = Integer.parseInt(node.name.substring(1));
				if(num > maxZ)
					maxZ = num;
			}
		}

		Set<String> badNodes = new HashSet<>(); //Part 2... Don't ask
		for(Node n : nodes.values())
		{
			n.solve();
			if(n.name.startsWith("z") && !n.name.equals("z00") && !n.name.equals("z45"))
				badNodes.addAll(n.validate());
		}

		long answer = 0;
		for(int i = 0; i <= maxZ; i++)
			answer += (nodes.get("z" + String.format("%02d", i)).answer ? 1L : 0L) << i;
		lap(answer);

		List<String> bnList = new ArrayList<>(badNodes);
		Collections.sort(bnList);
		lap(String.join(",", bnList));
	}

	private static class Node
	{
		public String name;
		public Node left;
		public Node right;
		public String op;
		public boolean answer;
		public boolean solved;

		public void solve()
		{
			if(left == null || solved)
				return;

			left.solve();
			right.solve();

			answer = switch(op)
			{
				case "AND" -> left.answer && right.answer;
				case "OR" -> left.answer || right.answer;
				case "XOR" -> left.answer ^ right.answer;
				default -> false;
			};
			solved = true;
		}

		public Set<String> validate()
		{
			Set<String> toRet = new HashSet<>();
			if(left == null)
				return toRet;

			if(name.startsWith("z"))
			{
				String ln = left.name;
				String rn = right.name;
				if(!op.equals("XOR") || ln.startsWith("x") || rn.startsWith("x") || ln.startsWith("y") || rn.startsWith("y"))
					toRet.add(name);
			}
			else if(left.op != null)
			{
				if(op.equals("OR") && (!left.op.equals("AND") || !right.op.equals("AND")))
				{
					toRet.add(left.op.equals("AND") ? right.name : left.name);
				}
				else if(op.equals("AND") && ((left.op.equals("AND") && !left.left.name.startsWith("x")) || (right.op.equals("AND") && right.left.name.startsWith("x"))))
				{
					toRet.add(left.op.equals("AND") && !left.left.name.startsWith("x") ? left.name : right.name);
				}
				else if(left.op.equals("XOR"))
				{
					String llo = left.left.op;
					String lro = left.right.op;
					if((llo != null && (llo.equals("OR") || llo.equals("XOR"))) && lro != null && (lro.equals("OR") || lro.equals("XOR")))
						toRet.add(left.name);
				}
				else if(right.op.equals("XOR"))
				{
					String rlo = right.left.op;
					String rro = right.right.op;
					if((rlo != null && (rlo.equals("OR") || rlo.equals("XOR"))) && rro != null && (rro.equals("OR") || rro.equals("XOR")))
						toRet.add(right.name);
				}
			}

			toRet.addAll(left.validate());
			toRet.addAll(right.validate());
			return toRet;
		}
	}
}