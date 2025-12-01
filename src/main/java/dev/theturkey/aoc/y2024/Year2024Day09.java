package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.List;

public class Year2024Day09 extends AOCPuzzle
{
	public Year2024Day09()
	{
		super("2024", "9");
	}


	@Override
	public void solve(List<String> input)
	{
		String map = input.get(0);
		List<Integer> blocksOrig = new ArrayList<>();
		int id = 0;
		boolean file = true;
		for(char c : map.toCharArray())
		{
			for(int i = 0; i < c - 48; i++)
				blocksOrig.add(file ? id : -1);

			if(file)
				id++;
			file = !file;
		}

		List<Integer> blocks = new ArrayList<>(blocksOrig);
		int lastFree = 0;
		for(int i = blocks.size() - 1; i >= 0; i--)
		{
			int tooMove = blocks.remove(i);
			if(tooMove == -1)
				continue;

			boolean moved = false;
			for(int j = lastFree; j < i; j++)
			{
				if(blocks.get(j) == -1)
				{
					blocks.set(j, tooMove);
					moved = true;
					lastFree = j;
					break;
				}
			}

			if(!moved)
			{
				blocks.add(tooMove);
				break;
			}
		}

		long checkSum = 0;
		for(int i = 0; i < blocks.size(); i++)
			checkSum += (long) i * blocks.get(i);

		lap(checkSum);

		int highestCheckedId = Integer.MAX_VALUE;
		blocks = new ArrayList<>(blocksOrig);
		for(int i = blocks.size() - 1; i >= 0; i--)
		{
			int tooMove = blocks.get(i);
			if(tooMove == -1 || tooMove >= highestCheckedId)
				continue;

			int tooMoveLength = 1;
			while(i - tooMoveLength >= 0 && blocks.get(i - tooMoveLength) == tooMove)
				tooMoveLength++;

			for(int j = 0; j < i; j++)
			{
				int freeSpace = 1;
				while(j + freeSpace < blocks.size() && blocks.get(j + freeSpace) == -1)
					freeSpace++;
				if(blocks.get(j) == -1 && freeSpace >= tooMoveLength)
				{
					for(int k = 0; k < tooMoveLength; k++)
					{
						blocks.set(j + k, tooMove);
						blocks.set(i - k, -1);
					}
					break;
				}
			}

			highestCheckedId = tooMove;
		}

		checkSum = 0;
		for(int i = 0; i < blocks.size(); i++)
			if(blocks.get(i) != -1)
				checkSum += (long) i * blocks.get(i);

		lap(checkSum);
	}
}
