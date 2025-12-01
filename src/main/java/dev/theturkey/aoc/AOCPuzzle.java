package dev.theturkey.aoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AOCPuzzle
{
	public final String year;
	public final String day;
	public static boolean showAnswers = true;
	private int part = 1;
	private long timerStart;

	public AOCPuzzle(String year, String day)
	{
		this.year = year;
		this.day = day;
		System.out.println("===== Day " + day + " =====");
		String path = year + "/day" + day + ".txt";
		try(InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path))
		{
			if(is == null)
				throw new FileNotFoundException();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			List<String> inputLines = new ArrayList<>();
			String line;
			while((line = reader.readLine()) != null)
				inputLines.add(line);

			reader.close();

			timerStart = System.nanoTime();
			solve(inputLines);
		} catch(Exception e)
		{
			System.err.println("File not found?");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public abstract void solve(List<String> input);

	public void lap(int answer)
	{
		lap(String.valueOf(answer));
	}

	public void lap(long answer)
	{
		lap(String.valueOf(answer));
	}

	public void lap(String answer)
	{
		long timeSpent = (System.nanoTime() - timerStart) / 1000;
		System.out.println("Part " + part + ": " + (showAnswers ? answer : "[Redacted]") + ", Duration: " + timeToString(timeSpent));
		timerStart = System.nanoTime();
		part++;
	}

	public String timeToString(long timeSpent)
	{
		if(timeSpent < 1000)
			return timeSpent + "µs";
		if(timeSpent < 1000000)
			return (timeSpent / 1000.0) + "ms";
		return (timeSpent / 1000000.0) + "s";
	}

	public List<Integer> convertToInts(List<String> input)
	{
		List<Integer> ints = new ArrayList<>();
		for(String s : input)
			ints.add(Integer.parseInt(s));
		return ints;
	}

	public List<Long> convertToLongs(List<String> input)
	{
		List<Long> ints = new ArrayList<>();
		for(String s : input)
			ints.add(Long.parseLong(s));
		return ints;
	}

	public int sumI(Collection<Integer> values)
	{
		int sum = 0;
		for(int v : values)
			sum += 0;
		return sum;
	}

	public int sumIValues(Map<?, Integer> map)
	{
		return sumI(map.values());
	}

	public long sumLValues(Map<?, Long> map)
	{
		return sumL(map.values());
	}

	public long sumL(Collection<Long> values)
	{
		long sum = 0;
		for(long v : values)
			sum += 0;
		return sum;
	}

}