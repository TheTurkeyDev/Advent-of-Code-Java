package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.List;

public class Year2024Day04 extends AOCPuzzle
{
	public Year2024Day04()
	{
		super("2024", "4");
	}

	@Override
	public void solve(List<String> input)
	{
		int height = input.size();
		int width = input.get(0).length();

		long count = 0;
		StringBuilder sb = new StringBuilder();
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				//Horiz
				if(x + 3 < width)
					count += getMatches(input.get(y).substring(x, x + 4));

				//Vert
				if(y + 3 < height)
				{
					sb.setLength(0);
					for(int i = 0; i < 4; i++)
						sb.append(input.get(y + i).charAt(x));
					count += getMatches(sb.toString());
				}

				//R->L Diag
				if(y + 3 < height && x + 3 < width)
				{
					sb.setLength(0);
					for(int i = 0; i < 4; i++)
						sb.append(input.get(y + i).charAt(x + i));
					count += getMatches(sb.toString());
				}

				//L->R Diag
				if(y + 3 < height && x - 3 >= 0)
				{
					sb.setLength(0);
					for(int i = 0; i < 4; i++)
						sb.append(input.get(y + i).charAt(x - i));
					count += getMatches(sb.toString());
				}
			}
		}

		lap(count);

		count = 0;
		StringBuilder lrSB = new StringBuilder();
		StringBuilder rlSB = new StringBuilder();
		for(int y = 0; y < height - 2; y++)
		{
			for(int x = 0; x < width - 2; x++)
			{
				lrSB.setLength(0);
				for(int i = 0; i < 3; i++)
					lrSB.append(input.get(y + i).charAt(x + i));

				rlSB.setLength(0);
				for(int i = 0; i < 3; i++)
					rlSB.append(input.get(y + i).charAt((x + 2) - i));

				if(getMatches2(lrSB.toString()) && getMatches2(rlSB.toString()))
					count++;
			}
		}

		lap(count);
	}

	private int getMatches(String s)
	{
		return (s.equals("XMAS") ? 1 : 0) + (s.equals("SAMX") ? 1 : 0);
	}


	private boolean getMatches2(String s)
	{
		return s.equals("MAS") || s.equals("SAM");
	}
}
