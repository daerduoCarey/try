import java.util.*;
import java.io.*;

class node
{
	public int i;
	public node a;
	public node(int ii,node aa)
	{
		i=ii;
		a=aa;
	}
}

public class ClassTest
{
	public static void main(String[] argv)
	{
		node a=new node(1,null);
		node b=new node(2,a);
		System.out.println(b.a.i);
	}
}

