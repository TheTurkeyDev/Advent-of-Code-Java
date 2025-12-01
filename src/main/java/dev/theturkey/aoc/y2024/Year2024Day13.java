package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;
import dev.theturkey.aoc.PointL;

import java.util.ArrayList;
import java.util.List;

public class Year2024Day13 extends AOCPuzzle
{
	public Year2024Day13()
	{
		super("2024", "13");
	}

	@Override
	public void solve(List<String> input)
	{
		List<ClawMachine> clawMachines = new ArrayList<>();
		for(int i = 0; i < input.size(); i += 4)
		{
			String abs = input.get(i);
			int x = Integer.parseInt(abs.substring(12, abs.indexOf(",")));
			int y = Integer.parseInt(abs.substring(abs.indexOf(",") + 4));
			PointL aButton = new PointL(y, x);
			String bbs = input.get(i + 1);
			x = Integer.parseInt(bbs.substring(12, bbs.indexOf(",")));
			y = Integer.parseInt(bbs.substring(bbs.indexOf(",") + 4));
			PointL bButton = new PointL(y, x);
			String prize = input.get(i + 2);
			x = Integer.parseInt(prize.substring(9, prize.indexOf(",")));
			y = Integer.parseInt(prize.substring(prize.indexOf(",") + 4));
			PointL prizeLoc = new PointL(y, x);
			PointL prize2Loc = new PointL(y + 10000000000000L, x + 10000000000000L);
			clawMachines.add(new ClawMachine(aButton, bButton, prizeLoc, prize2Loc));
		}

		long coins1 = 0;
		long coins2 = 0;
		for(ClawMachine clawMachine : clawMachines)
		{
			long minCost = calcCost(clawMachine, clawMachine.prize);
			if(minCost != Long.MAX_VALUE)
				coins1 += minCost;

			minCost = calcCost(clawMachine, clawMachine.prize2);
			if(minCost != Long.MAX_VALUE)
				coins2 += minCost;
		}

		lap(coins1);
		lap(coins2);
	}

	private long calcCost(ClawMachine clawMachine, PointL prize)
	{
		long c1 = -prize.col();
		long a1 = clawMachine.aButton.col();
		long b1 = clawMachine.bButton.col();
		long c2 = -prize.row();
		long a2 = clawMachine.aButton.row();
		long b2 = clawMachine.bButton.row();
		long denom = (a1 * b2) - (a2 * b1);
		long i = ((b1 * c2) - (b2 * c1)) / denom;
		long j = ((a2 * c1) - (a1 * c2)) / denom;
		long checkCol = ((a1 * i) + (b1 * j));
		long checkRow = ((a2 * i) + (b2 * j));
		return checkRow != prize.row() || checkCol != prize.col() ? 0 : (i * 3) + j;
	}

	record ClawMachine(PointL aButton, PointL bButton, PointL prize, PointL prize2)
	{

	}
}
