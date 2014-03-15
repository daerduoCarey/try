import java.util.*;
import java.io.*;
import token.*;
import main.*;

public class test
{
	public static void main(String[] args) throws IOException
	{
		lexer a = new lexer(args[0]);
		a.run();
		a.output();
	}
}
