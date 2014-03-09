import java.util.*;

public class MaxTest
{
	public static void main(String[] args)
	{
		System.out.println(max(1,2,3,4,5));
	}
	public static int max(int... a)
	{
		int tmp = a[0];
		for(int ele:a) 
		{
			if(ele>tmp) tmp=ele;
		}
		return tmp;
	}
}

