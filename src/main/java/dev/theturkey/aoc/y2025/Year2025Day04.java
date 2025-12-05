package dev.theturkey.aoc.y2025;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.List;

public class Year2025Day04 extends AOCPuzzle
{
	public Year2025Day04()
	{
		super("2025", "4");
	}

	@Override
	public void solve(List<String> input)
	{
		boolean[][] rolls = new boolean[input.size()][input.get(0).length()];
		for(int row = 0; row < input.size(); row++)
		{
			String line = input.get(row);
			for(int col = 0; col < line.length(); col++)
				rolls[row][col] = line.charAt(col) == '@';
		}

		long part1 = 0;
		for(int row = 0; row < input.size(); row++)
		{
			String line = input.get(row);
			for(int col = 0; col < line.length(); col++)
			{
				if(!rolls[row][col])
					continue;

				if(getNumAdj(rolls, row, col, input.size(), line.length()) < 4)
					part1++;
			}
		}

		lap(part1);

		int part2 = 0;
		boolean removed = true;
		while(removed)
		{
			removed = false;
			for(int row = 0; row < input.size(); row++)
			{
				String line = input.get(row);
				for(int col = 0; col < line.length(); col++)
				{
					if(!rolls[row][col])
						continue;

					if(getNumAdj(rolls, row, col, input.size(), line.length()) < 4)
					{
						part2++;
						removed = true;
						rolls[row][col] = false;
					}
				}
			}
		}

		lap(part2);
	}

	private int getNumAdj(boolean[][] rolls, int row, int col, int maxRow, int maxCol)
	{
		int adj = 0;
		for(int rowOff = -1; rowOff < 2; rowOff++)
		{
			for(int colOff = -1; colOff < 2; colOff++)
			{
				if(rowOff == 0 && colOff == 0)
					continue;
				int newRow = rowOff + row;
				int newCol = colOff + col;

				if(newRow < 0 || newCol < 0 || newRow >= maxRow || newCol >= maxCol)
					continue;

				if(rolls[newRow][newCol])
					adj++;
			}
		}

		return adj;
	}
}