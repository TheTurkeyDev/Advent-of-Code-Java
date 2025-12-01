package dev.theturkey.aoc.y2024;

import dev.theturkey.aoc.AOCPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Year2024Day17 extends AOCPuzzle
{
	public Year2024Day17()
	{
		super("2024", "17");
	}

	private long regA;
	private long regB;
	private long regC;

	@Override
	public void solve(List<String> input)
	{
		long regADef = Long.parseLong(input.get(0).substring(12));
		long regBDef = Long.parseLong(input.get(1).substring(12));
		long regCDef = Long.parseLong(input.get(2).substring(12));

		String[] expected = input.get(4).substring(9).split(",");
		List<Integer> program = Arrays.stream(expected).map(Integer::parseInt).toList();
		List<String> output = run(regADef, regBDef, regCDef, program, null);
		lap(String.join(",", output));

		long regVal = 0b1001011110111110100; //This number was reversed engineered based on increasingly "good" and longer output's.... It probably only works for my input
		do
		{
			regVal += 0b10000000000000000000;
			output = run(regVal, regBDef, regCDef, program, expected);
		} while(!areSame(expected, output));

		lap(regVal);
	}

	private boolean areSame(String[] expected, List<String> out)
	{
		if(expected.length != out.size())
			return false;

		for(int i = 0; i < out.size(); i++)
			if(!out.get(i).equals(expected[i]))
				return false;
		return true;
	}

	private List<String> run(long regADef, long regBDef, long regCDef, List<Integer> program, String[] expected)
	{
		regA = regADef;
		regB = regBDef;
		regC = regCDef;
		List<String> output = new ArrayList<>();
		int pc = 0;

		while(pc < program.size())
		{
			int operand = program.get(pc + 1);
			switch(program.get(pc))
			{
				case 0 -> regA = regA / (int) (Math.pow(2, getComboOperandValue(operand)));
				case 1 -> regB = regB ^ operand;
				case 2 -> regB = getComboOperandValue(operand) % 8;
				case 3 ->
				{
					if(regA != 0)
					{
						pc = operand;
						continue;
					}
				}
				case 4 -> regB = regB ^ regC;
				case 5 ->
				{
					String nextOut = String.valueOf(getComboOperandValue(operand) % 8);
					output.add(nextOut);
					if(expected != null && !expected[output.size() - 1].equals(nextOut))
						return output;
				}
				case 6 -> regB = regA / (int) (Math.pow(2, getComboOperandValue(operand)));
				case 7 -> regC = regA / (int) (Math.pow(2, getComboOperandValue(operand)));
			}

			pc += 2;
		}
		return output;
	}

	private long getComboOperandValue(int operand)
	{
		return switch(operand)
		{
			case 0, 1, 2, 3 -> operand;
			case 4 -> regA;
			case 5 -> regB;
			case 6 -> regC;
			default -> 0;
		};
	}
}