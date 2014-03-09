import java.util.*;

public class InterfaceTest 
{
	public static void main(String[] args)
	{
		Int a = new Int[10];
		for(int i=0;i<a.length;++i)
		{
			a[i] = new Int(10-i);
		}
		Arrays.sort(a);
		for(Int i:a)
		{
			System.out.print(i);
		}
	}
}

public class Int extends Integer implements Comparable<Int>
{
	public Int(int x)
	{
		super(x);
	}
	int compareTo(Int other)
	{
		return this-other;
	}
}
