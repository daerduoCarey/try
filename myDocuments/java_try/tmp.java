import java.io.*;
import java.util.*;

/**
 * @author mkch
 * @version 1.0
 * @see <a href="http://icarey.cc">reference</a>
 */
public class tmp
{
	public static void main(String[] argv)
	{
		int[][] a=new int [10][];
		for(int i=0;i<a.length;++i)
			a[i]=new int[i+1];
		System.out.print(Arrays.deepToString(a));
	}
}
