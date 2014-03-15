package token;

public class QUOTATION extends Token
{
	public QUOTATION(String data)
	{
		quotation = data;
	}
	public String get()
	{
		return quotation;
	}
	public String getId()
	{
		return "QUOTATION";
	}
	public String toString()
	{
		return "<"+getId()+", "+get()+">";
	}

	private String quotation;
}
