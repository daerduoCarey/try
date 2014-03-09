import java.util.*;

public class obj
{
	public static void main(String[] argv)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=1;i<=10;++i) list.add(new Integer(i));
		for(int i=10;i>=1;--i) list.add(new Integer(i));
		Integer[] out = new Integer[list.size()];
		list.toArray(out);
		for(Integer n:list) System.out.print(n);
	}
}

