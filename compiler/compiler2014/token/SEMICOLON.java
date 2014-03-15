package token;

public class SEMICOLON extends Token
{
	public SEMICOLON()
	{
	}
	public String get()
	{
		return "";
	}
	public String getId()
	{
		return "SEMICOLON";
	}
	public String toString()
	{
		return "<"+getId()+">";
	}
}

