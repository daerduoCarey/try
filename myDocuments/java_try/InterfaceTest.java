import java.util.*;

public class InterfaceTest 
{
	public static void main(String[] args)
	{
		Int[] a = new Int[10];
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

class Int implements Comparable<Int>
{
	public Int(int x)
	{
		number=x;
	}
	public int compareTo(Int other)
	{
		return number-other.getNumber();
	}
	public int getNumber()
	{
		return number;
	}
	public String toString()
	{
		return ""+number;
	}
	
	private int number;
}
