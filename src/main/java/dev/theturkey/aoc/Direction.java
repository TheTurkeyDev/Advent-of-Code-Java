package dev.theturkey.aoc;

public enum Direction
{
	UP,
	RIGHT,
	DOWN,
	LEFT;

	public Direction cycleCW()
	{
		return switch(this)
		{
			case UP -> RIGHT;
			case RIGHT -> DOWN;
			case DOWN -> LEFT;
			case LEFT -> UP;
		};
	}

	public Direction cycleCCW()
	{
		return switch(this)
		{
			case UP -> LEFT;
			case RIGHT -> UP;
			case DOWN -> RIGHT;
			case LEFT -> DOWN;
		};
	}
}
