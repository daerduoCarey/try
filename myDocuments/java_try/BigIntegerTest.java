import java.util.*;
import java.math.*;

public class BigIntegerTest
{
	public static void main(String[] argv)
	{
		int n=1000;
		BigInteger a=BigInteger.valueOf(1);
		for(int i=1;i<=n;++i)
			a=a.multiply(BigInteger.valueOf(i));
		System.out.println(a);
	}
}
