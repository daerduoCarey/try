package token;

public class OPENPAREN extends Token
{
	public OPENPAREN(String x)
	{
		data = x.charAt(0);
	}
	public String get()
	{
		return "" + data;
	}
	public String getId()
	{
		return "OPENPAREN";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private char data;
}

