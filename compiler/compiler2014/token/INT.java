package token;

public class INT extends Token
{
	public INT(String num)
	{
		number = Integer.parseInt(num);
	}
	public String get()
	{
		return ""+number;
	}
	public String getId()
	{
		return "INT";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private int number;
}

