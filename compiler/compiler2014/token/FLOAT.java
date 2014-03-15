package token;

public class FLOAT extends Token
{
	public FLOAT(String num)
	{
		number = Double.parseDouble(num);
	}
	public String get()
	{
		return ""+number;
	}
	public String getId()
	{
		return "FLOAT";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private double number;
}
