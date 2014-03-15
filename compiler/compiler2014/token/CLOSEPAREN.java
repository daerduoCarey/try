package token;

public class CLOSEPAREN extends Token
{
	public CLOSEPAREN(String x)
	{
		data = x.charAt(0);
	}
	public String get()
	{
		return "" + data;
	}
	public String getId()
	{
		return "CLOSEPAREN";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private char data;
}

